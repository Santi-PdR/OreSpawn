package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.WormSmallEntity;
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

/** Geometría y animación portadas de ModelWormSmall del JAR 1.12.2. */
public final class WormSmallModel extends EntityModel<WormSmallEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "wormsmall"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;

    public WormSmallModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head",
                CubeListBuilder.create().mirror().texOffs(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("body",
                CubeListBuilder.create().mirror().texOffs(6, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 19.0F, 0.0F));
        root.addOrReplaceChild("tail",
                CubeListBuilder.create().mirror().texOffs(12, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(WormSmallEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = Mth.cos(ageInTicks * 0.55F) * Mth.PI * 0.15F;
        tail.xRot = angle;
        float sinX = Mth.sin(angle) * 5.0F;
        float cosX = Mth.cos(angle) * 5.0F;
        body.z = tail.z - sinX;

        angle = Mth.cos(ageInTicks * 0.35F) * Mth.PI * 0.1F;
        tail.zRot = angle;
        float cosZ = Mth.cos(angle) * cosX;
        float sinZ = Mth.sin(angle) * cosX;
        body.x = tail.x + sinZ;
        body.y = tail.y - cosZ;

        angle = Mth.cos(ageInTicks * 0.45F) * Mth.PI * 0.15F;
        body.xRot = angle;
        sinX = Mth.sin(angle) * 5.0F;
        cosX = Mth.cos(angle) * 5.0F;
        head.z = body.z - sinX;

        angle = Mth.cos(ageInTicks * 0.25F) * Mth.PI * 0.1F;
        body.zRot = angle;
        cosZ = Mth.cos(angle) * cosX;
        sinZ = Mth.sin(angle) * cosX;
        head.x = body.x + sinZ;
        head.y = body.y - cosZ;

        head.xRot = 0.62F + Mth.cos(ageInTicks * 0.65F) * Mth.PI * 0.15F;
        head.zRot = Mth.cos(ageInTicks * 0.3F) * Mth.PI * 0.05F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
