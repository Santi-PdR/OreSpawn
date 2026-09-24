package com.santipdr.copyl.common.block;

import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.entity.RedAntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;

/** Ant nest block ported from 1.12.2 BlockAnt: releases red ants on sunny ticks. */
public final class AntHillBlock extends Block {
    public AntHillBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.DIRT)
                .strength(0.5F)
                .sound(SoundType.GRAVEL)
                .randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isDay() || !level.isEmptyBlock(pos.above())) {
            return;
        }

        AABB nearbyArea = new AABB(pos.getX() - 16.0D, 0.0D, pos.getZ() - 16.0D,
                pos.getX() + 16.0D, 200.0D, pos.getZ() + 16.0D);
        if (level.getEntitiesOfClass(RedAntEntity.class, nearbyArea).size() > 20) {
            return;
        }

        int amount = random.nextInt(6) + 2;
        for (int i = 0; i < amount; i++) {
            RedAntEntity ant = ModEntities.RED_ANT.get().create(level);
            if (ant == null) {
                continue;
            }
            ant.moveTo(pos.getX(), pos.getY() + 1.0D, pos.getZ(), random.nextFloat() * 360.0F, 0.0F);
            ant.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null, null);
            level.addFreshEntity(ant);
        }
    }
}
