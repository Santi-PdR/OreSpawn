package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.BirdModel;
import com.santipdr.copyl.common.entity.BirdEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Port de danger.orespawn.entity.render.RenderBird. */
public final class BirdRenderer extends MobRenderer<BirdEntity, BirdModel> {
    private static final ResourceLocation[] TEXTURES = {
            tex("bird6.png"), tex("bird1.png"), tex("bird2.png"),
            tex("bird3.png"), tex("bird4.png"), tex("bird5.png")
    };

    public BirdRenderer(EntityRendererProvider.Context context) {
        // RenderBird usaba ModelBird(0.6F) y shadowSize 0.
        super(context, new BirdModel(context.bakeLayer(BirdModel.LAYER_LOCATION)), 0.0F);
    }

    private static ResourceLocation tex(String file) {
        return new ResourceLocation(CopyL.MOD_ID, "textures/entity/" + file);
    }

    @Override
    public ResourceLocation getTextureLocation(BirdEntity entity) {
        int type = Mth.clamp(entity.getBirdType(), 0, 5);
        return TEXTURES[type];
    }

    @Override
    protected void setupRotations(BirdEntity entity, PoseStack poseStack, float ageInTicks,
                                  float rotationYaw, float partialTicks) {
        poseStack.translate(0.0F, Mth.cos(ageInTicks * 0.3F) * 0.1F, 0.0F);
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    }
}
