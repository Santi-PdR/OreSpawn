package com.santipdr.copyl.common.world;

import com.santipdr.copyl.CopyL;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * Resource keys for dimensions referenced by the original OreSpawn logic.
 *
 * The Mining Dimension is a dynamic datapack registry entry defined by
 * data/copyl/dimension/mining.json and data/copyl/dimension_type/mining.json.
 * This class provides the key used by teleports and commands; it does not
 * create a second, code-registered dimension.
 */
public final class ModDimensionKeys {
    public static final ResourceKey<Level> MINING = ResourceKey.create(
            Registries.DIMENSION,
            new ResourceLocation(CopyL.MOD_ID, "mining")
    );

    private ModDimensionKeys() {}
}
