package com.santipdr.copyl.common.world;

import com.santipdr.copyl.common.registry.ModRegistries;
import com.santipdr.copyl.common.world.AntHillFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.RegistryObject;

public final class ModWorldGenFeatures {
    public static final RegistryObject<Feature<?>> WORLDGEN_ORES =
            ModRegistries.FEATURES.register("worldgen_ores", WorldGenOresFeature::new);

    public static final RegistryObject<Feature<?>> ANT_HILLS =
            ModRegistries.FEATURES.register("ant_hills", AntHillFeature::new);

    public static final RegistryObject<Feature<?>> CORN_PLANT =
            ModRegistries.FEATURES.register("corn_plant", CornPlantFeature::new);



    public static void bootstrap() {
    }

    private ModWorldGenFeatures() {}
