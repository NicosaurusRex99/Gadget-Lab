package nicusha.gadget_lab.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.registry.BlockRegistry;

import java.util.Collections;
import java.util.stream.Collectors;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public ModBlockLootProvider(LootTableSubProvider.Context context) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), context);
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
