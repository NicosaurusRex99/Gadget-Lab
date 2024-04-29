package nicusha.gadget_lab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import java.util.List;

public class GravityBoots extends ArmorItem {

    public GravityBoots() {
        //TODO - Gravity boots
        super(ArmorMaterials.ARMADILLO, Type.BOOTS, new Item.Properties().stacksTo(1));
//        super(ArmorTypes.GRAVITY_BOOTS, Type.BOOTS, new Item.Properties().stacksTo(1));
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        Player player = event.getEntity();
//        if (player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.gravity_boots.get())) {
//            player.fallDistance = 0;
//            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 3, 2, false, false, false));
//        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + stack.getDescriptionId()));
    }

}
