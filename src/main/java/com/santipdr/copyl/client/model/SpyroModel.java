package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.SpyroEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public final class SpyroModel extends EntityModel<SpyroEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "spyro"), "main");
    private final ModelPart root, head, leftWing, rightWing, legs[], tail;

    public SpyroModel(ModelPart root) {
        this.root = root;
        head = root.getChild("head");
        leftWing = root.getChild("left_wing");
        rightWing = root.getChild("right_wing");
        legs = new ModelPart[]{root.getChild("left_front_leg"), root.getChild("right_front_leg"),
                root.getChild("left_back_leg"), root.getChild("right_back_leg")};
        tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -7.0F, -6.0F, 8.0F, 9.0F, 13.0F), PartPose.ZERO);
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23)
                .addBox(-3.5F, -13.0F, -12.0F, 7.0F, 7.0F, 7.0F)
                .texOffs(28, 23).addBox(-2.0F, -9.0F, -16.0F, 4.0F, 3.0F, 5.0F)
                .texOffs(40, 23).addBox(-5.0F, -16.0F, -9.0F, 2.0F, 4.0F, 2.0F)
                .addBox(3.0F, -16.0F, -9.0F, 2.0F, 4.0F, 2.0F), PartPose.ZERO);
        root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 38)
                .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(2.5F, 2.0F, -4.0F));
        root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 38).mirror()
                .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.5F, 2.0F, -4.0F));
        root.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(16, 38)
                .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(2.5F, 2.0F, 4.0F));
        root.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(16, 38).mirror()
                .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 5.0F, 3.0F), PartPose.offset(-2.5F, 2.0F, 4.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(36, 38)
                .addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 10.0F), PartPose.offset(0.0F, -3.5F, 7.0F));
        root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 48)
                .addBox(0.0F, -1.0F, -1.0F, 10.0F, 1.0F, 7.0F), PartPose.offsetAndRotation(3.5F, -4.0F, -1.0F, 0.0F, 0.0F, 0.15F));
        root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 48).mirror()
                .addBox(-10.0F, -1.0F, -1.0F, 10.0F, 1.0F, 7.0F), PartPose.offsetAndRotation(-3.5F, -4.0F, -1.0F, 0.0F, 0.0F, -0.15F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(SpyroEntity entity, float limbSwing, float limbSwingAmount, float age,
                          float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        float stride = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
        legs[0].xRot = stride;
        legs[3].xRot = stride;
        legs[1].xRot = -stride;
        legs[2].xRot = -stride;
        float flap = Mth.sin(age * 0.55F) * 0.55F;
        leftWing.zRot = 0.15F + flap;
        rightWing.zRot = -0.15F - flap;
        tail.yRot = Mth.sin(age * 0.08F) * 0.12F;
    }

    @Override
    public void renderToBuffer(PoseStack pose, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(pose, consumer, light, overlay, red, green, blue, alpha);
    }
}
