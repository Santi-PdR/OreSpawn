package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import com.santipdr.copyl.common.item.material.ModMaterialItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

import java.util.List;

/** Port de danger.orespawn.entity.CaveFisher del JAR 1.12.2 proporcionado. */
public final class CaveFisherEntity extends Monster {
    private static final EntityDataAccessor<Byte> ATTACKING =
            SynchedEntityData.defineId(CaveFisherEntity.class, EntityDataSerializers.BYTE);
    private static final double MOVE_SPEED = 0.20D;

    // Equivale a RenderInfo.ri1/ri2 del original: estado visual local de las pinzas.
    private int renderChoice1;
    private int renderChoice2;

    public CaveFisherEntity(EntityType<? extends CaveFisherEntity> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new LongRangeWanderGoal(this, 14, 1.0D));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACKING, (byte) 0);
    }

    @Override
    public void tick() {
        var speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.getBaseValue() != MOVE_SPEED) {
            speed.setBaseValue(MOVE_SPEED);
        }
        super.tick();
    }

    @Override
    protected void customServerAiStep() {
        if (isDeadOrDying()) {
            return;
        }

        super.customServerAiStep();
        if (level().getRandom().nextInt(8) != 0) {
            return;
        }

        LivingEntity target = findSomethingToAttack();
        if (target == null) {
            setAttacking(0);
            return;
        }

        double reach = 4.0D + target.getBbWidth() / 2.0D;
        if (distanceToSqr(target) < reach * reach) {
            setAttacking(1);
            if (level().getRandom().nextInt(7) == 0 || level().getRandom().nextInt(8) == 1) {
                doHurtTarget(target);
            }
        } else {
            getNavigation().moveTo(target, 1.2D);
        }
    }

    private LivingEntity findSomethingToAttack() {
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            return null;
        }
        AABB area = getBoundingBox().inflate(10.0D, 3.0D, 10.0D);
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity candidate : entities) {
            if (isSuitableTarget(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private boolean isSuitableTarget(LivingEntity candidate) {
        if (candidate == this || !candidate.isAlive()) {
            return false;
        }
        if (!getSensing().hasLineOfSight(candidate)) {
            return false;
        }
        if (candidate instanceof CaveFisherEntity || candidate instanceof Monster) {
            return false;
        }
        return !(candidate instanceof Player player) || !player.isCreative();
    }

    public int getAttacking() {
        return entityData.get(ATTACKING);
    }

    public void setAttacking(int attacking) {
        entityData.set(ATTACKING, (byte) attacking);
    }

    public int getRenderChoice1() {
        return renderChoice1;
    }

    public void setRenderChoice1(int value) {
        renderChoice1 = value;
    }

    public int getRenderChoice2() {
        return renderChoice2;
    }

    public void setRenderChoice2(int value) {
        renderChoice2 = value;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.CACTUS)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isPersistenceRequired();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.CRYO_HURT.get();
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

    private Item getLegacyDrop() {
        return switch (random.nextInt(6)) {
            case 0 -> Items.GOLD_NUGGET;
            case 1 -> ModMaterialItems.URANIUM_NUGGET.get();
            case 2 -> ModMaterialItems.TITANIUM_NUGGET.get();
            default -> null;
        };
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        Item drop = getLegacyDrop();
        if (drop != null) {
            spawnAtLocation(drop);
        }
    }

    public static boolean checkSpawnRules(EntityType<CaveFisherEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() <= 50 && Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }
}
