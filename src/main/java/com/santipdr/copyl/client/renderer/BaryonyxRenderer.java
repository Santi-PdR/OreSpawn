package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.BaryonyxModel;
import com.santipdr.copyl.common.entity.BaryonyxEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderBaryonyx 1.12.2. */
public final class BaryonyxRenderer extends MobRenderer<BaryonyxEntity, BaryonyxModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/baryonyx.png");

    public BaryonyxRenderer(EntityRendererProvider.Context context) {
        super(context, new BaryonyxModel(context.bakeLayer(BaryonyxModel.LAYER_LOCATION)), 0.8F);
    }

    @Override
    protected void scale(BaryonyxEntity entity, PoseStack poseStack, float partialTick) {
        if (entity.isBaby()) {
            poseStack.translate(0.0D, 0.75D, 0.0D);
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BaryonyxEntity entity) {
        return TEXTURE;
    }
}
