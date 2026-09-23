package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

import java.util.List;

/** Port directo de danger.orespawn.entity.Alosaurus del JAR 1.12.2 proporcionado. */
public final class AlosaurusEntity extends Monster {
    private static final EntityDataAccessor<Byte> ATTACKING =
            SynchedEntityData.defineId(AlosaurusEntity.class, EntityDataSerializers.BYTE);
    private static final double ORIGINAL_MOVE_SPEED = 0.35D;

    public AlosaurusEntity(EntityType<? extends AlosaurusEntity> type, Level level) {
        super(type, level);
        this.xpReward = 40;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 18.0D)
                .add(Attributes.ARMOR, 18.0D)
                .add(Attributes.FOLLOW_RANGE, 18.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, (byte) 0);
    }

    public int getAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(int attacking) {
        this.entityData.set(ATTACKING, (byte) attacking);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // 1.12.2: EntityAIMoveThroughVillage(this, 1.0D, false).
        this.goalSelector.addGoal(1, new MoveThroughVillageGoal(this, 1.0D, false, 4, () -> false));
        this.goalSelector.addGoal(2, new LongRangeWanderGoal(this, 16, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        var speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.getBaseValue() != ORIGINAL_MOVE_SPEED) {
            speed.setBaseValue(ORIGINAL_MOVE_SPEED);
        }
        super.tick();
    }

    @Override
    protected void customServerAiStep() {
        if (this.isRemoved()) {
            return;
        }
        super.customServerAiStep();

        if (this.random.nextInt(5) != 0) {
            return;
        }

        LivingEntity target = findSomethingToAttack();
        if (target == null) {
            setAttacking(0);
            return;
        }

        this.getLookControl().setLookAt(target, 10.0F, 10.0F);
        double reach = 4.0D + target.getBbWidth() / 2.0D;
        if (this.distanceToSqr(target) < reach * reach) {
            setAttacking(1);
            if (this.random.nextInt(4) == 0 || this.random.nextInt(5) == 1) {
                this.doHurtTarget(target);
            }
        } else {
            this.getNavigation().moveTo(target, 1.25D);
        }
    }

    private LivingEntity findSomethingToAttack() {
        AABB area = this.getBoundingBox().inflate(12.0D, 5.0D, 12.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity candidate : candidates) {
            if (isSuitableTargetOriginal(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isSuitableTargetOriginal(LivingEntity target) {
        if (target == this || !target.isAlive()) {
            return false;
        }
        if (target instanceof AlosaurusEntity || target instanceof CryolophosaurusEntity) {
            return false;
        }
        if (!this.getSensing().hasLineOfSight(target)) {
            return false;
        }
        return !(target instanceof Player player) || (!player.isCreative() && !player.isSpectator());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            double strength = 1.2D;
            double vertical = (!living.isAlive() || living instanceof Player) ? 0.2D : 0.1D;
            double angle = Math.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
            target.push(Math.cos(angle) * strength, vertical, Math.sin(angle) * strength);
        }
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        // Original: despawnea normalmente salvo que haya sido marcado persistente.
        return !this.isPersistenceRequired();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(4) == 0 ? ModSounds.ALOSAURUS_LIVING.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ALOSAURUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ALOSAURUS_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 1.5F;
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        for (int i = 0; i < 10; i++) {
            dropItemRand(Items.GOLD_NUGGET, 1);
        }
        for (int i = 0; i < 6; i++) {
            dropItemRand(Items.BEEF, 1);
        }
    }

    private void dropItemRand(Item item, int count) {
        ItemEntity dropped = new ItemEntity(
                this.level(),
                this.getX() + this.random.nextInt(4) - this.random.nextInt(4),
                this.getY() + 1.0D,
                this.getZ() + this.random.nextInt(4) - this.random.nextInt(4),
                new ItemStack(item, count)
        );
        this.level().addFreshEntity(dropped);
    }

    public static boolean checkSpawnRules(EntityType<AlosaurusEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (pos.getY() < 50 || level.getLevel().isDay()) {
            return false;
        }
        if (!Monster.checkMonsterSpawnRules(type, level, reason, pos, random)) {
            return false;
        }

        for (int dx = -1; dx <= 0; dx++) {
            for (int dz = -1; dz <= 0; dz++) {
                for (int dy = 1; dy <= 5; dy++) {
                    if (!level.getBlockState(pos.offset(dx, dy, dz)).isAir()) {
                        return false;
                    }
                }
            }
        }

        AABB nearby = new AABB(pos).inflate(16.0D, 8.0D, 16.0D);
        return level.getLevel().getEntitiesOfClass(AlosaurusEntity.class, nearby).isEmpty();
    }
}
