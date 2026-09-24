package com.santipdr.copyl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import java.util.function.Supplier;

/** Original OreSpawn crops that attract or hatch their corresponding insects. */
public final class InsectSpawnPlant extends CropBlock {
    private final Supplier<? extends ItemLike> seed;
    private final Supplier<? extends EntityType<? extends Mob>> insect;
    private final boolean spawnsByDay;
    private final int matureSpawnRate;

    public InsectSpawnPlant(Supplier<? extends ItemLike> seed,
                            Supplier<? extends EntityType<? extends Mob>> insect,
                            boolean spawnsByDay, int matureSpawnRate) {
        super(Properties.copy(Blocks.WHEAT));
        this.seed = seed;
        this.insect = insect;
        this.spawnsByDay = spawnsByDay;
        this.matureSpawnRate = matureSpawnRate;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return seed.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (level.isRaining() || level.isDay() != spawnsByDay) {
            return;
        }
        AABB area = new AABB(pos.getX() - 50.0D, 0.0D, pos.getZ() - 50.0D,
                pos.getX() + 50.0D, 200.0D, pos.getZ() + 50.0D);
        EntityType<? extends Mob> type = insect.get();
        if (level.getEntitiesOfClass(Mob.class, area, (Mob mob) -> mob.getType() == type).size() > 15) {
            return;
        }
        int rate = matureSpawnRate - (state.getValue(AGE) & 7);
        if (rate > 1 && random.nextInt(rate) != 0) {
            return;
        }
        if (!level.isEmptyBlock(pos.above())) {
            return;
        }
        Mob mob = type.create(level);
        if (mob != null) {
            mob.moveTo(pos.getX(), pos.getY() + 1.0D, pos.getZ(), 0.0F, 0.0F);
            level.addFreshEntity(mob);
        }
    }
}
