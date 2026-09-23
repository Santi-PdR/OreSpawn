package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.BeaverModel;
import com.santipdr.copyl.common.entity.BeaverEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderBeaver. */
public final class BeaverRenderer extends MobRenderer<BeaverEntity, BeaverModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/beaver.png");

    public BeaverRenderer(EntityRendererProvider.Context context) {
        super(context, new BeaverModel(context.bakeLayer(BeaverModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(BeaverEntity entity) {
        return TEXTURE;
    }
}
