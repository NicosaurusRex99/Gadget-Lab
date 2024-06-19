package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nicusha.gadget_lab.Main;
import nicusha.gadget_lab.registry.SoundRegistry;

import java.util.List;

public class HerbicideSpray extends ItemMod {

    public HerbicideSpray() {
        super(new Properties().stacksTo(1).durability(16));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        context.getPlayer().playSound(SoundRegistry.SPRAY.get(), 0.8F, 0.8F);
        if (!world.isClientSide) {
            clearVegetation(world, pos);
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getPlayer().getUsedItemHand()));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void clearVegetation(Level world, BlockPos pos) {
        AABB area = new AABB(pos).inflate(16);
        List<BlockPos> positions = BlockPos.betweenClosedStream(area).map(BlockPos::immutable).toList();

        for (BlockPos targetPos : positions) {
            BlockState targetState = world.getBlockState(targetPos);
            if (targetState.is(BlockTags.create(ResourceLocation.fromNamespaceAndPath(Main.MODID, "vegetation")))) {
                world.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.pass(itemstack);
    }
}
