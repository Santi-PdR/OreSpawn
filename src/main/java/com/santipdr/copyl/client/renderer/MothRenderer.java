package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.MothModel;
import com.santipdr.copyl.common.entity.MothEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Port de danger.orespawn.entity.render.RenderMoth. */
public final class MothRenderer extends MobRenderer<MothEntity, MothModel> {
    private static final ResourceLocation[] TEXTURES = {
            tex("lunamoth.png"), tex("eyemoth.png"), tex("firemoth.png"), tex("darkmoth.png")
    };

    public MothRenderer(EntityRendererProvider.Context context) {
        super(context, new MothModel(context.bakeLayer(MothModel.LAYER_LOCATION)), 0.0F);
    }

    private static ResourceLocation tex(String file) {
        return new ResourceLocation(CopyL.MOD_ID, "textures/entity/" + file);
    }

    @Override
    public ResourceLocation getTextureLocation(MothEntity entity) {
        return TEXTURES[Mth.clamp(entity.getMothType(), 0, 3)];
    }

    @Override
    protected void scale(MothEntity entity, PoseStack poseStack, float partialTickTime) {
        // ModelButterfly(0.6F, 1.5F): el original traslada -scale y luego escala todo el modelo.
        poseStack.translate(0.0D, -1.5D, 0.0D);
        poseStack.scale(1.5F, 1.5F, 1.5F);
    }
}
