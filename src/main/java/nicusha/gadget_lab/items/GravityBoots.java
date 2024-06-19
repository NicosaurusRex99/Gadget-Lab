package nicusha.gadget_lab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;
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
public class GravityBoots extends ArmorItem {

    public GravityBoots() {
        super(ArmourMaterials.GRAVITY_BOOTS, Type.BOOTS, new Item.Properties().stacksTo(1));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getInventory().armor.get(0).is(ItemRegistry.gravity_boots)) {
            player.fallDistance = 0;
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 3, 2, false, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + stack.getDescriptionId()));
    }

}
