package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class PortableCraftingTable extends Item {
    public PortableCraftingTable(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            BlockPos pos = player.blockPosition();
            if(!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider((i, inventory, player1) -> {
                    return new CraftingMenu(i, inventory, ContainerLevelAccess.create(level, pos)) {
                        @Override
                        public boolean stillValid(Player player) {
                            return true;
                        }
                    };
                }, this.getDescription()));
                level.playSound(null, pos, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResultHolder.fail(player.getItemInHand(hand));
    }


}
