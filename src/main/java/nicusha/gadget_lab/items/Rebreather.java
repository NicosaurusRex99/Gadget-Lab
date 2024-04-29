package nicusha.gadget_lab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.enums.ArmorTypes;
import nicusha.gadget_lab.registry.ItemRegistry;

import java.util.List;

public class Rebreather extends ArmorItem {
    public Rebreather() {
        super(ArmorMaterials.ARMADILLO, Type.HELMET, new Item.Properties().durability(6000));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        //TODO - rebreather
//        Player player = event.player;
//        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.rebreather.get())) {
//            if (event.phase == TickEvent.Phase.END && player.isInWater()) {
//                ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
//                itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
//                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3, 0, false, false));
//                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 0, false, false));
//            }
//        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltip." + stack.getDescriptionId()));
    }

}