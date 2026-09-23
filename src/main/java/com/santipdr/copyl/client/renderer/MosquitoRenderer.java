package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.MosquitoModel;
import com.santipdr.copyl.common.entity.MosquitoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderMosquito. */
public final class MosquitoRenderer extends MobRenderer<MosquitoEntity, MosquitoModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/mosquito.png");

    public MosquitoRenderer(EntityRendererProvider.Context context) {
        super(context, new MosquitoModel(context.bakeLayer(MosquitoModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MosquitoEntity entity) {
        return TEXTURE;
    }
}
