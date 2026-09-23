package com.santipdr.copyl.common.world.structure;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

/**
 * Lógica original del StructureGenerator de 1.12.2, preparada pero NO conectada
 * a ningún evento/worldgen mientras Mining Dimension siga pausada.
 */
public final class StructureGenerator {
    private final GenericDungeon genericDungeon = new GenericDungeon();

    public void generateMiningChunk(ServerLevel level, RandomSource random, int chunkX, int chunkZ) {
        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        int y = 5 + random.nextInt(40); // 5..44, igual al original.
        if (random.nextInt(16) == 0) {
            genericDungeon.makeDungeon(level, x, y, z);
        }
    }
}
