package nicusha.gadget_lab.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nicusha.gadget_lab.enchantment.PlaceTorchEffect;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class LocationEffectRegistry {
    public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LOCATION_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, MODID);

    public static final DeferredHolder<MapCodec<? extends EnchantmentLocationBasedEffect>, MapCodec<PlaceTorchEffect>> PLACE_TORCH = LOCATION_EFFECTS.register("place_torch", () -> PlaceTorchEffect.CODEC);

}
