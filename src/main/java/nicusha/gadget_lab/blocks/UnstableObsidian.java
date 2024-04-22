package nicusha.gadget_lab.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class UnstableObsidian extends IceBlock {
    public static final MapCodec<UnstableObsidian> CODEC = simpleCodec(UnstableObsidian::new);
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final int NEIGHBORS_TO_AGE = 4;
    private static final int NEIGHBORS_TO_MELT = 2;

    public MapCodec<UnstableObsidian> codec() {
        return CODEC;
    }

    public UnstableObsidian() {
        super(Properties.ofFullCopy(Blocks.OBSIDIAN));
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }
    public UnstableObsidian(BlockBehaviour.Properties blockProperties) {
        super(blockProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }

    public void randomTick(BlockState currentState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        this.tick(currentState, serverLevel, blockPos, randomSource);
    }

    public void tick(BlockState currentState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if ((randomSource.nextInt(3) == 0 || this.fewerNeigboursThan(serverLevel, blockPos, NEIGHBORS_TO_AGE)) && serverLevel.getMaxLocalRawBrightness(blockPos) > 11 - currentState.getValue(AGE) - currentState.getLightBlock(serverLevel, blockPos) && this.slightlyMelt(currentState, serverLevel, blockPos)) {
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

            for(Direction direction : Direction.values()) {
                mutableBlockPos.setWithOffset(blockPos, direction);
                BlockState neighborBlockState = serverLevel.getBlockState(mutableBlockPos);
                if (neighborBlockState.is(this) && !this.slightlyMelt(neighborBlockState, serverLevel, mutableBlockPos)) {
                    serverLevel.scheduleTick(mutableBlockPos, this, Mth.nextInt(randomSource, 20, 40));
                }
            }

        } else {
            serverLevel.scheduleTick(blockPos, this, Mth.nextInt(randomSource, 20, 40));
        }
    }
    public static BlockState meltsInto() {
        return Blocks.LAVA.defaultBlockState();
    }
    protected void melt(BlockState blockState, Level level, BlockPos pos) {
            level.setBlockAndUpdate(pos, meltsInto());
            level.neighborChanged(pos, meltsInto().getBlock(), pos);
    }
    private boolean slightlyMelt(BlockState currentState, Level level, BlockPos blockPos) {
        int age = currentState.getValue(AGE);
        if (age < MAX_AGE) {
            level.setBlock(blockPos, currentState.setValue(AGE, Integer.valueOf(age + 1)), 2);
            return false;
        } else {
            this.melt(currentState, level, blockPos);
            return true;
        }
    }

    public void neighborChanged(BlockState currentState, Level level, BlockPos blockPos, Block neighborBlock, BlockPos neighborBlockPos, boolean isMoving) {
        if (neighborBlock.defaultBlockState().is(this) && this.fewerNeigboursThan(level, blockPos, NEIGHBORS_TO_MELT)) {
            this.melt(currentState, level, blockPos);
        }

        super.neighborChanged(currentState, level, blockPos, neighborBlock, neighborBlockPos, isMoving);
    }

    private boolean fewerNeigboursThan(BlockGetter blockGetter, BlockPos blockPos, int neighborCountThreshold) {
        int neighborCount = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            mutableBlockPos.setWithOffset(blockPos, direction);
            if (blockGetter.getBlockState(mutableBlockPos).is(this)) {
                ++neighborCount;
                if (neighborCount >= neighborCountThreshold) {
                    return false;
                }
            }
        }

        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return ItemStack.EMPTY;
    }
}
