package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.CryolophosaurusModel;
import com.santipdr.copyl.common.entity.CryolophosaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Port de danger.orespawn.entity.render.RenderCryolophosaurus. */
public final class CryolophosaurusRenderer extends MobRenderer<CryolophosaurusEntity, CryolophosaurusModel> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/cryolophosaurus_exact.png");

    public CryolophosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CryolophosaurusModel(context.bakeLayer(CryolophosaurusModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(CryolophosaurusEntity entity) {
        return TEXTURE;
    }
}
