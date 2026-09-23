package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.GammaMetroidModel;
import com.santipdr.copyl.common.entity.GammaMetroidEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderGammaMetroid. */
public final class GammaMetroidRenderer extends MobRenderer<GammaMetroidEntity, GammaMetroidModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/gammametroid.png");

    public GammaMetroidRenderer(EntityRendererProvider.Context context) {
        super(context, new GammaMetroidModel(context.bakeLayer(GammaMetroidModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    protected void scale(GammaMetroidEntity entity, PoseStack poseStack, float partialTickTime) {
        float scale = entity.isBaby() ? 0.5F : 1.0F;
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(GammaMetroidEntity entity) {
        return TEXTURE;
    }
}
