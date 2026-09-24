package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import com.santipdr.copyl.common.entity.ai.MoveIndoorsGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

/** Port de danger.orespawn.entity.StinkBug del JAR 1.12.2 de referencia. */
public final class StinkBugEntity extends Animal {
    private static final double MOVE_SPEED = 0.15D;

    public StinkBugEntity(EntityType<? extends StinkBugEntity> type, Level level) {
        super(type, level);
        this.xpReward = 2;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 4.0F, 1.0D, 1.4D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(8, new LongRangeWanderGoal(this, 10, 1.0D));
        goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        goalSelector.addGoal(10, new MoveIndoorsGoal(this));
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
        if (!isDeadOrDying() && random.nextInt(200) == 1) {
            setLastHurtByMob(null);
        }
        super.customServerAiStep();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isRemoved()) {
            return false;
        }

        boolean hurt = super.hurt(source, amount);
        if (hurt && (getHealth() <= 0.0F || isRemoved()) && !level().isClientSide) {
            // 1.12.2 usa AxisAlignedBB.expand(8,5,8), que expande hacia +X/+Y/+Z.
            AABB cloud = getBoundingBox().expandTowards(8.0D, 5.0D, 8.0D);
            for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, cloud)) {
                living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0));
            }
        }
        return hurt;
    }

    @Override
    public void die(DamageSource source) {
        boolean wasDead = isDeadOrDying();
        super.die(source);
        if (!wasDead && !level().isClientSide) {
            playSound(ModSounds.randomStinkBugFart(random), getSoundVolume(), getVoicePitch());
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        // El JAR 1.12.2 devuelve false de forma explícita para isBreedingItem.
        return false;
    }

    @Nullable
    @Override
    public StinkBugEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return ModEntities.STINK_BUG.get().create(level);
    }

    public static boolean checkSpawnRules(EntityType<StinkBugEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 50;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isBaby() && !isLeashed();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }
}
