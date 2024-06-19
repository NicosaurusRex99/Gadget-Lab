package nicusha.gadget_lab.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.*;

import java.util.function.Supplier;

import static nicusha.gadget_lab.Main.MODID;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, MODID);


    private static <T extends Enchantment> DeferredHolder<Enchantment, T> register(String registryName, Supplier<T> enchant) {
        DeferredHolder<Enchantment, T> enchantment = ENCHANTMENTS.register(registryName, enchant);
        return enchantment;
    }
}
