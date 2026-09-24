package com.santipdr.copyl.common.world;

import com.santipdr.copyl.CopyL;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * Resource keys for dimensions referenced by original OreSpawn logic.
 *
 * The Mining Dimension is intentionally NOT registered here. Keeping only the
 * key lets entity logic be ported and compiled without creating the paused
 * dimension or making it appear in the Overworld.
 */
public final class ModDimensionKeys {
    public static final ResourceKey<Level> MINING = ResourceKey.create(
            Registries.DIMENSION,
            new ResourceLocation(CopyL.MOD_ID, "mining")
    );

    private ModDimensionKeys() {}
}
