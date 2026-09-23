package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

/** EntityTypes porteados del OreSpawn 1.12.2. */
public final class ModEntities {
    public static final RegistryObject<EntityType<AlienEntity>> ALIEN = ModRegistries.ENTITY_TYPES.register("alien", () -> EntityType.Builder.of(AlienEntity::new, MobCategory.MONSTER).sized(1.1F, 3.25F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":alien"));
    public static final RegistryObject<EntityType<GammaMetroidEntity>> GAMMA_METROID = ModRegistries.ENTITY_TYPES.register("gammametroid", () -> EntityType.Builder.of(GammaMetroidEntity::new, MobCategory.CREATURE).sized(1.5F, 1.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":gammametroid"));
    public static final RegistryObject<EntityType<CryolophosaurusEntity>> CRYOLOPHOSAURUS = ModRegistries.ENTITY_TYPES.register("cryolophosaurus", () -> EntityType.Builder.of(CryolophosaurusEntity::new, MobCategory.MONSTER).sized(0.75F, 0.75F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":cryolophosaurus"));
    public static final RegistryObject<EntityType<BirdEntity>> BIRD = ModRegistries.ENTITY_TYPES.register("bird", () -> EntityType.Builder.of(BirdEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":bird"));
    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = ModRegistries.ENTITY_TYPES.register("butterfly", () -> EntityType.Builder.of(ButterflyEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":butterfly"));
    public static final RegistryObject<EntityType<CassowaryEntity>> CASSOWARY = ModRegistries.ENTITY_TYPES.register("cassowary", () -> EntityType.Builder.of(CassowaryEntity::new, MobCategory.CREATURE).sized(0.5F, 1.2F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":cassowary"));
    public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ModRegistries.ENTITY_TYPES.register("dragonfly", () -> EntityType.Builder.of(DragonflyEntity::new, MobCategory.CREATURE).sized(1.5F, 0.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":dragonfly"));
    public static final RegistryObject<EntityType<PointysaurusEntity>> POINTYSAURUS = ModRegistries.ENTITY_TYPES.register("pointysaurus", () -> EntityType.Builder.of(PointysaurusEntity::new, MobCategory.MONSTER).sized(2.9F, 2.9F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":pointysaurus"));
    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY = ModRegistries.ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.AMBIENT).sized(0.4F, 0.8F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":firefly"));
    public static final RegistryObject<EntityType<MosquitoEntity>> MOSQUITO = ModRegistries.ENTITY_TYPES.register("mosquito", () -> EntityType.Builder.of(MosquitoEntity::new, MobCategory.AMBIENT).sized(0.2F, 0.2F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":mosquito"));
    public static final RegistryObject<EntityType<MothEntity>> MOTH = ModRegistries.ENTITY_TYPES.register("moth", () -> EntityType.Builder.of(MothEntity::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":moth"));
    public static final RegistryObject<EntityType<CageProjectileEntity>> CAGE_PROJECTILE = ModRegistries.ENTITY_TYPES.register("thrown_critter_cage", () -> EntityType.Builder.<CageProjectileEntity>of(CageProjectileEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":thrown_critter_cage"));
    public static void bootstrap() {}
    private ModEntities() {}
}
