package com.santipdr.copyl.common.world;

import com.santipdr.copyl.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/** Ports the 1.12.2 grass-decoration pass that turns surface grass into ant hills. */
public final class AntHillFeature extends Feature<NoneFeatureConfiguration> {
    public AntHillFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        int x = context.origin().getX() + random.nextInt(16) + 8;
        int z = context.origin().getZ() + random.nextInt(16) + 8;
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, new BlockPos(x, 0, z));
        BlockPos ground = surface.below();
        if (level.getBlockState(ground).is(Blocks.GRASS_BLOCK)) {
            level.setBlock(ground, ModBlocks.ANT_BLOCK.get().defaultBlockState(), 2);
        }
        return true;
    }
}
