package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.AlosaurusModel;
import com.santipdr.copyl.common.entity.AlosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderAlosaurus. */
public final class AlosaurusRenderer extends MobRenderer<AlosaurusEntity, AlosaurusModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/alosaurus.png");

    public AlosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new AlosaurusModel(context.bakeLayer(AlosaurusModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(AlosaurusEntity entity) {
        return TEXTURE;
    }
}
