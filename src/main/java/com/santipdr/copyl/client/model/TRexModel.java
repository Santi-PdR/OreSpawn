package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.TRexEntity;
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

/** Geometría y animación portadas de ModelTRex 1.12.2. */
public final class TRexModel extends EntityModel<TRexEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "trex"), "main");

    private final ModelPart root;
    private final ModelPart jaw;
    private final ModelPart leftleg, leftleg2, leftleg3, leftleg4;
    private final ModelPart rightleg, rightleg2, rightleg3, rightleg4;
    private final ModelPart shape11, shape17;

    public TRexModel(ModelPart root) {
        this.root = root;
        this.jaw = root.getChild("jaw");
        this.leftleg = root.getChild("leftleg");
        this.leftleg2 = root.getChild("leftleg2");
        this.leftleg3 = root.getChild("leftleg3");
        this.leftleg4 = root.getChild("leftleg4");
        this.rightleg = root.getChild("rightleg");
        this.rightleg2 = root.getChild("rightleg2");
        this.rightleg3 = root.getChild("rightleg3");
        this.rightleg4 = root.getChild("rightleg4");
        this.shape11 = root.getChild("Shape11");
        this.shape17 = root.getChild("Shape17");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        part(r,"Shape18",91,114,0,0,0,2,4,5,3.3F,-25,-23,0.5759587F,0,0.5585054F);
        part(r,"Shape19",71,114,0,0,0,2,4,5,-4,-24,-23,0.5759587F,0,-0.5585054F);
        part(r,"Shape20",91,30,0,0,0,2,7,5,5,-8,-6,0.3839724F,0,0);
        part(r,"Shape21",93,46,-2,0,0,2,7,5,-4,-8,-6,0.3839724F,0,0);
        part(r,"Shape1",0,0,-7,0,0,10,18,31,2.5F,-19,-8,0,0,0);
        part(r,"Shape2",62,0,-5,0,0,10,11,11,0.5F,-19,23,0,0,0);
        part(r,"Shape3",10,54,-3,0,0,7,7,25,0,-19,34,0,0,0);
        part(r,"Shape4",68,88,-5,0,0,8,9,16,1.5F,-25,-16,-0.4014257F,0,0);
        part(r,"Shape5",75,65,0,0,0,9,9,12,-4,-25,-27,0,0,0);
        part(r,"Shape6",0,50,0,0,0,7,9,9,-3,-25,-36,0,0,0);
        part(r,"jaw",0,86,-5,0,-10,7,1,13,2,-15,-24,0.5201081F,0,0);
        part(r,"leftleg",0,0,-1,0,0,3,16,10,6,-10,11,-0.1745329F,0,0);
        part(r,"leftleg2",0,106,-1,12,-8,3,15,5,6,-10,11,0.5061455F,0,0);
        part(r,"leftleg3",112,89,-1,19,16,3,9,3,6,-10,11,-0.4014257F,0,0);
        part(r,"Shape11",0,72,0,0,0,2,10,2,5,-5,-3,-0.5235988F,0,0);
        part(r,"rightleg",54,51,0,0,0,3,16,10,-7,-10,11,-0.1745329F,0,0);
        part(r,"rightleg2",23,106,0,12,-8,3,15,5,-7,-10,11,0.5061455F,0,0);
        part(r,"rightleg3",70,90,0,19,16,3,9,3,-7,-10,11,-0.4014257F,0,0);
        part(r,"leftleg4",42,113,-1,31,-1,3,3,8,6,-10,11,0,0,0);
        part(r,"rightleg4",44,93,0,31,-1,3,3,8,-7,-10,11,0,0,0);
        part(r,"Shape17",112,60,-2,0,0,2,10,2,-4,-3.533333F,-3,-0.5235988F,0,0);
        part(r,"TailExtension",0,10,0,0,0,3,3,10,-1,-19,59,0,0,0);
        part(r,"Spine1",73,0,0,0,0,2,2,3,-1,-21,0,0,0,0);
        part(r,"Spine2",73,0,0,0,0,2,2,3,-0.5F,-21,6,0,0,0);
        part(r,"Spine3",73,0,0,0,0,2,2,3,-0.5F,-21,12,0,0,0);
        part(r,"Spine4",73,0,0,0,0,2,2,3,-0.5F,-24,-9,-0.4014257F,0,0);
        part(r,"Spine5",73,0,0,0,0,2,2,3,-0.5F,-26,-14,-0.4014257F,0,0);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x,float y,float z,float dx,float dy,float dz,
                             float px,float py,float pz,float rx,float ry,float rz) {
        root.addOrReplaceChild(name, CubeListBuilder.create().texOffs(u,v).addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(TRexEntity e, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = limbSwingAmount > 0.1F
                ? Mth.cos(ageInTicks * 1.3F * 0.2F) * Mth.PI * 0.25F * limbSwingAmount
                : 0.0F;
        rightleg.xRot = -0.174F + angle;
        rightleg2.xRot = 0.506F + angle;
        rightleg3.xRot = -0.401F + angle;
        rightleg4.xRot = angle;
        leftleg.xRot = -0.174F - angle;
        leftleg2.xRot = 0.506F - angle;
        leftleg3.xRot = -0.401F - angle;
        leftleg4.xRot = -angle;
        jaw.xRot = e.getAttacking() != 0
                ? 0.52F + Mth.cos(ageInTicks * 0.45F) * Mth.PI * 0.18F
                : 0.1F;
        shape17.xRot = -0.523F + Mth.cos(ageInTicks * 0.1F) * Mth.PI * 0.05F;
        shape11.xRot = -0.523F + Mth.cos(ageInTicks * 0.1F) * Mth.PI * 0.05F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
