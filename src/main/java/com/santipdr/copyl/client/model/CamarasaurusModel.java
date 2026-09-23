package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.CamarasaurusEntity;
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

/** Geometría y animación portadas de ModelCamarasaurus del JAR 1.12.2. */
public final class CamarasaurusModel extends EntityModel<CamarasaurusEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "camarasaurus"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart tail0;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart neck3;
    private final ModelPart head1;
    private final ModelPart head2;
    private final ModelPart bLegUpLeft;
    private final ModelPart bLegUpRight;
    private final ModelPart bLegDownLeft;
    private final ModelPart bLegDownRight;
    private final ModelPart fLegUpLeft;
    private final ModelPart fLegUpRight;
    private final ModelPart fLegDownLeft;
    private final ModelPart fLegDownRight;

    public CamarasaurusModel(ModelPart root) {
        this.root = root;
        tail0 = root.getChild("Tail0");
        tail1 = root.getChild("Tail1");
        tail2 = root.getChild("Tail2");
        tail3 = root.getChild("Tail3");
        neck1 = root.getChild("Neck1");
        neck2 = root.getChild("Neck2");
        neck3 = root.getChild("Neck3");
        head1 = root.getChild("Head1");
        head2 = root.getChild("Head2");
        bLegUpLeft = root.getChild("BLegupleft");
        bLegUpRight = root.getChild("BLegupright");
        bLegDownLeft = root.getChild("BLegdownleft");
        bLegDownRight = root.getChild("BLegdownright");
        fLegUpLeft = root.getChild("FLegupleft");
        fLegUpRight = root.getChild("FLegupright");
        fLegDownLeft = root.getChild("FLegdownleft");
        fLegDownRight = root.getChild("FLegdownright");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r, "Body1", 0, 135, -6, 0, 0, 12, 12, 12, 0, -1, 0, 0, 0, 0);
        part(r, "Body2", 0, 160, -5, 0, 0, 10, 10, 6, 0, -2, -4, -0.1858931F, 0, 0);
        part(r, "Body3", 0, 177, -4, 0, 0, 8, 8, 4, 0, -3, -6, -0.3346075F, 0, 0);
        part(r, "Body4", 0, 120, -5, 0, 0, 10, 10, 4, 0, 0, 11, 0, 0, 0);
        part(r, "Tail0", 0, 107, -3, -2, 0, 6, 6, 6, 0, 3, 14, -0.0743572F, 0, 0);
        part(r, "Neck1", 0, 190, -3, 0, 0, 6, 6, 5, 0, -4, -9, -0.4089647F, 0, 0);
        part(r, "Neck2", 0, 202, -2, 0, -6, 4, 4, 7, 0, -3, -9, -0.5948578F, 0, 0);
        part(r, "Neck3", 0, 214, -2, -2, -12, 4, 4, 13, 0, -5, -15, -0.8179294F, 0, 0);
        part(r, "Head1", 0, 232, -4, -3, -6, 8, 6, 6, 0, -13, -22, -0.1115358F, 0, 0);
        part(r, "Head2", 0, 245, -3, -2, -4, 6, 4, 4, 0, -13, -27, 0, 0, 0);
        part(r, "Tail1", 0, 93, -2, -3, 0, 4, 4, 9, 0, 5, 19, -0.1115358F, 0, 0);
        part(r, "Tail2", 0, 82, -1, -1, 0, 2, 2, 8, 0, 4, 26, -0.0743572F, 0, 0);
        part(r, "Tail3", 0, 73, -0.5F, -0.5F, 0, 1, 1, 7, 0, 4.5F, 34, -0.0371786F, 0, 0);
        part(r, "BLegupleft", 49, 157, 0, 0, 0, 6, 8, 6, 2, 9, 7, -0.1487195F, 0, 0);
        part(r, "FLegupleft", 49, 141, 0, 0, -6, 6, 9, 6, 2, 8, 2, 0, 0, 0);
        part(r, "BLegupright", 49, 126, -6, 0, 0, 6, 8, 6, -2, 9, 7, -0.1487144F, 0, 0);
        part(r, "FLegupright", 49, 110, -6, 0, -6, 6, 9, 6, -2, 8, 2, 0, 0, 0);
        part(r, "BLegdownright", 115, 157, -5, 7, -1, 5, 8, 5, -2, 9, 7, 0, 0, 0);
        part(r, "FLegdownleft", 94, 143, 0, 8, -6, 5, 8, 5, 2, 8, 2, 0, 0, 0);
        part(r, "FLegdownright", 94, 157, -5, 8, -6, 5, 8, 5, -2, 8, 2, 0, 0, 0);
        part(r, "BLegdownleft", 115, 143, 0, 7, -1, 5, 8, 5, 2, 9, 7, 0, 0, 0);

        return LayerDefinition.create(mesh, 256, 256);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(CamarasaurusEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float legAngle = limbSwingAmount > 0.1F
                ? Mth.cos(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.25F * limbSwingAmount
                : 0.0F;

        fLegUpLeft.xRot = legAngle;
        fLegDownLeft.xRot = legAngle;
        fLegUpRight.xRot = -legAngle;
        fLegDownRight.xRot = -legAngle;
        bLegUpLeft.xRot = -0.15F - legAngle;
        bLegDownLeft.xRot = -legAngle;
        bLegUpRight.xRot = -0.15F + legAngle;
        bLegDownRight.xRot = legAngle;

        float healthRatio = entity.getMaxHealth() <= 0.0F ? 1.0F : entity.getHealth() / entity.getMaxHealth();
        float tailAngle = Mth.cos(ageInTicks * 0.8F * WING_SPEED * healthRatio)
                * Mth.PI * 0.25F * healthRatio;
        if (entity.isOrderedToSit()) {
            tailAngle = 0.0F;
        }

        tail0.yRot = tailAngle * 0.25F;
        tail1.z = tail0.z + Mth.cos(tail0.yRot) * 5.0F;
        tail1.x = tail0.x + Mth.sin(tail0.yRot) * 5.0F;
        tail1.yRot = tailAngle * 0.5F;
        tail2.z = tail1.z + Mth.cos(tail1.yRot) * 8.0F;
        tail2.x = tail1.x + Mth.sin(tail1.yRot) * 8.0F;
        tail2.yRot = tailAngle * 0.75F;
        tail3.z = tail2.z + Mth.cos(tail2.yRot) * 7.0F;
        tail3.x = tail2.x + Mth.sin(tail2.yRot) * 7.0F;
        tail3.yRot = tailAngle;

        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        neck1.yRot = yaw * 0.125F;
        neck2.z = neck1.z;
        neck2.x = neck1.x;
        neck2.yRot = yaw * 0.25F;
        neck3.z = neck2.z - Mth.cos(neck2.yRot) * 6.0F;
        neck3.x = neck2.x - Mth.sin(neck2.yRot) * 6.0F;
        neck3.yRot = yaw * 0.38F;
        head1.z = neck3.z - Mth.cos(neck3.yRot) * 7.0F;
        head1.x = neck3.x - Mth.sin(neck3.yRot) * 7.0F;
        head1.yRot = yaw;
        head2.z = head1.z - Mth.cos(head1.yRot) * 5.0F;
        head2.x = head1.x - Mth.sin(head1.yRot) * 5.0F;
        head2.yRot = yaw;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
