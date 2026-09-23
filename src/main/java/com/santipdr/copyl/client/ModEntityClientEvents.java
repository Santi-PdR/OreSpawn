package com.santipdr.copyl.client;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.ModEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Registro cliente de entidades sin modelo visual propio. */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModEntityClientEvents {
    private ModEntityClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // EntityCage no tenia renderer visible en el JAR 1.12.2; solo mostraba particulas.
        event.registerEntityRenderer(ModEntities.CAGE_PROJECTILE.get(), NoopRenderer::new);
    }
}
