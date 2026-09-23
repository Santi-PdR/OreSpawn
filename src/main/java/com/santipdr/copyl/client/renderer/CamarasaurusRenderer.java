package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.CamarasaurusModel;
import com.santipdr.copyl.common.entity.CamarasaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderCamarasaurus 1.12.2. */
public final class CamarasaurusRenderer extends MobRenderer<CamarasaurusEntity, CamarasaurusModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/camarasaurus.png");

    public CamarasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CamarasaurusModel(context.bakeLayer(CamarasaurusModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    protected void scale(CamarasaurusEntity entity, PoseStack poseStack, float partialTick) {
        if (entity.isBaby()) {
            poseStack.translate(0.0D, 0.9D, 0.0D);
            poseStack.scale(0.4F, 0.4F, 0.4F);
        } else {
            poseStack.translate(0.0D, 0.45000005D, 0.0D);
            poseStack.scale(0.7F, 0.7F, 0.7F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(CamarasaurusEntity entity) {
        return TEXTURE;
    }
}
