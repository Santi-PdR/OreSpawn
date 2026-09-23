package com.santipdr.copyl.common.world.structure;

import com.santipdr.copyl.CopyL;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

/**
 * Equivalente moderno del helper WorldGenStructure de OreSpawn.
 * El JAR 1.12.2 no trae templates .nbt, por lo que queda listo para los que
 * realmente aparezcan durante el resto del port, sin inventar ninguno.
 */
public final class WorldGenStructure {
    private final ResourceLocation structureId;

    public WorldGenStructure(String structureName) {
        this.structureId = new ResourceLocation(CopyL.MOD_ID, structureName);
    }

    public boolean generate(ServerLevel level, BlockPos pos) {
        Optional<StructureTemplate> template = level.getStructureManager().get(structureId);
        if (template.isEmpty()) {
            return false;
        }
        StructurePlaceSettings settings = new StructurePlaceSettings();
        template.get().placeInWorld(level, pos, pos, settings, level.getRandom(), 3);
        return true;
    }
}
