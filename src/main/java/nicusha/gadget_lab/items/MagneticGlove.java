package nicusha.gadget_lab.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class MagneticGlove extends ItemMod {

    public MagneticGlove() {
        super(new Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, "magnetic_glove"))));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (!level.isClientSide() && owner instanceof Player) {
            Player player = (Player) owner;
            double range = 5.0;
            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();
            if (!player.isCrouching()) {
                List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(range));
                for (ItemEntity item : items) {
                    if (!item.isAlive() || item == owner) continue;

                    double dx = playerX - item.getX();
                    double dy = playerY - item.getY();
                    double dz = playerZ - item.getZ();
                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (distance <= range) {
                        dx /= distance;
                        dy /= distance;
                        dz /= distance;
                        double acceleration = Math.min(0.2, distance / range);
                        item.setDeltaMovement(item.getDeltaMovement().add(dx * acceleration, dy * acceleration, dz * acceleration));
                    }
                }
            }
        }
    }
}