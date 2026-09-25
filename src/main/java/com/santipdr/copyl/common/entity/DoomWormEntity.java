package com.santipdr.copyl.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Server-authoritative segmented movement ported from the reference WormDoom class. */
public final class DoomWormEntity extends Monster {
    private static final int SEGMENTS = 100;
    private final double[] sx = new double[SEGMENTS];
    private final double[] sy = new double[SEGMENTS];
    private final double[] sz = new double[SEGMENTS];
    private final float[] syaw = new float[SEGMENTS];
    private final float[] spitch = new float[SEGMENTS];
    private float heading = 1.0F;
    private float turnVelocity;
    private float targetHeading = 1.0F;
    private float cycle;

    public DoomWormEntity(EntityType<? extends DoomWormEntity> type, Level level) {
        super(type, level);
        noPhysics = true;
        xpReward = 0;
        cycle = level.getRandom().nextFloat() * 360.0F;

        // WormDoom initializes the history with a 0.5625-block horizontal step.
        for (int i = 0; i < SEGMENTS; i++) {
            double distance = i * 0.5625D;
            sx[i] = getX() - Math.sin(Math.toRadians(heading)) * distance;
            sz[i] = getZ() - Math.cos(Math.toRadians(heading)) * distance;
            sy[i] = getY() + Math.sin(Math.toRadians(i * 10.0D + 180.0D)) * 4.0D;
            syaw[i] = heading;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 3000.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    protected void registerGoals() {}

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            for (int i = SEGMENTS - 1; i > 0; i--) {
                sx[i] = sx[i - 1];
                sy[i] = sy[i - 1];
                sz[i] = sz[i - 1];
                syaw[i] = syaw[i - 1];
                spitch[i] = spitch[i - 1];
            }
            sx[0] = getX();
            sy[0] = getY();
            sz[0] = getZ();
            syaw[0] = getYRot();
            spitch[0] = getXRot();
            setDeltaMovement(Vec3.ZERO);
            return;
        }

        boolean child = isBaby();
        int verticalRange = child ? 3 : 6;
        double speed = child ? 0.2D : 0.3D;
        double dx = Math.sin(Math.toRadians(heading)) * speed;
        double dz = Math.cos(Math.toRadians(heading)) * speed;

        // The original loop leaves y at -range-1 when the entire column is air.
        int verticalOffset = -verticalRange - 1;
        for (int y = verticalRange; y >= -verticalRange; y--) {
            if (!level().getBlockState(blockPosition().offset(0, y, 0)).isAir()) {
                verticalOffset = y;
                break;
            }
        }

        double frequency = child ? 20.0D : 10.0D;
        double wave = Math.sin(Math.toRadians(cycle * frequency)) * verticalRange;
        double dy = getDeltaMovement().y * 0.98D + 0.008D * -(wave - verticalOffset);
        cycle = (cycle + 0.5F) % 360.0F;

        // The reference chooses a fresh turn target with a 1-in-100 roll each tick.
        if (level().getRandom().nextInt(100) == 1) {
            targetHeading = level().getRandom().nextFloat() * 360.0F;
        }
        float delta = (float) Math.toRadians(targetHeading - heading);
        while (delta > Math.PI) delta -= (float) (Math.PI * 2.0D);
        while (delta < -Math.PI) delta += (float) (Math.PI * 2.0D);

        turnVelocity = 0.95F * turnVelocity + delta * (float) (180.0D / Math.PI / 20.0D);
        heading += turnVelocity;
        setYRot(-heading);
        setXRot((float) Math.toDegrees(Math.atan2(-dy, Math.sqrt(dx * dx + dz * dz))));
        setDeltaMovement(dx, dy, -dz);
        move(MoverType.SELF, getDeltaMovement());
    }

    public int segmentCount() { return SEGMENTS; }
    public double segmentX(int i) { return sx[i]; }
    public double segmentY(int i) { return sy[i]; }
    public double segmentZ(int i) { return sz[i]; }
    public float segmentYaw(int i) { return syaw[i]; }
    public float segmentPitch(int i) { return spitch[i]; }
}
