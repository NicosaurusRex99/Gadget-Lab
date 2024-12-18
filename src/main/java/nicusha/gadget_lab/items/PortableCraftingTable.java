package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class PortableCraftingTable extends ItemMod {
    public PortableCraftingTable() {
        super(new Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "portable_crafting_table"))));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
            BlockPos pos = player.blockPosition();
            if(!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider((i, inventory, player1) -> {
                    return new CraftingMenu(i, inventory, ContainerLevelAccess.create(level, pos)) {
                        @Override
                        public boolean stillValid(Player player) {
                            return true;
                        }
                    };
                }, this.getName()));
                level.playSound(null, pos, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.FAIL;
    }
}
