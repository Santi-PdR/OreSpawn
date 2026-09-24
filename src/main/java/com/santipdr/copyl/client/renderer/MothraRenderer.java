package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.ButterflyModel;
import com.santipdr.copyl.common.entity.MothraEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Renderer equivalente al RenderMothra original; el original usa eyemoth.png. */
public final class MothraRenderer extends MobRenderer<MothraEntity, ButterflyModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/eyemoth.png");

    public MothraRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MothraEntity entity) { return TEXTURE; }

    @Override
    protected void scale(MothraEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(8.0F, 8.0F, 8.0F);
    }
}
