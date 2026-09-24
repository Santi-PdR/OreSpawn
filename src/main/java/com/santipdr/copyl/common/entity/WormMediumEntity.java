package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;

/** Port directo de danger.orespawn.entity.WormMedium del JAR 1.12.2. */
public final class WormMediumEntity extends Monster {
    private int upCount;
    private int downCount;

    public WormMediumEntity(EntityType<? extends WormMediumEntity> type, Level level) {
        super(type, level);
        this.xpReward = 0;
        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR, 8.0D);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide || !isAlive()) {
            return;
        }

        WormSmallEntity smallWorm = nearestSmallWorm(8.0D, 8.0D, 8.0D);
        Player nearbyPlayer = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(8.0D), Player::isAlive)
                .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);

        // OreSpawnMain.PlayNicely is 0 by default in the reference build. Until
        // that global option itself is ported, preserve the default branch.
        boolean normalCycle = smallWorm != null || nearbyPlayer != null;

        if (!normalCycle) {
            upCount = random.nextInt(50);
            downCount = 0;
            if (!isBurrowBlockAllowed(blockAtYOffset(3.0D))) {
                discard();
                return;
            }
            setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.10000000149011612D, getDeltaMovement().z);
            setPos(getX(), getY() + 0.05000000074505806D, getZ());
        } else if (upCount > 0) {
            --upCount;
            if (upCount == 0) {
                downCount = 100 + random.nextInt(150);
            }
            if (nearbyPlayer != null) {
                pointAt(nearbyPlayer);
            }
            if (!isBurrowBlockAllowed(blockAtYOffset(0.25D))) {
                discard();
                return;
            }
            setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.20000000298023224D, getDeltaMovement().z);
            setPos(getX(), getY() + 0.10000000149011612D, getZ());
        } else {
            if (downCount > 0) {
                --downCount;
            } else {
                upCount = 25 + random.nextInt(75);
            }
            if (!isBurrowBlockAllowed(blockAtYOffset(3.0D))) {
                discard();
                return;
            }
            setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y + 0.10000000149011612D, getDeltaMovement().z);
            setPos(getX(), getY() + 0.05000000074505806D, getZ());
        }

        setDeltaMovement(0.0D, getDeltaMovement().y - 0.01D, 0.0D);
        zza = 0.0F;
    }

    private WormSmallEntity nearestSmallWorm(double x, double y, double z) {
        AABB box = getBoundingBox().inflate(x, y, z);
        return level().getEntitiesOfClass(WormSmallEntity.class, box, Entity::isAlive)
                .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    private BlockState blockAtYOffset(double offset) {
        return level().getBlockState(BlockPos.containing(getX(), getY() + offset, getZ()));
    }

    private static boolean isBurrowBlockAllowed(BlockState state) {
        return state.isAir()
                || state.is(Blocks.GRASS)
                || state.is(Blocks.TALL_GRASS)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.STONE);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (!isAlive()) {
            return;
        }

        // The original medium worm refuses to attack while a Small Worm is nearby.
        if (nearestSmallWorm(8.0D, 8.0D, 8.0D) != null) {
            return;
        }

        AABB search = getBoundingBox().inflate(2.25D, 8.0D, 2.25D);
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
        int damage = remaining > 15 ? remaining / 15 : 1;
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
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y * 0.65D, getDeltaMovement().z);
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

    public static boolean checkSpawnRules(EntityType<WormMediumEntity> type, ServerLevelAccessor level,
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
        return ModSounds.BIG_SPLAT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Override
    public float getVoicePitch() {
        return 1.5F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        dropOriginalItem(Items.ROTTEN_FLESH, 2);
        dropOriginalItem(Items.LEATHER, 2);
    }

    private void dropOriginalItem(net.minecraft.world.item.Item item, int count) {
        double x = getX() + random.nextInt(3) - random.nextInt(3);
        double y = getY() + 2.5D + random.nextInt(3);
        double z = getZ() + random.nextInt(3) - random.nextInt(3);
        level().addFreshEntity(new ItemEntity(level(), x, y, z, new ItemStack(item, count)));
    }
}
