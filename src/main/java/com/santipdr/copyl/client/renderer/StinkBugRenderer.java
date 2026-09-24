package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.StinkBugModel;
import com.santipdr.copyl.common.entity.StinkBugEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Render moderno equivalente al RenderStinkBug 1.12.2: sombra 0 y textura original. */
public final class StinkBugRenderer extends MobRenderer<StinkBugEntity, StinkBugModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/stinkbug.png");

    public StinkBugRenderer(EntityRendererProvider.Context context) {
        super(context, new StinkBugModel(context.bakeLayer(StinkBugModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(StinkBugEntity entity) {
        return TEXTURE;
    }
}
