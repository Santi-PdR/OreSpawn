package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.BeaverEntity;
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

/** Port geométrico y de animación de danger.orespawn.entity.model.ModelBeaver. */
public final class BeaverModel extends EntityModel<BeaverEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "beaver"), "main");

    private final ModelPart root;
    private final ModelPart teeth;
    private final ModelPart tail;
    private final ModelPart rff;
    private final ModelPart lff;
    private final ModelPart rrf;
    private final ModelPart lrf;

    public BeaverModel(ModelPart root) {
        this.root = root;
        this.teeth = root.getChild("teeth");
        this.tail = root.getChild("tail");
        this.rff = root.getChild("rff");
        this.lff = root.getChild("lff");
        this.rrf = root.getChild("rrf");
        this.lrf = root.getChild("lrf");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        part(root, "head", 0, 3, 0, 0, 0, 6, 5, 5, 0, 15, -8);
        part(root, "nose", 6, 0, 0, 0, 0, 2, 1, 1, 2, 18, -8.5F);
        part(root, "teeth", 0, 0, 0, 0, 0, 2, 2, 1, 2, 19, -8.2F);
        part(root, "body", 0, 13, 0, 0, 0, 8, 8, 10, -1, 14, -3);
        part(root, "tail", 22, 0, 0, -1, 0, 5, 1, 8, 0.5F, 21, 7);
        part(root, "rff", 22, 9, 0, 0, 0, 2, 2, 2, -0.5F, 22, -2.5F);
        part(root, "lff", 22, 9, 0, 0, 0, 2, 2, 2, 4.5F, 22, -2.5F);
        part(root, "rrf", 22, 9, 0, 0, 0, 2, 2, 2, -0.5F, 22, 4.5F);
        part(root, "lrf", 22, 9, 0, 0, 0, 2, 2, 2, 4.5F, 22, 4.5F);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, int dx, int dy, int dz,
                             float px, float py, float pz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).mirror().addBox(x, y, z, dx, dy, dz),
                PartPose.offset(px, py, pz));
    }

    @Override
    public void setupAnim(BeaverEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float leg = Mth.cos(ageInTicks * 3.7F) * Mth.PI * 0.45F * limbSwingAmount;
        rff.xRot = leg;
        lrf.xRot = leg;
        lff.xRot = -leg;
        rrf.xRot = -leg;
        teeth.xRot = Mth.cos(ageInTicks * 2.7F) * Mth.PI * 0.25F;
        tail.xRot = Mth.cos(ageInTicks * 0.5F) * Mth.PI * 0.05F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
