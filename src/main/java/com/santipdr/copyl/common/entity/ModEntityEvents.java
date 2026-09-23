package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.CopyL;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntityEvents {
    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ALIEN.get(), AlienEntity.createAttributes().build());
        event.put(ModEntities.ALOSAURUS.get(), AlosaurusEntity.createAttributes().build());
        event.put(ModEntities.BARYONYX.get(), BaryonyxEntity.createAttributes().build());
        event.put(ModEntities.GAMMA_METROID.get(), GammaMetroidEntity.createAttributes().build());
        event.put(ModEntities.CRYOLOPHOSAURUS.get(), CryolophosaurusEntity.createAttributes().build());
        event.put(ModEntities.BEAVER.get(), BeaverEntity.createAttributes().build());
        event.put(ModEntities.BIRD.get(), BirdEntity.createAttributes().build());
        event.put(ModEntities.BUTTERFLY.get(), ButterflyEntity.createAttributes().build());
        event.put(ModEntities.CASSOWARY.get(), CassowaryEntity.createAttributes().build());
        event.put(ModEntities.CAMARASAURUS.get(), CamarasaurusEntity.createAttributes().build());
        event.put(ModEntities.CAVE_FISHER.get(), CaveFisherEntity.createAttributes().build());
        event.put(ModEntities.DRAGONFLY.get(), DragonflyEntity.createAttributes().build());
        event.put(ModEntities.POINTYSAURUS.get(), PointysaurusEntity.createAttributes().build());
        event.put(ModEntities.FIREFLY.get(), FireflyEntity.createAttributes().build());
        event.put(ModEntities.MOSQUITO.get(), MosquitoEntity.createAttributes().build());
        event.put(ModEntities.MOTH.get(), MothEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.ALIEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlienEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.ALOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlosaurusEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.BARYONYX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaryonyxEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.GAMMA_METROID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GammaMetroidEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.CRYOLOPHOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CryolophosaurusEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.BEAVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeaverEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.CASSOWARY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CassowaryEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.CAMARASAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CamarasaurusEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.CAVE_FISHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaveFisherEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.POINTYSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PointysaurusEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.MOTH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MothEntity::checkSpawnRules);
        });
    }
    private ModEntityEvents() {}
}
