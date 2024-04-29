package nicusha.gadget_lab.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import nicusha.gadget_lab.enchantments.MagmaWalkerEnchantment;

public class MagmaWalkerEvent {

    @SubscribeEvent
    public static void onEntityMoved(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Level level = entity.level();
            BlockPos pos = new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
            int radius = 3;
            MagmaWalkerEnchantment.onEntityMoved(entity, level, pos, radius);
        }
    }
}