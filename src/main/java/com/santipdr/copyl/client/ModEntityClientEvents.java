package com.santipdr.copyl.client;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.*;
import com.santipdr.copyl.client.renderer.*;
import com.santipdr.copyl.common.entity.ModEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModEntityClientEvents {
    private ModEntityClientEvents() {}
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
        event.registerLayerDefinition(AlosaurusModel.LAYER_LOCATION, AlosaurusModel::createBodyLayer);
        event.registerLayerDefinition(BeaverModel.LAYER_LOCATION, BeaverModel::createBodyLayer);
        event.registerLayerDefinition(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
        event.registerLayerDefinition(ButterflyModel.LAYER_LOCATION, ButterflyModel::createBodyLayer);
        event.registerLayerDefinition(CassowaryModel.LAYER_LOCATION, CassowaryModel::createBodyLayer);
        event.registerLayerDefinition(CryolophosaurusModel.LAYER_LOCATION, CryolophosaurusModel::createBodyLayer);
        event.registerLayerDefinition(DragonflyModel.LAYER_LOCATION, DragonflyModel::createBodyLayer);
        event.registerLayerDefinition(FireflyModel.LAYER_LOCATION, FireflyModel::createBodyLayer);
        event.registerLayerDefinition(GammaMetroidModel.LAYER_LOCATION, GammaMetroidModel::createBodyLayer);
        event.registerLayerDefinition(MosquitoModel.LAYER_LOCATION, MosquitoModel::createBodyLayer);
        event.registerLayerDefinition(MothModel.LAYER_LOCATION, MothModel::createBodyLayer);
        event.registerLayerDefinition(PointysaurusModel.LAYER_LOCATION, PointysaurusModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CAGE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN.get(), AlienRenderer::new);
        event.registerEntityRenderer(ModEntities.ALOSAURUS.get(), AlosaurusRenderer::new);
        event.registerEntityRenderer(ModEntities.BEAVER.get(), BeaverRenderer::new);
        event.registerEntityRenderer(ModEntities.BIRD.get(), BirdRenderer::new);
        event.registerEntityRenderer(ModEntities.BUTTERFLY.get(), ButterflyRenderer::new);
        event.registerEntityRenderer(ModEntities.CASSOWARY.get(), CassowaryRenderer::new);
        event.registerEntityRenderer(ModEntities.CRYOLOPHOSAURUS.get(), CryolophosaurusRenderer::new);
        event.registerEntityRenderer(ModEntities.DRAGONFLY.get(), DragonflyRenderer::new);
        event.registerEntityRenderer(ModEntities.FIREFLY.get(), FireflyRenderer::new);
        event.registerEntityRenderer(ModEntities.GAMMA_METROID.get(), GammaMetroidRenderer::new);
        event.registerEntityRenderer(ModEntities.MOSQUITO.get(), MosquitoRenderer::new);
        event.registerEntityRenderer(ModEntities.MOTH.get(), MothRenderer::new);
        event.registerEntityRenderer(ModEntities.POINTYSAURUS.get(), PointysaurusRenderer::new);
    }
}
