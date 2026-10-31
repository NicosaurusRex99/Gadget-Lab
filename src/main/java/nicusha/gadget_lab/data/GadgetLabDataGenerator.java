
package nicusha.gadget_lab.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.data.loot.ModLootGenerator;
import nicusha.gadget_lab.data.tag.ModEnchantmentTagProvider;
import nicusha.gadget_lab.data.tag.ModItemTagProvider;
import nicusha.gadget_lab.data.tag.ModBlockTagProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class GadgetLabDataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();
        CompletableFuture<net.minecraft.core.HolderLookup.Provider> worldLookup = event.getWorldLookupProvider();
        CompletableFuture<net.minecraft.core.HolderLookup.Provider> reloadableLookup = event.getReloadableLookupProvider();
        generator.addProvider(true, new ModLangProvider(output, "en_us"));
        String[] popularLanguages = {"es_es", "es_mx", "fr_fr", "fr_ca", "de_de", "zh_cn", "zh_tw", "ja_jp", "ru_ru", "pt_br", "pt_pt", "it_it", "ko_kr", "pl_pl", "nl_nl", "tr_tr", "uk_ua", "cs_cz", "sv_se", "hu_hu", "fi_fi", "da_dk", "nb_no", "el_gr", "ro_ro", "id_id", "vi_vn", "th_th", "hi_in"};
        for (String lang : popularLanguages) {
            generator.addProvider(true, new ModLangProvider(output, lang));
        }
        event.createProvider(ModModelProvider::new);
        generator.addProvider(true, new ModEquipmentProvider(output));
        event.createBlockAndItemTags(ModBlockTagProvider::new, (packOutput, lookup, blockTags) -> new ModItemTagProvider(packOutput, lookup));
        RegistrySetBuilder worldRegistryBuilder = new RegistrySetBuilder().add(Registries.ENCHANTMENT, ModEnchantmentProvider::bootstrap);
        DatapackBuiltinEntriesProvider worldEntries = DatapackBuiltinEntriesProvider.forWorldLayer(output, "Gadget Lab World Entries", worldLookup, worldRegistryBuilder, Set.of(GadgetLab.MODID));
        generator.addProvider(true, worldEntries);
        RegistrySetBuilder reloadableRegistryBuilder = new RegistrySetBuilder().add(Registries.LOOT_TABLE, new ModLootGenerator()).add(RecipeProvider.asBootstrap(ModRecipeProvider::new));
        DatapackBuiltinEntriesProvider reloadableEntries = DatapackBuiltinEntriesProvider.forReloadableLayer(output, "Gadget Lab Reloadable Entries", worldLookup, reloadableLookup, reloadableRegistryBuilder, Set.of(GadgetLab.MODID));
        generator.addProvider(true, reloadableEntries);
        generator.addProvider(true, new ModEnchantmentTagProvider(output, worldEntries.getRegistryProvider()));
    }
}
