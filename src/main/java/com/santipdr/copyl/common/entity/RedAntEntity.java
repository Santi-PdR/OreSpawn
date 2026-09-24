package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Port de danger.orespawn.entity.RedAnt. La interacción de dimensión queda bloqueada hasta portar Mining Dimension. */
public final class RedAntEntity extends AntEntity {
    private static final double MOVE_SPEED = 0.20D;
    private int attackDelay = 20;

    public RedAntEntity(EntityType<? extends RedAntEntity> type, Level level) {
        super(type, level, MOVE_SPEED, 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return antAttributes(2.0D, MOVE_SPEED, 1.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.PanicGoal(this, 1.4D));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.addGoal(2, new com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal(this, 10, 1.0D));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (random.nextInt(15) != 0 || level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        return target.hurt(damageSources().mobAttack(this), 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide || isRemoved()) {
            return;
        }

        if (attackDelay > 0) {
            --attackDelay;
        }
        if (attackDelay > 0) {
            return;
        }
        attackDelay = 20;

        if (level().getDifficulty() == Difficulty.PEACEFUL) {
            return;
        }

        Player nearby = level().getNearestPlayer(this, 1.5D);
        if (nearby != null) {
            doHurtTarget(nearby);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        // En 1.12.2 una mano vacía alterna entre Overworld y Mining Dimension.
        // Mining Dimension está pausada por decisión del port, así que no se crea un destino falso.
        return super.mobInteract(player, hand);
    }

    public static boolean checkSpawnRules(EntityType<RedAntEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return AntEntity.checkAntSpawnRules(type, level, reason, pos, random);
    }

    @Nullable
    @Override
    public RedAntEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return null;
    }
}
