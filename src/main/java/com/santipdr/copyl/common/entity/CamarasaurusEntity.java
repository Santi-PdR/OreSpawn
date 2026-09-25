package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.Nullable;

/** Port directo de danger.orespawn.entity.Camarasaurus del JAR 1.12.2 proporcionado. */
public final class CamarasaurusEntity extends TamableAnimal {
    private static final double MOVE_SPEED = 0.20D;

    public CamarasaurusEntity(EntityType<? extends CamarasaurusEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(3, new FollowOwnerGoal(this, 2.0D, 10.0F, 2.0F, false));
        goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Monster.class, 8.0F, 1.0D, 1.4D));
        goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.APPLE), false));
        goalSelector.addGoal(6, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(8, new LongRangeWanderGoal(this, 10, 1.0D));
        goalSelector.addGoal(9, new RandomLookAroundGoal(this));
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

        super.customServerAiStep();

        if (isTame()) {
            return;
        }

        boolean shouldFeed = (random.nextInt(20) == 0 && getHealth() < getMaxHealth())
                || random.nextInt(250) == 0;
        if (!shouldFeed || LegacyGameplayFlags.PLAY_NICELY != 0) {
            return;
        }

        BlockPos plant = findNearestEdiblePlant();
        if (plant == null) {
            return;
        }

        getNavigation().moveTo(plant.getX(), plant.getY(), plant.getZ(), 1.0D);
        if (blockPosition().distSqr(plant) >= 12.0D) {
            return;
        }

        if (level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            level().removeBlock(plant, false);
        }
        heal(1.0F);
        playSound(SoundEvents.PLAYER_BURP, 1.0F, random.nextFloat() * 0.2F + 0.9F);
    }

    @Nullable
    private BlockPos findNearestEdiblePlant() {
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
                        if (!isOriginalFoodBlock(level().getBlockState(pos))) {
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

    private static boolean isOriginalFoodBlock(BlockState state) {
        return state.is(BlockTags.LEAVES)
                || state.is(Blocks.VINE)
                || state.is(Blocks.GRASS)
                || state.is(Blocks.TALL_GRASS)
                || state.is(Blocks.CACTUS)
                || state.is(Blocks.SUNFLOWER)
                || state.is(Blocks.LILAC)
                || state.is(Blocks.LARGE_FERN)
                || state.is(Blocks.ROSE_BUSH)
                || state.is(Blocks.PEONY);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        InteractionResult vanilla = super.mobInteract(player, hand);
        if (vanilla.consumesAction()) {
            return vanilla;
        }

        if (stack.is(Items.APPLE) && player.distanceToSqr(this) < 16.0D) {
            if (!level().isClientSide) {
                if (!isTame()) {
                    if (random.nextInt(2) == 0) {
                        tame(player);
                        setOrderedToSit(true);
                        level().broadcastEntityEvent(this, (byte) 7);
                        heal(getMaxHealth() - getHealth());
                    } else {
                        level().broadcastEntityEvent(this, (byte) 6);
                        setOrderedToSit(true);
                    }
                } else if (isOwnedBy(player)) {
                    if (getHealth() < getMaxHealth()) {
                        heal(getMaxHealth() - getHealth());
                    }
                    level().broadcastEntityEvent(this, (byte) 7);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (isTame() && isOwnedBy(player) && stack.is(Items.NAME_TAG) && player.distanceToSqr(this) < 16.0D) {
            if (!level().isClientSide) {
                setCustomName(stack.getHoverName());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (isTame() && isOwnedBy(player) && player.distanceToSqr(this) < 16.0D) {
            if (!level().isClientSide) {
                setOrderedToSit(!isOrderedToSit());
                getNavigation().stop();
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.GOLDEN_APPLE);
    }

    @Override
    public boolean canMate(Animal other) {
        if (other == this || !isTame() || !(other instanceof CamarasaurusEntity mate) || !mate.isTame()) {
            return false;
        }
        if (isOrderedToSit() || mate.isOrderedToSit()) {
            return false;
        }
        return isInLove() && mate.isInLove();
    }

    @Nullable
    @Override
    public CamarasaurusEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return ModEntities.CAMARASAURUS.get().create(level);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        float damage = Mth.ceil(distance - 3.0F);
        if (damage <= 0.0F) {
            return false;
        }
        playSound(damage > 3.0F ? SoundEvents.GENERIC_BIG_FALL : SoundEvents.GENERIC_SMALL_FALL, 1.0F, 1.0F);
        return hurt(damageSources().fall(), Math.min(2.0F, damage));
    }

    public static boolean checkSpawnRules(EntityType<CamarasaurusEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() >= 50 && level.getLevel().isDay();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isBaby() && !isLeashed() && !isTame() && !isPersistenceRequired();
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

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (isTame()) {
            int count = random.nextInt(5) + 2;
            spawnAtLocation(new ItemStack(Items.POPPY, count));
        }
    }
}
