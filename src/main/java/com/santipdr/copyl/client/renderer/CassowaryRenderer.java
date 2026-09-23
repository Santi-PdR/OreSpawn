package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.CassowaryModel;
import com.santipdr.copyl.common.entity.CassowaryEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderCassowary. */
public final class CassowaryRenderer extends MobRenderer<CassowaryEntity, CassowaryModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/cassowary.png");

    public CassowaryRenderer(EntityRendererProvider.Context context) {
        // El original usa ModelCassowary(1.0F) y shadowSize 0.0F.
        super(context, new CassowaryModel(context.bakeLayer(CassowaryModel.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(CassowaryEntity entity) {
        return TEXTURE;
    }
}
