package nicusha.gadget_lab.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import nicusha.gadget_lab.Main;


public class Quicksand extends Block {
    public static final MapCodec<Quicksand> CODEC = simpleCodec(Quicksand::new);
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);

    public MapCodec<Quicksand> codec() {
        return CODEC;
    }

    public Quicksand() {
        super(Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    }
    public Quicksand(Properties properties) {
        super(properties);
    }

    public boolean skipRendering(BlockState currentState, BlockState newState, Direction direction) {
        return newState.is(this) ? true : super.skipRendering(currentState, newState, direction);
    }

    public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.empty();
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || level.getBlockState(entity.blockPosition().below()).is(this)) {
            entity.makeStuckInBlock(state, new Vec3((double)0.9F, 1.5D, (double)0.9F));
            if (level.isClientSide) {
                RandomSource randomSource = level.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && randomSource.nextBoolean()) {
                    level.addParticle(ParticleTypes.ASH, entity.getX(), (double)(pos.getY() + 1), entity.getZ(), (double)(Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }

        if (!level.isClientSide) {
            if (entity.isOnFire() && (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(level, pos)) {
                level.destroyBlock(pos, false);
            }

            entity.setSharedFlagOnFire(false);
        }

    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float distance) {
        if (!((double)distance < 4.0D) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = (double)distance < 7.0D ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null) {
                if (entity instanceof FallingBlockEntity) {
                    return FALLING_COLLISION_SHAPE;
                } else if (canEntityWalkOnQuicksand(entity)) {
                    return Shapes.empty();
                }
            }
        }
        return super.getCollisionShape(state, blockGetter, pos, context);
    }


    public VoxelShape getVisualShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public static boolean canEntityWalkOnQuicksand(Entity entity) {
        return !entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Main.MODID, "entity_walk_on_quicksand")));
    }

    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos pos, PathComputationType pathComputationType) {
        return true;
    }
}
