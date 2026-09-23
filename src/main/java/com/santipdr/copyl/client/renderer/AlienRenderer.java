package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.AlienModel;
import com.santipdr.copyl.common.entity.AlienEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderAlien. */
public final class AlienRenderer extends MobRenderer<AlienEntity, AlienModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/alien_exact.png");

    public AlienRenderer(EntityRendererProvider.Context context) {
        // El original usaba ModelAlien(0.1F) y shadowSize 0.0F.
        super(context, new AlienModel(context.bakeLayer(AlienModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(AlienEntity entity) {
        return TEXTURE;
    }
}
