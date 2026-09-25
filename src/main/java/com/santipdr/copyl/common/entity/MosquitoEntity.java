package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Port directo de danger.orespawn.entity.Mosquito del JAR 1.12.2 proporcionado. */
public final class MosquitoEntity extends AmbientCreature {
    private BlockPos currentFlightTarget;

    public MosquitoEntity(EntityType<? extends MosquitoEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.6000000238418579D, motion.z);
    }

    @Override
    protected void customServerAiStep() {
        if (isRemoved()) {
            return;
        }
        super.customServerAiStep();

        BlockPos here = blockPosition();
        if (currentFlightTarget == null) {
            currentFlightTarget = here;
        }

        if (random.nextInt(20) == 0 || currentFlightTarget.distSqr(here) < 3.0D) {
            boolean chosePlayer = false;

            // El JAR intenta seguir al jugador más cercano dentro de 10x6x10 una de cada cuatro veces.
            if (random.nextInt(4) == 0) {
                AABB box = getBoundingBox().inflate(10.0D, 6.0D, 10.0D);
                List<Player> players = level().getEntitiesOfClass(Player.class, box);
                Player nearest = null;
                double nearestDistance = Double.MAX_VALUE;
                for (Player player : players) {
                    double distance = distanceToSqr(player);
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearest = player;
                    }
                }
                if (nearest != null) {
                    currentFlightTarget = new BlockPos(
                            (int) nearest.getX(),
                            (int) nearest.getY() + 2,
                            (int) nearest.getZ()
                    );
                    chosePlayer = true;
                }
            }

            if (!chosePlayer) {
                findRandomAirTarget();
            }
        }

        double dx = currentFlightTarget.getX() + 0.5D - getX();
        double dy = currentFlightTarget.getY() + 0.1D - getY();
        double dz = currentFlightTarget.getZ() + 0.5D - getZ();
        Vec3 motion = getDeltaMovement();

        setDeltaMovement(
                motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.10000000149011612D,
                motion.y + (Math.signum(dy) * 0.699999988079071D - motion.y) * 0.10000000149011612D,
                motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.10000000149011612D
        );

        Vec3 nextMotion = getDeltaMovement();
        float targetYaw = (float) (Mth.atan2(nextMotion.z, nextMotion.x) * (180.0D / Math.PI)) - 90.0F;
        float yawDelta = Mth.wrapDegrees(targetYaw - getYRot());
        this.zza = 0.3F;
        setYRot(getYRot() + yawDelta);
    }

    private void findRandomAirTarget() {
        int tries = 50;
        BlockState state;
        do {
            currentFlightTarget = new BlockPos(
                    (int) getX() + random.nextInt(6) - random.nextInt(6),
                    (int) getY() + random.nextInt(6) - 2,
                    (int) getZ() + random.nextInt(6) - random.nextInt(6)
            );
            state = level().getBlockState(currentFlightTarget);
            tries--;
        } while (!state.isAir() && tries != 0);
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        // Equivalente moderno a canTriggerWalking() = false del original.
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isPushable() {
        // El original no permite empujarlo y collideWithEntity estaba vacío.
        return false;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        // El original anula fall() y updateFallState().
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public float getVoicePitch() {
        return 1.5F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Legacy Mosquito sound field was never registered and remained null.
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
}
