package nicusha.gadget_lab.data;

import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.data.loot.LootGenerator;
import nicusha.gadget_lab.data.recipe.runner.CraftingGeneratorRunner;
import nicusha.gadget_lab.data.tag.BlockTagGen;
import nicusha.gadget_lab.data.tag.ItemTagGen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GadgetLab.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GadgetLabDataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new BlockStateGen(output, helper));
        generator.addProvider(true, new ItemModelGen(output, helper));
        generator.addProvider(true, new LootGenerator(output, lookupProvider));

        BlockTagGen blocktags = new BlockTagGen(output, lookupProvider, helper);
        event.getGenerator().addProvider(true, blocktags);
        event.getGenerator().addProvider(true, new ItemTagGen(output, lookupProvider, blocktags.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(true, new CraftingGeneratorRunner(output, lookupProvider));

        generator.addProvider(true, new LangGen(output));

        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Resources for Gadget Lab"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));

        generator.addProvider(true, new AdditionalGen(output));


    }
}
