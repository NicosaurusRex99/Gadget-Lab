package nicusha.gadget_lab.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.BlockRegistry;

import java.util.concurrent.CompletableFuture;

public class BlockTagGen extends IntrinsicHolderTagsProvider<Block> {
    public static final TagKey<Block> VEGETATION = create("vegetation");
    public static final TagKey<Block> PICKAXE = createModded("minecraft","mineable/pickaxe");
    public static final TagKey<Block> SHOVEL = createModded("minecraft","mineable/shovel");
    public static final TagKey<Block> SAND = createModded("minecraft","sand");

    public BlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.BLOCK, future, block -> block.builtInRegistryHolder().key(), Main.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(VEGETATION).addTags(BlockTags.FLOWERS, BlockTags.CROPS).add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.DEAD_BUSH);
        tag(SHOVEL).add(BlockRegistry.quicksand.get());
        tag(SAND).add(BlockRegistry.quicksand.get());
        tag(PICKAXE).add(BlockRegistry.unstable_obsidian.get(), BlockRegistry.pedestal.get(), BlockRegistry.white.get(), BlockRegistry.orange.get(), BlockRegistry.magenta.get(), BlockRegistry.light_blue.get(), BlockRegistry.yellow.get(), BlockRegistry.light_green.get(), BlockRegistry.pink.get(), BlockRegistry.gray.get(), BlockRegistry.light_gray.get(), BlockRegistry.cyan.get(), BlockRegistry.purple.get(), BlockRegistry.blue.get(), BlockRegistry.brown.get(), BlockRegistry.green.get(), BlockRegistry.red.get(), BlockRegistry.black.get());
    }

    private static TagKey<Block> createModded(String modid, String tagName) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modid, tagName));
    }

    public static TagKey<Block> create(String tagName) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Main.MODID, tagName));
    }

    public static TagKey<Block> makeCommonTag(String tagName) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", tagName));
    }

    @Override
    public String getName() {
        return "Gadget Lab Block Tags";
    }
}
