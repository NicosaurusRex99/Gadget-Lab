package nicusha.gadget_lab.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LaunchPad extends Block {
    private static final VoxelShape SHAPE = Shapes.or(Block.box(2, 0, 2, 14, 1, 14), Block.box(5, 1, 5, 11, 2, 11), Block.box(4, 1, 4, 12, 2, 12));

    public LaunchPad() {
        super(Block.Properties.of().strength(1.5F, 0.3F).pushReaction(PushReaction.DESTROY).randomTicks().jumpFactor(2).sound(SoundType.SLIME_BLOCK));
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        entity.setDeltaMovement(entity.getDeltaMovement().x, 1.25, entity.getDeltaMovement().z);

        if (entity instanceof ServerPlayer) {
            entity.fallDistance = 0.0F;
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.create(SHAPE.bounds().inflate(0.3D));
    }
}