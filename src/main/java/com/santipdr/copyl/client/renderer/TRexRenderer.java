package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.TRexModel;
import com.santipdr.copyl.common.entity.TRexEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderTRex(new ModelTRex(0.2F), 1.0F, 1.2F). */
public final class TRexRenderer extends MobRenderer<TRexEntity, TRexModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/trextexture.png");

    public TRexRenderer(EntityRendererProvider.Context context) {
        super(context, new TRexModel(context.bakeLayer(TRexModel.LAYER_LOCATION)), 1.2F);
    }

    @Override
    protected void scale(TRexEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(TRexEntity entity) {
        return TEXTURE;
    }
}
