package com.santipdr.copyl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.client.model.DoomWormModel;
import com.santipdr.copyl.common.entity.DoomWormEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public final class DoomWormRenderer extends MobRenderer<DoomWormEntity,DoomWormModel>{
    private static final ResourceLocation TEXTURE=new ResourceLocation(CopyL.MOD_ID,"textures/entity/wormdoomtexture.png");
    public DoomWormRenderer(EntityRendererProvider.Context c){super(c,new DoomWormModel(c.bakeLayer(DoomWormModel.LAYER_LOCATION)),0.0F);}
    @Override public ResourceLocation getTextureLocation(DoomWormEntity e){return TEXTURE;}
    @Override public void render(DoomWormEntity e,float yaw,float partial,PoseStack pose,MultiBufferSource buffers,int light){super.render(e,yaw,partial,pose,buffers,light);VertexConsumer v=buffers.getBuffer(model.renderType(getTextureLocation(e)));model.renderSegments(e,pose,v,light,OverlayTexture.NO_OVERLAY,1,1,1,1);}
}