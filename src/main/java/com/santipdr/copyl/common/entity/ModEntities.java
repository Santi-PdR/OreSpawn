package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

/** EntityTypes porteados del OreSpawn 1.12.2. */
public final class ModEntities {
    public static final RegistryObject<EntityType<AlienEntity>> ALIEN = ModRegistries.ENTITY_TYPES.register("alien", () -> EntityType.Builder.of(AlienEntity::new, MobCategory.MONSTER).sized(1.1F, 3.25F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":alien"));
    public static final RegistryObject<EntityType<AlosaurusEntity>> ALOSAURUS = ModRegistries.ENTITY_TYPES.register("alosaurus", () -> EntityType.Builder.of(AlosaurusEntity::new, MobCategory.MONSTER).sized(1.9F, 3.6F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":alosaurus"));
    public static final RegistryObject<EntityType<BaryonyxEntity>> BARYONYX = ModRegistries.ENTITY_TYPES.register("baryonyx", () -> EntityType.Builder.of(BaryonyxEntity::new, MobCategory.CREATURE).sized(1.5F, 2.8F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":baryonyx"));
    public static final RegistryObject<EntityType<GammaMetroidEntity>> GAMMA_METROID = ModRegistries.ENTITY_TYPES.register("gammametroid", () -> EntityType.Builder.of(GammaMetroidEntity::new, MobCategory.CREATURE).sized(1.5F, 1.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":gammametroid"));
    public static final RegistryObject<EntityType<CryolophosaurusEntity>> CRYOLOPHOSAURUS = ModRegistries.ENTITY_TYPES.register("cryolophosaurus", () -> EntityType.Builder.of(CryolophosaurusEntity::new, MobCategory.MONSTER).sized(0.75F, 0.75F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":cryolophosaurus"));
    public static final RegistryObject<EntityType<BeaverEntity>> BEAVER = ModRegistries.ENTITY_TYPES.register("beaver", () -> EntityType.Builder.of(BeaverEntity::new, MobCategory.CREATURE).sized(0.6F, 0.8F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":beaver"));
    public static final RegistryObject<EntityType<BirdEntity>> BIRD = ModRegistries.ENTITY_TYPES.register("bird", () -> EntityType.Builder.of(BirdEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":bird"));
    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = ModRegistries.ENTITY_TYPES.register("butterfly", () -> EntityType.Builder.of(ButterflyEntity::new, MobCategory.CREATURE).sized(0.4F, 0.4F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":butterfly"));
    public static final RegistryObject<EntityType<CassowaryEntity>> CASSOWARY = ModRegistries.ENTITY_TYPES.register("cassowary", () -> EntityType.Builder.of(CassowaryEntity::new, MobCategory.CREATURE).sized(0.5F, 1.2F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":cassowary"));
    public static final RegistryObject<EntityType<CamarasaurusEntity>> CAMARASAURUS = ModRegistries.ENTITY_TYPES.register("camarasaurus", () -> EntityType.Builder.of(CamarasaurusEntity::new, MobCategory.CREATURE).sized(0.5F, 1.2F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":camarasaurus"));
    public static final RegistryObject<EntityType<CaveFisherEntity>> CAVE_FISHER = ModRegistries.ENTITY_TYPES.register("cavefisher", () -> EntityType.Builder.of(CaveFisherEntity::new, MobCategory.MONSTER).sized(1.4F, 0.9F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":cavefisher"));
    public static final RegistryObject<EntityType<DragonflyEntity>> DRAGONFLY = ModRegistries.ENTITY_TYPES.register("dragonfly", () -> EntityType.Builder.of(DragonflyEntity::new, MobCategory.CREATURE).sized(1.5F, 0.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":dragonfly"));
    public static final RegistryObject<EntityType<PointysaurusEntity>> POINTYSAURUS = ModRegistries.ENTITY_TYPES.register("pointysaurus", () -> EntityType.Builder.of(PointysaurusEntity::new, MobCategory.MONSTER).sized(2.9F, 2.9F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":pointysaurus"));
    public static final RegistryObject<EntityType<FireflyEntity>> FIREFLY = ModRegistries.ENTITY_TYPES.register("firefly", () -> EntityType.Builder.of(FireflyEntity::new, MobCategory.AMBIENT).sized(0.4F, 0.8F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":firefly"));
    public static final RegistryObject<EntityType<MosquitoEntity>> MOSQUITO = ModRegistries.ENTITY_TYPES.register("mosquito", () -> EntityType.Builder.of(MosquitoEntity::new, MobCategory.AMBIENT).sized(0.2F, 0.2F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":mosquito"));
    public static final RegistryObject<EntityType<MothEntity>> MOTH = ModRegistries.ENTITY_TYPES.register("moth", () -> EntityType.Builder.of(MothEntity::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":moth"));
    public static final RegistryObject<EntityType<RedCowEntity>> RED_COW = ModRegistries.ENTITY_TYPES.register("redcow", () -> EntityType.Builder.of(RedCowEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":redcow"));
    public static final RegistryObject<EntityType<StinkBugEntity>> STINK_BUG = ModRegistries.ENTITY_TYPES.register("stinkbug", () -> EntityType.Builder.of(StinkBugEntity::new, MobCategory.CREATURE).sized(0.55F, 0.55F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":stinkbug"));
    public static final RegistryObject<EntityType<CageProjectileEntity>> CAGE_PROJECTILE = ModRegistries.ENTITY_TYPES.register("thrown_critter_cage", () -> EntityType.Builder.<CageProjectileEntity>of(CageProjectileEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(1).build(CopyL.MOD_ID + ":thrown_critter_cage"));
    public static void bootstrap() {}
    private ModEntities() {}
}
