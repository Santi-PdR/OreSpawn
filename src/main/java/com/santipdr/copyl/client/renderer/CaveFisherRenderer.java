package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.CaveFisherModel;
import com.santipdr.copyl.common.entity.CaveFisherEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderCaveFisher 1.12.2. */
public final class CaveFisherRenderer extends MobRenderer<CaveFisherEntity, CaveFisherModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/cavefisher.png");

    public CaveFisherRenderer(EntityRendererProvider.Context context) {
        super(context, new CaveFisherModel(context.bakeLayer(CaveFisherModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(CaveFisherEntity entity) {
        return TEXTURE;
    }
}
