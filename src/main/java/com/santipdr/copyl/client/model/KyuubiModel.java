package com.santipdr.copyl.client.model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.KyuubiEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
public final class KyuubiModel extends EntityModel<KyuubiEntity>{
 public static final ModelLayerLocation LAYER_LOCATION=new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID,"kyuubi"),"main");
 private final ModelPart root,head,legs[],tails[];
 public KyuubiModel(ModelPart root){this.root=root;head=root.getChild("head");legs=new ModelPart[]{root.getChild("front_left_leg"),root.getChild("front_right_leg"),root.getChild("back_left_leg"),root.getChild("back_right_leg")};tails=new ModelPart[9];for(int i=0;i<9;i++)tails[i]=root.getChild("tail"+i);}
 public static LayerDefinition createBodyLayer(){MeshDefinition m=new MeshDefinition();PartDefinition r=m.getRoot();
 r.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-4,-9,-7,8,10,15),PartPose.ZERO);
 r.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,30).addBox(-4,-14,-13,8,8,8).texOffs(32,30).addBox(-3,-18,-12,2,5,2).addBox(1,-18,-12,2,5,2),PartPose.ZERO);
 r.addOrReplaceChild("front_left_leg",CubeListBuilder.create().texOffs(40,0).addBox(-1.5F,-6,-1.5F,3,6,3),PartPose.offset(2.5F,0,-4));
 r.addOrReplaceChild("front_right_leg",CubeListBuilder.create().texOffs(40,0).mirror().addBox(-1.5F,-6,-1.5F,3,6,3),PartPose.offset(-2.5F,0,-4));
 r.addOrReplaceChild("back_left_leg",CubeListBuilder.create().texOffs(40,0).addBox(-1.5F,-6,-1.5F,3,6,3),PartPose.offset(2.5F,0,5));
 r.addOrReplaceChild("back_right_leg",CubeListBuilder.create().texOffs(40,0).mirror().addBox(-1.5F,-6,-1.5F,3,6,3),PartPose.offset(-2.5F,0,5));
 for(int i=0;i<9;i++){float fan=(i-4)*0.16F;float y=-7.5F+(Math.abs(i-4)%3)*1.6F;float z=7.0F+(Math.abs(i-4)/3)*1.5F;r.addOrReplaceChild("tail"+i,CubeListBuilder.create().texOffs(0,48).addBox(-1.5F,-1.5F,0,3,3,13),PartPose.offsetAndRotation(0,y,z,0,fan,0.42F));}
 return LayerDefinition.create(m,512,256);}
 @Override public void setupAnim(KyuubiEntity e,float swing,float amount,float age,float yaw,float pitch){head.yRot=yaw*Mth.DEG_TO_RAD;head.xRot=pitch*Mth.DEG_TO_RAD;float step=Mth.cos(swing*0.6662F)*1.1F*amount;legs[0].xRot=step;legs[3].xRot=step;legs[1].xRot=-step;legs[2].xRot=-step;for(int i=0;i<tails.length;i++)tails[i].yRot+=(float)Math.sin(age*0.08F+i*0.35F)*0.035F;}
 @Override public void renderToBuffer(PoseStack pose,VertexConsumer consumer,int light,int overlay,float red,float green,float blue,float alpha){root.render(pose,consumer,light,overlay,red,green,blue,alpha);}
}