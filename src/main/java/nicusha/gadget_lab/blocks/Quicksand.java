package nicusha.gadget_lab.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;

import static nicusha.gadget_lab.GadgetLab.MODID;

public class Quicksand extends FallingBlock {
    public static final MapCodec<Quicksand> CODEC = simpleCodec(Quicksand::new);
    private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);

    public static final TagKey<EntityType<?>> WALK_ON_QUICKSAND_TAG = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MODID, "entity_walk_on_quicksand"));

    public MapCodec<Quicksand> codec() {
        return CODEC;
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }

    public Quicksand() {
        super(Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MODID, "quicksand"))));
    }

    public Quicksand(Properties properties) {
        super(properties);
    }

    public boolean skipRendering(BlockState currentState, BlockState newState, Direction direction) {
        return newState.is(this) || super.skipRendering(currentState, newState, direction);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (!(entity instanceof LivingEntity) || level.getBlockState(entity.blockPosition().below()).is(this)) {
            entity.makeStuckInBlock(state, new Vec3((double)0.9F, 1.5D, (double)0.9F));
            if (level.isClientSide()) {
                RandomSource randomSource = level.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && randomSource.nextBoolean()) {
                    level.addParticle(ParticleTypes.ASH, entity.getX(), (double)(pos.getY() + 1), entity.getZ(), (double)(Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(randomSource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }
        if (!level.isClientSide()) {
            if (entity.isOnFire() && (level.getServer().getGameRules().get(GameRules.MOB_GRIEFING) || entity instanceof Player) && entity.mayInteract((ServerLevel) level, pos)) {
                level.destroyBlock(pos, false);
            }
            entity.setSharedFlagOnFire(false);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double distance) {
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
        return !entity.getType().builtInRegistryHolder().is(WALK_ON_QUICKSAND_TAG);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return true;
    }
}