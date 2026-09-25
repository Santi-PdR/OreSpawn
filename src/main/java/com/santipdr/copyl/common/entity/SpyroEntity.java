package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

/** Baby dragon: behavior, tame interactions and fire state ported from the original Spyro. */
public final class SpyroEntity extends TamableAnimal {
    private static final double BASE_SPEED = 0.3D;
    private int activity = 1;
    private boolean fireballsEnabled = true;
    @Nullable private BlockPos flightTarget;
    private boolean targetInSight;
    private boolean ownerFlying;

    public SpyroEntity(EntityType<? extends SpyroEntity> type, Level level) {
        super(type, level);
        xpReward = 35;
        setTame(false);
        setOrderedToSit(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 8.0F, 0.3D, 0.4D));
        goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.15D, 12.0F, 2.0F, false));
        goalSelector.addGoal(4, new TemptGoal(this, 1.25D, Ingredient.of(Items.BEEF), false));
        goalSelector.addGoal(5, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.75D));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean fireImmune() { return true; }

    @Override
    public boolean isFood(ItemStack stack) { return stack.is(Items.BEEF); }

    @Nullable
    @Override
    public SpyroEntity getBreedOffspring(net.minecraft.server.level.ServerLevel level, AgeableMob partner) {
        return null;
    }

    public static boolean checkSpawnRules(EntityType<SpyroEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getLevel().isDay() && pos.getY() >= 50
                && Mob.checkMobSpawnRules(type, level, reason, pos, random);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpyroActivity", activity);
        tag.putBoolean("SpyroFire", fireballsEnabled);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        activity = tag.getInt("SpyroActivity");
        fireballsEnabled = !tag.contains("SpyroFire") || tag.getBoolean("SpyroFire");
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source.is(DamageTypes.CACTUS) ? false : super.hurt(source, amount);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        // Spyro.updateAITasks heals one point on a 1-in-100 server-tick roll,
        // independently of water; water supplies lift rather than healing.
        if (random.nextInt(100) == 1 && getHealth() < getMaxHealth()) {
            heal(1.0F);
        }
        if (level().getDifficulty() == Difficulty.PEACEFUL) {
            setTarget(null);
            activity = 1;
            return;
        }
        // Attack selection and flight steering are handled together in tick().
    }

    private double targetScore(Monster target) {
        double score = distanceToSqr(target);
        if (target instanceof Creeper) score *= 0.5D;
        double area = target.getBbWidth() * target.getBbHeight();
        return area > 1.0D ? score / area : score;
    }

    private void attack(Monster target) {
        if (distanceToSqr(target) < Math.pow(3.0D + target.getBbWidth() * 0.5D, 2.0D)) {
            doHurtTarget(target);
            return;
        }
        if (distanceToSqr(target) >= 64.0D || isInWater()) return;

        // The legacy roll attempts 1/10 when fire is enabled, then falls through
        // to the 1/15 roll when that first attempt misses (or fire is disabled).
        boolean fire = fireballsEnabled && level().getRandom().nextInt(10) == 0;
        if (!fire) fire = level().getRandom().nextInt(15) == 1;
        if (!fire) return;

        SmallFireball fireball = new SmallFireball(level(), this,
                target.getX() - getX(),
                target.getY() + 0.25D - (getY() + 1.25D),
                target.getZ() - getZ());
        fireball.setPos(getX(), getY() + 1.25D, getZ());
        level().addFreshEntity(fireball);
        level().playSound(null, this, SoundEvents.GHAST_SHOOT, SoundSource.NEUTRAL,
                0.75F, 1.0F / (level().getRandom().nextFloat() * 0.2F + 0.9F));
    }

    private boolean canSeeTarget(double x, double y, double z) {
        Vec3 start = new Vec3(getX(), getY() + 0.75D, getZ());
        Vec3 end = new Vec3(x, y, z);
        return level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
    }

    private BlockPos chooseFlightTarget(BlockPos origin) {
        int minHorizontal = ownerFlying ? 0 : 6;
        int horizontalRange = ownerFlying ? 6 : isTame() && getOwner() != null ? 4 : 5;
        BlockPos candidate = origin;
        for (int attempt = 0; attempt < 50; attempt++) {
            int dx = minHorizontal + level().getRandom().nextInt(horizontalRange);
            int dz = minHorizontal + level().getRandom().nextInt(horizontalRange);
            if (level().getRandom().nextBoolean()) dx = -dx;
            if (level().getRandom().nextBoolean()) dz = -dz;
            int dy = level().getRandom().nextInt(9 + (ownerFlying ? 2 : 0)) - 4;
            candidate = origin.offset(dx, dy, dz);
            if (level().getBlockState(candidate).isAir()
                    && canSeeTarget(candidate.getX(), candidate.getY(), candidate.getZ())) {
                return candidate;
            }
        }
        return candidate;
    }

    @Override
    public void tick() {
        super.tick();
        // The original onUpdate adds a fixed upward motion while in water.
        if (isInWater()) {
            setDeltaMovement(getDeltaMovement().add(0.0D, 0.07D, 0.0D));
        }
        // Spyro's legacy onUpdate had a 1/100000 self-replacement roll for
        // non-persistent instances, using the normal natural-spawn initializer.
        if (!level().isClientSide && random.nextInt(100000) == 1 && !isPersistenceRequired()) {
            SpyroEntity replacement = ModEntities.SPYRO.get().create(level());
            if (replacement != null) {
                replacement.moveTo(getX(), getY(), getZ(), random.nextFloat() * 360.0F, 0.0F);
                level().addFreshEntity(replacement);
                if (level() instanceof ServerLevel serverLevel) {
                    replacement.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPosition()),
                            MobSpawnType.NATURAL, null, null);
                }
                if (isTame()) replacement.setTame(true);
                discard();
                return;
            }
        }
        if (isOrderedToSit()) {
            setNoGravity(false);
            activity = 1;
            flightTarget = null;
            getNavigation().stop();
            return;
        }
        setNoGravity(true);
        if (level().isClientSide) return;
        LivingEntity owner = isTame() ? getOwner() : null;
        ownerFlying = owner instanceof Player ownerPlayer && ownerPlayer.getAbilities().flying;

        boolean chooseNewTarget = flightTarget == null;
        if (activity == 2 && level().getRandom().nextInt(300) == 0) chooseNewTarget = true;
        if (owner != null) {
            double ownerDistance = distanceToSqr(owner);
            if (ownerDistance > 100.0D || ownerFlying && ownerDistance > 36.0D) {
                chooseNewTarget = true;
                activity = 2;
            }
        }
        if (!targetInSight && level().getRandom().nextInt(100) == 1) {
            activity = 1;
            if (level().getRandom().nextInt(8) == 1) {
                activity = 2;
                chooseNewTarget = true;
            }
        }

        if (activity == 2 && level().getRandom().nextInt(6) == 1
                && level().getDifficulty() != Difficulty.PEACEFUL) {
            List<Monster> targets = level().getEntitiesOfClass(Monster.class,
                    getBoundingBox().inflate(12.0D, 6.0D, 12.0D),
                    target -> target.isAlive() && hasLineOfSight(target));
            targets.sort(Comparator.comparingDouble(this::targetScore));
            if (!targets.isEmpty()) {
                Monster target = targets.get(0);
                if (isTame() && getHealth() / getMaxHealth() < 0.25F) {
                    activity = 2;
                    targetInSight = false;
                    flightTarget = BlockPos.containing(2.0D * getX() - target.getX(),
                            getY() + 1.0D, 2.0D * getZ() - target.getZ());
                    chooseNewTarget = false;
                } else {
                    activity = 2;
                    targetInSight = true;
                    flightTarget = BlockPos.containing(target.getX(), target.getY() + 1.0D, target.getZ());
                    getNavigation().moveTo(target, 1.25D);
                    chooseNewTarget = false;
                    attack(target);
                }
            } else {
                targetInSight = false;
            }
        }

        if (activity == 2 && flightTarget != null && !targetInSight) {
            Vec3 destination = new Vec3(flightTarget.getX(), flightTarget.getY(), flightTarget.getZ());
            if (destination.distanceToSqr(getX(), getY(), getZ()) < 2.1D) chooseNewTarget = true;
        }
        if (chooseNewTarget && !targetInSight) {
            BlockPos origin = owner != null ? owner.blockPosition() : blockPosition();
            flightTarget = chooseFlightTarget(origin);
        }

        if (activity == 2 && flightTarget != null) {
            double dx = flightTarget.getX() + 0.5D - getX();
            double dy = flightTarget.getY() + 0.1D - getY();
            double dz = flightTarget.getZ() + 0.5D - getZ();
            double speed = 0.5D;
            if (ownerFlying) {
                speed = 1.75D;
                if (owner != null && distanceToSqr(owner) > 49.0D) speed = 3.5D;
            }
            Vec3 motion = getDeltaMovement();
            setDeltaMovement(
                    motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.15D * speed,
                    motion.y + (Math.signum(dy) * 0.7D - motion.y) * 0.21D * speed,
                    motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.15D * speed);
            float targetYaw = (float) Math.toDegrees(Math.atan2(getDeltaMovement().z, getDeltaMovement().x)) - 90.0F;
            float yawTurn = targetYaw - getYRot();
            while (yawTurn < -180.0F) yawTurn += 360.0F;
            while (yawTurn >= 180.0F) yawTurn -= 360.0F;
            setYRot(getYRot() + yawTurn / 3.0F);
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (isTame()) {
            int count = 1 + random.nextInt(4);
            spawnAtLocation(new ItemStack(Items.BEEF, count));
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.DUCK_HURT.get(); }

    @Override
    protected SoundEvent getAmbientSound() { return null; }

    @Override
    protected float getSoundVolume() { return 0.4F; }

    @Override
    public float getVoicePitch() {
        return isBaby() ? 1.5F + (random.nextFloat() - random.nextFloat()) * 0.1F
                : 1.0F + (random.nextFloat() - random.nextFloat()) * 0.1F;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) { return false; }

    @Override
    protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state,
                                   BlockPos pos) { }

    @Override
    public net.minecraft.world.InteractionResult mobInteract(Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (distanceToSqr(player) >= 16.0D) return super.mobInteract(player, hand);
        if (stack.is(Items.BEEF) && !isTame()) {
            if (!level().isClientSide) {
                if (random.nextBoolean()) {
                    setTame(true);
                    setOwnerUUID(player.getUUID());
                    setOrderedToSit(true);
                    heal(getMaxHealth());
                    level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    level().broadcastEntityEvent(this, (byte) 6);
                }
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && item == Items.DEAD_BUSH) {
            if (!level().isClientSide) {
                setTame(false);
                heal(getMaxHealth());
                setOwnerUUID(null);
                setOrderedToSit(false);
                level().broadcastEntityEvent(this, (byte) 6);
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && item == Items.ICE) {
            if (!level().isClientSide) {
                setOrderedToSit(true);
                fireballsEnabled = false;
                level().broadcastEntityEvent(this, (byte) 6);
                player.displayClientMessage(Component.literal("Baby Spyro fireballs extinguished."), true);
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && item == Items.FLINT_AND_STEEL) {
            if (!level().isClientSide) {
                setOrderedToSit(true);
                fireballsEnabled = true;
                level().broadcastEntityEvent(this, (byte) 6);
                player.displayClientMessage(Component.literal("Baby Spyro fireballs lit!"), true);
            }
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && item == Items.NAME_TAG && stack.hasCustomHoverName()) {
            setCustomName(stack.getHoverName());
            if (!player.getAbilities().instabuild) stack.shrink(1);
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && stack.isEmpty()) {
            if (!level().isClientSide) setOrderedToSit(!isOrderedToSit());
            return net.minecraft.world.InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }
}
