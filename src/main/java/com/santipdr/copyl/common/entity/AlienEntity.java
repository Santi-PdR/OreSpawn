package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
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
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

/** Port de danger.orespawn.entity.Alien del JAR 1.12.2 proporcionado. */
public final class AlienEntity extends Monster {
    private static final EntityDataAccessor<Byte> ATTACKING =
            SynchedEntityData.defineId(AlienEntity.class, EntityDataSerializers.BYTE);

    private static final double ORIGINAL_MOVE_SPEED = 0.65D;

    public AlienEntity(EntityType<? extends AlienEntity> type, Level level) {
        super(type, level);
        this.xpReward = 100;
        this.setMaxUpStep(0.6F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
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
        this.goalSelector.addGoal(2, new LongRangeWanderGoal(this, 10, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.25D, 0.0D));
    }

    @Override
    public void tick() {
        if (this.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ORIGINAL_MOVE_SPEED);
        }
        super.tick();

        if (this.level().isClientSide && this.random.nextInt(20) == 1) {
            float distance = 1.7F + Math.abs(this.random.nextFloat() * 0.75F);
            double yaw = Math.toRadians(this.getYHeadRot());
            this.level().addParticle(
                    ParticleTypes.DRIPPING_LAVA,
                    this.getX() - distance * Math.sin(yaw),
                    this.getY() + 1.6D,
                    this.getZ() + distance * Math.cos(yaw),
                    0.0D, 0.0D, 0.0D
            );
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        if (this.random.nextInt(8) == 0) {
            LivingEntity target = findSomethingToAttack();
            if (target != null) {
                this.getLookControl().setLookAt(target, 10.0F, 10.0F);
                if (this.distanceToSqr(target) < 16.0D) {
                    setAttacking(1);
                    if (this.random.nextInt(4) == 0 || this.random.nextInt(5) == 1) {
                        this.doHurtTarget(target);
                    }
                }
                this.getNavigation().moveTo(target, 1.2D);
            } else {
                setAttacking(0);
            }
        } else if (this.random.nextInt(30) == 0) {
            BlockPos torch = findNearestTorch();
            if (torch != null) {
                this.getNavigation().moveTo(torch.getX(), torch.getY(), torch.getZ(), 1.0D);
                if (this.blockPosition().distSqr(torch) < 27.0D
                        && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.level().setBlock(torch, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        if (this.random.nextInt(40) == 1 && this.getHealth() < this.getMaxHealth()) {
            this.heal(1.0F);
        }
    }

    private LivingEntity findSomethingToAttack() {
        LivingEntity current = this.getTarget();
        if (current != null && current.isAlive()) {
            return current;
        }
        this.setTarget(null);

        AABB search = this.getBoundingBox().inflate(12.0D, 4.0D, 12.0D);
        List<Player> players = this.level().getEntitiesOfClass(Player.class, search,
                player -> player.isAlive() && !player.isCreative() && !player.isSpectator()
                        && this.getSensing().hasLineOfSight(player));
        return players.stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    private BlockPos findNearestTorch() {
        BlockPos center = this.blockPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        int[] radii = {2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14};

        for (int radius : radii) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz)) != radius) {
                            continue;
                        }
                        BlockPos pos = center.offset(dx, dy, dz);
                        var state = this.level().getBlockState(pos);
                        if (!state.is(Blocks.TORCH)
                                && !state.is(Blocks.WALL_TORCH)
                                && !state.is(ModBlocks.EXTREME_TORCH.get())
                                && !state.is(ModBlocks.EXTREME_WALL_TORCH.get())) {
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
        if (!super.doHurtTarget(target)) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            int durationMultiplier = switch (this.level().getDifficulty()) {
                case PEACEFUL -> 6;
                case EASY -> 8;
                case NORMAL -> 10;
                case HARD -> 12;
            };
            if (this.random.nextInt(5) == 1) {
                living.addEffect(new MobEffectInstance(MobEffects.HUNGER, durationMultiplier * 5, 0), this);
            }

            double strength = 1.1D;
            double vertical = target instanceof Player ? 0.2D : 0.1D;
            double angle = Math.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
            target.push(Math.cos(angle) * strength, vertical, Math.sin(angle) * strength);
        }
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.CACTUS)) {
            return false;
        }
        boolean result = super.hurt(source, amount);
        if (source.getEntity() instanceof LivingEntity attacker) {
            this.setTarget(attacker);
            this.getNavigation().moveTo(attacker, 1.2D);
            return true;
        }
        return result;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(4) == 0 ? ModSounds.ALIEN_LIVING.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ALIEN_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 1.0F;
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        int spiderEyes = 5 + this.random.nextInt(6);
        for (int i = 0; i < spiderEyes; i++) {
            dropItemRand(Items.SPIDER_EYE, 1);
        }

        int flint = 5 + this.random.nextInt(6);
        for (int i = 0; i < flint; i++) {
            dropItemRand(Items.FLINT, 1);
        }

        dropItemRand(Items.MAP, 1);
        dropItemRand(Items.CLOCK, 1);
        dropItemRand(Items.COMPASS, 1);
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

    public static boolean checkSpawnRules(EntityType<AlienEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (pos.getY() > 50 || !Monster.checkMonsterSpawnRules(type, level, reason, pos, random)) {
            return false;
        }
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 1; dy <= 3; dy++) {
                    if (!level.getBlockState(pos.offset(dx, dy, dz)).isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
