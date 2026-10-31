package nicusha.gadget_lab.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import nicusha.gadget_lab.enchantment.PlaceTorchEffect;

import static nicusha.gadget_lab.registry.EnchantmentRegistry.LUMINS;


public class ModEnchantmentProvider {

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        register(context, LUMINS, Enchantment.enchantment(Enchantment.definition(context.lookup(Registries.ITEM).getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), 2,  1, Enchantment.constantCost(1), Enchantment.constantCost(41), 4, EquipmentSlotGroup.HEAD)).withEffect(EnchantmentEffectComponents.LOCATION_CHANGED, PlaceTorchEffect.DEFAULT));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
