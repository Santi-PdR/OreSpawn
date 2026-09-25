package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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

/** Port de danger.orespawn.entity.Pointysaurus del JAR 1.12.2 proporcionado. */
public final class PointysaurusEntity extends Monster {
    private static final double ORIGINAL_MOVE_SPEED = 0.35D;
    private LivingEntity revengeTarget;

    public PointysaurusEntity(EntityType<? extends PointysaurusEntity> type, Level level) {
        super(type, level);
        this.xpReward = 40;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // 1.12.2: EntityAIMoveThroughVillage(this, 1.0D, false).
        // La API moderna agrega distancia + proveedor de puertas; 10/false conserva el comportamiento base.
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
        if (this.isRemoved()) {
            return;
        }
        super.customServerAiStep();

        // El original ejecuta esta lógica sólo en 1 de cada 6 ticks.
        if (this.random.nextInt(6) != 0) {
            return;
        }

        LivingEntity target = this.revengeTarget;
        if (target != null) {
            if (!target.isAlive() || this.random.nextInt(250) == 1) {
                target = null;
                this.revengeTarget = null;
            }
            if (target != null && !this.getSensing().hasLineOfSight(target)) {
                // El bytecode sólo limpia la variable local en este caso, no el campo rt.
                target = null;
            }
        }

        if (target == null) {
            target = findSomethingToAttack();
        }

        if (target == null) {
            setAttacking(0);
            return;
        }

        this.getLookControl().setLookAt(target, 10.0F, 10.0F);
        double attackReach = 4.0D + target.getBbWidth() / 2.0D;
        if (this.distanceToSqr(target) < attackReach * attackReach) {
            setAttacking(1);
            if (this.random.nextInt(5) == 0 || this.random.nextInt(6) == 1) {
                this.doHurtTarget(target);
            }
        } else {
            this.getNavigation().moveTo(target, 1.25D);
        }
    }

    private LivingEntity findSomethingToAttack() {
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            return null;
        }
        AABB search = this.getBoundingBox().inflate(12.0D, 5.0D, 12.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, search);
        for (LivingEntity candidate : candidates) {
            if (isSuitableTargetOriginal(candidate)) {
                // El 1.12.2 devuelve el primer candidato válido; no ordena por distancia.
                return candidate;
            }
        }
        return null;
    }

    private boolean isSuitableTargetOriginal(LivingEntity target) {
        if (target == this || !target.isAlive()) {
            return false;
        }
        if (target instanceof PointysaurusEntity || target instanceof Monster) {
            return false;
        }
        if (!this.getSensing().hasLineOfSight(target)) {
            return false;
        }
        return target instanceof Player player && !player.isCreative() && !player.isSpectator();
    }

    /** getAttacking() en el JAR devuelve siempre 1 y setAttacking() está vacío. */
    public int getAttacking() {
        return 1;
    }

    public void setAttacking(int attacking) {
        // Intencionalmente vacío: así está implementado en Pointysaurus 1.12.2.
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) {
            return false;
        }

        if (target instanceof LivingEntity) {
            double horizontal = 0.8D;
            double vertical = 0.1D;
            double angle = Math.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
            if (!target.isAlive() || target instanceof Player) {
                vertical *= 2.0D;
            }
            target.push(Math.cos(angle) * horizontal, vertical, Math.sin(angle) * horizontal);
        }
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // El original ignora completamente el daño de cactus.
        if (source.is(DamageTypes.CACTUS)) {
            return false;
        }

        boolean result = super.hurt(source, amount);
        if (source.getEntity() instanceof LivingEntity attacker) {
            this.revengeTarget = attacker;
        }
        return result;
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
        return 0.9F;
    }

    @Override
    public float getVoicePitch() {
        return 1.5F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        dropItemRand(Items.LEATHER, 10);
        dropItemRand(Items.BEEF, 6);
        dropItemRand(Items.ROTTEN_FLESH, 6);
        dropItemRand(Items.STRING, 6);
    }

    private void dropItemRand(Item item, int count) {
        if (this.level().isClientSide) {
            return;
        }
        for (int i = 0; i < count; i++) {
            double x = this.getX() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
            double z = this.getZ() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
            ItemEntity entity = new ItemEntity(this.level(), x, this.getY() + 2.0D, z, new ItemStack(item));
            this.level().addFreshEntity(entity);
        }
    }

    public static boolean checkSpawnRules(EntityType<PointysaurusEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (!Monster.checkMonsterSpawnRules(type, level, reason, pos, random)) {
            return false;
        }
        if (pos.getY() < 50 || level.getLevel().isDay()) {
            return false;
        }

        // El JAR exige aire en una columna de 5 bloques sobre un área 2x2 alrededor del spawn.
        for (int dz = -1; dz < 1; dz++) {
            for (int dx = -1; dx < 1; dx++) {
                for (int dy = 1; dy < 6; dy++) {
                    if (!level.getBlockState(pos.offset(dx, dy, dz)).isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
