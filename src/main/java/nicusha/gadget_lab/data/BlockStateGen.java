package nicusha.gadget_lab.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.blocks.LaunchPad;
import nicusha.gadget_lab.blocks.Pedestal;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.registry.BlockRegistry;

import java.util.Objects;

public class BlockStateGen extends BlockStateProvider {

    public BlockStateGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Main.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (var regObj : BlockRegistry.BLOCKS.getEntries()) {
            if (regObj.get() instanceof Block && (!(regObj.get() instanceof LaunchPad)) && (!(regObj.get() instanceof Pedestal)) && (!(regObj.get() instanceof UnstableObsidian))) {
                    blockItem(regObj.getId());
                }
        }
    }

    private void blockItem(ResourceLocation blockRegistryObject) {
        simpleBlock(BuiltInRegistries.BLOCK.get(blockRegistryObject).get().value(), new ModelFile.UncheckedModelFile(Main.MODID + ":block/"+ Objects.requireNonNull(blockRegistryObject).getPath()));
    }
}
