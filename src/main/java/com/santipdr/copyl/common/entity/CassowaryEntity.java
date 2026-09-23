package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Port directo de danger.orespawn.entity.Cassowary del JAR 1.12.2 proporcionado. */
public final class CassowaryEntity extends Animal {
    private static final double MOVE_SPEED = 0.25D;

    public CassowaryEntity(EntityType<? extends CassowaryEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, MOVE_SPEED)
                .add(Attributes.FOLLOW_RANGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        // El constructor 1.12.2 registra EntityAISwimming dos veces (prioridades 0 y 1).
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Monster.class, 8.0F, 1.0D, 1.399999976158142D));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.0D, 1.399999976158142D));
        goalSelector.addGoal(4, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 12.0F));
        // MyEntityAIWander original: 1/90, RandomPositionGenerator(10, 7), velocidad 1.0.
        goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D, 90));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
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
    }

    public static boolean checkSpawnRules(EntityType<CassowaryEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        // El 1.12.2 sólo comprobaba World#isDaytime(). El SpawnPlacement se encarga del suelo.
        return level.getLevel().isDay();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        // Original: las crías no pueden desaparecer; un adulto sí.
        return !isBaby();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        // onDeath() del original reproducía manualmente el mismo sonido de daño.
        return ModSounds.DUCK_HURT.get();
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean isWheat(ItemStack stack) {
        return stack.is(Items.WHEAT);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        // func_70877_b(ItemStack) devuelve false en el JAR, aunque isWheat() exista.
        return false;
    }

    @Nullable
    @Override
    public CassowaryEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return ModEntities.CASSOWARY.get().create(level);
    }
}
