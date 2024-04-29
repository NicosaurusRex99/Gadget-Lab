package nicusha.gadget_lab.registry;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;
import nicusha.gadget_lab.blocks.*;
import nicusha.gadget_lab.client.PedestalBlockRenderer;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredBlock<Block> pedestal = registerBlock("pedestal", () -> new Pedestal());
    public static final DeferredBlock<Block> quicksand = registerBlock("quicksand", () -> new Quicksand());
    public static final DeferredBlock<Block> unstable_obsidian = registerBlock("unstable_obsidian", () -> new UnstableObsidian());


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL = registerBlockEntity("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockRegistry.pedestal.get()).build(null));


    @OnlyIn(Dist.CLIENT)
    public static void renderTiles(){
        BlockEntityRenderers.register(BlockRegistry.PEDESTAL.get(), PedestalBlockRenderer::new);
    }

    private static <T extends Block> DeferredBlock<T> registerTablessBlock(String registryName, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }
    private static <T extends Block> DeferredBlock<T> registerBlock(String registryName, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerBlockEntity(String registryName, Supplier<BlockEntityType<T>> tile) {
        return BLOCK_ENTITIES.register(registryName, tile);
    }
}
