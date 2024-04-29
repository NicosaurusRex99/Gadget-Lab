package nicusha.gadget_lab.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import nicusha.gadget_lab.blocks.UnstableObsidian;
import nicusha.gadget_lab.registry.BlockRegistry;

public class MagmaWalkerEnchantment extends Enchantment {
    public MagmaWalkerEnchantment() {
        super(Enchantment.definition(ItemTags.FOOT_ARMOR_ENCHANTABLE, 2, 2, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 4, EquipmentSlot.FEET));
    }


    public boolean isTreasureOnly() {
        return true;
    }


    public static void onEntityMoved(LivingEntity entity, Level level, BlockPos pos, int radius) {
        if (entity.onGround()) {
            BlockState blockstate = BlockRegistry.unstable_obsidian.get().defaultBlockState();
            int i = Math.min(16, 2 + radius);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-i, -1, -i), pos.offset(i, -1, i))) {
                if (blockpos.closerToCenterThan(entity.position(), (double)i)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos);
                        if (blockstate2 == UnstableObsidian.meltsInto()&& blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !net.neoforged.neoforge.event.EventHooks.onBlockPlace(entity, net.neoforged.neoforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, blockstate);
                            level.scheduleTick(blockpos, BlockRegistry.unstable_obsidian.get(), Mth.nextInt(entity.getRandom(), 60, 120));
                        }
                    }
                }
            }

        }
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.DEPTH_STRIDER && enchantment != Enchantments.FROST_WALKER;
    }
}
