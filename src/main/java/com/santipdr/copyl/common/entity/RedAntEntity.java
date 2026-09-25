package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.world.DimensionTeleport;
import com.santipdr.copyl.common.world.ModDimensionKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/** Port directo de danger.orespawn.entity.RedAnt del JAR 1.12.2 proporcionado. */
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
        goalSelector.addGoal(2, new com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal(this, 10, 1.0D));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (level().getRandom().nextInt(15) != 0 || level().getDifficulty() == Difficulty.PEACEFUL) {
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
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty() && !level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            // Original 1.12.2 behavior:
            // Overworld -> Mining Dimension; any other dimension -> Overworld.
            // The Mining Dimension itself remains paused. When it is absent,
            // DimensionTeleport simply leaves the player where they are.
            var target = level().dimension().equals(Level.OVERWORLD)
                    ? ModDimensionKeys.MINING
                    : Level.OVERWORLD;
            DimensionTeleport.teleportToDimension(serverPlayer, target, player.getX(), player.getZ());
        }
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
