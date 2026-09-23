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
        event.put(ModEntities.GAMMA_METROID.get(), GammaMetroidEntity.createAttributes().build());
        event.put(ModEntities.CRYOLOPHOSAURUS.get(), CryolophosaurusEntity.createAttributes().build());
        event.put(ModEntities.BIRD.get(), BirdEntity.createAttributes().build());
        event.put(ModEntities.BUTTERFLY.get(), ButterflyEntity.createAttributes().build());
        event.put(ModEntities.DRAGONFLY.get(), DragonflyEntity.createAttributes().build());
        event.put(ModEntities.POINTYSAURUS.get(), PointysaurusEntity.createAttributes().build());
        event.put(ModEntities.FIREFLY.get(), FireflyEntity.createAttributes().build());
        event.put(ModEntities.MOSQUITO.get(), MosquitoEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.ALIEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AlienEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.GAMMA_METROID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GammaMetroidEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.CRYOLOPHOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CryolophosaurusEntity::checkSpawnRules);
            SpawnPlacements.register(ModEntities.POINTYSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PointysaurusEntity::checkSpawnRules);
        });
    }
    private ModEntityEvents() {}
}
