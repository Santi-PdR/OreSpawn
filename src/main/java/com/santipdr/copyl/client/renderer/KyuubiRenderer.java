package com.santipdr.copyl.client.renderer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.KyuubiModel;
import com.santipdr.copyl.common.entity.KyuubiEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
public final class KyuubiRenderer extends MobRenderer<KyuubiEntity,KyuubiModel>{
 private static final ResourceLocation TEXTURE=new ResourceLocation(CopyL.MOD_ID,"textures/entity/kyuubi.png");
 public KyuubiRenderer(EntityRendererProvider.Context context){super(context,new KyuubiModel(context.bakeLayer(KyuubiModel.LAYER_LOCATION)),0.3F);}
 @Override public ResourceLocation getTextureLocation(KyuubiEntity entity){return TEXTURE;}
}