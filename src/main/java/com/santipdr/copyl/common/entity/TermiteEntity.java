package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.entity.ai.LongRangeWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/** Port de danger.orespawn.entity.Termite con búsqueda y consumo de madera. */
public final class TermiteEntity extends AntEntity {
    private static final double MOVE_SPEED = 0.20D;
    private static final Set<Block> ORIGINAL_WOOD_BLOCKS = Set.of(
            Blocks.OAK_LOG, Blocks.SPRUCE_LOG, Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG,
            Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE, Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE,
            Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE,
            Blocks.OAK_SIGN,
            Blocks.OAK_SLAB, Blocks.SPRUCE_SLAB, Blocks.BIRCH_SLAB, Blocks.JUNGLE_SLAB, Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB,
            Blocks.OAK_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS,
            Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR,
            Blocks.OAK_PRESSURE_PLATE,
            Blocks.BOOKSHELF, Blocks.CRAFTING_TABLE
    );

    private int attackDelay = 20;
    private int closest = 99999;
    private BlockPos woodTarget;

    public TermiteEntity(EntityType<? extends TermiteEntity> type, Level level) {
        super(type, level, MOVE_SPEED, 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return antAttributes(5.0D, MOVE_SPEED, 2.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new PanicGoal(this, 1.4D));
        goalSelector.addGoal(2, new LongRangeWanderGoal(this, 8, 1.0D));
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
        if (attackDelay <= 0) {
            attackDelay = 20;
            if (level().getDifficulty() != Difficulty.PEACEFUL) {
                Player nearby = level().getNearestPlayer(this, 1.5D);
                if (nearby != null) {
                    doHurtTarget(nearby);
                }
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        if (!isRemoved() && random.nextInt(200) == 1) {
            setTarget(null);
        }

        if (!isRemoved() && random.nextInt(200) == 1) {
            closest = 99999;
            woodTarget = null;

            int radius = 1;
            while (radius < 8) {
                int vertical = Math.min(radius, 4);
                if (scanShell(blockPosition().above(), radius, vertical)) {
                    break;
                }
                radius += radius >= 5 ? 2 : 1;
            }

            if (woodTarget != null) {
                getNavigation().moveTo(woodTarget.getX(), woodTarget.getY(), woodTarget.getZ(), 1.0D);
                if (closest < 6) {
                    consumeWood(woodTarget);
                    heal(1.0F);
                }
            }
        }

        super.customServerAiStep();
    }

    private boolean scanShell(BlockPos origin, int radius, int vertical) {
        boolean found = false;
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -vertical; dy <= vertical; ++dy) {
                for (int dz = -radius; dz <= radius; ++dz) {
                    boolean shell = Math.abs(dx) == radius || Math.abs(dz) == radius || Math.abs(dy) == vertical;
                    if (!shell) {
                        continue;
                    }
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (!isOriginalWood(level().getBlockState(pos))) {
                        continue;
                    }
                    int distance = dx * dx + dy * dy + dz * dz;
                    if (distance < closest) {
                        closest = distance;
                        woodTarget = pos.immutable();
                    }
                    found = true;
                }
            }
        }
        return found;
    }

    private static boolean isOriginalWood(BlockState state) {
        if (state.is(net.minecraft.tags.BlockTags.BEDS)) {
            return true;
        }
        return ORIGINAL_WOOD_BLOCKS.contains(state.getBlock());
    }

    private void consumeWood(BlockPos pos) {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }

        // El original decide primero si esta interacción convierte en tierra o elimina el bloque.
        boolean removeBranch = random.nextInt(3) == 0;
        if (server.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            if (removeBranch) {
                server.removeBlock(pos, false);
            } else {
                server.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        }

        if (findBuddies() < 10) {
            TermiteEntity termite = ModEntities.TERMITE.get().create(server);
            if (termite != null) {
                double x = removeBranch ? pos.getX() + 0.1D : getX() + 0.1D;
                double y = removeBranch ? pos.getY() + 0.1D : getY() + 0.1D;
                double z = removeBranch ? pos.getZ() + 0.1D : getZ() + 0.1D;
                termite.moveTo(x, y, z, random.nextFloat() * 360.0F, 0.0F);
                server.addFreshEntity(termite);
            }
        }
    }

    private int findBuddies() {
        return level().getEntitiesOfClass(TermiteEntity.class, getBoundingBox().inflate(3.0D)).size();
    }

    public static boolean checkSpawnRules(EntityType<TermiteEntity> type, ServerLevelAccessor level,
                                          MobSpawnType reason, BlockPos pos, RandomSource random) {
        return AntEntity.checkAntSpawnRules(type, level, reason, pos, random);
    }

    @Nullable
    @Override
    public TermiteEntity getBreedOffspring(ServerLevel level, AgeableMob mate) {
        return null;
    }
}
