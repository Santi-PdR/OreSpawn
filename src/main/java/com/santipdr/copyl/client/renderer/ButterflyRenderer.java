package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.ButterflyModel;
import com.santipdr.copyl.common.entity.ButterflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Port de danger.orespawn.entity.render.RenderButterfly. */
public final class ButterflyRenderer extends MobRenderer<ButterflyEntity, ButterflyModel> {
    private static final ResourceLocation[] TEXTURES = {
            tex("butterfly.png"), tex("butterfly2.png"),
            tex("butterfly3.png"), tex("butterfly4.png")
    };

    public ButterflyRenderer(EntityRendererProvider.Context context) {
        super(context, new ButterflyModel(context.bakeLayer(ButterflyModel.LAYER_LOCATION)), 0.0F);
    }

    private static ResourceLocation tex(String file) {
        return new ResourceLocation(CopyL.MOD_ID, "textures/entity/" + file);
    }

    @Override
    public ResourceLocation getTextureLocation(ButterflyEntity entity) {
        int type = Mth.clamp(entity.getButterflyType(), 1, 4);
        return TEXTURES[type - 1];
    }

    @Override
    protected void setupRotations(ButterflyEntity entity, PoseStack poseStack, float ageInTicks,
                                  float rotationYaw, float partialTicks) {
        poseStack.translate(0.0F, Mth.cos(ageInTicks * 0.3F) * 0.1F, 0.0F);
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    }
}
