package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

/** Base moderna para RedAnt y Termite, equivalente a danger.orespawn.entity.Ant. */
public abstract class AntEntity extends Animal {
    protected final double moveSpeed;

    protected AntEntity(EntityType<? extends AntEntity> type, Level level, double moveSpeed, int xpReward) {
        super(type, level);
        this.moveSpeed = moveSpeed;
        this.xpReward = xpReward;
    }

    protected static AttributeSupplier.Builder antAttributes(double maxHealth, double moveSpeed, double attackDamage) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, maxHealth)
                .add(Attributes.MOVEMENT_SPEED, moveSpeed)
                .add(Attributes.ATTACK_DAMAGE, attackDamage)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    protected void registerBaseGoals(int wanderDistance) {
        goalSelector.addGoal(0, new PanicGoal(this, 1.4D));
        goalSelector.addGoal(1, new LongRangeWanderGoal(this, wanderDistance, 1.0D));
    }

    @Override
    public void tick() {
        var speed = getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.getBaseValue() != moveSpeed) {
            speed.setBaseValue(moveSpeed);
        }
        super.tick();
    }

    @Override
    protected void customServerAiStep() {
        if (random.nextInt(200) == 1) {
            setTarget(null);
        }
        super.customServerAiStep();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty()) {
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static boolean checkAntSpawnRules(EntityType<? extends AntEntity> type, ServerLevelAccessor level,
                                             MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (pos.getY() < 50) {
            return false;
        }
        AABB area = new AABB(pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D).inflate(20.0D, 10.0D, 20.0D);
        return level.getLevel().getEntitiesOfClass(AntEntity.class, area).size() <= 4;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isPersistenceRequired();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
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
        return 0.0F;
    }

    @Nullable
    @Override
    public abstract AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate);
}
