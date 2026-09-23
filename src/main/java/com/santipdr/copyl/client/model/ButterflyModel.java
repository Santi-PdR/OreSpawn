package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.ButterflyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Port geometrico directo de danger.orespawn.entity.model.ModelButterfly. */
public final class ButterflyModel extends EntityModel<ButterflyEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "butterfly"), "main");
    private static final float WING_SPEED = 0.6F;

    private final ModelPart root;
    private final ModelPart leftWing, rightWing, leftWing2, rightWing2;
    private final ModelPart leftWing3, rightWing3, leftWing4, rightWing4;

    public ButterflyModel(ModelPart root) {
        this.root = root;
        this.leftWing = root.getChild("leftwing");
        this.rightWing = root.getChild("rightwing");
        this.leftWing2 = root.getChild("leftwing2");
        this.rightWing2 = root.getChild("rightwing2");
        this.leftWing3 = root.getChild("leftwing3");
        this.rightWing3 = root.getChild("rightwing3");
        this.leftWing4 = root.getChild("leftwing4");
        this.rightWing4 = root.getChild("rightwing4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        part(r, "body", 21, 19, 0, 0, -4, 1, 1, 8, 0, 17, 0);
        part(r, "leftwing", 43, 24, 0, 0, -4, 5, 1, 5, 1, 17, 0);
        part(r, "rightwing", 43, 17, -5, 0, -4, 5, 1, 5, 0, 17, 0);
        part(r, "leftwing2", 0, 0, 1, 0, -6, 6, 1, 7, 1, 17, 0);
        part(r, "rightwing2", 29, 0, -7, 0, -6, 6, 1, 7, 0, 17, 0);
        part(r, "leftwing3", 0, 9, 0, 0, 1, 5, 1, 5, 1, 17, 0);
        part(r, "rightwing3", 27, 9, -5, 0, 1, 5, 1, 5, 0, 17, 0);
        part(r, "head", 21, 11, 0, 0, -6, 1, 1, 1, 0, 17, 1);
        part(r, "leftwing4", 2, 24, 0, 0, 6, 1, 1, 7, 1, 17, 0);
        part(r, "rightwing4", 2, 16, -1, 0, 6, 1, 1, 7, 0, 17, 0);
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).mirror().addBox(x, y, z, dx, dy, dz),
                PartPose.offset(px, py, pz));
    }

    @Override
    public void setupAnim(ButterflyEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float wing = Mth.cos(ageInTicks * 1.3F * WING_SPEED) * Mth.PI * 0.25F;
        rightWing.zRot = wing;
        rightWing2.zRot = wing;
        rightWing3.zRot = wing;
        rightWing4.zRot = wing;
        leftWing.zRot = -wing;
        leftWing2.zRot = -wing;
        leftWing3.zRot = -wing;
        leftWing4.zRot = -wing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
