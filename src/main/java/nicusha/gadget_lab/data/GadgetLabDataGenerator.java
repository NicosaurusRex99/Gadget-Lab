package nicusha.gadget_lab.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.data.loot.LootGenerator;
import nicusha.gadget_lab.data.recipe.runner.CraftingGeneratorRunner;
import nicusha.gadget_lab.data.tag.ItemTagGen;
import nicusha.gadget_lab.data.tag.ModBlockTagsProvider;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class GadgetLabDataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModModelProvider(output));
        generator.addProvider(true, new LootGenerator(output, lookupProvider));
        ModBlockTagsProvider blocktags = new ModBlockTagsProvider(output, lookupProvider);
        event.getGenerator().addProvider(true, blocktags);
        event.getGenerator().addProvider(true, new ItemTagGen(output, lookupProvider));
        generator.addProvider(true, new CraftingGeneratorRunner(output, lookupProvider));
        generator.addProvider(true, new LangGen(output));
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(Component.literal("Resources for Gadget Lab"), new InclusiveRange<>(PackFormat.of(88, 512)))));
        generator.addProvider(true, new AdditionalGen(output));


    }
}
