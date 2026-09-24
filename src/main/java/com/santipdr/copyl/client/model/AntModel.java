package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.AntEntity;
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

/** Geometría y animación de ModelAnt 1.12.2, compartida por Red Ant y Termite. */
public final class AntModel<T extends AntEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "ant"), "main");

    private final ModelPart root;
    private final ModelPart jawsr;
    private final ModelPart jawsl;
    private final ModelPart llegtop1;
    private final ModelPart llegbot1;
    private final ModelPart llegtop2;
    private final ModelPart llegbot2;
    private final ModelPart llegtop3;
    private final ModelPart llegbot3;
    private final ModelPart rlegtop1;
    private final ModelPart rlegbot1;
    private final ModelPart rlegtop2;
    private final ModelPart rlegbot2;
    private final ModelPart rlegtop3;
    private final ModelPart rlegbot3;

    public AntModel(ModelPart root) {
        this.root = root;
        jawsr = root.getChild("jawsr");
        jawsl = root.getChild("jawsl");
        llegtop1 = root.getChild("llegtop1");
        llegbot1 = root.getChild("llegbot1");
        llegtop2 = root.getChild("llegtop2");
        llegbot2 = root.getChild("llegbot2");
        llegtop3 = root.getChild("llegtop3");
        llegbot3 = root.getChild("llegbot3");
        rlegtop1 = root.getChild("rlegtop1");
        rlegbot1 = root.getChild("rlegbot1");
        rlegtop2 = root.getChild("rlegtop2");
        rlegbot2 = root.getChild("rlegbot2");
        rlegtop3 = root.getChild("rlegtop3");
        rlegbot3 = root.getChild("rlegbot3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r, "thorax", 22, 0, 0, 0, 0, 3, 3, 3, 0, 17, 0, 0, 0, 0);
        part(r, "thorax1", 18, 0, 1, 1, -1, 1, 1, 1, 0, 17, 0, 0, 0, 0);
        part(r, "thorax3", 34, 0, 1, 1, 3, 1, 1, 1, 0, 17, 0, 0, 0, 0);
        part(r, "abdomen", 38, 0, 0, 0, 4, 3, 3, 5, 0, 17, 0, 0, 0, 0);
        part(r, "abdomen1", 54, 0, 1, 1, 9, 1, 1, 1, 0, 17, 0, 0, 0, 0);
        part(r, "head", 6, 0, 0, -1, -4, 3, 3, 3, 0, 17, 0, 0, 0, 0);
        part(r, "jawsr", 0, 9, -1, 0, -6, 1, 1, 3, 0, 17, 0, 0, 0, 0);
        part(r, "jawsl", 0, 14, 3, 0, -6, 1, 1, 3, 0, 17, 0, 0, 0, 0);
        part(r, "llegtop1", 15, 10, 3, 1, 1, 3, 1, 1, 0, 17, 0, 0, 0, 0.3839724F);
        part(r, "llegbot1", 15, 19, 5, -3, 1, 3, 1, 1, 0, 17, 0, 0, 0, 1.064651F);
        part(r, "llegtop2", 15, 13, 3, 1, 2, 3, 1, 1, 0, 17, 0, 0, -0.2094395F, 0.3839724F);
        part(r, "llegbot2", 15, 22, 5, -3, 2, 3, 1, 1, 0, 17, 0, 0, -0.2268928F, 1.064651F);
        part(r, "llegtop3", 15, 16, 3, 1, 0, 3, 1, 1, 0, 17, 0, 0, 0.3490659F, 0.3839724F);
        part(r, "llegbot3", 15, 25, 5, -3, 0, 3, 1, 1, 0, 17, 0, 0, 0.3490659F, 1.064651F);
        part(r, "rlegtop1", 25, 10, -4, 2, 1, 3, 1, 1, 0, 17, 0, 0, 0, -0.4712389F);
        part(r, "rlegbot1", 25, 19, -7, 0, 1, 3, 1, 1, 0, 17, 0, 0, 0, -0.9773844F);
        part(r, "rlegtop2", 25, 13, -4, 2, 0, 3, 1, 1, 0, 17, 0, 0, -0.5934119F, -0.4712389F);
        part(r, "rlegbot2", 25, 22, -7, 0, 0, 3, 1, 1, 0, 17, 0, 0, -0.5934119F, -0.9773844F);
        part(r, "rlegtop3", 25, 16, -4, 2, 2, 3, 1, 1, 0, 17, 0, 0, 0.418879F, -0.4712389F);
        part(r, "rlegbot3", 25, 25, -7, 0, 2, 3, 1, 1, 0, 17, 0, 0, 0.418879F, -0.9773844F);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().mirror().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float walk = Mth.cos(ageInTicks * 2.7F) * Mth.PI * 0.45F * limbSwingAmount;

        llegtop1.xRot = walk;
        llegbot1.xRot = walk;
        rlegtop2.xRot = walk;
        rlegbot2.xRot = walk;
        rlegtop3.xRot = walk;
        rlegbot3.xRot = walk;

        rlegtop1.xRot = -walk;
        rlegbot1.xRot = -walk;
        llegtop2.xRot = -walk;
        llegbot2.xRot = -walk;
        llegtop3.xRot = -walk;
        llegbot3.xRot = -walk;

        jawsl.yRot = Mth.cos(ageInTicks * 0.4F) * Mth.PI * 0.05F;
        jawsr.yRot = -jawsl.yRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
