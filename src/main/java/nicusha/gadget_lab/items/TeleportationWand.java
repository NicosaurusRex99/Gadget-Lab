package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

public class TeleportationWand extends ItemMod {

    public TeleportationWand() {
        super(new Item.Properties().durability(512));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int maxTeleportDistance = 32;
        double teleportDistance = 0;
        double maxTeleportDistanceSq = maxTeleportDistance * maxTeleportDistance;

        Vec3 eyePosition = player.getEyePosition(1);
        Vec3 viewVector = player.getViewVector(1);
        Vec3 targetPosition = eyePosition.add(viewVector.x * maxTeleportDistance, viewVector.y * maxTeleportDistance, viewVector.z * maxTeleportDistance);
        BlockHitResult targetBlockHit = player.level().clip(new ClipContext(eyePosition, targetPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        if (targetBlockHit.getType() == HitResult.Type.BLOCK) {
            double distanceSq = eyePosition.distanceToSqr(targetBlockHit.getLocation());
            if (distanceSq <= maxTeleportDistanceSq) {
                teleportDistance = Math.sqrt(distanceSq) - 1;
            }
        } else {
            teleportDistance = maxTeleportDistance;
        }

        Vec3 teleportPosition = eyePosition.add(viewVector.x * teleportDistance, viewVector.y * teleportDistance, viewVector.z * teleportDistance);
        BlockPos teleportBlockPos = new BlockPos((int) teleportPosition.x, (int) teleportPosition.y, (int) teleportPosition.z);

        player.teleportTo(teleportBlockPos.getX() + 0.5, teleportBlockPos.getY() + 1, teleportBlockPos.getZ() + 0.5);

        if (!player.isCreative()) {
            stack.hurtAndBreak(1, player, (player1) -> {
                player1.broadcastBreakEvent(player.getUsedItemHand());
            });
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}