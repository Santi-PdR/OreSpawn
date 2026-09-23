package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.FireflyModel;
import com.santipdr.copyl.common.entity.FireflyEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderFirefly, incluido el abdomen intermitente full-bright. */
public final class FireflyRenderer extends MobRenderer<FireflyEntity, FireflyModel> {
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/firefly.png");

    public FireflyRenderer(EntityRendererProvider.Context context) {
        super(context, new FireflyModel(context.bakeLayer(FireflyModel.LAYER_LOCATION)), 0.0F);
        addLayer(new GlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(FireflyEntity entity) {
        return TEXTURE;
    }

    private static final class GlowLayer extends RenderLayer<FireflyEntity, FireflyModel> {
        private GlowLayer(FireflyRenderer renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                           FireflyEntity entity, float limbSwing, float limbSwingAmount,
                           float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entity.isBlinking()) {
                return;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
            getParentModel().renderGlow(poseStack, consumer, LightTexture.FULL_BRIGHT, overlay,
                    1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
