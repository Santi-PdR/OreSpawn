package com.santipdr.copyl.client;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.AlienModel;
import com.santipdr.copyl.client.model.BirdModel;
import com.santipdr.copyl.client.model.CryolophosaurusModel;
import com.santipdr.copyl.client.model.DragonflyModel;
import com.santipdr.copyl.client.model.GammaMetroidModel;
import com.santipdr.copyl.client.renderer.AlienRenderer;
import com.santipdr.copyl.client.renderer.BirdRenderer;
import com.santipdr.copyl.client.renderer.CryolophosaurusRenderer;
import com.santipdr.copyl.client.renderer.DragonflyRenderer;
import com.santipdr.copyl.client.renderer.GammaMetroidRenderer;
import com.santipdr.copyl.common.entity.ModEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Registro cliente de entidades portadas de OreSpawn. */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModEntityClientEvents {
    private ModEntityClientEvents() {
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AlienModel.LAYER_LOCATION, AlienModel::createBodyLayer);
        event.registerLayerDefinition(BirdModel.LAYER_LOCATION, BirdModel::createBodyLayer);
        event.registerLayerDefinition(CryolophosaurusModel.LAYER_LOCATION, CryolophosaurusModel::createBodyLayer);
        event.registerLayerDefinition(DragonflyModel.LAYER_LOCATION, DragonflyModel::createBodyLayer);
        event.registerLayerDefinition(GammaMetroidModel.LAYER_LOCATION, GammaMetroidModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CAGE_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.ALIEN.get(), AlienRenderer::new);
        event.registerEntityRenderer(ModEntities.BIRD.get(), BirdRenderer::new);
        event.registerEntityRenderer(ModEntities.CRYOLOPHOSAURUS.get(), CryolophosaurusRenderer::new);
        event.registerEntityRenderer(ModEntities.DRAGONFLY.get(), DragonflyRenderer::new);
        event.registerEntityRenderer(ModEntities.GAMMA_METROID.get(), GammaMetroidRenderer::new);
    }
}
