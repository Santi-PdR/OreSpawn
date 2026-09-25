package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.DoomWormEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public final class DoomWormModel extends EntityModel<DoomWormEntity> {
    public static final ModelLayerLocation LAYER_LOCATION=new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID,"doomworm"),"main");
    private final ModelPart head,body;
    public DoomWormModel(ModelPart root){head=root.getChild("head");body=root.getChild("body");}
    public static LayerDefinition createBodyLayer(){MeshDefinition m=new MeshDefinition();PartDefinition r=m.getRoot();r.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-8,-8,-8,16,16,16),PartPose.ZERO);r.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,32).addBox(-7,-7,-7,14,14,14),PartPose.ZERO);return LayerDefinition.create(m,64,256);}
    @Override public void setupAnim(DoomWormEntity e,float a,float b,float age,float yaw,float pitch){head.yRot=(float)Math.toRadians(e.segmentYaw(0)-e.getYRot());head.xRot=(float)Math.toRadians(e.segmentPitch(0));}
    @Override public void renderToBuffer(PoseStack p,VertexConsumer c,int l,int o,float r,float g,float b,float a){head.render(p,c,l,o,r,g,b,a);}
    public void renderSegments(DoomWormEntity e,PoseStack p,VertexConsumer c,int l,int o,float r,float g,float b,float a){for(int i=1;i<e.segmentCount();i++){p.pushPose();p.translate((e.segmentX(i)-e.getX())*16,(e.segmentY(i)-e.getY())*16,(e.segmentZ(i)-e.getZ())*16);float s=Math.max(.01F,1.0F-i/100.0F);p.scale(s,s,s);body.render(p,c,l,o,r,g,b,a);p.popPose();}}
}