package com.santipdr.copyl.client.renderer;

import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.entity.RedCowEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Equivalente moderno de RenderEnchantedCow para RedCow. */
public final class RedCowRenderer extends MobRenderer<RedCowEntity, CowModel<RedCowEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CopyL.MOD_ID, "textures/entity/red_cow.png");

    public RedCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(RedCowEntity entity) {
        return TEXTURE;
    }
}
