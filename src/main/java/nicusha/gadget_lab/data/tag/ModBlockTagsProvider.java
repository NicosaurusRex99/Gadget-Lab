package nicusha.gadget_lab.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import nicusha.gadget_lab.registry.BlockRegistry;

import java.util.concurrent.CompletableFuture;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public static final TagKey<Block> VEGETATION = customTag("vegetation");
    public static final TagKey<Block> SAND = commonTag("sands");

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(VEGETATION)
                .addTags(BlockTags.FLOWERS, BlockTags.CROPS)
                .add(
                        Blocks.SHORT_GRASS.builtInRegistryHolder().getKey(),
                        Blocks.TALL_GRASS.builtInRegistryHolder().getKey(),
                        Blocks.DEAD_BUSH.builtInRegistryHolder().getKey()
                );

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(BlockRegistry.quicksand.getKey());
        tag(SAND).add(BlockRegistry.quicksand.getKey());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockRegistry.unstable_obsidian.getKey(),
                BlockRegistry.pedestal.getKey(),
                BlockRegistry.white.getKey(),
                BlockRegistry.orange.getKey(),
                BlockRegistry.magenta.getKey(),
                BlockRegistry.light_blue.getKey(),
                BlockRegistry.yellow.getKey(),
                BlockRegistry.light_green.getKey(),
                BlockRegistry.pink.getKey(),
                BlockRegistry.gray.getKey(),
                BlockRegistry.light_gray.getKey(),
                BlockRegistry.cyan.getKey(),
                BlockRegistry.purple.getKey(),
                BlockRegistry.blue.getKey(),
                BlockRegistry.brown.getKey(),
                BlockRegistry.green.getKey(),
                BlockRegistry.red.getKey(),
                BlockRegistry.black.getKey()
        );

        // Automatic tag assignment for generic block types
        for (var entry : BlockRegistry.BLOCKS.getEntries()) {
            Block b = entry.get();
            var key = b.builtInRegistryHolder().getKey();

            if (b instanceof StairBlock) tag(BlockTags.STAIRS).add(key);
            if (b instanceof SlabBlock) tag(BlockTags.SLABS).add(key);
            if (b instanceof ButtonBlock) tag(BlockTags.BUTTONS).add(key);
            if (b instanceof PressurePlateBlock) tag(BlockTags.PRESSURE_PLATES).add(key);
            if (b instanceof WallBlock) tag(BlockTags.WALLS).add(key);
            if (b instanceof DoorBlock) tag(BlockTags.DOORS).add(key);
            if (b instanceof TrapDoorBlock) tag(BlockTags.TRAPDOORS).add(key);
            if (b instanceof FenceBlock) tag(BlockTags.FENCES).add(key);
            if (b instanceof FenceGateBlock) tag(BlockTags.FENCE_GATES).add(key);
        }
    }

    private static TagKey<Block> commonTag(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", name));
    }

    private static TagKey<Block> minecraftTag(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(name));
    }

    private static TagKey<Block> customTag(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, name));
    }
}