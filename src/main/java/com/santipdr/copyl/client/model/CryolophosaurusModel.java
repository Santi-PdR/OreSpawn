package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.CryolophosaurusEntity;
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

/** Port geométrico de danger.orespawn.entity.model.ModelCryolophosaurus. */
public final class CryolophosaurusModel extends EntityModel<CryolophosaurusEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "cryolophosaurus"), "main");

    private final ModelPart root;
    private final ModelPart jaw;
    private final ModelPart rightleg;
    private final ModelPart rightleg2;
    private final ModelPart rightleg3;
    private final ModelPart rightleg4;
    private final ModelPart leftleg;
    private final ModelPart leftleg2;
    private final ModelPart leftleg3;
    private final ModelPart leftleg4;

    public CryolophosaurusModel(ModelPart root) {
        this.root = root;
        this.jaw = root.getChild("jaw");
        this.rightleg = root.getChild("rightleg");
        this.rightleg2 = root.getChild("rightleg2");
        this.rightleg3 = root.getChild("rightleg3");
        this.rightleg4 = root.getChild("rightleg4");
        this.leftleg = root.getChild("leftleg");
        this.leftleg2 = root.getChild("leftleg2");
        this.leftleg3 = root.getChild("leftleg3");
        this.leftleg4 = root.getChild("leftleg4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        part(root, "Shape1", 0, 0, 0, 0, 0, 8, 9, 18, 0, 0, 0, 0, 0, 0);
        part(root, "Shape2", 53, 0, 0, 0, 0, 6, 4, 11, 1, -2, -7, -0.2268928F, 0, 0);
        part(root, "Shape3", 0, 41, 0, 0, 0, 6, 4, 10, 1, -2, -15, 0, 0, 0);
        part(root, "jaw", 0, 30, 0, 0, 0, 4, 9, 1, 2, 1, -8, -1.256637F, 0, 0);
        part(root, "Shape5", 91, 0, 0, 0, 0, 6, 6, 7, 1, 0, 18, 0, 0, 0);
        part(root, "Shape6", 36, 31, 0, 0, 0, 4, 4, 14, 2, 0, 25, 0, 0, 0);
        part(root, "Shape7", 43, 8, 0, 0, 0, 1, 4, 2, -1, 8, 0, 0.1919862F, 0, 0);
        part(root, "Shape8", 9, 0, 0, 0, 0, 1, 3, 1, -1, 11, 1, -0.2617994F, 0, 0);
        part(root, "Shape9", 0, 0, 0, 0, 0, 2, 4, 1, 3, -4, -9, -0.9424778F, 0, 0);
        part(root, "rightleg", 0, 58, 0, 0, 0, 2, 10, 6, -1, 2, 12, -0.2792527F, 0, 0);
        part(root, "Shape11", 39, 0, 0, 0, 0, 4, 3, 3, 2, -1, -18, 0, 0, 0);
        part(root, "rightleg2", 0, 77, 0, 7, -5, 2, 10, 3, -1, 2, 12, 0.3839724F, 0, 0);
        part(root, "rightleg3", 35, 31, 0, 10, 12, 2, 7, 2, -1, 2, 12, -0.6806784F, 0, 0);
        part(root, "rightleg4", 68, 55, 0, 20, -5, 2, 2, 6, -1, 2, 12, 0, 0, 0);
        part(root, "leftleg", 22, 58, 0, 0, 0, 2, 10, 6, 7, 2, 12, -0.2792527F, 0, 0);
        part(root, "Shape16", 0, 8, 0, 0, 0, 1, 4, 2, 8, 8, 0, 0.1919862F, 0, 0);
        part(root, "Shape17", 9, 9, 0, 0, 0, 1, 3, 1, 8, 11, 1, -0.2617994F, 0, 0);
        part(root, "leftleg2", 16, 77, 0, 7, -5, 2, 10, 3, 7, 2, 12, 0.3839724F, 0, 0);
        part(root, "leftleg3", 67, 31, 0, 10, 12, 2, 7, 2, 7, 2, 12, -0.6806784F, 0, 0);
        part(root, "leftleg4", 47, 56, 0, 20, -5, 2, 2, 6, 7, 2, 12, 0, 0, 0);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(CryolophosaurusEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float leg = 0.0F;
        if (limbSwingAmount > 0.1F) {
            leg = Mth.cos(ageInTicks * 0.4F * 1.5F) * Mth.PI * 0.25F * limbSwingAmount;
        }

        rightleg.xRot = -0.2792527F + leg;
        rightleg2.xRot = 0.384F + leg;
        rightleg3.xRot = -0.68F + leg;
        rightleg4.xRot = leg;

        leftleg.xRot = -0.2792527F - leg;
        leftleg2.xRot = 0.384F - leg;
        leftleg3.xRot = -0.68F - leg;
        leftleg4.xRot = -leg;

        jaw.xRot = -1.15F + Mth.cos(ageInTicks * 0.28F) * Mth.PI * 0.1F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        // El modelo 1.12 hacía estas transformaciones dentro de render().
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.67499995D, 0.0D);
        poseStack.scale(0.55F, 0.55F, 0.55F);
        root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
