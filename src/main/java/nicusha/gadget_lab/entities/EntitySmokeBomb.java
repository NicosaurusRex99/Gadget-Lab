package nicusha.gadget_lab.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import nicusha.gadget_lab.registry.ItemRegistry;

public class EntitySmokeBomb extends ThrowableItemProjectile {
    private static final double SMOKE_RADIUS = 5.0;
    private int smokeTicks = 1200;
    LivingEntity owner;

    public EntitySmokeBomb(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public EntitySmokeBomb(EntityType<? extends ThrowableItemProjectile> type, Level world, LivingEntity owner) {
        super(type, world);
        this.owner = owner;
    }

    @Override
    public void applyOnProjectileSpawned(ServerLevel level, ItemStack spawnedFrom) {
        if(owner!=null)setOwner(owner);
        super.applyOnProjectileSpawned(level, spawnedFrom);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.smokeTicks > 0)
            this.smokeTicks--;
        if(level().isClientSide()) {
            for (int i = 0; i < 200; ++i) {
                double motionX = random.nextGaussian() * 0.02;
                double motionY = random.nextGaussian() * 0.02;
                double motionZ = random.nextGaussian() * 0.02;
                double offsetX = random.nextGaussian() * SMOKE_RADIUS;
                double offsetY = random.nextGaussian() * SMOKE_RADIUS;
                double offsetZ = random.nextGaussian() * SMOKE_RADIUS;
                level().addParticle(ParticleTypes.LARGE_SMOKE, getX() + offsetX, getY() + offsetY, getZ() + offsetZ, motionX, motionY, motionZ);
            }

        }
        if (this.smokeTicks <= 0) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setDeltaMovement(Vec3.ZERO);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.smoke_bomb.get();
    }
}