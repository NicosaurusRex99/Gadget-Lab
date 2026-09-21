package nicusha.gadget_lab.data;

import net.minecraft.core.HolderLookup;
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
import nicusha.gadget_lab.data.tag.ModItemTagProvider;
import nicusha.gadget_lab.data.tag.ModBlockTagProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class GadgetLabDataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> world = event.getWorldLookupProvider();
        CompletableFuture<HolderLookup.Provider> reloadable = event.getReloadableLookupProvider();
        DataGenerator generator = event.getGenerator();

        generator.addProvider(true, new ModLangProvider(event.getGenerator().getPackOutput(), "en_us"));
//        String[] popularLanguages = {"es_es", "fr_fr", "de_de", "zh_cn", "ja_jp", "ru_ru", "hi_in", "ar_sa", "th_th", "tk_ph", "sv_se", "pt_pt", "it_it"};
//        for (String lang : popularLanguages) {
//            generator.addProvider(true, new ModLangProvider(event.getGenerator().getPackOutput(), lang));
//        }
        event.createProvider(ModModelProvider::new);
        generator.addProvider(true, new ModEquipmentProvider(output));
        event.createBlockAndItemTags(ModBlockTagProvider::new, (packOutput, lookup, blockTags) -> new ModItemTagProvider(packOutput, lookup));
        event.addProvider(DatapackBuiltinEntriesProvider.forReloadableLayer(output, "Gadget Lab Reloadable", world, reloadable, new RegistrySetBuilder().add(Registries.LOOT_TABLE, new ModLootGenerator()), Set.of(GadgetLab.MODID)));

        generator.addProvider(true, DatapackBuiltinEntriesProvider.forReloadableLayer(output, "Gadget Lab Recipes", world, reloadable, new RegistrySetBuilder().add(RecipeProvider.asBootstrap(ModRecipeProvider::new)), Set.of(GadgetLab.MODID)));


    }
}
