package com.santipdr.copyl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** Port de BlockTitanium/BlockUranium: partículas ocasionales alrededor de caras expuestas. */
public final class SparklingStorageBlock extends Block {
    public SparklingStorageBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(20) == 0) {
            ParticleSparkle.mixedOnExposedFaces(level, pos, random);
        }
    }
}
