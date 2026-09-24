package com.santipdr.copyl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.StinkBugEntity;
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

/** Geometría y animación portadas del ModelStinkBug original (50 piezas, textura 64x32). */
public final class StinkBugModel extends EntityModel<StinkBugEntity> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "stinkbug"), "main");

    // RenderStinkBug del JAR construye ModelStinkBug(0.1F).
    private static final float WING_SPEED = 0.1F;

    private final ModelPart root;
    private final ModelPart f1, f2, f3, f4, f6;
    private final ModelPart b9, b10, jaw, h1, h2, tail;
    private final ModelPart[] tailKnobs;

    public StinkBugModel(ModelPart root) {
        this.root = root;
        this.f1 = root.getChild("f1");
        this.f2 = root.getChild("f2");
        this.f3 = root.getChild("f3");
        this.f4 = root.getChild("f4");
        this.f6 = root.getChild("f6");
        this.b9 = root.getChild("b9");
        this.b10 = root.getChild("b10");
        this.jaw = root.getChild("jaw");
        this.h1 = root.getChild("h1");
        this.h2 = root.getChild("h2");
        this.tail = root.getChild("tail");
        this.tailKnobs = new ModelPart[] {
                root.getChild("t5"), root.getChild("t4"), root.getChild("t3"), root.getChild("t2"), root.getChild("t1"),
                root.getChild("t10"), root.getChild("t9"), root.getChild("t8"), root.getChild("t7"), root.getChild("t6"),
                root.getChild("t15"), root.getChild("t14"), root.getChild("t13"), root.getChild("t12"), root.getChild("t11"),
                root.getChild("t20"), root.getChild("t19"), root.getChild("t18"), root.getChild("t17"), root.getChild("t16"),
                root.getChild("t22"), root.getChild("t21")
        };
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();

        part(r,"f6",20,16,-2,0,-1,2,2,2,-3.5F,22,3,0,0,0);
        part(r,"b10",0,2,-0.5F,-1.5F,-0.5F,1,2,1,0,17,1,-0.5235988F,0,0);
        part(r,"l6",20,13,-2,0,-1,2,1,2,-3,21,3,0,0,0);
        part(r,"l4",20,13,-2,0,-1,2,1,2,-3,21,-3,0,0,0);
        part(r,"f4",20,16,-2,0,-1,2,2,2,-3.5F,22,-3,0,0,0);
        part(r,"l5",20,13,-2,0,-1,2,1,2,-3,21,0,0,0,0);
        part(r,"f5",20,16,-2,0,-1,2,2,2,-3.5F,22,0,0,0,0);
        part(r,"l3",20,13,0,0,-1,2,1,2,3,21,3,0,0,0);
        part(r,"l2",20,13,0,0,-1,2,1,2,3,21,0,0,0,0);
        part(r,"l1",20,13,0,0,-1,2,1,2,3,21,-3,0,0,0);
        part(r,"f3",20,16,0,0,-1,2,2,2,3.5F,22,3,0,0,0);
        part(r,"f2",20,16,0,0,-1,2,2,2,3.5F,22,0,0,0,0);
        part(r,"f1",20,16,0,0,-1,2,2,2,3.5F,22,-3,0,0,0);
        part(r,"jaw",28,8,-3.5F,0,-8,5,1,4,1,21,0,0.122173F,0,0);
        part(r,"b9",0,2,-0.5F,-1.5F,-0.5F,1,2,1,0,17,-1,0.5235988F,0,0);
        part(r,"head",28,0,-3.5F,-3.5F,-8,5,4,4,1,21,0,0,0,0);
        part(r,"b4",0,0,1,-0.5F,2.5F,1,1,1,0,17,0,0,0,0);
        part(r,"h1",0,2,-0.5F,-2,-0.5F,1,2,1,-1.5F,18,-7,0.5235988F,0.3490659F,0);
        part(r,"h2",0,2,-0.5F,-2,-0.5F,1,2,1,1.5F,18,-7,0.5235988F,-0.3490659F,0);
        part(r,"body",0,0,-4,-4,-4,6,5,8,1,21,0,0,0,0);
        part(r,"t21",0,0,0.5F,3.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"tail",0,13,-2,0,0,4,4,6,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t22",0,0,-1.5F,3.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t20",0,0,-1.5F,3.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t19",0,0,0.5F,3.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t6",0,0,1.5F,2.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t11",0,0,0.5F,-0.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t9",0,0,0.5F,-0.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t4",0,0,1.5F,2.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t2",0,0,1.5F,2.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t7",0,0,0.5F,-0.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t12",0,0,-1.5F,-0.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t10",0,0,-1.5F,-0.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t8",0,0,-1.5F,-0.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t5",0,0,1.5F,0.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t3",0,0,1.5F,0.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t1",0,0,1.5F,0.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t18",0,0,-2.5F,2.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t16",0,0,-2.5F,2.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t14",0,0,-2.5F,2.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t13",0,0,-2.5F,0.5F,0,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t15",0,0,-2.5F,0.5F,2,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"t17",0,0,-2.5F,0.5F,4,1,1,1,0,17.5F,4,-0.3316126F,0,0);
        part(r,"b1",0,0,1,-0.5F,-3.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b2",0,0,1.5F,-0.5F,-1.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b3",0,0,1.5F,-0.5F,0.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b8",0,0,-2,-0.5F,2.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b7",0,0,-2.5F,-0.5F,0.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b6",0,0,-2.5F,-0.5F,-1.5F,1,1,1,0,17,0,0,0,0);
        part(r,"b5",0,0,-1.966667F,-0.5F,-3.5F,1,1,1,0,17,0,0,0,0);

        return LayerDefinition.create(mesh, 64, 32);
    }

    private static void part(PartDefinition root, String name, int u, int v,
                             float x, float y, float z, float dx, float dy, float dz,
                             float px, float py, float pz, float rx, float ry, float rz) {
        root.addOrReplaceChild(name,
                CubeListBuilder.create().texOffs(u, v).addBox(x, y, z, dx, dy, dz),
                PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(StinkBugEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = Mth.sin(ageInTicks * 3.1F * WING_SPEED) * Mth.PI * 0.3F * limbSwingAmount;
        // Se conserva incluso la rareza del original: f3 se asigna dos veces y f5 no se anima.
        f3.xRot = angle;
        f1.xRot = angle;
        f6.xRot = -angle;
        f4.xRot = -angle;
        f2.xRot = -angle;

        angle = Mth.sin(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.2F;
        b9.zRot = angle;
        b10.zRot = -angle;

        angle = Mth.sin(ageInTicks * 0.2F * WING_SPEED) * Mth.PI * 0.04F;
        jaw.xRot = 0.18F + angle;
        h1.xRot = 0.52F + Mth.sin(ageInTicks * 0.4F * WING_SPEED) * Mth.PI * 0.15F;
        h1.yRot = -0.3F + Mth.sin(ageInTicks * 0.43F * WING_SPEED) * Mth.PI * 0.15F;
        h2.xRot = 0.52F + Mth.sin(ageInTicks * 0.46F * WING_SPEED) * Mth.PI * 0.15F;
        h2.yRot = 0.3F + Mth.sin(ageInTicks * 0.49F * WING_SPEED) * Mth.PI * 0.15F;

        tail.xRot = -0.2F + Mth.sin(ageInTicks * 0.1F * WING_SPEED) * Mth.PI * 0.1F;
        for (ModelPart knob : tailKnobs) {
            knob.xRot = tail.xRot;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay,
                               float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
