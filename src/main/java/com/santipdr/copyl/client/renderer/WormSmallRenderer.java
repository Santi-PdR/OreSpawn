package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.WormSmallModel;
import com.santipdr.copyl.common.entity.WormSmallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderWormSmall 1.12.2. */
public final class WormSmallRenderer extends MobRenderer<WormSmallEntity, WormSmallModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/wormsmalltexture.png");

    public WormSmallRenderer(EntityRendererProvider.Context context) {
        super(context, new WormSmallModel(context.bakeLayer(WormSmallModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(WormSmallEntity entity) {
        return TEXTURE;
    }
}
