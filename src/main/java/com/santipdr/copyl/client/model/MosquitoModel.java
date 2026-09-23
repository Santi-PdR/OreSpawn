package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.MosquitoEntity;
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

/** Port geometrico de danger.orespawn.entity.model.ModelMosquito. */
public final class MosquitoModel extends EntityModel<MosquitoEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "mosquito"), "main");

    private final ModelPart root;
    private final ModelPart leftWing1;
    private final ModelPart rightWing1;
    private final ModelPart leftWing2;
    private final ModelPart rightWing2;

    public MosquitoModel(ModelPart root) {
        this.root = root;
        this.leftWing1 = root.getChild("leftwing1");
        this.rightWing1 = root.getChild("rightwing1");
        this.leftWing2 = root.getChild("leftwing2");
        this.rightWing2 = root.getChild("rightwing2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r, "body", 8, 18, 0, 0, -2, 1, 1, 8, 0, 17, 0);
        part(r, "leftwing1", 16, 13, 1, 0, -1, 3, 1, 3, 1, 17, 0);
        part(r, "rightwing1", 2, 13, -4, 0, -1, 3, 1, 3, 0, 17, 0);
        part(r, "leftwing2", 15, 8, 0, 0, 0, 5, 1, 1, 1, 17, 0);
        part(r, "rightwing2", 2, 8, -5, 0, 0, 5, 1, 1, 0, 17, 0);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).mirror().addBox(x, y, z, dx, dy, dz),
                PartPose.offset(px, py, pz));
    }

    @Override
    public void setupAnim(MosquitoEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float wing = Mth.cos(ageInTicks * 3.0F) * Mth.PI * 0.25F;
        rightWing1.zRot = wing;
        rightWing2.zRot = wing;
        leftWing1.zRot = -wing;
        leftWing2.zRot = -wing;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
