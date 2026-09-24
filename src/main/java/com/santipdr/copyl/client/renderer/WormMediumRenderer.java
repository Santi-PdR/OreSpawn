package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.WormMediumModel;
import com.santipdr.copyl.common.entity.WormMediumEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderWormMedium 1.12.2. */
public final class WormMediumRenderer extends MobRenderer<WormMediumEntity, WormMediumModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/wormmediumtexture.png");

    public WormMediumRenderer(EntityRendererProvider.Context context) {
        super(context, new WormMediumModel(context.bakeLayer(WormMediumModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(WormMediumEntity entity) {
        return TEXTURE;
    }
}
