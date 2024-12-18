package nicusha.gadget_lab.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nicusha.gadget_lab.GadgetLab;
import nicusha.gadget_lab.registry.SoundRegistry;

import java.util.List;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class HerbicideSpray extends ItemMod {

    public HerbicideSpray() {
        super(new Properties().stacksTo(1).durability(16).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "herbicide_spray"))));
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
            if (targetState.is(BlockTags.create(ResourceLocation.fromNamespaceAndPath(GadgetLab.MODID, "vegetation")))) {
                world.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.PASS;
    }


}
