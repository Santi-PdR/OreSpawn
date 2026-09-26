package com.santipdr.copyl.common.world;

import com.mojang.serialization.MapCodec;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.registry.ModRegistries;
import java.util.Locale;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.minecraftforge.registries.RegistryObject;

/**
 * Recreates the 1.12 EntitySpawns biome-name filters for Dragonfly and Mosquito.
 * Dragonfly matched registry IDs containing "swamp" or "lake"; Mosquito was
 * registered only in the vanilla Swampland biome.
 */
public final class ModBiomeModifiers {
    public static final RegistryObject<MapCodec<LegacySwampLakeInsectSpawns>> LEGACY_SWAMP_LAKE_INSECT_SPAWNS =
            ModRegistries.BIOME_MODIFIER_SERIALIZERS.register(
                    "legacy_swamp_lake_insect_spawns",
                    () -> LegacySwampLakeInsectSpawns.CODEC);

    public static void bootstrap() {
        // Force codec registration before the Forge biome modifier registry is populated.
    }

    private ModBiomeModifiers() {
    }

    public record LegacySwampLakeInsectSpawns() implements BiomeModifier {
        public static final MapCodec<LegacySwampLakeInsectSpawns> CODEC =
                MapCodec.unit(new LegacySwampLakeInsectSpawns());

        @Override
        public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
            if (phase != Phase.ADD) {
                return;
            }

            String id = biome.unwrapKey()
                    .map(ResourceKey::location)
                    .map(location -> location.toString().toLowerCase(Locale.ROOT))
                    .orElse("");

            if (id.contains("swamp") || id.contains("lake")) {
                builder.getMobSpawnSettings().addSpawn(
                        MobCategory.CREATURE,
                        new SpawnerData(ModEntities.DRAGONFLY.get(), 15, 1, 1));
            }

            if (id.equals("minecraft:swamp")) {
                builder.getMobSpawnSettings().addSpawn(
                        MobCategory.CREATURE,
                        new SpawnerData(ModEntities.MOSQUITO.get(), 15, 1, 1));
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }
}
