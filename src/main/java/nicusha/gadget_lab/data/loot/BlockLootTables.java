package nicusha.gadget_lab.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.registry.BlockRegistry;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockLootTables extends BlockLootSubProvider {

    public BlockLootTables(HolderLookup.Provider provider) {
    super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
}

    @Override
    protected void generate() {
        for (var regObj : BlockRegistry.BLOCKS.getEntries()) {
            if ((!(regObj.get() instanceof UnstableObsidian))) {
                dropSelf(regObj.get());
            }
        }
        add(BlockRegistry.unstable_obsidian.get(), createSingleItemTable(Blocks.OBSIDIAN));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}
