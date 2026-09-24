package com.santipdr.copyl.common.world.structure;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.world.ModDimensionKeys;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Connects OreSpawn's 1.12.2 structure generator to newly generated Mining Dimension chunks. */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID)
public final class MiningDungeonChunkEvents {
    private static final StructureGenerator GENERATOR = new StructureGenerator();

    private MiningDungeonChunkEvents() {}

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!event.isNewChunk() || !(event.getLevel() instanceof ServerLevel level)
                || !level.dimension().equals(ModDimensionKeys.MINING)) {
            return;
        }

        ChunkPos chunkPos = event.getChunk().getPos();
        // Stable per world/chunk, so the 1/16 roll does not depend on chunk load order.
        RandomSource random = RandomSource.create(level.getSeed() ^ chunkPos.toLong());
        GENERATOR.generateMiningChunk(level, random, chunkPos.x, chunkPos.z);
    }
}
