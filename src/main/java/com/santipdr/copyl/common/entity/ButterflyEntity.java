package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;

/** Port de danger.orespawn.entity.Butterfly del JAR 1.12.2 proporcionado. */
public final class ButterflyEntity extends PathfinderMob {
    private BlockPos spawnPosition;
    private final int butterflyType;

    public ButterflyEntity(EntityType<? extends ButterflyEntity> type, Level level) {
        super(type, level);
        this.butterflyType = random.nextInt(4) + 1;
    }

    public int getButterflyType() {
        return butterflyType;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
                .add(Attributes.MAX_HEALTH, 1.0D);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (spawnPosition != null && (!level().isEmptyBlock(spawnPosition) || spawnPosition.getY() < 1)) {
            spawnPosition = null;
        }

        if (spawnPosition == null || random.nextInt(30) == 0 ||
                spawnPosition.distToCenterSqr(getX(), getY(), getZ()) < 4.0D) {
            spawnPosition = new BlockPos(
                    Mth.floor(getX()) + random.nextInt(7) - random.nextInt(7),
                    Mth.floor(getY()) + random.nextInt(6) - 2,
                    Mth.floor(getZ()) + random.nextInt(7) - random.nextInt(7)
            );
        }

        double dx = spawnPosition.getX() + 0.5D - getX();
        double dy = spawnPosition.getY() + 0.1D - getY();
        double dz = spawnPosition.getZ() + 0.5D - getZ();
        Vec3 motion = getDeltaMovement();

        setDeltaMovement(
                motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.1D,
                motion.y + (Math.signum(dy) * 0.699999988079071D - motion.y) * 0.1D,
                motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.1D
        );

        Vec3 nextMotion = getDeltaMovement();
        float targetYaw = (float) (Mth.atan2(nextMotion.z, nextMotion.x) * (180.0D / Math.PI)) - 90.0F;
        setYRot(getYRot() + Mth.wrapDegrees(targetYaw - getYRot()));
        this.zza = 0.5F;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.6000000238418579D, motion.z);
        if (!level().isClientSide && level().getSkyDarken() > 7) {
            discard();
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }
}
