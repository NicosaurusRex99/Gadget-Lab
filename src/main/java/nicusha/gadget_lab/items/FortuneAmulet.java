package nicusha.gadget_lab.items;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.ItemRegistry;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FortuneAmulet extends ItemMod {
    public FortuneAmulet() {
        super(new Item.Properties().durability(500));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player != null && player.isAlive()) {
            ItemStack fortuneAmulet = new ItemStack(ItemRegistry.fortune_amulet.get());
            if (player.inventory.contains(fortuneAmulet)) {
                if (!player.hasEffect(MobEffects.LUCK)) {
                    MobEffectInstance fortuneEffect = new MobEffectInstance(MobEffects.LUCK, 120, 2, false, false, false);
                    player.addEffect(fortuneEffect);
                    fortuneAmulet.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                }
            }
        }
    }

}