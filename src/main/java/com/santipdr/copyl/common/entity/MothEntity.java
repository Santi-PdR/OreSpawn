package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.util.LegacyRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** Port de danger.orespawn.entity.Moth del JAR 1.12.2 proporcionado. */
public final class MothEntity extends PathfinderMob {
    private BlockPos currentFlightTarget;
    private final int mothType;
    private int closest = 99999;
    private int torchX;
    private int torchY;
    private int torchZ;

    public MothEntity(EntityType<? extends MothEntity> type, Level level) {
        super(type, level);
        this.mothType = LegacyRandom.nextInt(4);
    }

    public int getMothType() {
        return mothType;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.6D, motion.z);
    }

    @Override
    protected void customServerAiStep() {
        if (isRemoved()) {
            return;
        }
        super.customServerAiStep();

        BlockPos here = new BlockPos((int) getX(), (int) getY(), (int) getZ());
        if (currentFlightTarget == null) {
            currentFlightTarget = here;
        }

        if (random.nextInt(100) == 0 || currentFlightTarget.distSqr(here) < 4.0D) {
            chooseRandomAirTarget();
        } else if (!level().isDay() && random.nextInt(10) == 0) {
            closest = 99999;
            torchX = 0;
            torchY = 0;
            torchZ = 0;

            for (int radius = 2; radius < 15; radius++) {
                if (scanTorchShell(here.getX(), here.getY(), here.getZ(), radius, radius, radius)) {
                    break;
                }
                // Rareza original: a partir de 6 incrementa dos veces el radio.
                if (radius >= 6) {
                    radius++;
                }
            }

            if (closest < 99999) {
                currentFlightTarget = new BlockPos(torchX, torchY + 1, torchZ);
            }
        }

        double dx = currentFlightTarget.getX() + 0.5D - getX();
        double dy = currentFlightTarget.getY() + 0.1D - getY();
        double dz = currentFlightTarget.getZ() + 0.5D - getZ();
        Vec3 motion = getDeltaMovement();

        setDeltaMovement(
                motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.10000000149011612D,
                motion.y + (Math.signum(dy) * 0.68D - motion.y) * 0.10000000149011612D,
                motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.10000000149011612D
        );

        Vec3 nextMotion = getDeltaMovement();
        float targetYaw = (float) (Mth.atan2(nextMotion.z, nextMotion.x) * (180.0D / Math.PI)) - 90.0F;
        float yawDelta = Mth.wrapDegrees(targetYaw - getYRot());
        this.zza = 0.75F;
        setYRot(getYRot() + yawDelta);
    }

    private void chooseRandomAirTarget() {
        int tries = 25;
        BlockState state = Blocks.STONE.defaultBlockState();
        while (!state.isAir() && tries != 0) {
            currentFlightTarget = new BlockPos(
                    (int) getX() + random.nextInt(10) - random.nextInt(10),
                    (int) getY() + random.nextInt(6) - 2,
                    (int) getZ() + random.nextInt(10) - random.nextInt(10)
            );
            state = level().getBlockState(currentFlightTarget);
            tries--;
        }
    }

    /**
     * Replica scan_it(): sólo revisa las seis caras del cubo y conserva la antorcha más cercana.
     * El original acepta vanilla Torch y OreSpawn Extreme Torch.
     */
    private boolean scanTorchShell(int x, int y, int z, int dx, int dy, int dz) {
        int found = 0;

        for (int oy = -dy; oy <= dy; oy++) {
            for (int oz = -dz; oz <= dz; oz++) {
                found += considerTorch(new BlockPos(x + dx, y + oy, z + oz), dx * dx + oz * oz + oy * oy);
                found += considerTorch(new BlockPos(x - dx, y + oy, z + oz), dx * dx + oz * oz + oy * oy);
            }
        }
        for (int ox = -dx; ox <= dx; ox++) {
            for (int oz = -dz; oz <= dz; oz++) {
                found += considerTorch(new BlockPos(x + ox, y + dy, z + oz), dy * dy + oz * oz + ox * ox);
                found += considerTorch(new BlockPos(x + ox, y - dy, z + oz), dy * dy + oz * oz + ox * ox);
            }
        }
        for (int ox = -dx; ox <= dx; ox++) {
            for (int oy = -dy; oy <= dy; oy++) {
                found += considerTorch(new BlockPos(x + ox, y + oy, z + dz), dz * dz + oy * oy + ox * ox);
                found += considerTorch(new BlockPos(x + ox, y + oy, z - dz), dz * dz + oy * oy + ox * ox);
            }
        }

        return found != 0;
    }

    private int considerTorch(BlockPos pos, int distance) {
        BlockState state = level().getBlockState(pos);
        if (!state.is(Blocks.TORCH) && !state.is(ModBlocks.EXTREME_TORCH.get())) {
            return 0;
        }
        if (distance < closest) {
            closest = distance;
            torchX = pos.getX();
            torchY = pos.getY();
            torchZ = pos.getZ();
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        // fall() y updateFallState() están vacíos en el original.
    }

    public static boolean checkSpawnRules(EntityType<MothEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos).isAir()
                && !level.getLevel().isDay()
                && pos.getY() >= 50;
    }
}
