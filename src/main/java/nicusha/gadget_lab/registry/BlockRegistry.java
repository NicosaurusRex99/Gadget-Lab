package nicusha.gadget_lab.registry;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.*;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;
import nicusha.gadget_lab.blocks.*;
import nicusha.gadget_lab.client.PedestalBlockRenderer;

import java.util.function.Supplier;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredBlock<Block> pedestal = registerBlock("pedestal", Pedestal::new);
    public static final DeferredBlock<Block> quicksand = registerBlock("quicksand", Quicksand::new);
    public static final DeferredBlock<Block> unstable_obsidian = registerBlock("unstable_obsidian", UnstableObsidian::new);
    public static final DeferredBlock<Block> launch_pad = registerBlock("launch_pad", LaunchPad::new);

    public static final DeferredBlock<Block> white = registerBlock("white", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "white")))));
    public static final DeferredBlock<Block> orange = registerBlock("orange", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "orange")))));
    public static final DeferredBlock<Block> magenta = registerBlock("magenta", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "magenta")))));
    public static final DeferredBlock<Block> light_blue = registerBlock("light_blue", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "light_blue")))));
    public static final DeferredBlock<Block> yellow = registerBlock("yellow", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "yellow")))));
    public static final DeferredBlock<Block> light_green = registerBlock("light_green", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "light_green")))));
    public static final DeferredBlock<Block> pink = registerBlock("pink", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "pink")))));
    public static final DeferredBlock<Block> gray = registerBlock("gray", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "gray")))));
    public static final DeferredBlock<Block> light_gray = registerBlock("light_gray", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "light_gray")))));
    public static final DeferredBlock<Block> cyan = registerBlock("cyan", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "cyan")))));
    public static final DeferredBlock<Block> purple = registerBlock("purple", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "purple")))));
    public static final DeferredBlock<Block> blue = registerBlock("blue", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "blue")))));
    public static final DeferredBlock<Block> brown = registerBlock("brown", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "brown")))));
    public static final DeferredBlock<Block> green = registerBlock("green", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "green")))));
    public static final DeferredBlock<Block> red = registerBlock("red", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "red")))));
    public static final DeferredBlock<Block> black = registerBlock("black", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.5F, 6.0F).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "black")))));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL = BLOCK_ENTITIES.register("pedestal", () -> new BlockEntityType<>(PedestalBlockEntity::new, BlockRegistry.pedestal.get()));

    public static void renderTiles(){
        BlockEntityRenderers.register(BlockRegistry.PEDESTAL.get(), PedestalBlockRenderer::new);
    }

    private static <T extends Block> DeferredBlock<T> registerTablessBlock(String registryName, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, registryName)))));
        return registeredBlock;
    }
    private static <T extends Block> DeferredBlock<T> registerBlock(String registryName, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, registryName)))));
        return registeredBlock;
    }

    private static <T extends BlockEntityType<?>> DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> registerBlockEntity(String registryName, Supplier<BlockEntityType<?>> tile) {
                return BLOCK_ENTITIES.register(registryName, tile);
    }
}
