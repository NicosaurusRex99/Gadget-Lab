package nicusha.gadget_lab.registry;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;
import nicusha.gadget_lab.blocks.*;
import nicusha.gadget_lab.client.PedestalBlockRenderer;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final RegistryObject<Block> pedestal = registerBlock("pedestal", () -> new Pedestal());
    public static final RegistryObject<Block> quicksand = registerBlock("quicksand", () -> new Quicksand());


    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = registerBlockEntity("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, BlockRegistry.pedestal.get()).build(null));


    @OnlyIn(Dist.CLIENT)
    public static void renderTiles(){
        BlockEntityRenderers.register(BlockRegistry.PEDESTAL.get(), PedestalBlockRenderer::new);
    }

    private static <T extends Block> RegistryObject<T> registerTablessBlock(String registryName, Supplier<T> block, Rarity rarity) {
        RegistryObject<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties().rarity(rarity)));
        return registeredBlock;
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> block) {
        RegistryObject<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntity(String registryName, Supplier<BlockEntityType<T>> tile) {
        Main.LOGGER.info(registryName + " has registered");
        return BLOCK_ENTITIES.register(registryName, tile);
    }
}
