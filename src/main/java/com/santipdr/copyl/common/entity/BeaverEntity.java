package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/** Port de danger.orespawn.entity.Beaver del JAR 1.12.2 proporcionado. */
public final class BeaverEntity extends Animal {
    private static final double MOVE_SPEED = 0.20D;
    private static final int TREE_RECURSION_LIMIT = 200;

    public BeaverEntity(EntityType<? extends BeaverEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 8.0F, 1.0D, 1.5D));
        goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.0D, 1.5D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(7, new LongRangeWanderGoal(this, 10, 1.0D));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
        if (random.nextInt(200) == 1) {
            setTarget(null);
        }

        boolean wantsTree = (random.nextInt(30) == 0 && getHealth() < getMaxHealth())
                || random.nextInt(350) == 1;
        if (wantsTree && LegacyGameplayFlags.PLAY_NICELY == 0) {
            seekAndCutTree();
        }

        if (random.nextInt(200) == 1) {
            followNearestBeaver();
        }

        super.customServerAiStep();
    }

    private void seekAndCutTree() {
        BlockPos target = findNearestWood();
        if (target == null) {
            return;
        }

        getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.0D);
        if (blockPosition().distSqr(target) >= 12.0D) {
            return;
        }

        if (level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            cutConnectedWood(target);
        }
        heal(1.0F);
    }

    @Nullable
    private BlockPos findNearestWood() {
        BlockPos origin = blockPosition().above();
        BlockPos best = null;
        double bestDistance = 99999.0D;

        for (int radius = 1; radius <= 10; radius += radius >= 6 ? 2 : 1) {
            int vertical = Math.min(radius, 2);
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -vertical; dy <= vertical; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (Math.abs(dx) != radius && Math.abs(dz) != radius) {
                            continue;
                        }
                        BlockPos pos = origin.offset(dx, dy, dz);
                        if (!isWood(level().getBlockState(pos))) {
                            continue;
                        }
                        double distance = dx * dx + dy * dy + dz * dz;
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            best = pos.immutable();
                        }
                    }
                }
            }
            if (best != null) {
                return best;
            }
        }
        return null;
    }

    private static boolean isWood(BlockState state) {
        return state.is(BlockTags.LOGS)
                || state.is(BlockTags.WOODEN_FENCES)
                || state.is(BlockTags.FENCE_GATES);
    }

    private void cutConnectedWood(BlockPos start) {
        if (!isWood(level().getBlockState(start))) {
            return;
        }

        Queue<BlockPos> open = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        open.add(start.immutable());

        int broken = 0;
        while (!open.isEmpty() && broken < TREE_RECURSION_LIMIT) {
            BlockPos current = open.remove();
            if (!visited.add(current) || !isWood(level().getBlockState(current))) {
                continue;
            }

            BlockState state = level().getBlockState(current);
            Item drop = state.getBlock().asItem();
            level().removeBlock(current, false);
            if (drop != Items.AIR) {
                double x = getX() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
                double y = getY() + 4.0D + level().getRandom().nextInt(4);
                double z = getZ() + LegacyRandom.nextInt(4) - LegacyRandom.nextInt(4);
                ItemEntity entity = new ItemEntity(level(), x, y, z, new ItemStack(drop));
                entity.setPickUpDelay(10);
                level().addFreshEntity(entity);
            }
            broken++;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }
                        BlockPos next = current.offset(dx, dy, dz);
                        if (!visited.contains(next) && isWood(level().getBlockState(next))) {
                            open.add(next.immutable());
                        }
                    }
                }
            }
        }
    }

    private void followNearestBeaver() {
        AABB area = getBoundingBox().inflate(16.0D, 6.0D, 16.0D);
        List<BeaverEntity> beavers = level().getEntitiesOfClass(BeaverEntity.class, area,
                beaver -> beaver != this && beaver.isAlive());
        beavers.stream()
                .min(Comparator.comparingDouble(this::distanceToSqr))
                .ifPresent(beaver -> getNavigation().moveTo(beaver, 0.5D));
    }

    public static boolean checkSpawnRules(EntityType<BeaverEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (pos.getY() < 50 || pos.getY() > 100) {
            return false;
        }
        BlockState below = level.getBlockState(pos.below());
        return below.is(Blocks.DIRT)
                || below.is(Blocks.GRASS_BLOCK)
                || below.is(Blocks.TALL_GRASS)
                || below.is(BlockTags.LEAVES);
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
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CRYO_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public float getVoicePitch() {
        return isBaby()
                ? (random.nextFloat() - random.nextFloat()) * 0.1F + 1.5F
                : (random.nextFloat() - random.nextFloat()) * 0.1F + 1.0F;
    }

    public boolean isWheat(ItemStack stack) {
        return stack.is(Items.WHEAT);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public BeaverEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return ModEntities.BEAVER.get().create(level);
    }
}
