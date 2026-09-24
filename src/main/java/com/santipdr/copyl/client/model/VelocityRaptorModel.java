package com.santipdr.copyl.client.model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.VelocityRaptorEntity;
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
/** Exact geometry/animation port of ModelVelocityRaptor from the supplied 1.12.2 JAR. */
public final class VelocityRaptorModel extends EntityModel<VelocityRaptorEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CopyL.MOD_ID, "velocityraptor"), "main");
    private static final float WING_SPEED = 1.0F;
    private final ModelPart root;
    private final ModelPart hf1;
    private final ModelPart hf2;
    private final ModelPart hf3;
    private final ModelPart hf4;
    private final ModelPart lff1;
    private final ModelPart lff2;
    private final ModelPart lff3;
    private final ModelPart rff1;
    private final ModelPart rff2;
    private final ModelPart rff3;
    private final ModelPart tf1;
    private final ModelPart tf2;
    private final ModelPart tf3;
    private final ModelPart tf4;
    private final ModelPart lf1;
    private final ModelPart lf2;
    private final ModelPart rf1;
    private final ModelPart rf2;
    private final ModelPart bl1;
    private final ModelPart bl2;
    private final ModelPart bl3;
    private final ModelPart bl4;
    private final ModelPart br1;
    private final ModelPart br2;
    private final ModelPart br3;
    private final ModelPart br4;

    public VelocityRaptorModel(ModelPart root) {
        this.root = root;
        this.hf1 = root.getChild("hf1");
        this.hf2 = root.getChild("hf2");
        this.hf3 = root.getChild("hf3");
        this.hf4 = root.getChild("hf4");
        this.lff1 = root.getChild("lff1");
        this.lff2 = root.getChild("lff2");
        this.lff3 = root.getChild("lff3");
        this.rff1 = root.getChild("rff1");
        this.rff2 = root.getChild("rff2");
        this.rff3 = root.getChild("rff3");
        this.tf1 = root.getChild("tf1");
        this.tf2 = root.getChild("tf2");
        this.tf3 = root.getChild("tf3");
        this.tf4 = root.getChild("tf4");
        this.lf1 = root.getChild("lf1");
        this.lf2 = root.getChild("lf2");
        this.rf1 = root.getChild("rf1");
        this.rf2 = root.getChild("rf2");
        this.bl1 = root.getChild("bl1");
        this.bl2 = root.getChild("bl2");
        this.bl3 = root.getChild("bl3");
        this.bl4 = root.getChild("bl4");
        this.br1 = root.getChild("br1");
        this.br2 = root.getChild("br2");
        this.br3 = root.getChild("br3");
        this.br4 = root.getChild("br4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition r = mesh.getRoot();
        part(r, "hf3", 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 7.0F, -2.0F, 0.4537856F, 0.0F, 0.0F, true);
        part(r, "hf4", 0, 0, 0.0F, -0.2F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 8.0F, -1.5F, 0.2443461F, 0.0F, 0.0F, true);
        part(r, "hf2", 0, 0, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 7.0F, -3.5F, 0.6632251F, 0.0F, 0.0F, true);
        part(r, "hf1", 0, 1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, 7.0F, -4.5F, 0.9424778F, 0.0F, 0.0F, true);
        part(r, "lff2", 0, 6, 0.5F, 2.5F, 3.0F, 0.0F, 1.0F, 3.0F, 2.0F, 14.0F, 1.0F, -0.4537856F, 0.0F, 0.0F, true);
        part(r, "lff1", 0, 6, 0.5F, 2.0F, 2.0F, 0.0F, 1.0F, 3.0F, 2.0F, 14.0F, 1.0F, -0.2792527F, 0.0F, 0.0F, true);
        part(r, "lff3", 0, 6, 0.5F, 1.0F, 4.0F, 0.0F, 1.0F, 3.0F, 2.0F, 14.0F, 1.0F, -1.047198F, 0.0F, 0.0F, true);
        part(r, "rff2", 0, 6, -0.5F, 2.5F, 3.0F, 0.0F, 1.0F, 3.0F, -2.0F, 14.0F, 1.0F, -0.4537856F, 0.0F, 0.0F, true);
        part(r, "rff3", 0, 6, -0.5F, 1.0F, 4.0F, 0.0F, 1.0F, 3.0F, -2.0F, 14.0F, 1.0F, -1.047198F, 0.0F, 0.0F, true);
        part(r, "rff1", 0, 6, -0.5F, 2.0F, 2.0F, 0.0F, 1.0F, 3.0F, -2.0F, 14.0F, 1.0F, -0.2792527F, 0.0F, 0.0F, true);
        part(r, "tf4", 0, 3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 11.0F, 25.0F, -0.5410521F, 0.0F, 0.0F, true);
        part(r, "tf1", 0, 3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 11.0F, 19.0F, -0.5410521F, 0.0F, 0.0F, true);
        part(r, "Shape1", 0, 0, -2.0F, 0.0F, 0.0F, 4.0F, 7.0F, 11.0F, 0.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "neck", 0, 19, -1.0F, -7.0F, -2.0F, 2.0F, 8.0F, 3.0F, 0.0F, 12.0F, 2.0F, 1.082104F, 0.0F, 0.0F, true);
        part(r, "head1", 0, 49, -2.0F, 0.0F, -7.0F, 3.0F, 4.0F, 7.0F, 0.5F, 7.0F, -1.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "lf1", 0, 31, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, 2.0F, 14.0F, 1.0F, 0.2792527F, 0.0F, 0.0F, true);
        part(r, "lf2", 16, 19, 0.0F, 1.0F, 2.0F, 1.0F, 4.0F, 1.0F, 2.0F, 14.0F, 1.0F, -0.4363323F, 0.0F, 0.0F, true);
        part(r, "head2", 20, 0, -1.0F, 0.0F, -10.0F, 2.0F, 4.0F, 4.0F, 0.0F, 7.0F, -1.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "tail1", 0, 38, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 4.0F, 0.0F, 10.0F, 11.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "tail2", 26, 11, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 10.0F, -0.5F, 10.0F, 15.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "bl1", 22, 24, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, 4.0F, 2.0F, 13.0F, 6.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "br1", 36, 0, -1.0F, 0.0F, 0.0F, 2.0F, 6.0F, 4.0F, -2.0F, 13.0F, 6.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "bl2", 12, 26, -1.0F, 5.0F, -3.0F, 2.0F, 5.0F, 2.0F, 2.0F, 13.0F, 6.0F, 0.4886922F, 0.0F, 0.0F, true);
        part(r, "br2", 13, 36, -1.0F, 5.0F, -3.0F, 2.0F, 5.0F, 2.0F, -2.0F, 13.0F, 6.0F, 0.4886922F, 0.0F, 0.0F, true);
        part(r, "bl3", 28, 39, -1.0F, 9.0F, -1.0F, 2.0F, 2.0F, 4.0F, 2.0F, 13.0F, 6.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "br3", 18, 45, -1.0F, 9.0F, -1.0F, 2.0F, 2.0F, 4.0F, -2.0F, 13.0F, 6.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "rf1", 35, 31, -1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 2.0F, -2.0F, 14.0F, 1.0F, 0.2792527F, 0.0F, 0.0F, true);
        part(r, "rf2", 11, 19, -1.0F, 1.0F, 2.0F, 1.0F, 4.0F, 1.0F, -2.0F, 14.0F, 1.0F, -0.4363323F, 0.0F, 0.0F, true);
        part(r, "tf2", 0, 3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 11.0F, 21.0F, -0.5410521F, 0.0F, 0.0F, true);
        part(r, "tf3", 0, 3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 11.0F, 23.0F, -0.5410521F, 0.0F, 0.0F, true);
        part(r, "bl4", 31, 10, -1.0F, 6.0F, -5.0F, 1.0F, 3.0F, 1.0F, 2.0F, 13.0F, 6.0F, 0.6283185F, 0.0F, 0.0F, true);
        part(r, "br4", 31, 15, 0.0F, 6.0F, -5.0F, 1.0F, 3.0F, 1.0F, -2.0F, 13.0F, 6.0F, 0.6283185F, 0.0F, 0.0F, true);
        part(r, "Hat1", 50, 0, 0.0F, 0.0F, 0.0F, 4.0F, 1.0F, 5.0F, -2.0F, 6.0F, -6.0F, 0.0F, 0.0F, 0.0F, true);
        part(r, "Hat2", 50, 0, 0.0F, 0.0F, 0.0F, 3.0F, 2.0F, 3.0F, -1.5F, 4.0F, -4.0F, 0.0F, 0.0F, 0.0F, true);
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static void part(PartDefinition root, String name, int u, int v, float x, float y, float z, float dx, float dy, float dz, float px, float py, float pz, float rx, float ry, float rz, boolean mirror) {
        CubeListBuilder cube = CubeListBuilder.create().texOffs(u, v);
        if (mirror) cube = cube.mirror();
        cube = cube.addBox(x, y, z, dx, dy, dz);
        root.addOrReplaceChild(name, cube, PartPose.offsetAndRotation(px, py, pz, rx, ry, rz));
    }

    @Override
    public void setupAnim(VelocityRaptorEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float angle = limbSwingAmount > 0.1F ? Mth.cos(ageInTicks * 1.3F * WING_SPEED) * Mth.PI * 0.25F * limbSwingAmount : 0.0F;
        bl1.xRot = angle; bl2.xRot = angle + 0.488F; bl3.xRot = angle; bl4.xRot = angle + 0.628F;
        br1.xRot = -angle; br2.xRot = -angle + 0.488F; br3.xRot = -angle; br4.xRot = -angle + 0.628F;
        float healthRatio = entity.getMaxHealth() <= 0.0F ? 1.0F : ((int) entity.getHealth()) / entity.getMaxHealth();
        angle = Mth.cos(ageInTicks * 1.25F * WING_SPEED * healthRatio) * Mth.PI * 0.1F * healthRatio;
        hf1.yRot = angle; hf2.yRot = -angle; hf3.yRot = angle; hf4.yRot = -angle;
        angle = Mth.cos(ageInTicks * 0.3F) * Mth.PI * 0.05F;
        lf1.xRot = angle + 0.279F; lf2.xRot = angle - 0.436F; lff1.xRot = angle - 0.279F; lff2.xRot = angle - 0.453F; lff3.xRot = angle - 1.047F;
        rf1.xRot = -angle + 0.279F; rf2.xRot = -angle - 0.436F; rff1.xRot = -angle - 0.279F; rff2.xRot = -angle - 0.453F; rff3.xRot = -angle - 1.047F;
        angle = Mth.cos(ageInTicks * 1.3F * WING_SPEED) * Mth.PI * 0.1F;
        lff1.yRot = angle; lff2.yRot = -angle; lff3.yRot = angle; rff1.yRot = -angle; rff2.yRot = angle; rff3.yRot = -angle;
        angle = entity.isOrderedToSit() ? 0.0F : Mth.cos(ageInTicks * 1.4F * WING_SPEED * healthRatio) * Mth.PI * 0.25F * healthRatio;
        tf1.zRot = angle; tf2.zRot = -angle; tf3.zRot = angle; tf4.zRot = -angle;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, consumer, light, overlay, red, green, blue, alpha);
    }
}
