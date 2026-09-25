package com.santipdr.copyl.common.entity;

/**
 * Mutable compatibility switches that were public static fields in the 1.12.2 OreSpawn main class.
 */
public final class LegacyGameplayFlags {
    /**
     * Mirrors OreSpawnMain.PlayNicely. Zero is the original default; nonzero prevents hostile or
     * destructive behaviours that explicitly checked this flag.
     */
    public static volatile int PLAY_NICELY = 0;

    private LegacyGameplayFlags() {}
}
