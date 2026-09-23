package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.PointysaurusEntity;
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

/** Port geometrico y de animacion de danger.orespawn.entity.model.ModelPointysaurus. */
public final class PointysaurusModel extends EntityModel<PointysaurusEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "pointysaurus"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart lfleg, rfleg, lrleg, rrleg;
    private final ModelPart head, guard, nose, lhorn, rhorn, chorn, tail;
    private final ModelPart[] bumps = new ModelPart[16];

    public PointysaurusModel(ModelPart root) {
        this.root = root;
        this.lfleg = root.getChild("lfleg");
        this.rfleg = root.getChild("rfleg");
        this.lrleg = root.getChild("lrleg");
        this.rrleg = root.getChild("rrleg");
        this.head = root.getChild("head");
        this.guard = root.getChild("guard");
        this.nose = root.getChild("nose");
        this.lhorn = root.getChild("lhorn");
        this.rhorn = root.getChild("rhorn");
        this.chorn = root.getChild("chorn");
        this.tail = root.getChild("tail");
        for (int i = 0; i < bumps.length; i++) {
            bumps[i] = root.getChild("bump" + (i + 1));
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r,"lfleg",102,66,-3,0,-3,6,8,6,9,16,-8,0,0,0);
        part(r,"rfleg",102,66,-3,0,-3,6,8,6,-9,16,-8,0,0,0);
        part(r,"lrleg",0,0,-4,0,-4,8,8,8,9,16,12,0,0,0);
        part(r,"rrleg",0,0,-4,0,-4,8,8,8,-9,16,12,0,0,0);
        part(r,"body1",0,87,-4,0,0,22,9,30,-7,9,-12,0,0,0);
        part(r,"head",70,0,-6,-10,-12,12,10,12,0,11,-7,-0.1919862F,0,0);
        part(r,"body2",0,63,-9,0,0,18,7,15,0,2,-9,0,0,0);
        part(r,"body3",0,44,-8,0,0,16,6,11,0,3,6,0,0,0);
        part(r,"guard",60,34,-14,-20,-8,28,23,3,0,11,-7,-0.2617994F,0,0);
        part(r,"nose",39,0,-5,-9,-15,10,6,5,0,11,-7,0,0,0);
        part(r,"lhorn",0,18,8,-16,-29,2,2,23,0,11,-7,-0.1570796F,-0.1396263F,0);
        part(r,"rhorn",0,18,-9,-16,-29,2,2,23,0,11,-7,-0.1570796F,0.1396263F,0);
        part(r,"chorn",52,13,-1.5F,-9,-20,3,3,5,0,11,-7,0,0,0);
        part(r,"tail",68,70,-3,-3,0,6,6,9,0,7,15,0.2792527F,0,0);

        bump(r,"bump1",14,-20); bump(r,"bump2",14,-15); bump(r,"bump3",14,-10);
        bump(r,"bump4",14,-5); bump(r,"bump5",14,0);
        bump(r,"bump6",-16,-20); bump(r,"bump7",-16,-15); bump(r,"bump8",-16,-10);
        bump(r,"bump9",-16,-5); bump(r,"bump10",-16,0);
        bump(r,"bump11",12,-22); bump(r,"bump12",7,-22); bump(r,"bump13",2,-22);
        bump(r,"bump14",-4,-22); bump(r,"bump15",-9,-22); bump(r,"bump16",-14,-22);

        return LayerDefinition.create(mesh,128,128);
    }

    private static void bump(PartDefinition r, String name, float x, float y) {
        part(r,name,57,17,x,y,-8,2,2,2,0,11,-7,-0.2617994F,0,0);
    }

    private static void part(PartDefinition r, String name, int u, int v,
                             float x,float y,float z,float dx,float dy,float dz,
                             float px,float py,float pz,float rx,float ry,float rz) {
        r.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u,v).mirror().addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(PointysaurusEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float leg = 0.0F;
        if (limbSwingAmount > 0.1F) {
            leg = Mth.cos(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.25F * limbSwingAmount;
        }
        lfleg.xRot = leg;
        rrleg.xRot = leg;
        rfleg.xRot = -leg;
        lrleg.xRot = -leg;

        head.yRot = (float) Math.toRadians(netHeadYaw) * 0.45F;
        nose.yRot = head.yRot;
        chorn.yRot = head.yRot;
        lhorn.yRot = head.yRot - 0.14F;
        rhorn.yRot = head.yRot + 0.14F;
        guard.yRot = head.yRot;
        for (ModelPart bump : bumps) {
            bump.yRot = head.yRot;
        }

        head.xRot = (float) Math.toRadians(headPitch) * 0.45F;
        nose.xRot = head.xRot;
        chorn.xRot = head.xRot;
        lhorn.xRot = head.xRot - 0.16F;
        rhorn.xRot = head.xRot - 0.16F;
        guard.xRot = head.xRot - 0.262F;
        for (ModelPart bump : bumps) {
            bump.xRot = guard.xRot;
        }

        // getAttacking() en Pointysaurus 1.12.2 siempre devuelve 1.
        float tailYaw = Mth.cos(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.25F;
        tail.yRot = tailYaw;
        float tailPitch = Mth.cos(ageInTicks * 0.02F * WING_SPEED) * Mth.PI * 0.15F;
        tail.xRot = tailPitch + 0.28F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
