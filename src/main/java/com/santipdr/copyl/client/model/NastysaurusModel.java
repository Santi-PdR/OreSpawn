package com.santipdr.copyl.client.model;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.NastysaurusEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
public final class NastysaurusModel extends EntityModel<NastysaurusEntity>{
 public static final ModelLayerLocation LAYER_LOCATION=new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID,"nastysaurus"),"main");
 private final ModelPart head,tail;private final ModelPart[] legs=new ModelPart[4];
 public NastysaurusModel(ModelPart root){head=root.getChild("head");tail=root.getChild("tail");legs[0]=root.getChild("front_left_leg");legs[1]=root.getChild("front_right_leg");legs[2]=root.getChild("rear_left_leg");legs[3]=root.getChild("rear_right_leg");}
 public static LayerDefinition createBodyLayer(){MeshDefinition mesh=new MeshDefinition();PartDefinition r=mesh.getRoot();
 r.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,0).addBox(-16,-48,-20,32,30,48),PartPose.ZERO);
 r.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,80).addBox(-13,-66,-37,26,24,24),PartPose.ZERO);
 r.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(160,0).addBox(-5,-39,20,10,13,42),PartPose.ZERO);
 r.addOrReplaceChild("front_left_leg",CubeListBuilder.create().texOffs(160,80).addBox(-6,-26,-5,12,26,12),PartPose.offset(11,0,-13));
 r.addOrReplaceChild("front_right_leg",CubeListBuilder.create().texOffs(160,80).mirror().addBox(-6,-26,-5,12,26,12),PartPose.offset(-11,0,-13));
 r.addOrReplaceChild("rear_left_leg",CubeListBuilder.create().texOffs(220,80).addBox(-7,-27,-6,14,27,14),PartPose.offset(11,0,15));
 r.addOrReplaceChild("rear_right_leg",CubeListBuilder.create().texOffs(220,80).mirror().addBox(-7,-27,-6,14,27,14),PartPose.offset(-11,0,15));
 return LayerDefinition.create(mesh,512,256);}
 @Override public void setupAnim(NastysaurusEntity e,float swing,float amount,float age,float yaw,float pitch){head.yRot=yaw*Mth.DEG_TO_RAD;head.xRot=pitch*Mth.DEG_TO_RAD;float step=Mth.cos(swing*0.6662F)*0.8F*amount;legs[0].xRot=step;legs[3].xRot=step;legs[1].xRot=-step;legs[2].xRot=-step;tail.yRot=Mth.cos(age*0.08F)*0.08F;}
}