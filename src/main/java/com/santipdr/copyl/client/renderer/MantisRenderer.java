package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.MantisModel;
import com.santipdr.copyl.common.entity.MantisEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderMantis 1.12.2. */
public final class MantisRenderer extends MobRenderer<MantisEntity, MantisModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/mantis.png");

    public MantisRenderer(EntityRendererProvider.Context context) {
        super(context, new MantisModel(context.bakeLayer(MantisModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MantisEntity entity) {
        return TEXTURE;
    }
}
