package nicusha.gadget_lab.events;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.enchantments.MagmaWalkerEnchantment;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagmaWalkerEvent {

    @SubscribeEvent
    public static void onEntityMoved(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        BlockPos pos = new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
        int radius = 3;
        MagmaWalkerEnchantment.onEntityMoved(entity, level, pos, radius);
    }
}