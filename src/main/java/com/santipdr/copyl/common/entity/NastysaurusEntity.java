package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

/** Port de danger.orespawn.entity.Nastysaurus del JAR OreSpawn 1.12.2. */
public final class NastysaurusEntity extends Monster {
    private static final EntityDataAccessor<Byte> ATTACKING =
            SynchedEntityData.defineId(NastysaurusEntity.class, EntityDataSerializers.BYTE);
    private static final double ORIGINAL_MOVE_SPEED = 0.35D;
    private LivingEntity revengeTarget;

    public NastysaurusEntity(EntityType<? extends NastysaurusEntity> type, Level level) {
        super(type, level);
        this.xpReward = 40;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, (byte) 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ARMOR, 17.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.0D, false, 10, () -> false));
        this.goalSelector.addGoal(2, new LongRangeWanderGoal(this, 16, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ORIGINAL_MOVE_SPEED);
        }
        super.tick();
    }

    @Override
    protected void customServerAiStep() {
        if (this.isRemoved()) return;
        super.customServerAiStep();
        if (this.random.nextInt(5) != 0) return;

        LivingEntity target = this.revengeTarget;
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            target = null;
        }
        if (target != null) {
            if (!target.isAlive() || this.random.nextInt(250) == 1) {
                target = null;
                this.revengeTarget = null;
            } else if (!this.getSensing().hasLineOfSight(target)) {
                target = null;
            }
        }
        if (target == null) target = findSomethingToAttack();
        if (target == null) {
            setAttacking(0);
            return;
        }

        this.getLookControl().setLookAt(target, 10.0F, 10.0F);
        double reach = 4.5D + target.getBbWidth() / 2.0D;
        if (this.distanceToSqr(target) < reach * reach) {
            setAttacking(1);
            if (this.random.nextInt(4) == 0 || this.random.nextInt(5) == 1) this.doHurtTarget(target);
        } else {
            this.getNavigation().moveTo(target, 1.25D);
        }
    }

    private LivingEntity findSomethingToAttack() {
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            return null;
        }
        AABB search = this.getBoundingBox().inflate(32.0D, 8.0D, 32.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, search);
        candidates.sort(Comparator.comparingDouble(this::getOriginalTargetScore));
        for (LivingEntity candidate : candidates) {
            if (candidate == this || !candidate.isAlive()
                    || candidate instanceof NastysaurusEntity
                    || candidate instanceof CryolophosaurusEntity
                    || !this.getSensing().hasLineOfSight(candidate)
                    || (candidate instanceof Player player && player.isCreative())) continue;
            return candidate;
        }
        return null;
    }

    private double getOriginalTargetScore(LivingEntity target) {
        double score = this.distanceToSqr(target);
        if (target instanceof Creeper) {
            score /= 2.0D;
        }
        double targetArea = (double) target.getBbHeight() * target.getBbWidth();
        if (targetArea > 1.0D) {
            score /= targetArea;
        }
        return score;
    }

    public int getAttacking() { return this.entityData.get(ATTACKING); }
    public void setAttacking(int attacking) { this.entityData.set(ATTACKING, (byte) attacking); }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) return false;
        double vertical = (!target.isAlive() || target instanceof Player) ? 0.2D : 0.1D;
        double angle = Math.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
        target.push(Math.cos(angle) * 1.2D, vertical, Math.sin(angle) * 1.2D);
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.CACTUS)) return false;
        boolean result = super.hurt(source, amount);
        if (source.getEntity() instanceof LivingEntity attacker) this.revengeTarget = attacker;
        return result;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(4) == 0 ? ModSounds.ALOSAURUS_LIVING.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.ALOSAURUS_HURT.get(); }

    @Override
    protected float getSoundVolume() { return 1.5F; }

    @Override
    public float getVoicePitch() { return 1.0F; }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        dropItemRand(Items.DIAMOND, 10);
        dropItemRand(Items.ROTTEN_FLESH, 10);
        dropItemRand(Items.BEEF, 10);
        dropItemRand(Items.STRING, 10);
    }

    private void dropItemRand(Item item, int count) {
        if (this.level().isClientSide) return;
        for (int i = 0; i < count; i++) {
            double x = this.getX() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
            double z = this.getZ() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
            ItemEntity entity = new ItemEntity(this.level(), x, this.getY() + 3.0D, z, new ItemStack(item));
            this.level().addFreshEntity(entity);
        }
    }

    public static boolean checkSpawnRules(EntityType<NastysaurusEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (!Monster.checkMonsterSpawnRules(type, level, reason, pos, random)
                || pos.getY() < 50 || level.getLevel().isDay()) return false;
        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = 1; y < 6; y++) {
                    if (!level.getBlockState(pos.offset(x, y, z)).isAir()) return false;
                }
            }
        }
        AABB nearby = new AABB(pos).inflate(16.0D, 8.0D, 16.0D);
        return level.getLevel().getEntitiesOfClass(NastysaurusEntity.class, nearby).isEmpty();
    }
}
