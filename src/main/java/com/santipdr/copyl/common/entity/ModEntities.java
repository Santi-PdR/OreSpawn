package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

/** Primer grupo de EntityTypes del OreSpawn 1.12.2. */
public final class ModEntities {
    public static final RegistryObject<EntityType<AlienEntity>> ALIEN =
            ModRegistries.ENTITY_TYPES.register("alien", () -> EntityType.Builder
                    .of(AlienEntity::new, MobCategory.MONSTER)
                    .sized(1.1F, 3.25F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .build(CopyL.MOD_ID + ":alien"));

    public static final RegistryObject<EntityType<GammaMetroidEntity>> GAMMA_METROID =
            ModRegistries.ENTITY_TYPES.register("gammametroid", () -> EntityType.Builder
                    .of(GammaMetroidEntity::new, MobCategory.CREATURE)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .build(CopyL.MOD_ID + ":gammametroid"));

    public static final RegistryObject<EntityType<CryolophosaurusEntity>> CRYOLOPHOSAURUS =
            ModRegistries.ENTITY_TYPES.register("cryolophosaurus", () -> EntityType.Builder
                    .of(CryolophosaurusEntity::new, MobCategory.MONSTER)
                    .sized(0.75F, 0.75F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .build(CopyL.MOD_ID + ":cryolophosaurus"));

    public static void bootstrap() {
    }

    private ModEntities() {
    }
}
