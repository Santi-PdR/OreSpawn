package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

/** Port de danger.orespawn.entity.GammaMetroid del JAR 1.12.2 proporcionado. */
public final class GammaMetroidEntity extends TamableAnimal {
    private static final double ORIGINAL_MOVE_SPEED = 0.15D;

    public GammaMetroidEntity(EntityType<? extends GammaMetroidEntity> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 2.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.IRON_INGOT), false));
        this.goalSelector.addGoal(4, new LongRangeWanderGoal(this, 16, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
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
        super.customServerAiStep();

        if (this.level().getDifficulty() != Difficulty.PEACEFUL
                && this.level().getRandom().nextInt(5) == 0
                && !this.isBaby()
                && !this.isTame()) {
            LivingEntity target = findSomethingToAttack();
            if (target != null) {
                this.getLookControl().setLookAt(target, 10.0F, 10.0F);
                if (this.distanceTo(target) <= 9.0F) {
                    if (this.level().getRandom().nextInt(4) == 0 || this.level().getRandom().nextInt(5) == 1) {
                        this.doHurtTarget(target);
                    }
                } else {
                    this.getNavigation().moveTo(target, 1.25D);
                }
            }
        }

        if (((this.random.nextInt(20) == 0 && this.getHealth() < this.getMaxHealth())
                || this.random.nextInt(100) == 0)
                && !this.isOrderedToSit()
                && LegacyGameplayFlags.PLAY_NICELY == 0) {
            BlockPos stone = findNearestStone();
            if (stone != null) {
                this.getNavigation().moveTo(stone.getX(), stone.getY(), stone.getZ(), 1.0D);
                if (this.blockPosition().distSqr(stone) < 12.0D
                        && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.level().setBlock(stone, Blocks.AIR.defaultBlockState(), 3);
                    this.heal(1.0F);
                    this.playSound(Blocks.STONE.defaultBlockState().getSoundType().getBreakSound(),
                            0.5F, this.random.nextFloat() * 0.2F + 1.5F);
                }
            }
        }
    }

    private LivingEntity findSomethingToAttack() {
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            return null;
        }
        if (this.isBaby() || this.isTame()) {
            return null;
        }
        AABB search = this.getBoundingBox().inflate(10.0D, 3.0D, 10.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, search, target -> {
            if (target == this || !target.isAlive() || target instanceof GammaMetroidEntity || target instanceof Monster) {
                return false;
            }
            if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
                return false;
            }
            return this.getSensing().hasLineOfSight(target);
        });
        return candidates.stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    private BlockPos findNearestStone() {
        BlockPos center = this.blockPosition().above();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;

        for (int radius = 1; radius < 6; radius++) {
            int vertical = Math.min(radius, 2);
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -vertical; dy <= vertical; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        BlockPos pos = center.offset(dx, dy, dz);
                        if (!this.level().getBlockState(pos).is(Blocks.STONE)) {
                            continue;
                        }
                        double distance = center.distSqr(pos);
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            best = pos.immutable();
                        }
                    }
                }
            }
            if (best != null) {
                break;
            }
        }
        return best;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return target.hurt(this.damageSources().mobAttack(this), 10.0F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult parentResult = super.mobInteract(player, hand);
        if (parentResult.consumesAction()) {
            return parentResult;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.IRON_INGOT)
                && this.distanceTo(player) < 25.0F
                && !this.isTame()
                && !this.level().isClientSide) {
            if (this.random.nextInt(3) == 0) {
                this.setTame(true);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte) 7);
                this.heal(this.getMaxHealth() - this.getHealth());
            } else {
                this.setTame(false);
                this.level().broadcastEntityEvent(this, (byte) 6);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.IRON_INGOT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        GammaMetroidEntity child = ModEntities.GAMMA_METROID.get().create(level);
        if (child != null && this.isTame()) {
            child.setTame(true);
        }
        return child;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        if (this.isBaby() || this.isTame()) {
            return false;
        }
        return super.removeWhenFarAway(distanceToClosestPlayer);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(5) == 1 ? ModSounds.GAMMA_METROID_LIVING.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.DUCK_HURT.get();
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

        int nuggets = 5 + LegacyRandom.nextInt(10);
        for (int i = 0; i < nuggets; i++) {
            dropItemRand(Items.GOLD_NUGGET, 1);
        }

        int iron = 6 + LegacyRandom.nextInt(10);
        for (int i = 0; i < iron; i++) {
            dropItemRand(Items.IRON_INGOT, 1);
        }
    }

    private void dropItemRand(Item item, int count) {
        ItemEntity dropped = new ItemEntity(
                this.level(),
                this.getX() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4),
                this.getY() + 1.0D,
                this.getZ() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4),
                new ItemStack(item, count)
        );
        this.level().addFreshEntity(dropped);
    }

    public static boolean checkSpawnRules(EntityType<GammaMetroidEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() <= 50;
    }
}
