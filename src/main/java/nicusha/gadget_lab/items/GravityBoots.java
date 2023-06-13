package nicusha.gadget_lab.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.enums.ArmorTypes;
import nicusha.gadget_lab.registry.ItemRegistry;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GravityBoots extends ArmorItem {

    public GravityBoots() {
        super(ArmorTypes.GRAVITY_BOOTS, Type.BOOTS, new Item.Properties().stacksTo(1));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.gravity_boots.get())) {
            player.fallDistance = 0;
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 3, 2, false, false, false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip." + stack.getDescriptionId()));
    }
}
