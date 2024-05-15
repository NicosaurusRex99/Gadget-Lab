package nicusha.gadget_lab.items;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.ItemRegistry;


@EventBusSubscriber(modid = Main.MODID)
public class LuckyAmulet extends ItemMod {
    public LuckyAmulet() {
        super(new Item.Properties().durability(500));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player != null && player.isAlive()) {
            ItemStack fortuneAmulet = new ItemStack(ItemRegistry.lucky_amulet.get());
            if (player.getInventory().contains(fortuneAmulet)) {
                if (!player.hasEffect(MobEffects.LUCK)) {
                    MobEffectInstance fortuneEffect = new MobEffectInstance(MobEffects.LUCK, 120, 2, false, false, false);
                    player.addEffect(fortuneEffect);
                    fortuneAmulet.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                }
            }
        }
    }

}