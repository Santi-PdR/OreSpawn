package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.AntModel;
import com.santipdr.copyl.common.entity.TermiteEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderTermite. */
public final class TermiteRenderer extends MobRenderer<TermiteEntity, AntModel<TermiteEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/termite.png");

    public TermiteRenderer(EntityRendererProvider.Context context) {
        super(context, new AntModel<>(context.bakeLayer(AntModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    protected void scale(TermiteEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.translate(0.0D, 0.9D, 0.0D);
        poseStack.scale(0.4F, 0.4F, 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(TermiteEntity entity) {
        return TEXTURE;
    }
}
