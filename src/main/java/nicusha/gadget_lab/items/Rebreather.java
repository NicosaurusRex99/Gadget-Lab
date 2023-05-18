package nicusha.gadget_lab.items;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.enums.ArmorTypes;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Rebreather extends ArmorItem {
    public Rebreather() {
        super(ArmorTypes.REBREATHER, Type.HELMET, new Item.Properties()
                .rarity(Rarity.RARE).durability(6000));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END && player.isInWater()) {
            ItemStack itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
            itemStack.hurtAndBreak(1, player, (player1) -> {
                player1.broadcastBreakEvent(player.getUsedItemHand());
            });
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 3, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3, 0, false, false));
        }
    }
}