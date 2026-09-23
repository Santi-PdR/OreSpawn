package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.CassowaryEntity;
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

/** Port geométrico de danger.orespawn.entity.model.ModelCassowary. */
public final class CassowaryModel extends EntityModel<CassowaryEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "cassowary"), "main");

    private final ModelPart root;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart beak;
    private final ModelPart crest;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart foot1;
    private final ModelPart foot2;
    private final ModelPart gobbler;

    public CassowaryModel(ModelPart root) {
        this.root = root;
        this.neck = root.getChild("neck");
        this.head = root.getChild("head");
        this.beak = root.getChild("beak");
        this.crest = root.getChild("crest");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.foot1 = root.getChild("foot1");
        this.foot2 = root.getChild("foot2");
        this.gobbler = root.getChild("gobbler");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r, "tail", 38, 16, -3.0F, 0.0F, 0.0F, 6, 9, 7,
                0.0F, 8.0F, 1.0F, 0.8922867F, 0.0F, 0.0F);
        part(r, "body", 0, 13, -4.0F, 0.0F, 0.0F, 8, 10, 9,
                0.0F, 5.0F, -3.0F, 0.3346075F, 0.0F, 0.0F);
        part(r, "neck1", 48, 0, -2.0F, 0.0F, 0.0F, 4, 5, 4,
                0.0F, 4.0F, -1.0F, -1.189716F, 0.0F, 0.0F);
        part(r, "neck", 38, 0, -1.0F, 0.0F, 0.0F, 2, 7, 2,
                0.0F, 8.0F, -3.0F, -2.806985F, 0.0F, 0.0F);
        part(r, "head", 24, 0, -1.0F, -2.0F, -3.0F, 2, 2, 4,
                0.0F, 2.0F, -6.0F, 0.0371786F, 0.0F, 0.0F);
        part(r, "beak", 28, 7, -0.5F, 0.0F, 3.0F, 1, 1, 3,
                0.0F, 2.0F, -6.0F, -3.104414F, 0.0F, 0.0F);
        part(r, "leg1", 0, 0, -0.5F, 0.0F, -1.0F, 1, 11, 2,
                3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        part(r, "leg2", 0, 0, -0.5F, 0.0F, -1.0F, 1, 11, 2,
                -3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        part(r, "crest", 10, 0, -0.5F, -4.0F, 1.0F, 1, 4, 5,
                0.0F, 2.0F, -6.0F, 1.710216F, 0.0F, 0.0F);
        part(r, "foot1", 47, 10, -1.033333F, 11.0F, -2.0F, 2, 1, 3,
                -3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        part(r, "foot2", 47, 10, -1.0F, 11.0F, -2.0F, 2, 1, 3,
                3.0F, 12.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        part(r, "gobbler", 38, 10, -0.5F, -1.0F, -2.5F, 1, 5, 1,
                0.0F, 8.0F, -3.0F, 0.0F, 0.0F, 0.0F);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, int dx, int dy, int dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).mirror().addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(CassowaryEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float legMotion;
        float neckMotion;
        if (limbSwingAmount > 0.1F) {
            legMotion = Mth.cos(ageInTicks * 1.3F) * Mth.PI * 0.15F * limbSwingAmount;
            neckMotion = Mth.cos(ageInTicks * 2.6F) * Mth.PI * 0.1F * limbSwingAmount;
        } else {
            legMotion = 0.0F;
            neckMotion = 0.0F;
        }

        leg1.xRot = legMotion;
        foot2.xRot = legMotion;
        leg2.xRot = -legMotion;
        foot1.xRot = -legMotion;
        neck.xRot = -2.827F + neckMotion;
        gobbler.xRot = neckMotion;

        float dynamicZ = neck.z + Mth.sin(neck.xRot) * 7.0F;
        float dynamicY = neck.y + Mth.cos(neck.xRot) * 7.0F;
        head.z = dynamicZ;
        crest.z = dynamicZ;
        beak.z = dynamicZ;
        head.y = dynamicY;
        crest.y = dynamicY;
        beak.y = dynamicY;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
