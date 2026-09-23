package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Port de danger.orespawn.entity.Firefly desde el JAR 1.12.2. */
public final class FireflyEntity extends AmbientCreature {
    private int blinkLength;
    private int blinker;
    private BlockPos currentFlightTarget;

    public FireflyEntity(EntityType<? extends FireflyEntity> type, Level level) {
        super(type, level);
        this.blinkLength = 20 + random.nextInt(20);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D);
    }

    /** El modelo 1.12.2 usaba 240 durante la primera mitad del ciclo y 0 en la segunda. */
    public boolean isBlinking() {
        return blinker < blinkLength / 2;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.600000023841D, motion.z);

        blinker++;
        if (blinker > blinkLength) {
            blinker = 0;
        }

        if (!level().isClientSide && !isPersistenceRequired()) {
            long time = level().getDayTime() % 24000L;
            if (time <= 11000L && random.nextInt(500) == 1) {
                discard();
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (isRemoved()) {
            return;
        }

        if (currentFlightTarget == null) {
            currentFlightTarget = blockPosition();
        }

        if (random.nextInt(40) == 0 || currentFlightTarget.distToCenterSqr(getX(), getY(), getZ()) < 2.0D) {
            int tries = 25;
            BlockPos candidate;
            do {
                candidate = new BlockPos(
                        Mth.floor(getX()) + random.nextInt(4) - random.nextInt(4),
                        Mth.floor(getY()) + random.nextInt(4) - 2,
                        Mth.floor(getZ()) + random.nextInt(4) - random.nextInt(4)
                );
                currentFlightTarget = candidate;
                tries--;
            } while (!level().isEmptyBlock(candidate) && tries > 0);
        }

        double dx = currentFlightTarget.getX() + 0.5D - getX();
        double dy = currentFlightTarget.getY() + 0.1D - getY();
        double dz = currentFlightTarget.getZ() + 0.5D - getZ();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(
                motion.x + (Math.signum(dx) * 0.2D - motion.x) * 0.1D,
                motion.y + (Math.signum(dy) * 0.699999988079071D - motion.y) * 0.1D,
                motion.z + (Math.signum(dz) * 0.2D - motion.z) * 0.1D
        );

        Vec3 nextMotion = getDeltaMovement();
        float targetYaw = (float) (Mth.atan2(nextMotion.z, nextMotion.x) * (180.0D / Math.PI)) - 90.0F;
        float yawDelta = Mth.wrapDegrees(targetYaw - getYRot());
        this.zza = 0.2F;
        setYRot(getYRot() + yawDelta / 4.0F);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        // El original anulaba fall() y updateFallState().
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        int count = random.nextInt(3);
        for (int i = 0; i < count; i++) {
            spawnAtLocation(ModItems.EXTREME_TORCH.get());
        }
    }

    /** Condiciones de getCanSpawnHere() del original, sin inventar biomas. */
    public boolean matchesOriginalSpawnConditions() {
        if (!level().isEmptyBlock(blockPosition())) {
            return false;
        }
        if (level().isDay()) {
            return false;
        }
        AABB nearby = getBoundingBox().inflate(20.0D, 8.0D, 20.0D);
        if (level().getEntitiesOfClass(FireflyEntity.class, nearby).size() > 10) {
            return false;
        }
        return getY() >= 50.0D;
    }
}
