package com.santipdr.copyl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

final class ParticleSparkle {
    private static final double OFFSET = 0.0625D;

    private ParticleSparkle() {}

    static void redstoneOnExposedFaces(Level level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            if (level.getBlockState(neighbor).isSolidRender(level, neighbor)) continue;
            if (random.nextInt(3) != 2) continue;
            spawnOnFace(level, pos, direction, random, DustParticleOptions.REDSTONE);
        }
    }

    static void mixedOnExposedFaces(Level level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.relative(direction);
            if (level.getBlockState(neighbor).isSolidRender(level, neighbor)) continue;
            ParticleOptions particle = switch (random.nextInt(3)) {
                case 0 -> ParticleTypes.FLAME;
                case 1 -> ParticleTypes.SMOKE;
                default -> DustParticleOptions.REDSTONE;
            };
            spawnOnFace(level, pos, direction, random, particle);
        }
    }

    private static void spawnOnFace(Level level, BlockPos pos, Direction direction, RandomSource random, ParticleOptions particle) {
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + random.nextDouble();
        double z = pos.getZ() + random.nextDouble();
        switch (direction) {
            case DOWN -> y = pos.getY() - OFFSET;
            case UP -> y = pos.getY() + 1.0D + OFFSET;
            case NORTH -> z = pos.getZ() - OFFSET;
            case SOUTH -> z = pos.getZ() + 1.0D + OFFSET;
            case WEST -> x = pos.getX() - OFFSET;
            case EAST -> x = pos.getX() + 1.0D + OFFSET;
        }
        level.addParticle(particle, x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
