package nicusha.gadget_lab.registry;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.*;
import nicusha.gadget_lab.enchantments.MagmaWalkerEnchantment;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);
    public static final RegistryObject<Enchantment> MAGMA_WALKER = register("magma_walker", () -> new MagmaWalkerEnchantment(Enchantment.Rarity.RARE));


    private static <T extends Enchantment> RegistryObject<T> register(String registryName, Supplier<T> enchant) {
        RegistryObject<T> enchantment = ENCHANTMENTS.register(registryName, enchant);
        return enchantment;
    }
}
