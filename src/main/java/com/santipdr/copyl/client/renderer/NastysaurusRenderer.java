package com.santipdr.copyl.client.renderer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.NastysaurusModel;
import com.santipdr.copyl.common.entity.NastysaurusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
public final class NastysaurusRenderer extends MobRenderer<NastysaurusEntity,NastysaurusModel>{
 private static final ResourceLocation TEXTURE=new ResourceLocation(CopyL.MOD_ID,"textures/entity/nastysaurus.png");
 public NastysaurusRenderer(EntityRendererProvider.Context context){super(context,new NastysaurusModel(context.bakeLayer(NastysaurusModel.LAYER_LOCATION)),1.2F);}
 @Override public ResourceLocation getTextureLocation(NastysaurusEntity entity){return TEXTURE;}
}