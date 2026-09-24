package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevelAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;

/** Port directo de danger.orespawn.entity.WormSmall del JAR 1.12.2. */
public final class WormSmallEntity extends Monster {
    private int upCount = 50;
    private int downCount;

    public WormSmallEntity(EntityType<? extends WormSmallEntity> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide || !isAlive()) {
            return;
        }

        Player nearby = level().getNearestPlayer(this, 8.0D);
        if (upCount > 0) {
            --upCount;
            if (upCount == 0) {
                downCount = 100 + random.nextInt(150);
            }
            if (nearby != null) {
                pointAt(nearby);
            }
            if (!isBurrowBlockAllowed(blockAtYOffset(0.25D))) {
                discard();
                return;
            }
            setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.15000000596046448D, getDeltaMovement().z);
            setPos(getX(), getY() + 0.10000000149011612D, getZ());
        } else {
            if (downCount > 0) {
                --downCount;
            } else {
                upCount = 25 + random.nextInt(50);
            }
            if (!isBurrowBlockAllowed(blockAtYOffset(2.0D))) {
                discard();
                return;
            }
            setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.20000000298023224D, getDeltaMovement().z);
            setPos(getX(), getY() + 0.05000000074505806D, getZ());
        }

        setDeltaMovement(0.0D, getDeltaMovement().y - 0.01D, 0.0D);
        zza = 0.0F;
    }

    private BlockState blockAtYOffset(double offset) {
        return level().getBlockState(BlockPos.containing(getX(), getY() + offset, getZ()));
    }

    private static boolean isBurrowBlockAllowed(BlockState state) {
        if (state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS)) {
            return true;
        }
        return state.isAir() || state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.STONE);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!isAlive()) {
            return;
        }

        AABB search = getBoundingBox().inflate(1.5D, 4.0D, 1.5D);
        Player player = level().getEntitiesOfClass(Player.class, search,
                        p -> p.isAlive() && !p.getAbilities().instabuild)
                .stream()
                .min(Comparator.comparingDouble(this::distanceToSqr))
                .orElse(null);
        if (player == null) {
            return;
        }

        pointAt(player);
        if (upCount <= 0 || random.nextInt(15) != 1) {
            return;
        }

        doHurtTarget(player);
        if (random.nextInt(6) != 1) {
            return;
        }

        ItemStack stolen = player.getInventory().getItem(1);
        if (stolen.isEmpty()) {
            return;
        }

        player.getInventory().setItem(1, ItemStack.EMPTY);
        int remaining = stolen.getMaxDamage() - stolen.getDamageValue();
        int damage = remaining > 20 ? remaining / 20 : 1;
        if (stolen.isDamageableItem()) {
            stolen.setDamageValue(Mth.clamp(stolen.getDamageValue() + damage, 0, stolen.getMaxDamage()));
            if (stolen.getDamageValue() >= stolen.getMaxDamage()) {
                stolen.shrink(1);
            }
        }

        if (!stolen.isEmpty()) {
            double x = getX() + random.nextInt(5) - random.nextInt(5);
            double z = getZ() + random.nextInt(5) - random.nextInt(5);
            level().addFreshEntity(new ItemEntity(level(), x, getY() + 3.0D, z, stolen));
        }
    }

    private void pointAt(Player player) {
        double dx = player.getX() - getX();
        double dz = player.getZ() - getZ();
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
        setYRot(yaw);
        yHeadRot = yaw;
    }

    @Override
    public void tick() {
        if (isPersistenceRequired()) {
            noPhysics = false;
        }
        super.tick();
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y * 0.75D, getDeltaMovement().z);
    }

    @Override
    public void push(Entity entity) {
        // Original applyEntityCollision was intentionally empty.
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_WALL)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    public static boolean checkSpawnRules(EntityType<WormSmallEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return !level.getLevel().isDay();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.LITTLE_SPLAT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public float getVoicePitch() {
        return 1.5F;
    }
}
