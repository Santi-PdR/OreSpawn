package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.GammaMetroidEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Port geométrico y de animación de danger.orespawn.entity.model.ModelGammaMetroid. */
public final class GammaMetroidModel extends EntityModel<GammaMetroidEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "gammametroid"), "main");

    private final ModelPart root;
    private final ModelPart shell1;
    private final ModelPart shell2;
    private final ModelPart shell3;
    private final ModelPart shell4;
    private final ModelPart beakLower;
    private final ModelPart leftTusk;
    private final ModelPart middleTusk;
    private final ModelPart rightTusk;
    private final ModelPart leftFrontUpperLeg;
    private final ModelPart leftFrontLowerLeg;
    private final ModelPart leftRearUpperLeg;
    private final ModelPart leftRearLowerLeg;
    private final ModelPart rightFrontUpperLeg;
    private final ModelPart rightFrontLowerLeg;
    private final ModelPart rightRearUpperLeg;
    private final ModelPart rightRearLowerLeg;

    public GammaMetroidModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.root = root;
        this.shell1 = root.getChild("Shell1");
        this.shell2 = root.getChild("Shell2");
        this.shell3 = root.getChild("Shell3");
        this.shell4 = root.getChild("Shell4");
        this.beakLower = root.getChild("BeakLower");
        this.leftTusk = root.getChild("LeftTusk");
        this.middleTusk = root.getChild("MiddleTusk");
        this.rightTusk = root.getChild("RightTusk");
        this.leftFrontUpperLeg = root.getChild("LeftFrontUpperLeg");
        this.leftFrontLowerLeg = root.getChild("LeftFrontLowerLeg");
        this.leftRearUpperLeg = root.getChild("LeftRearUpperLeg");
        this.leftRearLowerLeg = root.getChild("LeftRearLowerLeg");
        this.rightFrontUpperLeg = root.getChild("RightFrontUpperLeg");
        this.rightFrontLowerLeg = root.getChild("RightFrontLowerLeg");
        this.rightRearUpperLeg = root.getChild("RightRearUpperLeg");
        this.rightRearLowerLeg = root.getChild("RightRearLowerLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        part(root, "Shell3", 128, 0, -6, -6, 0, 12, 12, 7, 0, 7, 10, -0.9599311F, 0.6283185F, 0.5235988F);
        part(root, "Shell4", 48, 34, 0, 0, 0, 6, 6, 8, -3, 9, 13, -0.2792527F, 0, 0);
        part(root, "Head", 48, 48, 0, 0, 0, 16, 8, 6, -8, -1, -11, 0, 0, 0);
        part(root, "BeakUpper", 114, 44, -3, 0, -3, 6, 4, 6, 0, 5, -11, 0.1047198F, 0.7853982F, 0.1047198F);
        part(root, "BeakLower", 120, 54, -1.5F, 0, -1.5F, 3, 6, 3, 0, 9, -12, 0.1396263F, 0.7853982F, 0.1396263F);
        part(root, "LeftTusk", 76, 50, 0, 0, -12, 2, 2, 12, 5, 6, -10, 0.1047198F, 0.0872665F, 0);
        part(root, "MiddleTusk", 76, 50, -1, 0, -12, 2, 2, 12, 0, -2, -10, 0.122173F, 0, 0);
        part(root, "RightTusk", 76, 50, -2, 0, -12, 2, 2, 12, -5, 6, -10, 0.1047198F, -0.0872665F, 0);
        part(root, "LeftFrontUpperLeg", 64, 0, 0, 0, -1.5F, 3, 8, 3, 8, 8, -2, -0.1745329F, 0, -0.6632251F);
        part(root, "LeftFrontLowerLeg", 48, 0, -1, 0, -1, 2, 11, 2, 14, 13, -3.5F, -0.2617994F, 0.1396263F, 0);
        part(root, "LeftRearUpperLeg", 64, 0, -1, 0, -1.5F, 3, 8, 3, 8, 9, 7, 0.1745329F, 0, -0.8203047F);
        part(root, "LeftRearLowerLeg", 48, 0, -1, 0, -1, 2, 11, 2, 14, 14, 8.5F, 0.3141593F, -0.1570796F, -0.2792527F);
        part(root, "RightFrontUpperLeg", 64, 0, -3, 0, -1.5F, 3, 8, 3, -8, 8, -2, -0.1745329F, 0, 0.6632251F);
        part(root, "RightFrontLowerLeg", 48, 0, -1, 0, -1, 2, 11, 2, -14, 13, -3.5F, -0.2617994F, -0.1396263F, 0);
        part(root, "RightRearUpperLeg", 64, 0, -2, 0, -1.5F, 3, 8, 3, -8, 9, 7, 0.1745329F, 0, 0.8203047F);
        part(root, "RightRearLowerLeg", 48, 0, -1, 0, -1, 2, 11, 2, -14, 14, 8.5F, 0.3141593F, 0.1570796F, 0.2792527F);
        part(root, "Core", 82, 33, -3, 0, -3, 6, 6, 6, 0, 8, 3, -0.122173F, 0, 0);
        part(root, "Bellyinside", 150, 3, -8, -1, -8, 16, 1, 16, 0, 8, 2, -0.122173F, 0, 0);
        part(root, "Bellyoutside", 0, 0, -8, -6, -8, 16, 14, 16, 0, 8, 2, -0.122173F, 0, 0);
        part(root, "Shell1", 64, 0, -10, -10, 2, 19, 19, 12, 0, 4, -7, 0, 0, 0.7853982F);
        part(root, "Shell2", 0, 30, -9, -9, 0, 16, 16, 8, 0, 4.5F, 5, -0.5235988F, 0.3665191F, 0.715585F);

        return LayerDefinition.create(mesh, 256, 64);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(GammaMetroidEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        final float speed = 1.0F;

        leftTusk.xRot = Mth.cos(ageInTicks * 0.81F * speed) * Mth.PI * 0.08F;
        rightTusk.xRot = Mth.cos(ageInTicks * 0.87F * speed) * Mth.PI * 0.08F;
        middleTusk.xRot = Mth.cos(ageInTicks * 0.99F * speed) * Mth.PI * 0.08F;
        leftTusk.yRot = Mth.cos(ageInTicks * 1.11F * speed) * Mth.PI * 0.08F;
        rightTusk.yRot = Mth.cos(ageInTicks * 1.17F * speed) * Mth.PI * 0.08F;
        middleTusk.yRot = Mth.cos(ageInTicks * 1.25F * speed) * Mth.PI * 0.08F;

        float leg = Mth.cos(ageInTicks * 2.0F * speed) * Mth.PI * 0.12F * limbSwingAmount;
        float nextLeg = Mth.cos((ageInTicks + 0.1F) * 2.0F * speed) * Mth.PI * 0.12F * limbSwingAmount;
        float lift = 0.0F;
        if (nextLeg > leg) {
            lift = 0.47F * limbSwingAmount - Math.abs(leg);
        }

        doLeftFrontLeg(leftFrontUpperLeg, leftFrontLowerLeg, leg, lift);
        doRightFrontLeg(rightFrontUpperLeg, rightFrontLowerLeg, -leg, lift);
        doLeftRearLeg(leftRearUpperLeg, leftRearLowerLeg, -leg, lift);
        doRightRearLeg(rightRearUpperLeg, rightRearLowerLeg, leg, lift);

        float shell = Mth.cos(ageInTicks * 0.4F * speed) * Mth.PI * 0.05F;
        if (entity.isOrderedToSit()) {
            shell = 0.0F;
        }
        shell1.xRot = shell / 4.0F;
        shell1.yRot = -shell / 4.0F;
        shell2.xRot = shell - 0.49F;
        shell2.yRot = -shell + 0.33F;
        shell3.xRot = shell - 0.96F;
        shell3.yRot = -shell + 0.63F;
        shell4.xRot = shell - 0.28F;

        float beak = Math.abs(Mth.cos(ageInTicks * 0.75F * speed) * Mth.PI * 0.1F);
        beakLower.xRot = beak + 0.14F;
        beakLower.zRot = beak + 0.14F;
    }

    private static void doLeftFrontLeg(ModelPart upper, ModelPart lower, float swing, float lift) {
        upper.xRot = swing - 0.17F;
        lower.xRot = swing - 0.26F;
        lower.z = upper.z + (float) Math.sin(upper.xRot) * 7.0F - 0.5F;
        upper.zRot = -lift - 0.66F;
        lower.zRot = -lift;
        lower.y = upper.y + 5.0F * (float) Math.cos(upper.xRot);
        lower.x = upper.x + Math.abs((float) Math.sin(upper.zRot) * 7.0F) + 1.0F;
    }

    private static void doLeftRearLeg(ModelPart upper, ModelPart lower, float swing, float lift) {
        upper.xRot = swing + 0.17F;
        lower.xRot = swing + 0.31F;
        lower.z = upper.z + (float) Math.sin(upper.xRot) * 7.0F - 0.5F;
        upper.zRot = -lift - 0.82F;
        lower.zRot = -lift;
        lower.y = upper.y + 5.0F * (float) Math.cos(upper.xRot);
        lower.x = upper.x + Math.abs((float) Math.sin(upper.zRot) * 7.0F) + 1.5F;
    }

    private static void doRightFrontLeg(ModelPart upper, ModelPart lower, float swing, float lift) {
        upper.xRot = swing - 0.17F;
        lower.xRot = swing - 0.26F;
        lower.z = upper.z + (float) Math.sin(upper.xRot) * 7.0F - 0.5F;
        upper.zRot = -lift + 0.34F;
        lower.zRot = -lift;
        lower.y = upper.y + 5.0F * (float) Math.cos(upper.xRot);
        lower.x = upper.x - Math.abs((float) Math.sin(upper.zRot) * 7.0F) - 1.0F;
    }

    private static void doRightRearLeg(ModelPart upper, ModelPart lower, float swing, float lift) {
        upper.xRot = swing + 0.17F;
        lower.xRot = swing + 0.31F;
        lower.z = upper.z + (float) Math.sin(upper.xRot) * 7.0F - 0.5F;
        upper.zRot = -lift + 0.82F;
        lower.zRot = -lift;
        lower.y = upper.y + 5.0F * (float) Math.cos(upper.xRot);
        lower.x = upper.x - Math.abs((float) Math.sin(upper.zRot) * 7.0F) - 1.5F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
