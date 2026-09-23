package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.AlienEntity;
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

import java.util.Map;
import java.util.WeakHashMap;

/** Port geometrico y de animacion de danger.orespawn.entity.model.ModelAlien. */
public final class AlienModel extends EntityModel<AlienEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "alien"), "main");
    private static final float WING_SPEED = 0.1F;

    private final ModelPart root;
    private final ModelPart rThigh, lThigh, lShin, rShin, lShin1, rShin1, lFoot, rFoot;
    private final ModelPart neck, fan, tail1, tail2, tail3, tail4, tail5;
    private final ModelPart fanl1, fanr1, fanl2, fanr2, fanl3, fanr3, fanl4, fanr4;
    private final ModelPart fanl5, fanr5, fanl6, fanr6, fanl7, fanr7;
    private final ModelPart spike1, spike2, spike3, spike4, spike5;
    private final ModelPart head, head1, head2, jaw1, jaw2, fang1, fang2, fang3, fang4;
    private final ModelPart arml1, armr1, arml2, armr2, clawr1, clawr2, clawr3, clawl1, clawl2, clawl3;
    private final Map<AlienEntity, RenderState> renderStates = new WeakHashMap<>();

    public AlienModel(ModelPart root) {
        this.root = root;
        rThigh = root.getChild("rThigh"); lThigh = root.getChild("lThigh");
        lShin = root.getChild("lShin"); rShin = root.getChild("rShin");
        lShin1 = root.getChild("lShin1"); rShin1 = root.getChild("rShin1");
        lFoot = root.getChild("lFoot"); rFoot = root.getChild("rFoot");
        neck = root.getChild("neck"); fan = root.getChild("fan");
        tail1 = root.getChild("tail1"); tail2 = root.getChild("tail2"); tail3 = root.getChild("tail3");
        tail4 = root.getChild("tail4"); tail5 = root.getChild("tail5");
        fanl1 = root.getChild("fanl1"); fanr1 = root.getChild("fanr1");
        fanl2 = root.getChild("fanl2"); fanr2 = root.getChild("fanr2");
        fanl3 = root.getChild("fanl3"); fanr3 = root.getChild("fanr3");
        fanl4 = root.getChild("fanl4"); fanr4 = root.getChild("fanr4");
        fanl5 = root.getChild("fanl5"); fanr5 = root.getChild("fanr5");
        fanl6 = root.getChild("fanl6"); fanr6 = root.getChild("fanr6");
        fanl7 = root.getChild("fanl7"); fanr7 = root.getChild("fanr7");
        spike1 = root.getChild("spike1"); spike2 = root.getChild("spike2"); spike3 = root.getChild("spike3");
        spike4 = root.getChild("spike4"); spike5 = root.getChild("spike5");
        head = root.getChild("head"); head1 = root.getChild("head1"); head2 = root.getChild("head2");
        jaw1 = root.getChild("jaw1"); jaw2 = root.getChild("jaw2");
        fang1 = root.getChild("fang1"); fang2 = root.getChild("fang2");
        fang3 = root.getChild("fang3"); fang4 = root.getChild("fang4");
        arml1 = root.getChild("arml1"); armr1 = root.getChild("armr1");
        arml2 = root.getChild("arml2"); armr2 = root.getChild("armr2");
        clawr1 = root.getChild("clawr1"); clawr2 = root.getChild("clawr2"); clawr3 = root.getChild("clawr3");
        clawl1 = root.getChild("clawl1"); clawl2 = root.getChild("clawl2"); clawl3 = root.getChild("clawl3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        part(r,"torso",0,46,-4.5F,-2,0,9,8,10,0,-2.5F,-8,-0.1919862F,0,0);
        part(r,"stomach",0,27,-3.5F,-5,8,7,6,12,0,-2.5F,-8,-0.5585054F,0,0);
        part(r,"rThigh",59,45,-1.5F,-4,-2.5F,4,14,5,-4.5F,7,8,-0.8028515F,0.2443461F,0.418879F);
        part(r,"lThigh",40,45,-2.5F,-4,-2.5F,4,14,5,4.5F,7,8,-0.8028515F,-0.2443461F,-0.418879F);
        part(r,"lShin",79,49,-2,8,-5.5F,3,3,12,4.5F,7,8,-0.4014257F,-0.2443461F,-0.418879F);
        part(r,"rShin",79,33,-1,8,-5.5F,3,3,12,-4.5F,7,8,-0.4014257F,0.2443461F,0.418879F);
        part(r,"lShin1",113,40,-1.5F,5.5F,9,2,9,2,4.5F,7,8,-0.8028515F,-0.2443461F,-0.418879F);
        part(r,"rShin1",113,53,-0.5F,5.5F,9,2,9,2,-4.5F,7,8,-0.8028515F,0.2443461F,0.418879F);
        part(r,"lFoot",110,24,5,15,-8,2,2,6,4.5F,7,8,0,-0.2443461F,0);
        part(r,"rFoot",95,24,-7,15,-8,2,2,6,-4.5F,7,8,0,0.2443461F,0);
        part(r,"neck",23,86,-2,-2,-4,4,6,5,0,-2.5F,-8,-0.1919862F,0,0);
        part(r,"fan",149,10,-3,-24,0,6,24,1,0,-7,-10,0,0,0);
        part(r,"tail2",85,66,-2,-1.5F,0,4,4,11,0,9.5F,20.5F,-0.3141593F,0,0);
        part(r,"tail3",118,66,-1.5F,-1.5F,0,3,3,11,0,13.5F,30.5F,-0.2094395F,0,0);
        part(r,"tail4",149,66,-1,-1,0,2,2,11,0,15.5F,40.5F,-0.1396263F,0,0);
        part(r,"tail5",178,66,-0.5F,-0.5F,0,1,1,11,0,17.5F,50.5F,-0.0523599F,0,0);
        part(r,"tail1",50,66,-2,-2.5F,0,4,4,11,0,6.5F,10.5F,-0.4014257F,0,0);
        fan(r,"fanl1",0.2617994F); fan(r,"fanr1",-0.2617994F);
        fan(r,"fanl2",0.5235988F); fan(r,"fanr2",-0.5235988F);
        fan(r,"fanl3",0.7853982F); fan(r,"fanr3",-0.7853982F);
        fan(r,"fanl4",1.047198F); fan(r,"fanr4",-1.047198F);
        fan(r,"fanl5",1.308997F); fan(r,"fanr5",-1.308997F);
        fan(r,"fanl6",1.570796F); fan(r,"fanr6",-1.570796F);
        part(r,"spike4",178,66,-0.5F,-0.5F,0,1,1,11,0,16,41,-0.0523599F,0.5235988F,0);
        part(r,"spike5",178,66,-0.5F,-0.5F,0,1,1,11,0,16,41,-0.0523599F,-0.5759587F,0);
        part(r,"spike3",178,66,-0.5F,-0.5F,0,1,1,11,0,13.5F,30.5F,0.3141593F,0,0);
        fan(r,"fanl7",1.832596F); fan(r,"fanr7",-1.832596F);
        part(r,"head",200,0,-3,-4,-7,6,7,8,0,-3,-11,0,0,0);
        part(r,"head1",200,18,-2.5F,-2,-15,5,2,8,0,-3,-11,0,0,0);
        part(r,"jaw1",200,43,-2,-1,-7,4,2,8,0,-2,-19,0,0,0);
        part(r,"head2",200,31,-2,-2,-22,4,2,7,0,-3,-11,0,0,0);
        part(r,"jaw2",200,56,-1.5F,-1,-13,3,2,6,0,-2,-19,0,0,0);
        part(r,"fang1",42,0,1,0,-20,1,5,1,0,-3,-11,0,0,0);
        part(r,"fang2",50,0,-2,0,-20,1,5,1,0,-3,-11,0,0,0);
        part(r,"fang3",60,0,1,0,-14,1,3,1,0,-3,-11,0,0,0);
        part(r,"fang4",69,0,-2,0,-14,1,3,1,0,-3,-11,0,0,0);
        part(r,"spike2",178,66,-0.5F,-0.5F,0,1,1,11,0,9.5F,20.5F,0.3141593F,0,0);
        part(r,"spike1",178,66,-0.5F,-1.5F,0,1,1,11,0,6.5F,10.5F,0.3141593F,0,0);
        part(r,"arml1",50,98,0,0,-2,11,3,4,2,-1,-6,0,-0.5235988F,0.1745329F);
        part(r,"armr1",49,88,0,0,-2,11,3,4,-3,-1,-6,0,-2.617994F,-0.1745329F);
        part(r,"arml2",41,107,0,-1,-1,15,3,3,11,2,-1,0,0.8552113F,0);
        part(r,"armr2",42,115,0,-1,-2,15,3,3,-11,2,-1,0,2.268928F,0);
        part(r,"clawr1",100,85,-0.5F,-1,-6,1,1,6,-21,2,-12,-0.1745329F,0.4363323F,0);
        part(r,"clawr2",100,94,0,0,-10,1,1,10,-21,2,-12,0,0.8726646F,0);
        part(r,"clawr3",100,107,0,1,-6,1,1,6,-21,2,-12,0.1745329F,0.4363323F,0);
        part(r,"clawl2",130,94,0,0,0,1,1,10,21,2,-12,0,2.268928F,0);
        part(r,"clawl3",130,109,0,1,0,1,1,6,21,2,-12,-0.1745329F,2.70526F,0);
        part(r,"clawl1",130,83,0,-1,0,1,1,6,21,2,-12,0.1745329F,2.70526F,0);
        return LayerDefinition.create(mesh,256,128);
    }

    private static void fan(PartDefinition r, String name, float rz) {
        part(r,name,130,10,-3,-24,0,6,24,1,0,-7,-10,0,0,rz);
    }

    private static void part(PartDefinition r, String name, int u, int v,
                             float x,float y,float z,float dx,float dy,float dz,
                             float px,float py,float pz,float rx,float ry,float rz) {
        r.addOrReplaceChild(name, CubeListBuilder.create().texOffs(u,v).mirror().addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(AlienEntity e,float limbSwing,float limbSwingAmount,float age,float yaw,float pitch) {
        float leg=Mth.cos(age*4.0F*WING_SPEED)*Mth.PI*0.5F*limbSwingAmount;
        doLeftLeg(leg); doRightLeg(-leg); animateFans(e,age); animateHead(yaw);
        RenderState s=renderStates.computeIfAbsent(e,k->new RenderState());
        float cycle=Mth.cos(age*3.5F*WING_SPEED)*Mth.PI*0.5F;
        float next=Mth.cos((age+0.2F)*3.5F*WING_SPEED)*Mth.PI*0.5F;
        if(next>0&&cycle<0){
            if(e.getAttacking()==0){s.claw=e.getRandom().nextInt(15);s.tail=e.getRandom().nextInt(15);s.jaw=e.getRandom().nextInt(15);}
            else{s.claw=e.getRandom().nextInt(4);s.tail=e.getRandom().nextInt(2);s.jaw=1;}
        }
        doTail(s.tail==1?cycle:Mth.cos(age*WING_SPEED)*Mth.PI*0.05F);
        doJaw(s.jaw==1?Mth.cos(age*3.5F*WING_SPEED)*Mth.PI*0.35F:Mth.cos(age*WING_SPEED)*Mth.PI*0.02F);
        float claw=Mth.cos(age*WING_SPEED*3.5F)*Mth.PI*0.2F;
        doLeftClaw(s.claw==1||s.claw==3?claw:Mth.cos(age*WING_SPEED)*Mth.PI*0.03F);
        doRightClaw(s.claw==2||s.claw==3?-claw:-Mth.cos(age*WING_SPEED)*Mth.PI*0.03F);
    }

    private void animateFans(AlienEntity e,float age){
        ModelPart[] l={fanl1,fanl2,fanl3,fanl4,fanl5,fanl6,fanl7};
        ModelPart[] r={fanr1,fanr2,fanr3,fanr4,fanr5,fanr6,fanr7};
        if(e.getAttacking()==0){fan.xRot=-1.85F;fan.zRot=0;for(int i=0;i<7;i++){l[i].xRot=-1.85F;r[i].xRot=-1.85F;l[i].zRot=0;r[i].zRot=0;}return;}
        float phase=0.5235988F,speed=1.22F,amp=0.1F;
        fan.xRot=Mth.cos(age*speed*WING_SPEED)*Mth.PI*amp;fan.zRot=0;
        float[] z={0.261F,0.523F,0.785F,1.047F,1.309F,1.571F,1.832F};
        for(int i=0;i<7;i++){float m=Mth.cos(age*speed*WING_SPEED-(i+1)*phase)*Mth.PI*amp;l[i].xRot=m;r[i].xRot=m;l[i].zRot=z[i];r[i].zRot=-z[i];}
    }

    private void animateHead(float yaw){
        neck.yRot=(float)Math.toRadians(yaw)*0.35F;head.yRot=(float)Math.toRadians(yaw)*0.75F;
        head.z=neck.z-(float)Math.cos(neck.yRot)*3;head.x=neck.x+(float)Math.sin(neck.yRot)*3;
        copyHead(head1);copyHead(head2);copyHead(fang1);copyHead(fang2);copyHead(fang3);copyHead(fang4);
        jaw1.yRot=head.yRot;jaw1.z=head.z-(float)Math.cos(head.yRot)*8;jaw1.x=head.x-(float)Math.sin(head.yRot)*8;
        jaw2.yRot=jaw1.yRot;jaw2.z=jaw1.z;jaw2.x=jaw1.x;
    }
    private void copyHead(ModelPart p){p.yRot=head.yRot;p.z=head.z;p.x=head.x;}
    private void doLeftLeg(float f){lFoot.xRot=f;lShin.xRot=f-0.4F;lShin1.xRot=f-0.8F;lThigh.xRot=f-0.8F;}
    private void doRightLeg(float f){rFoot.xRot=f;rShin.xRot=f-0.4F;rShin1.xRot=f-0.8F;rThigh.xRot=f-0.8F;}
    private void doJaw(float f){jaw1.xRot=Math.abs(f);jaw2.xRot=jaw1.xRot;}
    private void doTail(float f){
        tail1.yRot=f*0.25F;spike1.yRot=tail1.yRot;
        tail2.yRot=f*0.5F;tail2.z=tail1.z+(float)Math.cos(tail1.yRot)*10;tail2.x=tail1.x+(float)Math.sin(tail1.yRot)*10;copyTail(spike2,tail2,0);
        tail3.yRot=f*0.8F;tail3.z=tail2.z+(float)Math.cos(tail2.yRot)*10;tail3.x=tail2.x+(float)Math.sin(tail2.yRot)*10;copyTail(spike3,tail3,0);
        tail4.yRot=f*1.25F;tail4.z=tail3.z+(float)Math.cos(tail3.yRot)*10;tail4.x=tail3.x+(float)Math.sin(tail3.yRot)*10;
        copyTail(spike4,tail4,0.52F);copyTail(spike5,tail4,-0.52F);
        tail5.yRot=f*1.5F;tail5.z=tail4.z+(float)Math.cos(tail4.yRot)*10;tail5.x=tail4.x+(float)Math.sin(tail4.yRot)*10;
    }
    private static void copyTail(ModelPart dst,ModelPart src,float yOff){dst.yRot=src.yRot+yOff;dst.z=src.z;dst.x=src.x;}
    private void doLeftClaw(float f){
        arml1.yRot=-0.52F+Math.abs(f*2);arml2.z=arml1.z-(float)Math.sin(arml1.yRot)*9;arml2.x=arml1.x+(float)Math.cos(arml1.yRot)*9;arml2.yRot=0.855F+Math.abs(f);
        clawl1.z=arml2.z-(float)Math.sin(arml2.yRot)*14;clawl1.x=arml2.x+(float)Math.cos(arml2.yRot)*14;clawl1.yRot=2.7F+Math.abs(f*4);
        clawl2.z=clawl1.z;clawl2.x=clawl1.x;clawl2.yRot=2.27F+Math.abs(f*4);clawl3.z=clawl1.z;clawl3.x=clawl1.x;clawl3.yRot=2.7F+Math.abs(f*4);
    }
    private void doRightClaw(float f){
        armr1.yRot=-2.61F-Math.abs(f*2);armr2.z=armr1.z-(float)Math.sin(armr1.yRot)*9;armr2.x=armr1.x+(float)Math.cos(armr1.yRot)*9;armr2.yRot=2.27F-Math.abs(f);
        clawr1.z=armr2.z-(float)Math.sin(armr2.yRot)*14;clawr1.x=armr2.x+(float)Math.cos(armr2.yRot)*14;clawr1.yRot=0.436F-Math.abs(f*4);
        clawr2.z=clawr1.z;clawr2.x=clawr1.x;clawr2.yRot=0.87F-Math.abs(f*4);clawr3.z=clawr1.z;clawr3.x=clawr1.x;clawr3.yRot=0.436F-Math.abs(f*4);
    }
    @Override public void renderToBuffer(PoseStack ps,VertexConsumer b,int light,int overlay,float r,float g,float bl,float a){root.render(ps,b,light,overlay,r,g,bl,a);}
    private static final class RenderState{int claw,tail,jaw;}
}
