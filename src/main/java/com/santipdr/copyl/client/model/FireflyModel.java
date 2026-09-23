package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.FireflyEntity;
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

/** Port geometrico de danger.orespawn.entity.model.ModelFirefly. */
public final class FireflyModel extends EntityModel<FireflyEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "firefly"), "main");
    private static final float WING_SPEED = 1.5F;

    private final ModelPart root;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;
    private final ModelPart tailLight;

    public FireflyModel(ModelPart root) {
        this.root = root;
        this.wingLeft = root.getChild("wing_left");
        this.wingRight = root.getChild("wing_right");
        this.tailLight = root.getChild("tail_light");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r,"body",38,12,-3,-3,-3,5,5,5,-1,9,-1,0,0,0);
        part(r,"wing_left",46,0,0,-6,0,0,6,2,1,6,-2,0,0.0174533F,0.6981317F);
        part(r,"wing_right",53,0,0,-6,0,0,6,2,-4,6,-2,0,0,-0.6981317F);
        part(r,"head",3,14,0,0,0,3,3,3,-3,7,-7,0.2230717F,0,0);
        part(r,"mouth",26,15,0,0,0,1,1,3,-2,9,-8,0.2117115F,0,0);
        part(r,"eye_left",18,12,0,0,0,1,2,2,-1,6.5F,-6,0.0174533F,0.2602503F,-0.2230717F);
        part(r,"eye_right",18,18,1,-0.6F,-0.6F,1,2,2,-4,6.5F,-6,0,-0.2602503F,0.2230717F);
        part(r,"front_leg_left",32,0,0,0,0,1,5,1,-1,10,-3,-0.2792527F,0,-0.2792527F);
        part(r,"front_leg_right",22,0,0,0,0,1,5,1,-3,10,-3,-0.2792527F,0,0.2792527F);
        part(r,"back_leg_left",11,0,0,0,0,1,5,1,-1,10,-1,0.2792527F,0,-0.2792527F);
        part(r,"back_leg_right",2,0,0,0,0,1,5,1,-3,10,-1,0.2792527F,0,0.2792527F);
        part(r,"tail_light",10,27,0,0,0,3,3,4,-3,6,1,0,0,0);

        return LayerDefinition.create(mesh,64,128);
    }

    private static void part(PartDefinition r, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        r.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u,v).mirror().addBox(x,y,z,dx,dy,dz),
                PartPose.offsetAndRotation(px,py,pz,rx,ry,rz));
    }

    @Override
    public void setupAnim(FireflyEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float wing = Mth.cos(ageInTicks * WING_SPEED) * Mth.PI * 0.35F;
        wingLeft.zRot = 1.11F + wing;
        wingRight.zRot = -1.11F - wing;
    }

    public void renderGlow(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                           float red, float green, float blue, float alpha) {
        tailLight.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
