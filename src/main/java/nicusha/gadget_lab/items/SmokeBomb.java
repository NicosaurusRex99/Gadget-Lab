package nicusha.gadget_lab.items;

import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import nicusha.gadget_lab.entities.EntitySmokeBomb;
import nicusha.gadget_lab.registry.EntityRegistry;

public class SmokeBomb extends ItemMod {
    public SmokeBomb() {
        super(new Item.Properties().stacksTo(16));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            EntitySmokeBomb bomb = new EntitySmokeBomb(EntityRegistry.SMOKE_BOMB.get(), level, player);
            bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(bomb);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SMOKER_SMOKE, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative())
                player.getItemInHand(hand).shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(player.getUsedItemHand()), level.isClientSide());
    }

}
