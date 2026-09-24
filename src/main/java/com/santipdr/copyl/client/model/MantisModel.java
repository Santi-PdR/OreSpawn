package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.MantisEntity;
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

/** Port geométrico/animado de ModelMantis 1.12.2. */
public final class MantisModel extends EntityModel<MantisEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "mantis"), "main");

    private static final float WING_SPEED = 1.0F;
    private final ModelPart root;
    private final ModelPart lfwing;
    private final ModelPart rfwing;
    private final ModelPart lrwing;
    private final ModelPart rrwing;
    private final ModelPart larm1;
    private final ModelPart larm2;
    private final ModelPart larm3;
    private final ModelPart rarm1;
    private final ModelPart rarm2;
    private final ModelPart rarm3;

    public MantisModel(ModelPart root) {
        this.root = root;
        lfwing = root.getChild("lfwing");
        rfwing = root.getChild("rfwing");
        lrwing = root.getChild("lrwing");
        rrwing = root.getChild("rrwing");
        larm1 = root.getChild("larm1");
        larm2 = root.getChild("larm2");
        larm3 = root.getChild("larm3");
        rarm1 = root.getChild("rarm1");
        rarm2 = root.getChild("rarm2");
        rarm3 = root.getChild("rarm3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        part(root, "lfleg1", 28, 35, 0, 0, 0, 1, 10, 1, 27, 16, -3, 0, 0, -0.6283185F);
        part(root, "lfleg2", 0, 32, 0, 0, 0, 1, 22, 1, 21, -5, -3, 0, 0, -0.2792527F);
        part(root, "lfleg3", 64, 2, 0, 0, 0, 20, 1, 1, 2, -5, 0, 0, 0.1570796F, 0);
        part(root, "lfleg4", 64, 20, 15, 0, -2, 4, 1, 5, 2, -5, 0, 0, 0.1570796F, 0);
        part(root, "lrleg1", 35, 35, 0, 0, 0, 1, 10, 1, 32, 18, 11, 0, 0, -0.8726646F);
        part(root, "lrleg2", 14, 32, 0, 0, 0, 1, 22, 1, 21, 0, 11, 0, 0, -0.5410521F);
        part(root, "lrleg3", 64, 11, 0, 0, 0, 20, 1, 1, 2, 0, 8, 0, -0.1570796F, 0);
        part(root, "lrleg4", 64, 36, 15, 0, -2, 4, 1, 5, 2, 0, 8, 0, -0.1570796F, 0);
        part(root, "abdomen", 118, 0, 0, 0, 0, 9, 5, 53, -4, -11, 0, -0.5061455F, 0, 0);
        part(root, "thorax", 145, 62, 0, 0, 0, 15, 3, 13, -7, -14, -12, -0.2443461F, 0, 0);
        part(root, "neck1", 145, 82, 0, 0, 0, 9, 1, 15, -4, -15, -27, -0.0698132F, 0, 0);
        part(root, "neck2", 40, 150, 0, 0, 0, 3, 1, 2, -1, -15, -29, 0, 0, 0);
        part(root, "head1", 0, 150, 0, 0, 0, 2, 6, 1, 0, -16, -30, 0, 0, 0.1396263F);
        part(root, "head2", 10, 150, -2, 0, 0, 2, 6, 1, 0, -16, -30, 0, 0, -0.1745329F);
        part(root, "leye", 20, 150, 1, 0, -0.5F, 2, 2, 1, 0, -16, -30, 0, 0, 0.1396263F);
        part(root, "reye", 30, 150, -3, 0, -0.5F, 2, 2, 1, 0, -16, -30, 0, 0, -0.1745329F);
        part(root, "lantenna", 53, 150, 0, -20, 0, 1, 20, 1, 0, -16, -30, 0, 0, 0.2792527F);
        part(root, "rantenna", 60, 150, -1, -20, 0, 1, 20, 1, 0, -16, -30, 0, 0, -0.2792527F);
        part(root, "larm1", 51, 0, 0, 0, -1, 1, 23, 4, 2, -14, -23, 0.0349066F, 0, 0);
        part(root, "larm2", 30, 0, 0, -18, -2, 1, 18, 2, 2, 8, -22, 0.5585054F, 0, 0);
        part(root, "larm3", 16, 0, 0, 0, 0, 1, 21, 1, 2, -7, -33, 0, 0, 0);
        part(root, "lfwing", 0, 67, 0, 0, 0, 48, 1, 12, 2, -11, 0, -0.2268928F, 0, -0.6981317F);
        part(root, "rfwing", 0, 83, -48, 0, 0, 48, 1, 12, -1, -11, 0, -0.2268928F, 0, 0.6981317F);
        part(root, "lrwing", 0, 100, 0, 0, 0, 42, 1, 17, 2, -6, 10, -0.2268928F, 0, -0.3490659F);
        part(root, "rrwing", 0, 122, -42, 0, 0, 42, 1, 17, -1, -6, 10, -0.2268928F, 0, 0.3490659F);
        part(root, "rarm1", 38, 0, 0, 0, -1, 1, 23, 4, -1, -14, -23, 0.0349066F, 0, 0);
        part(root, "rarm2", 22, 0, 0, -18, -2, 1, 18, 2, -1, 8, -22, 0.5585054F, 0, 0);
        part(root, "rarm3", 10, 0, 0, 0, 0, 1, 21, 1, -1, -7, -33, 0, 0, 0);
        part(root, "rlfleg3", 64, 6, -20, 0, 0, 20, 1, 1, -1, -5, 0, 0, -0.1570796F, 0);
        part(root, "rfleg4", 64, 28, -19, 0, -2, 4, 1, 5, -1, -5, 0, 0, -0.1570796F, 0);
        part(root, "rfleg2", 7, 32, 0, 0, 0, 1, 22, 1, -21, -5, -3, 0, 0, 0.2792527F);
        part(root, "rfleg1", 42, 35, 0, 0, 0, 1, 10, 1, -27, 16, -3, 0, 0, 0.6283185F);
        part(root, "rrleg3", 64, 16, -20, 0, 0, 20, 1, 1, -1, 0, 8, 0, 0.1570796F, 0);
        part(root, "rrleg4", 64, 44, -19, 0, -2, 4, 1, 5, -1, 0, 8, 0, 0.1570796F, 0);
        part(root, "rrleg2", 21, 32, 0, 0, 0, 1, 22, 1, -21, 0, 11, 0, 0, 0.5410521F);
        part(root, "rrleg1", 49, 35, 0, 0, 0, 1, 10, 1, -32, 18, 11, 0, 0, 0.8726646F);

        return LayerDefinition.create(mesh, 256, 256);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().mirror().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(MantisEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float wing = Mth.cos(ageInTicks * 0.9F * WING_SPEED) * Mth.PI * 0.25F;
        lfwing.zRot = -0.698F - wing;
        rfwing.zRot = 0.698F + wing;

        float rearWing = Mth.cos(ageInTicks * 0.9F * WING_SPEED) * Mth.PI * 0.35F;
        lrwing.zRot = -0.349F + rearWing;
        rrwing.zRot = 0.349F - rearWing;

        float arm;
        float base;
        if (entity.getAttacking() == 0) {
            arm = Mth.cos(ageInTicks * 0.051F * WING_SPEED) * Mth.PI * 0.013F;
            base = -0.2F;
        } else {
            arm = Mth.cos(ageInTicks * 0.51F * WING_SPEED) * Mth.PI * 0.25F;
            base = -0.698F;
        }

        larm1.xRot = base + arm;
        larm2.z = larm1.z + 1.0F + Mth.sin(larm1.xRot) * 22.0F;
        larm2.y = larm1.y + Mth.cos(larm1.xRot) * 22.0F;
        larm2.xRot = -base - arm;
        larm3.z = larm2.z + 1.0F - Mth.sin(larm2.xRot) * 17.0F;
        larm3.y = larm2.y - Mth.cos(larm2.xRot) * 17.0F;
        larm3.xRot = base + arm;

        rarm1.xRot = base - arm;
        rarm2.z = rarm1.z + 1.0F + Mth.sin(rarm1.xRot) * 22.0F;
        rarm2.y = rarm1.y + Mth.cos(rarm1.xRot) * 22.0F;
        rarm2.xRot = -base + arm;
        rarm3.z = rarm2.z + 1.0F - Mth.sin(rarm2.xRot) * 17.0F;
        rarm3.y = rarm2.y - Mth.cos(rarm2.xRot) * 17.0F;
        rarm3.xRot = base - arm;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
