package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.AlosaurusEntity;
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

/** Port geométrico y de animación de danger.orespawn.entity.model.ModelAlosaurus. */
public final class AlosaurusModel extends EntityModel<AlosaurusEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "alosaurus"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart jaw;
    private final ModelPart leftleg;
    private final ModelPart leftleg2;
    private final ModelPart leftleg3;
    private final ModelPart leftleg4;
    private final ModelPart rightleg;
    private final ModelPart rightleg2;
    private final ModelPart rightleg3;
    private final ModelPart rightleg4;
    private final ModelPart shape11;
    private final ModelPart shape17;

    public AlosaurusModel(ModelPart root) {
        this.root = root;
        this.jaw = root.getChild("jaw");
        this.leftleg = root.getChild("leftleg");
        this.leftleg2 = root.getChild("leftleg2");
        this.leftleg3 = root.getChild("leftleg3");
        this.leftleg4 = root.getChild("leftleg4");
        this.rightleg = root.getChild("rightleg");
        this.rightleg2 = root.getChild("rightleg2");
        this.rightleg3 = root.getChild("rightleg3");
        this.rightleg4 = root.getChild("rightleg4");
        this.shape11 = root.getChild("Shape11");
        this.shape17 = root.getChild("Shape17");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        part(root, "Shape18", 91, 114, 0F, 0F, 0F, 2, 4, 5, 3.3F, -25F, -27F, 0.5759587F, 0F, 0.5585054F);
        part(root, "Shape19", 71, 114, 0F, 0F, 0F, 2, 4, 5, -4F, -24F, -28F, 0.5759587F, 0F, -0.5585054F);
        part(root, "Shape20", 91, 30, 0F, 0F, 0F, 2, 7, 5, 5F, -8F, -6F, 0.3839724F, 0F, 0F);
        part(root, "Shape21", 93, 46, -2F, 0F, 0F, 2, 7, 5, -4F, -8F, -6F, 0.3839724F, 0F, 0F);
        part(root, "Shape1", 0, 0, -7F, 0F, 0F, 10, 18, 31, 2.5F, -19F, -8F, 0F, 0F, 0F);
        part(root, "Shape2", 62, 0, -5F, 0F, 0F, 10, 11, 11, 0.5F, -19F, 23F, 0F, 0F, 0F);
        part(root, "Shape3", 10, 54, -3F, 0F, 0F, 7, 7, 25, 0F, -19F, 34F, 0F, 0F, 0F);
        part(root, "Shape4", 68, 88, -5F, 0F, 0F, 8, 9, 16, 1.5F, -25F, -16F, -0.4014257F, 0F, 0F);
        part(root, "Shape5", 75, 65, 0F, 0F, 0F, 9, 9, 12, -4F, -25F, -27F, 0F, 0F, 0F);
        part(root, "Shape6", 0, 50, 0F, 0F, 0F, 7, 9, 9, -3F, -25F, -36F, 0F, 0F, 0F);
        part(root, "jaw", 0, 86, -5F, 0F, -10F, 7, 1, 13, 2F, -15F, -24F, 0.5201081F, 0F, 0F);
        part(root, "leftleg", 0, 0, -1F, 0F, 0F, 3, 16, 10, 6F, -10F, 11F, -0.1745329F, 0F, 0F);
        part(root, "leftleg2", 0, 106, -1F, 12F, -8F, 3, 15, 5, 6F, -10F, 11F, 0.5061455F, 0F, 0F);
        part(root, "leftleg3", 112, 89, -1F, 19F, 16F, 3, 9, 3, 6F, -10F, 11F, -0.4014257F, 0F, 0F);
        part(root, "Shape11", 0, 72, 0F, 0F, 0F, 2, 10, 2, 5F, -5F, -3F, -0.5235988F, 0F, 0F);
        part(root, "rightleg", 54, 51, 0F, 0F, 0F, 3, 16, 10, -7F, -10F, 11F, -0.1745329F, 0F, 0F);
        part(root, "rightleg2", 23, 106, 0F, 12F, -8F, 3, 15, 5, -7F, -10F, 11F, 0.5061455F, 0F, 0F);
        part(root, "rightleg3", 70, 90, 0F, 19F, 16F, 3, 9, 3, -7F, -10F, 11F, -0.4014257F, 0F, 0F);
        part(root, "leftleg4", 42, 113, -1F, 31F, -1F, 3, 3, 8, 6F, -10F, 11F, 0F, 0F, 0F);
        part(root, "rightleg4", 44, 93, 0F, 31F, -1F, 3, 3, 8, -7F, -10F, 11F, 0F, 0F, 0F);
        part(root, "Shape17", 112, 60, -2F, 0F, 0F, 2, 10, 2, -4F, -3.533333F, -3F, -0.5235988F, 0F, 0F);

        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).mirror().addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(AlosaurusEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float leg = 0.0F;
        if (limbSwingAmount > 0.1F) {
            leg = Mth.cos(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.25F * limbSwingAmount;
        }

        rightleg.xRot = -0.1745329F + leg;
        rightleg2.xRot = 0.5061455F + leg;
        rightleg3.xRot = -0.4014257F + leg;
        rightleg4.xRot = leg;
        leftleg.xRot = -0.1745329F - leg;
        leftleg2.xRot = 0.5061455F - leg;
        leftleg3.xRot = -0.4014257F - leg;
        leftleg4.xRot = -leg;

        if (entity.getAttacking() != 0) {
            jaw.xRot = 0.5201081F + Mth.cos(ageInTicks * 0.45F) * Mth.PI * 0.18F;
        } else {
            jaw.xRot = 0.1F;
        }

        float arm = -0.5235988F + Mth.cos(ageInTicks * 0.1F) * Mth.PI * 0.05F;
        shape17.xRot = arm;
        shape11.xRot = arm;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
