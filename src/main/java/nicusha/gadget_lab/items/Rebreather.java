package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.items.materials.ArmourMaterials;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.function.Consumer;

@EventBusSubscriber(modid = GadgetLab.MODID)
public class Rebreather extends Item {
    public Rebreather() {
        super(new Item.Properties().durability(6000).humanoidArmor(ArmourMaterials.REBREATHER, ArmorType.HELMET).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(GadgetLab.MODID, "rebreather"))));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.rebreather.asItem())) {
            if (player.isInWater()) {
                ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
                itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.SPEED, 3, 0, false, false));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("tooltip." + itemStack.getItem().getDescriptionId()));
    }

}