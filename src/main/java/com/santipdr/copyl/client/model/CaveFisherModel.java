package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.CaveFisherEntity;
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

/** Port geométrico y de animación de danger.orespawn.entity.model.ModelCaveFisher. */
public final class CaveFisherModel extends EntityModel<CaveFisherEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "cavefisher"), "main");

    private static final float WING_SPEED = 1.0F;
    private static final float HALF_PI_LEG_PHASE = 1.570795F;
    private final ModelPart root;

    public CaveFisherModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r, "Nose", 0, 0, -.5F, -.5F, -12, 1, 1, 6, 0, 19, -4, 0, 0, 0);
        part(r, "EyeLeft", 0, 28, .5F, -2.5F, -2.5F, 3, 2, 2, 0, 19, -4, 0, 0, 0);
        part(r, "HeadMid", 0, 0, -2.5F, -1.5F, -5, 5, 3, 2, 0, 19, -4, 0, 0, 0);
        part(r, "HeadEnd", 0, 0, -2, -1, -6, 4, 2, 1, 0, 19, -4, 0, 0, 0);
        part(r, "TailTuft", 0, 23, -2, -1, 3, 4, 1, 2, 0, 19, 10, 0, 0, 0);
        part(r, "EyeRight", 0, 28, -3.5F, -2.5F, -2.5F, 3, 2, 2, 0, 19, -4, 0, 0, 0);

        part(r, "BodyTopLeft4", 0, 0, 0, 0, 0, 6, 3, 4, 0, 16.2F, 7, .1047198F, .1047198F, .1047198F);
        part(r, "BodyTopRight4", 0, 0, -5, 0, 0, 6, 3, 4, -1, 16.2F, 7, .1047198F, -.1047198F, -.1047198F);
        part(r, "BodyTopLeft1", 0, 0, 0, 0, 0, 5, 3, 4, 0, 16, -4, .1745329F, .1745329F, .1047198F);
        part(r, "BodyTopRight1", 0, 0, -5, 0, 0, 5, 3, 4, 0, 16, -4, .1745329F, -.1745329F, -.1047198F);
        part(r, "BodyTopRight2", 0, 0, -5, 0, 0, 7, 3, 4, -1, 16, -1, .2094395F, -.1745329F, -.1047198F);
        part(r, "BodyTopLeft2", 0, 0, -1, 0, 0, 7, 3, 4, 0, 16, -1, .2094395F, .1745329F, .1047198F);
        part(r, "BodyTopRight3", 0, 0, -5, 0, 0, 6, 3, 4, -1, 16, 3, .1396263F, -.1396263F, -.1047198F);
        part(r, "BodyTopLeft3", 0, 0, 0, 0, 0, 6, 3, 4, 0, 16, 3, .1396263F, .1396263F, .1047198F);
        part(r, "HeadBase", 0, 0, -3, -2, -3, 6, 4, 3, 0, 19, -4, 0, 0, 0);
        part(r, "TailBase", 0, 0, -3, -2, 0, 6, 3, 3, 0, 19, 10, 0, 0, 0);
        part(r, "BodyLow2", 34, 0, 0, 0, 0, 8, 2, 7, -4, 18.3F, 3, 0, 0, 0);
        part(r, "BodyLow1", 34, 0, 0, 0, 0, 8, 2, 7, -4, 18.7F, -4, 0, 0, 0);
        part(r, "Spine5", 0, 0, -.5F, 0, 0, 1, 1, 4, 0, 16, 8.6F, 0, 0, 0);
        part(r, "Spine1", 0, 0, -.5F, 0, 0, 1, 1, 4, 0, 16, -4.2F, .2443461F, 0, 0);
        part(r, "Spine2", 0, 0, -.5F, 0, 0, 1, 1, 5, 0, 16, -1.2F, .3141593F, 0, 0);
        part(r, "Spine3", 0, 0, -.5F, 0, 0, 1, 1, 6, 0, 16, 1.8F, .2792527F, 0, 0);
        part(r, "Spine4", 0, 0, -.5F, 0, 0, 1, 1, 8, 0, 16, 3.8F, .1745329F, 0, 0);

        part(r, "RightArmSeg4", 0, 0, -3.2F, -1, -10.5F, 2, 2, 4, -4.7F, 17.5F, -3, 0, .0872665F, 0);
        part(r, "LeftArmSeg1", 0, 13, -.5F, -.5F, -4, 1, 1, 4, 4.7F, 17.5F, -3, 0, -.5235988F, 0);
        part(r, "LeftArmSeg3", 0, 13, 1, -.5F, -8, 1, 1, 3, 4.7F, 17.5F, -3, 0, -.1745329F, 0);
        part(r, "RightArmSeg2", 0, 0, -1.5F, -1, -6, 2, 2, 4, -4.7F, 17.5F, -3, 0, .3490659F, 0);
        part(r, "RightArmSeg1", 0, 13, -.5F, -.5F, -4, 1, 1, 4, -4.7F, 17.5F, -3, 0, .5235988F, 0);
        part(r, "LeftArmSeg5", 0, 13, 2.4F, -.5F, -12, 1, 1, 3, 4.7F, 17.5F, -3, 0, 0, 0);
        part(r, "LeftArmSeg2", 0, 0, -.5F, -1, -6, 2, 2, 4, 4.7F, 17.5F, -3, 0, -.3490659F, 0);
        part(r, "LeftClawTop", 15, 15, 1.8F, 4.7F, -15, 2, 2, 5, 4.7F, 17.5F, -3, -.5410521F, 0, 0);
        part(r, "RightArmSeg3", 0, 13, -2, -.5F, -8, 1, 1, 3, -4.7F, 17.5F, -3, 0, .1745329F, 0);
        part(r, "RightArmSeg5", 0, 13, -3.6F, -.5F, -12, 1, 1, 3, -4.7F, 17.5F, -3, 0, 0, 0);
        part(r, "LeftArmSeg4", 0, 0, 1.1F, -1, -10.5F, 2, 2, 4, 4.7F, 17.5F, -3, 0, -.0872665F, 0);
        part(r, "LeftClawBase", 0, 0, 1.8F, -1, -13, 2, 2, 2, 4.7F, 17.5F, -3, 0, 0, 0);
        part(r, "RightClawBase", 0, 0, -4.2F, -1, -13, 2, 2, 2, -4.7F, 17.5F, -3, 0, 0, 0);
        part(r, "LeftClawLow", 25, 25, 1.8F, -4.3F, -15, 2, 1, 4, 4.7F, 17.5F, -3, .3490659F, 0, 0);
        part(r, "RightClawTop", 15, 15, -4.2F, 4.7F, -15, 2, 2, 5, -4.7F, 17.5F, -3, -.5410521F, 0, 0);
        part(r, "RightClawLow", 25, 25, -4.2F, -4.3F, -15, 2, 1, 4, -4.7F, 17.5F, -3, .3490659F, 0, 0);

        addLegSet(r, "LF", true, .5F);
        addLegSet(r, "LM", true, 4.5F);
        addLegSet(r, "LB", true, 8.5F);
        addLegSet(r, "RF", false, .5F);
        addLegSet(r, "RM", false, 4.5F);
        addLegSet(r, "RB", false, 8.5F);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void addLegSet(PartDefinition r, String prefix, boolean left, float z) {
        float px = left ? 5.0F : -5.0F;
        float sign = left ? -1.0F : 1.0F;
        float b1x = left ? .5F : -3.5F;
        float b2x = left ? 2.5F : -5.5F;
        float b3x = left ? 5.1F : -8.1F;
        float b4x = left ? 5.0F : -6.0F;
        float b5x = left ? 4.6F : -6.4F;
        float b6x = left ? 5.5F : -6.5F;

        part(r, prefix + "Leg1", 0, 13, b1x, -.5F, -.5F, 3, 1, 1, px, 18, z, 0, 0, sign * .4363323F);
        part(r, prefix + "Leg2", 0, 0, b2x, .5F, -1, 3, 2, 2, px, 18, z, 0, 0, sign * .9599311F);
        part(r, prefix + "Leg3", 2, 0, b3x, -1.5F, -1, 3, 1, 2, px, 18, z, 0, 0, sign * .5759587F);
        part(r, prefix + "Leg4", 0, 13, b4x, -3, -.5F, 1, 3, 1, px, 18, z, 0, 0, sign * .2094395F);
        part(r, prefix + "Leg5", 0, 0, b5x, -1, -1, 2, 6, 2, px, 18, z, 0, 0, sign * .1047198F);
        part(r, prefix + "Leg6", 0, 13, b6x, 3, -.5F, 1, 3, 1, px, 18, z, 0, 0, 0);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, int dx, int dy, int dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(CaveFisherEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float front = Mth.cos(ageInTicks * 2.0F * WING_SPEED) * Mth.PI * .12F * limbSwingAmount;
        float middle = Mth.cos(ageInTicks * 2.0F * WING_SPEED - HALF_PI_LEG_PHASE) * Mth.PI * .12F * limbSwingAmount;
        float back = Mth.cos(ageInTicks * 2.0F * WING_SPEED - 2.0F * HALF_PI_LEG_PHASE) * Mth.PI * .12F * limbSwingAmount;
        setLegYaw("LF", front); setLegYaw("RF", -front);
        setLegYaw("LM", middle); setLegYaw("RM", -middle);
        setLegYaw("LB", back); setLegYaw("RB", -back);

        float clawAngle = Mth.cos(ageInTicks * 3.0F * WING_SPEED) * Mth.PI * .15F;
        float nextAngle = Mth.cos((ageInTicks + .1F) * 3.0F * WING_SPEED) * Mth.PI * .15F;

        // Quirk del modelo 1.12.2: si no está atacando el ángulo se pone a cero
        // antes de comprobar el cruce, por lo que las pinzas aleatorias en idle
        // prácticamente no llegan a activarse.
        if (entity.getAttacking() == 0) {
            clawAngle = 0.0F;
        }

        if (nextAngle > 0.0F && clawAngle < 0.0F) {
            entity.setRenderChoice1(0);
            if (entity.getAttacking() == 0) {
                entity.setRenderChoice1(entity.getRandom().nextInt(20));
                entity.setRenderChoice2(entity.getRandom().nextInt(25));
            } else {
                entity.setRenderChoice1(entity.getRandom().nextInt(4));
                entity.setRenderChoice2(entity.getRandom().nextInt(3));
            }
        }

        float active = (entity.getRenderChoice1() == 1 || entity.getRenderChoice1() == 3) ? clawAngle : 0.0F;
        doClaw("Left", active);
        doClaw("Right", active);
    }

    private void setLegYaw(String prefix, float angle) {
        for (int i = 1; i <= 6; i++) {
            root.getChild(prefix + "Leg" + i).yRot = angle;
        }
    }

    private void doClaw(String side, float angle) {
        float a = Math.abs(angle);
        for (int i = 1; i <= 5; i++) {
            root.getChild(side + "ArmSeg" + i).xRot = a;
        }
        root.getChild(side + "ClawBase").xRot = a;
        root.getChild(side + "ClawTop").xRot = a - .54F;
        root.getChild(side + "ClawLow").xRot = a + .35F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
