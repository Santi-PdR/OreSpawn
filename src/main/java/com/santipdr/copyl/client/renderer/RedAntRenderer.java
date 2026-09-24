package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.AntModel;
import com.santipdr.copyl.common.entity.RedAntEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente a RenderRedAnt. */
public final class RedAntRenderer extends MobRenderer<RedAntEntity, AntModel<RedAntEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/red_ant.png");

    public RedAntRenderer(EntityRendererProvider.Context context) {
        super(context, new AntModel<>(context.bakeLayer(AntModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    protected void scale(RedAntEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.translate(0.0D, 0.9D, 0.0D);
        poseStack.scale(0.4F, 0.4F, 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(RedAntEntity entity) {
        return TEXTURE;
    }
}
