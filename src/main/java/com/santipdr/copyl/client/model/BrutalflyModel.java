package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.BrutalflyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

public final class BrutalflyModel extends EntityModel<BrutalflyEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "brutalfly"), "main");
    private final ButterflyModel butterfly;
    public BrutalflyModel(ModelPart root) { butterfly = new ButterflyModel(root); }
    public static LayerDefinition createBodyLayer() { return ButterflyModel.createBodyLayer(); }
    @Override public void setupAnim(BrutalflyEntity entity, float limbSwing, float limbSwingAmount,
                                    float ageInTicks, float netHeadYaw, float headPitch) {
        butterfly.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
    @Override public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                                         float red, float green, float blue, float alpha) {
        butterfly.renderToBuffer(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
