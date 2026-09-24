package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.VelocityRaptorModel;
import com.santipdr.copyl.common.entity.VelocityRaptorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderVelocityRaptor del JAR 1.12.2. */
public final class VelocityRaptorRenderer extends MobRenderer<VelocityRaptorEntity, VelocityRaptorModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/velocityraptor.png");

    public VelocityRaptorRenderer(EntityRendererProvider.Context context) {
        super(context, new VelocityRaptorModel(context.bakeLayer(VelocityRaptorModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    protected void scale(VelocityRaptorEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(0.8F, 0.8F, 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(VelocityRaptorEntity entity) {
        return TEXTURE;
    }
}
