package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.DoomWormModel;
import com.santipdr.copyl.common.entity.DoomWormEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public final class DoomWormRenderer extends MobRenderer<DoomWormEntity, DoomWormModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CopyL.MOD_ID, "textures/entity/wormdoomtexture.png");

    public DoomWormRenderer(EntityRendererProvider.Context context) {
        super(context, new DoomWormModel(context.bakeLayer(DoomWormModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(DoomWormEntity entity) {
        return TEXTURE;
    }

    /** WormDoom overrides both legacy distance checks to always render its 100-segment body. */
    @Override
    public boolean shouldRender(DoomWormEntity entity, Frustum frustum, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(DoomWormEntity entity, float yaw, float partialTick, PoseStack pose,
                      MultiBufferSource buffers, int packedLight) {
        super.render(entity, yaw, partialTick, pose, buffers, packedLight);
        VertexConsumer vertexConsumer = buffers.getBuffer(model.renderType(getTextureLocation(entity)));
        model.renderSegments(entity, pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
