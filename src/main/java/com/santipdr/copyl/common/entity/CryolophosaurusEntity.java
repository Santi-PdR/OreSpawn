package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import com.santipdr.copyl.common.item.material.ModMaterialItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/** Port de danger.orespawn.entity.Cryolophosaurus del JAR 1.12.2 proporcionado. */
public final class CryolophosaurusEntity extends Monster {
    private static final double ORIGINAL_MOVE_SPEED = 0.25D;

    public CryolophosaurusEntity(EntityType<? extends CryolophosaurusEntity> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.MOVEMENT_SPEED, ORIGINAL_MOVE_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 18.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.35D));
        this.goalSelector.addGoal(3, new LongRangeWanderGoal(this, 10, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
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

        if (this.random.nextInt(200) == 1) {
            this.setTarget(null);
        }

        if (this.random.nextInt(5) == 1) {
            LivingEntity target = findSomethingToAttack();
            if (target != null) {
                this.getNavigation().moveTo(target, 1.25D);
                if (this.distanceToSqr(target) < 5.0D
                        && (this.random.nextInt(12) == 0 || this.random.nextInt(14) == 1)) {
                    this.doHurtTarget(target);
                }
            }
        }
    }

    private LivingEntity findSomethingToAttack() {
        if (LegacyGameplayFlags.PLAY_NICELY != 0) {
            return null;
        }
        AABB search = this.getBoundingBox().inflate(9.0D, 2.0D, 9.0D);
        List<LivingEntity> candidates = this.level().getEntitiesOfClass(LivingEntity.class, search, this::isSuitableTargetOriginal);
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    private boolean isSuitableTargetOriginal(LivingEntity target) {
        if (target == this || !target.isAlive() || !this.getSensing().hasLineOfSight(target)) {
            return false;
        }
        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return false;
        }

        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        if (id != null && CopyL.MOD_ID.equals(id.getNamespace())) {
            String path = id.getPath();
            if (path.equals("alosaurus")
                    || path.equals("trex")
                    || path.equals("cryolophosaurus")
                    || path.equals("ant")
                    || path.equals("red_ant")) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(6) == 0 ? ModSounds.CRYO_LIVING.get() : null;
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
        return 0.75F;
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        Item selected = switch (this.random.nextInt(10)) {
            case 0 -> Items.CHICKEN;
            case 1 -> ModMaterialItems.URANIUM_NUGGET.get();
            case 2 -> ModMaterialItems.TITANIUM_NUGGET.get();
            default -> null;
        };
        if (selected == null) {
            return;
        }

        int count = this.random.nextInt(3);
        if (looting > 0) {
            count += this.random.nextInt(looting + 1);
        }
        for (int i = 0; i < count; i++) {
            this.spawnAtLocation(selected);
        }
    }

    public static boolean checkSpawnRules(EntityType<CryolophosaurusEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        if (!Monster.checkMonsterSpawnRules(type, level, reason, pos, random)) {
            return false;
        }
        return pos.getY() <= 50 || !level.getLevel().isDay();
    }
}
