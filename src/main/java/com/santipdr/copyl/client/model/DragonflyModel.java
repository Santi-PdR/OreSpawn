package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.DragonflyEntity;
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

/** Port geometrico de danger.orespawn.entity.model.ModelDragonfly. */
public final class DragonflyModel extends EntityModel<DragonflyEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "dragonfly"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart lfwing, lrwing, rfwing, rrwing, rjaw, ljaw;

    public DragonflyModel(ModelPart root) {
        this.root = root;
        this.lfwing = root.getChild("lfwing");
        this.lrwing = root.getChild("lrwing");
        this.rfwing = root.getChild("rfwing");
        this.rrwing = root.getChild("rrwing");
        this.rjaw = root.getChild("rjaw");
        this.ljaw = root.getChild("ljaw");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r,"Shape1",0,0,0,0,0,5,4,7,0,16,0,0,0,0);
        part(r,"lfwing",0,33,0,0,0,10,1,3,5,16,1,0,0.4886922F,0);
        part(r,"Shape3",0,13,-2,0,-4,4,3,4,2.5F,16,-1,0.4886922F,0,0);
        part(r,"Shape4",9,21,0,0,0,1,2,3,1,18,-6,0.4886922F,0.1745329F,0);
        part(r,"Shape5",0,21,0,0,0,1,2,3,3,18,-6,0.4886922F,-0.1745329F,0);
        part(r,"rjaw",0,27,-1,0,0,1,3,1,2,19,-5,0.4363323F,0.1745329F,0);
        part(r,"ljaw",5,27,0,0,0,1,3,1,3,19,-5,0.4363323F,-0.1745329F,0);
        part(r,"tail1",25,0,-1,0,0,3,3,7,2,16,7,0,0,0);
        part(r,"tail2",25,11,0,0,0,1,2,9,2,16,14,0,0,0);
        part(r,"Shape10",23,0,-1,0,0,1,4,1,1,18,0,-0.2792527F,0,0.3490659F);
        part(r,"Shape11",40,0,0,0,-4,1,1,4,-1,21,0,0,0,0);
        part(r,"Shape12",18,12,-1,0,0,1,3,1,0,21,-4,0,0,-0.1919862F);
        part(r,"Shape13",18,0,0,0,0,1,4,1,4,18,0,-0.2792527F,0,-0.3490659F);
        part(r,"Shape14",51,0,0,0,-4,1,1,4,5,21,0,0,0,0);
        part(r,"Shape15",13,12,0,0,0,1,3,1,5,21,-4,0,0,0.1919862F);
        part(r,"Shape16",9,53,0,0,0,3,1,1,5,19.5F,3,0,0,0.6457718F);
        part(r,"Shape17",0,56,0,0,0,1,3,1,6,21,3,0,0,0);
        part(r,"Shape18",0,53,-3,0,0,3,1,1,0,19.5F,3,0,0,-0.6457718F);
        part(r,"Shape19",5,56,-1,0,0,1,3,1,-1,21,3,0,0,0);
        part(r,"Shape20",9,61,0,0,0,3,1,1,4,19.5F,6,0,-0.6457718F,0.5061455F);
        part(r,"Shape21",0,61,0,0,0,3,1,1,1.5F,19.5F,7,0,-2.391101F,0.5061455F);
        part(r,"Shape22",0,0,0,0,0,1,3,1,-1,21,7.5F,0,0,0);
        part(r,"Shape23",0,13,0,0,0,1,3,1,5,21,7.5F,0,0,0);
        part(r,"lrwing",0,38,0,0,-3,10,1,3,5,16,6,0,-0.3839724F,0);
        part(r,"rfwing",0,48,-10,0,0,10,1,3,0,16,1,0,-0.4886922F,0);
        part(r,"rrwing",0,43,-10,0,-3,10,1,3,0,16,6,0,0.3839724F,0);

        return LayerDefinition.create(mesh,64,64);
    }

    private static void part(PartDefinition r,String name,int u,int v,
                             float x,float y,float z,float dx,float dy,float dz,
                             float px,float py,float pz,float rx,float ry,float rz) {
        r.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u,v).mirror().addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(DragonflyEntity entity,float limbSwing,float limbSwingAmount,float age,float yaw,float pitch) {
        float wing = Mth.cos(age * 1.3F * WING_SPEED) * Mth.PI * 0.25F;
        lfwing.yRot = wing;
        rfwing.yRot = -wing;
        lrwing.yRot = wing + 3.14F;
        rrwing.yRot = -wing + 3.14F;

        float jaw = Mth.cos(age * 0.3F * WING_SPEED) * Mth.PI * 0.1F;
        ljaw.xRot = jaw;
        rjaw.xRot = -jaw;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack,consumer,light,overlay,red,green,blue,alpha);
    }
}
