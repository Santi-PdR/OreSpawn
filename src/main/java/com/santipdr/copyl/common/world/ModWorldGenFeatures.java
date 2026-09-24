package com.santipdr.copyl.common.world;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.RegistryObject;

public final class ModWorldGenFeatures {
    public static final RegistryObject<Feature<?>> WORLDGEN_ORES =
            ModRegistries.FEATURES.register("worldgen_ores", WorldGenOresFeature::new);

    public static void bootstrap() {}\n\n    private ModWorldGenFeatures() {}
}
