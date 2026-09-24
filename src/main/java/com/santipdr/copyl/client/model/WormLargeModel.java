package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.WormLargeEntity;
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

/** Geometría y animación del ModelWormLarge original. */
public final class WormLargeModel extends EntityModel<WormLargeEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "wormlarge"), "main");

    private final ModelPart root;
    private final ModelPart head1, head2, head3, head4, head5;
    private final ModelPart neck1, neck2, neck3, neck4, neck5;
    private final ModelPart tailtip;
    private final ModelPart tooth1, tooth2, tooth3, tooth4, tooth5, tooth6, tooth7, tooth8;

    public WormLargeModel(ModelPart root) {
        this.root = root;
        head1 = root.getChild("head1"); head2 = root.getChild("head2"); head3 = root.getChild("head3");
        head4 = root.getChild("head4"); head5 = root.getChild("head5");
        neck1 = root.getChild("neck1"); neck2 = root.getChild("neck2"); neck3 = root.getChild("neck3");
        neck4 = root.getChild("neck4"); neck5 = root.getChild("neck5");
        tailtip = root.getChild("tailtip");
        tooth1 = root.getChild("tooth1"); tooth2 = root.getChild("tooth2");
        tooth3 = root.getChild("tooth3"); tooth4 = root.getChild("tooth4");
        tooth5 = root.getChild("tooth5"); tooth6 = root.getChild("tooth6");
        tooth7 = root.getChild("tooth7"); tooth8 = root.getChild("tooth8");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        r.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0,0).addBox(-8,-8,-20,16,16,20), PartPose.offset(0,0,10));
        r.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(83,27).addBox(8,-3,-20,3,6,19), PartPose.offset(0,0,10));
        r.addOrReplaceChild("head3", CubeListBuilder.create().texOffs(9,65).addBox(-11,-3,-20,3,6,19), PartPose.offset(0,0,10));
        r.addOrReplaceChild("head4", CubeListBuilder.create().texOffs(77,0).addBox(-3,-11,-20,6,3,20), PartPose.offset(0,0,10));
        r.addOrReplaceChild("head5", CubeListBuilder.create().texOffs(10,39).addBox(-3,8,-20,6,3,20), PartPose.offset(0,0,10));
        r.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(25,94).addBox(-6,-6,-36,12,12,36), PartPose.offsetAndRotation(0,20,33,-0.6981317F,0,0));
        r.addOrReplaceChild("neck4", CubeListBuilder.create().texOffs(25,146).addBox(-2,-8,-38,4,2,38), PartPose.offsetAndRotation(0,20,33,-0.6981317F,0,0));
        r.addOrReplaceChild("neck5", CubeListBuilder.create().texOffs(125,189).addBox(-2,6,-31,4,2,31), PartPose.offsetAndRotation(0,20,33,-0.6981317F,0,0));
        r.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(25,189).addBox(6,-2,-34,2,4,34), PartPose.offsetAndRotation(0,20,33,-0.6981317F,0,0));
        r.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(125,147).addBox(-8,-2,-34,2,4,34), PartPose.offsetAndRotation(0,20,33,-0.6981317F,0,0));
        r.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(145,21).addBox(-4,-4,0,8,8,24), PartPose.offset(0,20,29));
        r.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(180,0).addBox(-1.5F,-1.5F,0,3,3,12), PartPose.offsetAndRotation(0,19.5F,52,0.3490659F,0,0));
        r.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(145,56).addBox(4,-1,2,1,2,14), PartPose.offset(0,20,29));
        r.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(145,90).addBox(-5,-1,2,1,2,14), PartPose.offset(0,20,29));
        r.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(145,76).addBox(-1,-5,7,2,1,9), PartPose.offset(0,20,29));
        r.addOrReplaceChild("tooth1", CubeListBuilder.create().texOffs(0,220).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(0,9,-10));
        r.addOrReplaceChild("tooth2", CubeListBuilder.create().texOffs(0,210).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(0,-9,-10));
        r.addOrReplaceChild("tooth3", CubeListBuilder.create().texOffs(0,200).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(9,0,-10));
        r.addOrReplaceChild("tooth4", CubeListBuilder.create().texOffs(0,190).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(-9,0,-10));
        r.addOrReplaceChild("tooth5", CubeListBuilder.create().texOffs(0,180).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(-6,-6,-10));
        r.addOrReplaceChild("tooth6", CubeListBuilder.create().texOffs(0,170).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(6,6,-10));
        r.addOrReplaceChild("tooth7", CubeListBuilder.create().texOffs(0,160).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(6,-6,-10));
        r.addOrReplaceChild("tooth8", CubeListBuilder.create().texOffs(0,150).addBox(-0.5F,-0.5F,-7,1,1,7), PartPose.offset(-6,6,-10));
        return LayerDefinition.create(mesh,256,256);
    }

    @Override
    public void setupAnim(WormLargeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float a2;
        double dist = 32.0D;
        float a = Mth.cos(ageInTicks * 0.25F) * Mth.PI * 0.08F;
        neck1.xRot = a -= 0.698F;
        neck1.yRot = a2 = Mth.cos(ageInTicks * 0.15F) * Mth.PI * 0.07F;
        neck4.xRot = neck5.xRot = neck3.xRot = neck2.xRot = neck1.xRot;
        neck4.yRot = neck5.yRot = neck3.yRot = neck2.yRot = neck1.yRot;
        double d1 = Math.cos(a) * dist;
        double d2 = Math.sin(a) * dist;
        head1.z = (float)(neck1.z - d1);
        double d3 = Math.sin(a2) * d1;
        head1.x = (float)(neck1.x - d3);
        head1.y = (float)(neck1.y + d2);
        head1.xRot = a = Mth.cos(ageInTicks * 0.35F) * Mth.PI * 0.15F;
        head1.yRot = a2 = Mth.cos(ageInTicks * 0.45F) * Mth.PI * 0.05F;
        syncHead(head2); syncHead(head3); syncHead(head4); syncHead(head5);

        dist = 19.0D;
        d1 = Math.cos(a) * dist;
        d2 = Math.sin(a) * dist;
        tooth1.z = (float)(head1.z - d1);
        d3 = Math.sin(a2) * d1;
        tooth1.x = (float)(head1.x - d3);
        tooth1.y = (float)(head1.y + d2 - 9.0D);
        tooth2.setPos(tooth1.x, tooth1.y + 18.0F, tooth1.z);
        tooth3.setPos(tooth1.x + 9.0F, tooth1.y + 9.0F, tooth1.z);
        tooth4.setPos(tooth1.x - 9.0F, tooth1.y + 9.0F, tooth1.z);
        tooth5.setPos(tooth1.x - 6.0F, tooth1.y + 3.0F, tooth1.z);
        tooth6.setPos(tooth1.x + 6.0F, tooth1.y + 15.0F, tooth1.z);
        tooth7.setPos(tooth1.x + 6.0F, tooth1.y + 3.0F, tooth1.z);
        tooth8.setPos(tooth1.x - 6.0F, tooth1.y + 15.0F, tooth1.z);
        tooth1.z -= (float)Math.sin(head1.xRot) * 9.0F;
        tooth2.z += (float)Math.sin(head1.xRot) * 9.0F;
        tooth3.z -= (float)Math.sin(head1.yRot) * 9.0F;
        tooth4.z += (float)Math.sin(head1.yRot) * 9.0F;
        tooth7.z -= (float)Math.sin(head1.xRot) * 6.0F + (float)Math.sin(head1.yRot) * 6.0F;
        tooth6.z += (float)Math.sin(head1.xRot) * 6.0F - (float)Math.sin(head1.yRot) * 6.0F;
        tooth5.z -= (float)Math.sin(head1.xRot) * 6.0F - (float)Math.sin(head1.yRot) * 6.0F;
        tooth8.z += (float)Math.sin(head1.xRot) * 6.0F + (float)Math.sin(head1.yRot) * 6.0F;

        a = Mth.cos(ageInTicks * 0.57F) * Mth.PI * 0.35F;
        tooth1.xRot = head1.xRot + a; tooth2.xRot = head1.xRot - a;
        tooth3.yRot = head1.yRot + a; tooth4.yRot = head1.yRot - a;
        tooth5.xRot = head1.xRot + a; tooth7.xRot = head1.xRot + a;
        tooth6.xRot = head1.xRot - a; tooth8.xRot = head1.xRot - a;
        tooth6.yRot = head1.yRot + a; tooth7.yRot = head1.yRot + a;
        tooth5.yRot = head1.yRot - a; tooth8.yRot = head1.yRot - a;
        a = Mth.cos(ageInTicks * 0.63F) * Mth.PI * 0.15F;
        tailtip.xRot = a + 0.35F;
        tailtip.yRot = Mth.cos(ageInTicks * 0.63F + 1.57075F) * Mth.PI * 0.15F;
    }

    private void syncHead(ModelPart p) {
        p.setPos(head1.x, head1.y, head1.z);
        p.xRot = head1.xRot;
        p.yRot = head1.yRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
