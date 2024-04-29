package nicusha.gadget_lab.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import nicusha.gadget_lab.block_entities.PedestalBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class Pedestal extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");

    public Pedestal() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_ITEM, false));
    }

    public Pedestal(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_ITEM, false));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PedestalBlockEntity) {
            PedestalBlockEntity pedestal = (PedestalBlockEntity) blockEntity;
            pedestal.interact(player);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_ITEM);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        boolean hasItem = false;
        if (blockEntity instanceof PedestalBlockEntity) {
            hasItem = ((PedestalBlockEntity) blockEntity).hasItem();
        }
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(HAS_ITEM, hasItem);
    }

    @Override
    public void onRemove(BlockState state, net.minecraft.world.level.Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof PedestalBlockEntity) {
                ((PedestalBlockEntity)tileEntity).dropItem(world, pos);
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    public static final MapCodec<Pedestal> CODEC = simpleCodec(Pedestal::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip."+stack.getDescriptionId()));
    }
}