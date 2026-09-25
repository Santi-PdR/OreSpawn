package com.santipdr.copyl.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Server-authoritative segmented movement ported from the reference WormDoom class. */
public final class DoomWormEntity extends Monster {
    private static final int SEGMENTS=100;
    private final double[] sx=new double[SEGMENTS], sy=new double[SEGMENTS], sz=new double[SEGMENTS];
    private final float[] syaw=new float[SEGMENTS], spitch=new float[SEGMENTS];
    private float heading=1.0F, turnVelocity, targetHeading, cycle;
    private int turnTicks;
    public DoomWormEntity(EntityType<? extends DoomWormEntity> type, Level level) {
        super(type,level); noPhysics=true; xpReward=0;
        for(int i=0;i<SEGMENTS;i++){double d=i*1.125D;sx[i]=getX()-Math.sin(Math.toRadians(heading))*d;sz[i]=getZ()-Math.cos(Math.toRadians(heading))*d;sy[i]=getY()+Math.sin(Math.toRadians(i*10.0D+180.0D))*4.0D;syaw[i]=heading;}
    }
    public static AttributeSupplier.Builder createAttributes(){return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH,3000.0D).add(Attributes.MOVEMENT_SPEED,0.1D);}
    @Override protected void registerGoals(){}
    @Override public MobType getMobType(){return MobType.UNDEFINED;}
    @Override public void tick(){
        super.tick();
        if(level().isClientSide){for(int i=SEGMENTS-1;i>0;i--){sx[i]=sx[i-1];sy[i]=sy[i-1];sz[i]=sz[i-1];syaw[i]=syaw[i-1];spitch[i]=spitch[i-1];}sx[0]=getX();sy[0]=getY();sz[0]=getZ();syaw[0]=getYRot();spitch[0]=getXRot();setDeltaMovement(Vec3.ZERO);return;}
        double speed=isBaby()?0.2D:0.3D, dx=Math.sin(Math.toRadians(heading))*speed, dz=Math.cos(Math.toRadians(heading))*speed;
        int offset=0;for(int y=isBaby()?3:6;y>= (isBaby()?-3:-6);y--){if(!level().getBlockState(blockPosition().offset(0,y,0)).isAir()){offset=y;break;}}
        double wave= isBaby()?2.0D:4.0D, dy=getDeltaMovement().y*0.98D+0.008D*-(Math.sin(Math.toRadians(cycle*10.0D))*wave-offset);
        cycle=(cycle+0.5F)%360.0F;if(++turnTicks>=100){turnTicks=0;targetHeading=random.nextFloat()*360.0F;}
        float delta=(float)Math.toRadians(targetHeading-heading);while(delta>Math.PI)delta-=(float)(Math.PI*2);while(delta<-Math.PI)delta+=(float)(Math.PI*2);
        turnVelocity=0.95F*turnVelocity+delta*(float)(180.0D/Math.PI/20.0D);heading+=turnVelocity;
        setYRot(-heading);setXRot((float)Math.toDegrees(Math.atan2(-dy,Math.sqrt(dx*dx+dz*dz))));setDeltaMovement(dx,dy,-dz);move(MoverType.SELF,getDeltaMovement());
    }
    public int segmentCount(){return SEGMENTS;} public double segmentX(int i){return sx[i];} public double segmentY(int i){return sy[i];} public double segmentZ(int i){return sz[i];}
    public float segmentYaw(int i){return syaw[i];} public float segmentPitch(int i){return spitch[i];}
}