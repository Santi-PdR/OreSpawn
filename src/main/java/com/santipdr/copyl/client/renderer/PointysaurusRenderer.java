package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.PointysaurusModel;
import com.santipdr.copyl.common.entity.PointysaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderPointysaurus. */
public final class PointysaurusRenderer extends MobRenderer<PointysaurusEntity, PointysaurusModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/pointysaurus_original.png");

    public PointysaurusRenderer(EntityRendererProvider.Context context) {
        // El original usaba ModelPointysaurus(1.5F) y shadowSize 0.7F.
        super(context, new PointysaurusModel(context.bakeLayer(PointysaurusModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(PointysaurusEntity entity) {
        return TEXTURE;
    }
}
