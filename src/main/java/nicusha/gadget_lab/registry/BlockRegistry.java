package nicusha.gadget_lab.registry;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);






    private static <T extends Block> RegistryObject<T> registerTablessBlock(String registryName, Supplier<T> block, Rarity rarity) {
        RegistryObject<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties().rarity(rarity)));
        return registeredBlock;
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, Rarity.COMMON);
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String registryName, Supplier<T> block, Rarity rarity) {
        RegistryObject<T> registeredBlock = BLOCKS.register(registryName, block);
        ItemRegistry.ITEMS.register(registryName, () -> new BlockItem(registeredBlock.get(), new Item.Properties().rarity(rarity)));
        return registeredBlock;
    }
}
