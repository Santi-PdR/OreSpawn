package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.WormLargeModel;
import com.santipdr.copyl.common.entity.WormLargeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente al RenderWormLarge original. */
public final class WormLargeRenderer extends MobRenderer<WormLargeEntity, WormLargeModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/wormlargetexture.png");

    public WormLargeRenderer(EntityRendererProvider.Context context) {
        super(context, new WormLargeModel(context.bakeLayer(WormLargeModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(WormLargeEntity entity) {
        return TEXTURE;
    }
}
