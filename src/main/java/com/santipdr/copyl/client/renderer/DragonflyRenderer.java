package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.DragonflyModel;
import com.santipdr.copyl.common.entity.DragonflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderDragonfly. */
public final class DragonflyRenderer extends MobRenderer<DragonflyEntity, DragonflyModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/dragonfly.png");

    public DragonflyRenderer(EntityRendererProvider.Context context) {
        // El original usaba ModelDragonfly(1.5F) y shadowSize 0.
        super(context, new DragonflyModel(context.bakeLayer(DragonflyModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(DragonflyEntity entity) {
        return TEXTURE;
    }
}
