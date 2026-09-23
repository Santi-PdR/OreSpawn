package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.BaryonyxEntity;
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

/** Port geométrico y de animación de danger.orespawn.entity.model.ModelBaryonyx. */
public final class BaryonyxModel extends EntityModel<BaryonyxEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "baryonyx"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart shape13;
    private final ModelPart shape15;
    private final ModelPart shape16;
    private final ModelPart shape17;
    private final ModelPart shape21;
    private final ModelPart shape24;
    private final ModelPart shape25;
    private final ModelPart shape26;

    public BaryonyxModel(ModelPart root) {
        this.root = root;
        this.shape13 = root.getChild("Shape13");
        this.shape15 = root.getChild("Shape15");
        this.shape16 = root.getChild("Shape16");
        this.shape17 = root.getChild("Shape17");
        this.shape21 = root.getChild("Shape21");
        this.shape24 = root.getChild("Shape24");
        this.shape25 = root.getChild("Shape25");
        this.shape26 = root.getChild("Shape26");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        for (int i = 0; i < 19; i++) {
            part(r, "Shape" + (27 + i), 0, 0, 0, 0, 0, 0, 2, 1,
                    0, -17, -10 + i * 3, 0, 0, 0);
        }
        part(r, "Shape46", 0, 0, 0, 0, 0, 0, 2, 1, 0, -12, -11, 0, 0, 0);
        part(r, "Shape47", 0, 0, 0, 0, 0, 0, 2, 1, 0, -13, -13, 0, 0, 0);
        part(r, "Shape48", 0, 0, 0, 0, 0, 0, 2, 1, 0, -15, -15, 0, 0, 0);
        part(r, "Shape49", 0, 0, 0, 0, 0, 0, 2, 1, 0, -16, -16, 0, 0, 0);
        part(r, "Shape50", 0, 0, 0, 0, 0, 0, 1, 1, 0, -19, -17, 0, 0, 0);
        part(r, "Shape51", 0, 0, 0, 0, 0, 0, 1, 1, 0, -19, -19, 0, 0, 0);

        part(r, "Shape1", 0, 0, 0, 0, 0, 10, 17, 25, -5, -15, -10, 0, 0, 0);
        part(r, "Shape2", 0, 93, -3, 0, -11, 6, 10, 11, 0, -10, -6, -0.1919862F, 0, 0);
        part(r, "Shape3", 29, 110, -2, -9, -8, 4, 9, 8, 0, -10, -11, 0.7504916F, 0, 0);
        part(r, "Shape4", 54, 108, 0, 0, 0, 6, 7, 12, -3, -18, -28, 0, 0, 0);
        part(r, "Shape5", 54, 86, 0, 0, 0, 3, 6, 15, -1.5F, -17.5F, -43, 0, 0, 0);
        part(r, "Shape6", 0, 43, 0, 0, 0, 8, 11, 8, -4, -15, 15, 0, 0, 0);
        part(r, "Shape7", 0, 63, 0, 0, 0, 6, 6, 23, -3, -15, 23, 0, 0, 0);
        part(r, "Shape8", 47, 0, 0, 0, 0, 2, 5, 3, 5, 0, -7, 0, 0, 0);
        part(r, "Shape9", 49, 10, 0, 0, 0, 2, 6, 2, 5.1F, 3, -6, -0.3839724F, 0, 0);
        part(r, "Shape10", 13, 17, 0, 0, 0, 2, 4, 3, 5, 7, -8, 0, 0, 0);
        part(r, "Shape11", 0, 17, 0, 0, -2, 1, 1, 2, 5, 8, -8, 0, 0, 0);
        part(r, "Shape12", 0, 21, 0, 0, 0, 1, 1, 1, 5, 9, -11, 0, 0, 0);
        part(r, "Shape13", 95, 36, 0, 0, 0, 3, 21, 13, 5, -15, 2, 0, 0, 0);
        part(r, "Shape14", 36, 94, 0, 0, -3, 3, 5, 3, -1.5F, -17, -43, 0, 0, 0);
        part(r, "Shape15", 113, 71, 0, 18, 8, 3, 18, 4, 5, -15, 2, -0.1745329F, 0, 0);
        part(r, "Shape16", 13, 11, -2, 0, 0, 2, 1, 3, 5, 10, -8, 0, 0, 0);
        part(r, "Shape17", 0, 74, 0, 35, -1, 3, 3, 6, 5, -15, 2, 0, 0, 0);
        part(r, "Shape18", 58, 0, -2, 0, 0, 2, 5, 3, -5, 0, -7, 0, 0, 0);
        part(r, "Shape19", 59, 10, -2, 0, 0, 2, 6, 2, -5.1F, 3, -6, -0.3839724F, 0, 0);
        part(r, "Shape20", 71, 5, -2, 0, 0, 2, 4, 3, -5, 7, -8, 0, 0, 0);
        part(r, "Shape21", 71, 0, 0, 0, 0, 2, 1, 3, -5, 10, -8, 0, 0, 0);
        part(r, "Shape22", 0, 10, -1, 0, -2, 1, 1, 2, -5, 8, -8, 0, 0, 0);
        part(r, "Shape23", 0, 14, -1, 0, 0, 1, 1, 1, -5, 9, -11, 0, 0, 0);
        part(r, "Shape24", 95, 0, -3, 0, 0, 3, 22, 13, -5, -15, 2, 0, 0, 0);
        part(r, "Shape25", 96, 71, -3, 18, 8, 3, 18, 4, -5, -15, 2, -0.1745329F, 0, 0);
        part(r, "Shape26", 0, 64, -3, 35, -1, 3, 3, 6, -5, -15, 2, 0, 0, 0);
        part(r, "Shape52", 9, 0, 0, 0, 0, 0, 2, 2, 0, -19, -30, 0, 0, 0);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, int dx, int dy, int dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(BaryonyxEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = limbSwingAmount > 0.1F
                ? Mth.cos(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.15F * limbSwingAmount
                : 0.0F;

        shape24.xRot = angle;
        shape25.xRot = -0.17F + angle;
        shape26.xRot = angle;
        shape13.xRot = -angle;
        shape15.xRot = -0.17F - angle;
        shape17.xRot = -angle;

        float armAngle = Mth.cos(ageInTicks * 0.7F * WING_SPEED) * Mth.PI * 0.25F;
        shape21.zRot = armAngle;
        shape16.zRot = -armAngle;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
