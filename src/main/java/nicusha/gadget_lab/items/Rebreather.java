package nicusha.gadget_lab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.items.materials.ArmourMaterials;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.List;

@EventBusSubscriber(modid = Main.MODID)
public class Rebreather extends ArmorItem {
    public Rebreather() {
        super(ArmourMaterials.REBREATHER, Type.HELMET, new Item.Properties().durability(6000));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.rebreather.get())) {
            if (player.isInWater()) {
                ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
                itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 0, false, false));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip." + stack.getDescriptionId()));
    }

}