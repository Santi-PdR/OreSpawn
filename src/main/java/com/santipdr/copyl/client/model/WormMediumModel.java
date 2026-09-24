package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.WormMediumEntity;
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

/** Geometría y animación portadas de ModelWormMedium del JAR 1.12.2. */
public final class WormMediumModel extends EntityModel<WormMediumEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "wormmedium"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tooth1;
    private final ModelPart tooth2;
    private final ModelPart tooth3;
    private final ModelPart tooth4;
    private final ModelPart head2;

    public WormMediumModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
        this.tooth1 = root.getChild("tooth1");
        this.tooth2 = root.getChild("tooth2");
        this.tooth3 = root.getChild("tooth3");
        this.tooth4 = root.getChild("tooth4");
        this.head2 = root.getChild("head2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        r.addOrReplaceChild("head", CubeListBuilder.create().mirror().texOffs(24, 0)
                        .addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        r.addOrReplaceChild("body", CubeListBuilder.create().mirror().texOffs(37, 0)
                        .addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F),
                PartPose.offset(0.0F, 13.0F, 0.0F));
        r.addOrReplaceChild("tail", CubeListBuilder.create().mirror().texOffs(50, 0)
                        .addBox(-1.5F, -12.0F, -1.5F, 3.0F, 12.0F, 3.0F),
                PartPose.offset(0.0F, 25.0F, 0.0F));
        r.addOrReplaceChild("tooth1", CubeListBuilder.create().mirror().texOffs(15, 0)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(1.0F, -11.0F, 0.0F));
        r.addOrReplaceChild("tooth2", CubeListBuilder.create().mirror().texOffs(5, 0)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(-1.0F, -11.0F, 0.0F));
        r.addOrReplaceChild("tooth3", CubeListBuilder.create().mirror().texOffs(0, 0)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, -11.0F, 1.0F));
        r.addOrReplaceChild("tooth4", CubeListBuilder.create().mirror().texOffs(10, 0)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, -11.0F, -1.0F));
        r.addOrReplaceChild("head2", CubeListBuilder.create().mirror().texOffs(0, 6)
                        .addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(WormMediumEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = Mth.cos(ageInTicks * 0.45F) * Mth.PI * 0.10F;
        tail.xRot = angle;
        float sinX = Mth.sin(angle) * 12.0F;
        float cosX = Mth.cos(angle) * 12.0F;
        body.z = tail.z - sinX;

        angle = Mth.cos(ageInTicks * 0.25F) * Mth.PI * 0.08F;
        tail.zRot = angle;
        float cosZ = Mth.cos(angle) * cosX;
        float sinZ = Mth.sin(angle) * cosX;
        body.x = tail.x + sinZ;
        body.y = tail.y - 12.0F + (12.0F - cosZ);

        angle = Mth.cos(ageInTicks * 0.35F) * Mth.PI * 0.10F;
        body.xRot = angle;
        sinX = Mth.sin(angle) * 12.0F;
        cosX = Mth.cos(angle) * 12.0F;
        head.z = body.z - sinX;
        head2.z = head.z;

        angle = Mth.cos(ageInTicks * 0.15F) * Mth.PI * 0.07F;
        body.zRot = angle;
        cosZ = Mth.cos(angle) * cosX;
        sinZ = Mth.sin(angle) * cosX;
        head.x = body.x + sinZ;
        head2.x = head.x;
        head.y = body.y - 12.0F + (12.0F - cosZ);
        head2.y = head.y;

        head.xRot = 0.62F + Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
        head2.xRot = head.xRot;
        head.zRot = Mth.cos(ageInTicks * 0.25F) * Mth.PI * 0.05F;
        head2.zRot = head.zRot;

        tooth1.xRot = head.xRot;
        tooth2.xRot = head.xRot;
        tooth3.xRot = head.xRot;
        tooth4.xRot = head.xRot;
        float toothSinX = Mth.sin(head.xRot) * 12.0F;
        float toothCosX = Mth.cos(head.xRot) * 12.0F;
        tooth1.z = head.z - toothSinX;
        tooth2.z = tooth1.z;
        tooth3.z = tooth1.z;
        tooth4.z = tooth1.z;
        tooth1.zRot = head.zRot;
        tooth2.zRot = head.zRot;
        tooth3.zRot = head.zRot;
        tooth4.zRot = head.zRot;
        float toothCosZ = Mth.cos(head.zRot) * toothCosX;
        float toothSinZ = Mth.sin(head.zRot) * toothCosX;
        tooth1.x = head.x + toothSinZ;
        tooth2.x = tooth1.x;
        tooth3.x = tooth1.x;
        tooth4.x = tooth1.x;
        tooth1.y = head.y - 12.0F + (12.0F - toothCosZ);
        tooth2.y = tooth1.y;
        tooth3.y = tooth1.y;
        tooth4.y = tooth1.y;

        tooth1.z += 1.0F;
        tooth2.z -= 1.0F;
        tooth1.xRot = tooth1.xRot - 0.4F - Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
        tooth2.xRot = tooth2.xRot + 0.4F + Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
        tooth3.x += 1.0F;
        tooth4.x -= 1.0F;
        tooth3.zRot = tooth3.zRot + 0.4F + Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
        tooth4.zRot = tooth4.zRot - 0.4F - Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
