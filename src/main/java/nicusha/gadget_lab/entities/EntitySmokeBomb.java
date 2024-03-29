package nicusha.gadget_lab.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.Level;

public class EntitySmokeBomb extends ThrowableProjectile {
    private static final double SMOKE_RADIUS = 5.0;
    private static final int SMOKE_DURATION_TICKS = 1200;
    private int smokeTicks;

    public EntitySmokeBomb(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.smokeTicks = SMOKE_DURATION_TICKS;
    }

    public EntitySmokeBomb(EntityType<? extends ThrowableProjectile> type, Level world, LivingEntity owner) {
        super(type, owner, world);
        this.smokeTicks = SMOKE_DURATION_TICKS;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.smokeTicks > 0)
            this.smokeTicks--;

        for (int i = 0; i < 200; ++i) {
            double motionX = random.nextGaussian() * 0.02;
            double motionY = random.nextGaussian() * 0.02;
            double motionZ = random.nextGaussian() * 0.02;
            double offsetX = random.nextGaussian() * SMOKE_RADIUS;
            double offsetY = random.nextGaussian() * SMOKE_RADIUS;
            double offsetZ = random.nextGaussian() * SMOKE_RADIUS;
            level().addParticle(ParticleTypes.LARGE_SMOKE, getX() + offsetX, getY() + offsetY, getZ() + offsetZ, motionX, motionY, motionZ);
        }

        if (this.smokeTicks <= 0 && !level().isClientSide) {
            this.kill();
        }
    }
}