package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.ItemRegistry;

import static nicusha.gadget_lab.GadgetLab.MODID;


@EventBusSubscriber(modid = GadgetLab.MODID)
public class LuckyAmulet extends ItemMod {
    public LuckyAmulet() {
        super(new Item.Properties().durability(500).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "lucky_amulet"))));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player != null && player.isAlive()) {
            ItemStack fortuneAmulet = new ItemStack(ItemRegistry.lucky_amulet.asItem());
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