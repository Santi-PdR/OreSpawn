package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.SpyroModel;
import com.santipdr.copyl.common.entity.SpyroEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class SpyroRenderer extends MobRenderer<SpyroEntity, SpyroModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/spyro.png");

    public SpyroRenderer(EntityRendererProvider.Context context) {
        super(context, new SpyroModel(context.bakeLayer(SpyroModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpyroEntity entity) { return TEXTURE; }
}
