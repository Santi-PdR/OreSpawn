package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.BirdEntity;
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

/** Port geometrico de danger.orespawn.entity.model.ModelBird. */
public final class BirdModel extends EntityModel<BirdEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "bird"), "main");
    private static final float WING_SPEED = 0.6F;

    private final ModelPart root;
    private final ModelPart body, head, beak, lowerBeak;
    private final ModelPart feather1, feather2, feather3;
    private final ModelPart tail1, tail2, tail3;
    private final ModelPart leftWing1, leftWing2, rightWing1, rightWing2;
    private final ModelPart leg, otherLeg;

    public BirdModel(ModelPart root) {
        this.root = root;
        body = root.getChild("Body");
        head = root.getChild("Head");
        beak = root.getChild("Beak");
        lowerBeak = root.getChild("LowerBeak");
        feather1 = root.getChild("feather1");
        feather2 = root.getChild("feather2");
        feather3 = root.getChild("feather3");
        tail1 = root.getChild("tailfeather1");
        tail2 = root.getChild("tailfeather2");
        tail3 = root.getChild("tailfeather3");
        leftWing1 = root.getChild("lwing1");
        leftWing2 = root.getChild("lwing2");
        rightWing1 = root.getChild("rwing1");
        rightWing2 = root.getChild("rwing2");
        leg = root.getChild("leg");
        otherLeg = root.getChild("otherleg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r,"Body",0,0,0,0,0,5,3,6,-1,18,0,0,0,0);
        part(r,"Head",22,0,0,0,0,3,3,4,0,16,-3,0,0,0);
        part(r,"Beak",0,21,0,0,0,1,1,3,1,17,-6,0,0,0);
        part(r,"LowerBeak",1,17,0,0,0,1,1,1,1,18,-4,0,0,0);
        part(r,"feather2",15,9,0,-2.5F,-0.75F,1,3,1,1,16,0,-0.6426736F,0,0);
        part(r,"feather1",11,9,0,-2.5F,-0.5F,1,3,1,1,16,-2,-0.2230717F,0,0);
        part(r,"feather3",19,9,0,-3,0.5F,1,4,1,1,16,1,-1.276259F,0,0);
        part(r,"tailfeather1",46,15,0,0,0,3,2,3,0,18,6,0,0,0);
        part(r,"rwing1",23,9,0,0,0,1,4,4,-1,18,1,0,0,1.595066F);
        part(r,"lwing1",33,9,-1,0,0,1,4,4,4,18,1,0,0,-1.561488F);
        part(r,"leg",4,12,0,0,0,1,3,1,2,21,3,0.8726646F,0,0);
        part(r,"otherleg",0,12,0,0,0,1,3,1,0,21,3,0.6108652F,0,0);
        part(r,"lwing2",10,14,4,0,0,3,1,3,4,18,1,0,0,0);
        part(r,"rwing2",10,19,-7,0,0,3,1,3,-1,18,1,0,0,0);
        part(r,"tailfeather2",44,20,-0.5F,0,3,4,1,4,0,18,6,0,0,0);
        part(r,"tailfeather3",36,26,-1,0,7,5,1,4,0,18,6,0,0,0);

        return LayerDefinition.create(mesh,64,32);
    }

    private static void part(PartDefinition r,String name,int u,int v,
                             float x,float y,float z,float dx,float dy,float dz,
                             float px,float py,float pz,float rx,float ry,float rz) {
        r.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u,v).mirror().addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(BirdEntity entity,float limbSwing,float limbSwingAmount,float age,float yaw,float pitch) {
        float wing = Mth.cos(age * 1.5F * WING_SPEED) * Mth.PI * 0.35F;
        leftWing1.zRot = -1.5F + wing;
        leftWing2.zRot = wing;
        rightWing1.zRot = 1.5F - wing;
        rightWing2.zRot = -wing;

        float tail = Mth.cos(age * 0.3F * WING_SPEED) * Mth.PI * 0.1F;
        tail1.xRot = tail;
        tail2.xRot = tail;
        tail3.xRot = tail;

        feather1.zRot = Mth.cos(age * 1.1F * WING_SPEED) * Mth.PI * 0.08F;
        feather2.zRot = Mth.cos(age * 1.2F * WING_SPEED) * Mth.PI * 0.08F;
        feather3.zRot = Mth.cos(age * 1.3F * WING_SPEED) * Mth.PI * 0.08F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack,consumer,light,overlay,red,green,blue,alpha);
    }
}
