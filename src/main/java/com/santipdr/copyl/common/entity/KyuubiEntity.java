package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;

/** OreSpawn 1.12.2 Kyuubi behavior ported to Forge 1.20.1. */
public final class KyuubiEntity extends Monster {
    private static final double MOVE_SPEED = 0.25D;

    public KyuubiEntity(EntityType<? extends KyuubiEntity> type, Level level) {
        super(type, level);
        this.xpReward = 30;
        this.fireImmune();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 125.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 10.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.350000023841858D));
        goalSelector.addGoal(2, new MoveThroughVillageGoal(this, 1.0D, false, 10, () -> false));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 10.0F));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void tick() {
        if (getAttribute(Attributes.MOVEMENT_SPEED) != null) {
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(MOVE_SPEED);
        }
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide && random.nextInt(10) == 1) {
            level().addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), 1.0F),
                    getX(), getY() + 2.0D, getZ(), 0.0D, 0.0D, 0.0D);
            level().addParticle(ParticleTypes.LAVA, getX(), getY() + 2.0D, getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (!isAlive()) return;
        if (random.nextInt(200) == 1) setTarget(null);
        super.customServerAiStep();
        if (random.nextInt(10) != 1) return;

        LivingEntity target = findSomethingToAttack();
        if (target == null) return;

        getLookControl().setLookAt(target, 10.0F, 10.0F);
        getNavigation().moveTo(target, 1.25D);
        if (distanceToSqr(target) >= 64.0D || (random.nextInt(6) != 0 && random.nextInt(8) != 1)) return;

        SmallFireball fireball = new SmallFireball(level(), this,
                target.getX() - getX(),
                target.getY() + 0.75D - (getY() + 1.25D),
                target.getZ() - getZ());
        fireball.setPos(getX(), getY() + 1.25D, getZ());
        level().addFreshEntity(fireball);
        level().playSound(null, this, SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE,
                0.75F, 0.8F / (random.nextFloat() * 0.4F + 0.8F));
    }

    private LivingEntity findSomethingToAttack() {
        List<LivingEntity> candidates = level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(12.0D, 4.0D, 12.0D));
        candidates.sort(Comparator.comparingDouble(this::originalTargetScore));
        for (LivingEntity candidate : candidates) {
            if (isSuitableTarget(candidate)) return candidate;
        }
        return null;
    }

    private double originalTargetScore(LivingEntity target) {
        double score = distanceToSqr(target);
        if (target instanceof net.minecraft.world.entity.monster.Creeper) score /= 2.0D;
        double area = target.getBbWidth() * target.getBbHeight();
        return area > 1.0D ? score / area : score;
    }

    private boolean isSuitableTarget(LivingEntity target) {
        if (target == this || !target.isAlive() || target instanceof Monster
                || target instanceof ZombifiedPiglin || !getSensing().hasLineOfSight(target)) return false;
        return !(target instanceof Player player && player.isCreative());
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (target != null && !isSuitableTarget(target)) return;
        super.setTarget(target);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return super.doHurtTarget(target);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (tickCount % 10 == 0) {
            setSecondsOnFire(5);
            if (isInWater() && !level().isClientSide) doHurtTarget(this);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // The 1.12.2 JAR references a Kyuubi sound event but ships no Kyuubi audio asset.
        return null;
    }

    @Override protected SoundEvent getHurtSound(DamageSource source) { return ModSounds.ALOSAURUS_HURT.get(); }
    @Override protected SoundEvent getDeathSound() { return ModSounds.ALOSAURUS_DEATH.get(); }
    @Override protected float getSoundVolume() { return 0.75F; }
    @Override public float getVoicePitch() { return 1.0F; }
    @Override public boolean removeWhenFarAway(double distanceToClosestPlayer) { return !isPersistenceRequired(); }

    @Override
    protected Item getDropItem() {
        return switch (random.nextInt(6)) {
            case 0 -> Items.GOLD_NUGGET;
            case 1 -> com.santipdr.copyl.common.item.material.ModMaterialItems.URANIUM_NUGGET.get();
            case 2 -> com.santipdr.copyl.common.item.material.ModMaterialItems.TITANIUM_NUGGET.get();
            default -> null;
        };
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        dropItemRand(Items.COAL, 10);
        dropItemRand(Blocks.REDSTONE_BLOCK.asItem(), 3);
        dropItemRand(Blocks.QUARTZ_BLOCK.asItem(), 4);
    }

    private void dropItemRand(Item item, int amount) {
        for (int i = 0; i < amount; i++) {
            double x = getX() + random.nextInt(4) - random.nextInt(4);
            double z = getZ() + random.nextInt(4) - random.nextInt(4);
            level().addFreshEntity(new ItemEntity(level(), x, getY() + 1.0D, z, new ItemStack(item)));
        }
    }

    public static boolean checkSpawnRules(EntityType<KyuubiEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return true;
    }
}
