package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

/** Mothra: boss volador y atacante del original danger.orespawn.entity.Mothra. */
public final class MothraEntity extends ButterflyEntity implements Enemy {
    private BlockPos flightTarget;
    private int lastX, lastY, lastZ;
    private int stuckTicks;
    private int wingTicks;
    private int healTicks = 100;
    private final float moveSpeed = 0.35F;

    public MothraEntity(EntityType<? extends MothraEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ARMOR, 12.0D);
    }

    @Override
    public boolean fireImmune() { return true; }

    @Override
    protected float getSoundVolume() { return 1.5F; }

    @Override
    public float getVoicePitch() { return 1.0F; }

    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() { return null; }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) { return null; }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() { return net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE; }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingModifier, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, lootingModifier, recentlyHit);
        if (!level().isClientSide) {
            // Original 1.12.2 order: item frame, explosion particles, scattered item drops, moths.
            dropLegacyItem(net.minecraft.world.item.Items.ITEM_FRAME);
            for (int i = 0; i < 20; i++) {
                double px = getX() + random.nextFloat() * 8.0D - random.nextFloat() * 8.0D;
                double py = getY() + 2.0D + random.nextFloat() * 4.0D - random.nextFloat() * 4.0D;
                double pz = getZ() + random.nextFloat() * 8.0D - random.nextFloat() * 8.0D;
                ((ServerLevel) level()).sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION, px, py, pz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            for (int i = 0; i < 53; i++) {
                dropLegacyItem(net.minecraft.world.item.Items.GOLD_NUGGET);
            }
            for (int i = 0; i < 3; i++) {
                dropLegacyItem(net.minecraft.world.item.Items.BLAZE_ROD);
            }
            dropLegacyItem(net.minecraft.world.item.Items.NETHER_STAR);
            for (int i = 0; i < 20; i++) {
                MothEntity moth = ModEntities.MOTH.get().create(level());
                if (moth != null) {
                    moth.moveTo(getX() + 0.5D, getY() + 1.0D, getZ() + 0.5D, random.nextFloat() * 360.0F, 0.0F);
                    level().addFreshEntity(moth);
                }
            }
        }
    }

    private void dropLegacyItem(net.minecraft.world.item.Item item) {
        double x = getX() + LegacyRandom.nextInt(8) - LegacyRandom.nextInt(8);
        double z = getZ() + LegacyRandom.nextInt(8) - LegacyRandom.nextInt(8);
        net.minecraft.world.entity.item.ItemEntity dropped = new net.minecraft.world.entity.item.ItemEntity(
                level(), x, getY() + 1.0D, z, new net.minecraft.world.item.ItemStack(item));
        level().addFreshEntity(dropped);
    }

    @Override
    public boolean isPushable() { return true; }

    @Override
    public void push(net.minecraft.world.entity.Entity entity) { }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (level().isClientSide) return;
        int x = (int) getX(), y = (int) getY(), z = (int) getZ();
        if (lastX == x && lastY == y && lastZ == z) stuckTicks++;
        else { stuckTicks = 0; lastX = x; lastY = y; lastZ = z; }
        int attackDivisor = level().getDifficulty() == Difficulty.HARD ? 2 : 3;
        if (flightTarget == null) {
            flightTarget = new BlockPos((int) getX(), (int) getY(), (int) getZ());
        }

        boolean chooseNewFlightTarget = stuckTicks > 50;
        if (!chooseNewFlightTarget) {
            // The 1.12.2 condition is random roll OR target within three blocks.
            chooseNewFlightTarget = level().getRandom().nextInt(300) == 0
                    || distanceFromLegacyPositionSquared() < 9.0D;
        }

        if (chooseNewFlightTarget) {
            chooseFlightTarget();
        } else if (level().getRandom().nextInt(10) == 0
                && level().getDifficulty() != Difficulty.PEACEFUL) {
            Player player = level().getEntitiesOfClass(Player.class,
                    getBoundingBox().inflate(25.0D, 20.0D, 25.0D))
                    .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
            if (player != null && player.getAbilities().instabuild) {
                player = null;
            }
            if (player != null && player.isAlive() && hasLineOfSight(player)) {
                flightTarget = new BlockPos((int) player.getX(), (int) player.getY() + 4, (int) player.getZ());
                if (random.nextInt(attackDivisor) == 0) attackWithSomething(player);
            } else if (player == null && level().getRandom().nextInt(3) == 0
                    && LegacyGameplayFlags.PLAY_NICELY == 0) {
                LivingEntity victim = level().getEntitiesOfClass(LivingEntity.class,
                        getBoundingBox().inflate(15.0D, 20.0D, 15.0D),
                        e -> e != this && e.isAlive()
                                && !(e instanceof MothraEntity)
                                && !(e instanceof BrutalflyEntity)
                                && !(e instanceof VelocityRaptorEntity)
                                && !(e instanceof CryolophosaurusEntity)
                                && !(e instanceof MantisEntity)
                                && (!(e instanceof Player p) || !p.getAbilities().instabuild)
                                && hasLineOfSight(e))
                        .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
                if (victim != null) {
                    flightTarget = new BlockPos((int) victim.getX(), (int) victim.getY() + 5, (int) victim.getZ());
                    if (level().getRandom().nextInt(attackDivisor) == 0) attackWithSomething(victim);
                }
            }
        }
        if (flightTarget == null) return;
        double dx = flightTarget.getX() + 0.5D - getX();
        double dy = flightTarget.getY() + 0.1D - getY();
        double dz = flightTarget.getZ() + 0.5D - getZ();
        Vec3 m = getDeltaMovement();
        setDeltaMovement(m.x + (Math.signum(dx) * 0.5D - m.x) * 0.30001D,
                m.y + (Math.signum(dy) * 0.7D - m.y) * 0.20001D,
                m.z + (Math.signum(dz) * 0.5D - m.z) * 0.30001D);
        Vec3 v = getDeltaMovement();
        setYRot((float)(Mth.atan2(v.z, v.x) * (180.0D / Math.PI)) - 90.0F);
    }

    private double distanceFromLegacyPositionSquared() {
        double dx = flightTarget.getX() - (int) getX();
        double dy = flightTarget.getY() - (int) getY();
        double dz = flightTarget.getZ() - (int) getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    private void chooseFlightTarget() {
        int groundOffset = 0;
        int bestHeight = 20;
        BlockPos origin = new BlockPos((int) getX(), (int) getY(), (int) getZ());
        for (int ox = -5; ox <= 5; ox += 5) for (int oz = -5; oz <= 5; oz += 5) {
            for (int dy = 1; dy <= 19; dy++) {
                BlockPos sample = origin.offset(ox, -dy, oz);
                if (!level().isEmptyBlock(sample)) {
                    int height = dy;
                    if (height < bestHeight) bestHeight = height;
                    break;
                }
            }
        }
        if (bestHeight > 10) groundOffset = bestHeight - 9;
        for (int tries = 0; tries < 50; tries++) {
            int ox = random.nextInt(20) + 8, oz = random.nextInt(20) + 8;
            if (random.nextBoolean()) ox = -ox;
            if (random.nextBoolean()) oz = -oz;
            BlockPos candidate = origin.offset(ox, random.nextInt(7) - 1 - groundOffset, oz);
            flightTarget = candidate;
            if (level().isEmptyBlock(candidate) && hasLineOfSightTo(candidate)) {
                stuckTicks = 0; return;
            }
        }
        stuckTicks = 0;
    }

    private boolean hasLineOfSightTo(BlockPos target) {
        Vec3 start = new Vec3(getX(), getY() + 0.75D, getZ());
        Vec3 end = new Vec3(target.getX(), target.getY(), target.getZ());
        return level().clip(new net.minecraft.world.level.ClipContext(start, end,
                net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE, this)).getType() ==
                net.minecraft.world.phys.HitResult.Type.MISS;
    }

    private void attackWithSomething(LivingEntity target) {
        if (level().getDifficulty() == Difficulty.PEACEFUL) return;
        double sx = getX() - 2.25D * Math.sin(Math.toRadians(getYRot()));
        double sz = getZ() + 2.25D * Math.cos(Math.toRadians(getYRot()));
        double dx = target.getX() - sx, dy = target.getY() + 0.55D - getY(), dz = target.getZ() - sz;
        if (level().getDifficulty() == Difficulty.EASY ||
                (level().getDifficulty() == Difficulty.NORMAL && random.nextBoolean())) {
            SmallFireball fireball = new SmallFireball(level(), this, dx, dy, dz);
            fireball.setPos(sx, getY(), sz);
            level().addFreshEntity(fireball);
            playSound(net.minecraft.sounds.SoundEvents.GHAST_SHOOT, 0.75F,
                    0.8F / (random.nextFloat() * 0.4F + 0.8F));
        } else {
            LargeFireball fireball = new LargeFireball(level(), this, dx, dy, dz, 1);
            fireball.setPos(sx, getY(), sz);
            level().addFreshEntity(fireball);
            playSound(net.minecraft.sounds.SoundEvents.GHAST_SHOOT, 1.0F,
                    0.8F / (random.nextFloat() * 0.4F + 0.8F));
        }
        heal(1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.6D, motion.z);
        if (++wingTicks > 30) {
            if (!level().isClientSide) playSound(ModSounds.MOTHRA_WINGS.get(), 1.0F, 1.0F);
            wingTicks = 0;
        }
        if (--healTicks <= 0) {
            if (getHealth() < getMaxHealth()) heal(1.0F);
            healTicks = 200;
        }
    }

    public static boolean checkSpawnRules(EntityType<MothraEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (pos.getY() < 70 || level.getLevel().isDay()) return false;
        for (int dx = -4; dx < 4; dx++) {
            for (int dz = -4; dz < 4; dz++) {
                for (int dy = 1; dy < 10; dy++) {
                    if (!level.getBlockState(pos.offset(dx, dy, dz)).isAir()) return false;
                }
            }
        }
        AABB area = new AABB(pos).inflate(64.0D, 32.0D, 64.0D);
        return level.getLevel().getEntitiesOfClass(MothraEntity.class, area).isEmpty();
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) { return false; }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) { }
}
