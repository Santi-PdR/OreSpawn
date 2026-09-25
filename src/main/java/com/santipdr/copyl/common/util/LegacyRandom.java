package com.santipdr.copyl.common.util;

import java.util.Random;

/**
 * Shared random stream used by the original OreSpawn code.
 *
 * <p>The 1.12.2 mod creates one java.util.Random with seed 151 and shares it
 * across gameplay systems. Keep decisions that used OreSpawnRand on this
 * stream instead of substituting a per-level random source.</p>
 */
public final class LegacyRandom {
    private static final Random RANDOM = new Random(151L);

    private LegacyRandom() {
    }

    public static synchronized int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }
}
