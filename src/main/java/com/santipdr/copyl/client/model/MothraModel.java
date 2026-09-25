package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.MothraEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

/** Reutiliza la geometría de Butterfly con el tipo concreto del boss. */
public final class MothraModel extends EntityModel<MothraEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "mothra"), "main");
    private final ButterflyModel wings;

    public MothraModel(ModelPart root) { this.wings = new ButterflyModel(root); }

    public static LayerDefinition createBodyLayer() { return ButterflyModel.createBodyLayer(); }

    @Override
    public void setupAnim(MothraEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        wings.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        wings.renderToBuffer(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
