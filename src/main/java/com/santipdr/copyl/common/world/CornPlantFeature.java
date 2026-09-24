package com.santipdr.copyl.common.world;

import com.santipdr.copyl.common.block.CornPlantBlock;
import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.block.entity.CornPlantBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/** Ports the 1% grass-decoration corn patch pass and its 32 stalk attempts. */
public final class CornPlantFeature extends Feature<NoneFeatureConfiguration> {
    public CornPlantFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        if (level.getLevel().dimension() == Level.NETHER) {
            return false;
        }
        int x = context.origin().getX() + random.nextInt(16) + 8;
        int z = context.origin().getZ() + random.nextInt(16) + 8;
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, new BlockPos(x, 0, z));
        BlockPos.MutableBlockPos candidate = new BlockPos.MutableBlockPos();
        boolean generated = false;

        for (int i = 0; i < 32; i++) {
            candidate.set(surface.getX() + random.nextInt(8) - random.nextInt(8),
                    surface.getY() + random.nextInt(4) - random.nextInt(4),
                    surface.getZ() + random.nextInt(8) - random.nextInt(8));
            if (!level.isEmptyBlock(candidate) || level.getBlockState(candidate.below()).is(ModBlocks.CORN_PLANT.get())
                    || level.isEmptyBlock(candidate.below()) || !level.getBlockState(candidate.below()).is(Blocks.GRASS_BLOCK)) {
                continue;
            }
            int height = 0;
            BlockPos.MutableBlockPos stalk = candidate.mutable();
            while (height < 21 && (level.isEmptyBlock(stalk) || level.getBlockState(stalk).is(Blocks.TALL_GRASS))
                    && stalk.getY() < 255 && ModBlocks.CORN_PLANT.get().canSurvive(
                    ModBlocks.CORN_PLANT.get().defaultBlockState(), level, stalk)) {
                BlockState mature = ModBlocks.CORN_PLANT.get().defaultBlockState().setValue(CornPlantBlock.STAGE, 3);
                if (!level.setBlock(stalk, mature, 2)) break;
                if (level.getBlockEntity(stalk) instanceof CornPlantBlockEntity tile) {
                    int contribution = random.nextInt(5) + 3;
                    tile.setHeightContribution(contribution);
                    height += contribution;
                } else {
                    height += random.nextInt(5) + 3;
                }
                stalk.move(0, Math.max(1, height == 0 ? 1 : 0), 0); // corrected below to the segment contribution
                // Move to the next candidate height; each segment stores the height it contributes.
                int lastContribution = level.getBlockEntity(stalk.below()) instanceof CornPlantBlockEntity tile
                        ? tile.getHeightContribution() : 3;
                stalk.set(stalk.getX(), stalk.getY() + lastContribution, stalk.getZ());
            }
            level.setBlock(stalk, ModBlocks.CORN_PLANT.get().defaultBlockState(), 2);
            generated = true;
        }
        return generated;
    }
}
