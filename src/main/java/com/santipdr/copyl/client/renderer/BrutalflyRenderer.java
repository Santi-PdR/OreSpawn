package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.BrutalflyModel;
import com.santipdr.copyl.common.entity.BrutalflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;

public final class BrutalflyRenderer extends MobRenderer<BrutalflyEntity, BrutalflyModel> {
    private static final ResourceLocation OVERLAY =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/Brutalfly_overlay2.png");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/brutalfly.png");
    public BrutalflyRenderer(EntityRendererProvider.Context context) {
        super(context, new BrutalflyModel(context.bakeLayer(BrutalflyModel.LAYER_LOCATION)), 0.0F);
        addLayer(new RenderLayer<>(this) {
            @Override public void render(com.mojang.blaze3d.vertex.PoseStack poseStack, MultiBufferSource buffers,
                    int packedLight, BrutalflyEntity entity, float limbSwing, float limbSwingAmount,
                    float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(OVERLAY));
                getParentModel().renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                        1.0F, 1.0F, 1.0F, 1.0F);
            }
        });
    }
    @Override public ResourceLocation getTextureLocation(BrutalflyEntity entity) { return TEXTURE; }
    @Override protected void scale(BrutalflyEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(8.0F, 8.0F, 8.0F);
    }
}
