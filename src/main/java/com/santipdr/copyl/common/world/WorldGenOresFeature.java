package com.santipdr.copyl.common.world;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/** Ports the registered 1.12.2 WorldGenOres pass, including its per-vein counts and height ranges. */
public final class WorldGenOresFeature extends Feature<NoneFeatureConfiguration> {
    public WorldGenOresFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        int chunkMinX = context.origin().getX() - Math.floorMod(context.origin().getX(), 16);
        int chunkMinZ = context.origin().getZ() - Math.floorMod(context.origin().getZ(), 16);

        generate(context, random, ModBlocks.URANIUM_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ,
                3, 20, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.TITANIUM_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ,
                3, 20, random.nextInt(5) + 5, 3);

        generate(context, random, ModBlocks.ALOSAURUS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.BARYONYX_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.CAMARASAURUS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.CRYOLOPHOSAURUS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.POINTYSAURUS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.TREX_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.BIRD_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.COW_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.CREEPER_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.GHAST_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.HORSE_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.PIG_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.ZOMBIE_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.ALIEN_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.CAVEFISHER_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.NASTYSAURUS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.VELOCITYRAPTOR_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.GAMMAMETROID_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.SPYRO_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.DRAGONFLY_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.BEAVER_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.BRUTALFLY_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.MOTHRA_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.MANTIS_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.KYUUBI_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.CASSOWARY_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.REDCOW_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);
        generate(context, random, ModBlocks.STINKBUG_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ, 40, 80, random.nextInt(5) + 5, 3);

        generate(context, random, ModBlocks.AMETHYST_ORE.get().defaultBlockState(), chunkMinX, chunkMinZ,
                3, 15, random.nextInt(4) + 4, 1);
        return true;
    }

    private static void generate(FeaturePlaceContext<NoneFeatureConfiguration> context, RandomSource random,
                                 BlockState ore, int chunkMinX, int chunkMinZ,
                                 int minY, int maxY, int veinSize, int chances) {
        int deltaY = maxY - minY;
        for (int attempt = 0; attempt < chances; attempt++) {
            int x = chunkMinX + random.nextInt(16) + 8;
            int y = minY + random.nextInt(deltaY);
            int z = chunkMinZ + random.nextInt(16) + 8;
            generateVein(context, random, ore, x, y, z, veinSize);
        }
    }

    /** Same ellipsoid vein profile used by 1.12.2 WorldGenMinable, replacing stone only. */
    private static void generateVein(FeaturePlaceContext<NoneFeatureConfiguration> context, RandomSource random,
                                     BlockState ore, int x, int y, int z, int veinSize) {
        float angle = random.nextFloat() * (float) Math.PI;
        double x0 = x + Math.sin(angle) * veinSize / 8.0F;
        double x1 = x - Math.sin(angle) * veinSize / 8.0F;
        double z0 = z + Math.cos(angle) * veinSize / 8.0F;
        double z1 = z - Math.cos(angle) * veinSize / 8.0F;
        double y0 = y + random.nextInt(3) - 2;
        double y1 = y + random.nextInt(3) - 2;
        MutableBlockPos cursor = new MutableBlockPos();

        for (int step = 0; step <= veinSize; step++) {
            double cx = x0 + (x1 - x0) * step / veinSize;
            double cy = y0 + (y1 - y0) * step / veinSize;
            double cz = z0 + (z1 - z0) * step / veinSize;
            double radius = random.nextDouble() * veinSize / 16.0;
            double rx = (Math.sin(step * Math.PI / veinSize) + 1.0) * radius + 1.0;
            double ry = (Math.sin(step * Math.PI / veinSize) + 1.0) * radius + 1.0;

            int minX = (int) Math.floor(cx - rx / 2.0);
            int minY = (int) Math.floor(cy - ry / 2.0);
            int minZ = (int) Math.floor(cz - rx / 2.0);
            int maxX = (int) Math.floor(cx + rx / 2.0);
            int maxY = (int) Math.floor(cy + ry / 2.0);
            int maxZ = (int) Math.floor(cz + rx / 2.0);

            for (int bx = minX; bx <= maxX; bx++) {
                double dx = (bx + 0.5 - cx) / (rx / 2.0);
                if (dx * dx >= 1.0) continue;
                for (int by = minY; by <= maxY; by++) {
                    double dy = (by + 0.5 - cy) / (ry / 2.0);
                    if (dx * dx + dy * dy >= 1.0) continue;
                    for (int bz = minZ; bz <= maxZ; bz++) {
                        double dz = (bz + 0.5 - cz) / (rx / 2.0);
                        if (dx * dx + dy * dy + dz * dz >= 1.0) continue;
                        if (by < context.level().getMinBuildHeight() || by >= context.level().getMaxBuildHeight()) continue;
                        BlockPos pos = cursor.set(bx, by, bz);
                        if (context.level().getBlockState(pos).is(Blocks.STONE)) {
                            context.level().setBlock(pos, ore, 2);
                        }
                    }
                }
            }
        }
    }
}
