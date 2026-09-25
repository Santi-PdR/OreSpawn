package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.BrutalflyModel;
import com.santipdr.copyl.common.entity.BrutalflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class BrutalflyRenderer extends MobRenderer<BrutalflyEntity, BrutalflyModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/brutalfly.png");
    public BrutalflyRenderer(EntityRendererProvider.Context context) {
        super(context, new BrutalflyModel(context.bakeLayer(BrutalflyModel.LAYER_LOCATION)), 0.0F);
    }
    @Override public ResourceLocation getTextureLocation(BrutalflyEntity entity) { return TEXTURE; }
    @Override protected void scale(BrutalflyEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(8.0F, 8.0F, 8.0F);
    }
}
