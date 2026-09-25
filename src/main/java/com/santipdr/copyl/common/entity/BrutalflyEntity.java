package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

/** OreSpawn 1.12.2 Brutalfly flight, combat, spawn and death behavior. */
public final class BrutalflyEntity extends ButterflyEntity implements Enemy {
    private BlockPos flightTarget;
    private int lastX, lastY, lastZ, stuckTicks, wingTicks, healTicks = 100;

    public BrutalflyEntity(EntityType<? extends BrutalflyEntity> type, Level level) {
        super(type, level);
        xpReward = 100;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 110.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D).add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR, 6.0D);
    }

    @Override protected net.minecraft.sounds.SoundEvent getAmbientSound() { return null; }
    @Override protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) { return null; }
    @Override protected net.minecraft.sounds.SoundEvent getDeathSound() { return net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE; }
    @Override public boolean isPushable() { return true; }
    @Override public void push(Entity entity) { }
    @Override public boolean causeFallDamage(float distance, float multiplier, DamageSource source) { return false; }
    @Override protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) { }

    @Override public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && source.getEntity() instanceof LivingEntity attacker && attacker != this && !level().isClientSide)
            flightTarget = BlockPos.containing(attacker.getX(), attacker.getY() + 2.0D, attacker.getZ());
        return result;
    }

    @Override protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (level() instanceof ServerLevel server) {
            for (int i = 0; i < 20; i++) {
                server.sendParticles(ParticleTypes.EXPLOSION,
                        getX() + random.nextFloat() * 8 - random.nextFloat() * 8,
                        getY() + 2 + random.nextFloat() * 4 - random.nextFloat() * 4,
                        getZ() + random.nextFloat() * 8 - random.nextFloat() * 8, 1, 0, 0, 0, 0);
                ButterflyEntity moth = ModEntities.BUTTERFLY.get().create(level());
                if (moth != null) {
                    moth.moveTo(getX() + .5, getY() + 1, getZ() + .5, random.nextFloat() * 360, 0);
                    level().addFreshEntity(moth);
                }
            }
            spawnAtLocation(net.minecraft.world.item.Items.GOLD_NUGGET, 53);
        }
    }

    @Override protected void customServerAiStep() {
        super.customServerAiStep();
        if (level().isClientSide) return;
        int x=Mth.floor(getX()), y=Mth.floor(getY()), z=Mth.floor(getZ());
        if (lastX==x && lastY==y && lastZ==z) stuckTicks++;
        else { lastX=x; lastY=y; lastZ=z; stuckTicks=0; }
        if (flightTarget == null || stuckTicks > 30 ||
                (random.nextInt(200)==0 && flightTarget.distToCenterSqr(getX(),getY(),getZ())<81))
            chooseFlightTarget();

        if (random.nextInt(6)==0 && level().getDifficulty()!=Difficulty.PEACEFUL) {
            Player player = level().getEntitiesOfClass(Player.class,getBoundingBox().inflate(30,20,30))
                    .stream().filter(p -> p.isAlive()&&!p.getAbilities().instabuild&&hasLineOfSight(p))
                    .min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
            LivingEntity target = player;
            if (target==null && random.nextInt(3)==0)
                target=level().getEntitiesOfClass(LivingEntity.class,getBoundingBox().inflate(25,20,25),this::isOriginalHostile)
                        .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
            if (target!=null) {
                flightTarget=BlockPos.containing(target.getX(),target.getY()+(player!=null?4:5),target.getZ());
                if (distanceToSqr(target)<=25) doHurtTarget(target);
                else if (random.nextInt(level().getDifficulty()==Difficulty.HARD?2:3)==0) shoot(target);
            }
        }
        if (flightTarget==null) return;
        Vec3 m=getDeltaMovement();
        double dx=flightTarget.getX()+.5-getX(),dy=flightTarget.getY()+.1-getY(),dz=flightTarget.getZ()+.5-getZ();
        setDeltaMovement(m.x+(Math.signum(dx)*.5-m.x)*.30001,
                m.y+(Math.signum(dy)*.7-m.y)*.20001,m.z+(Math.signum(dz)*.5-m.z)*.30001);
        Vec3 v=getDeltaMovement();
        setYRot((float)(Mth.atan2(v.z,v.x)*180/Math.PI)-90);
    }

    private boolean isOriginalHostile(LivingEntity e) {
        return e!=this && e.isAlive() && !(e instanceof BrutalflyEntity) && !(e instanceof MothraEntity)
                && hasLineOfSight(e) && (e instanceof Enemy || e instanceof Player p&&!p.getAbilities().instabuild);
    }

    private void chooseFlightTarget() {
        BlockPos origin=blockPosition();
        int nearestGround=999;
        for(int ox=-5;ox<=5;ox+=5) for(int oz=-5;oz<=5;oz+=5)
            for(int down=1;down<=19;down++) if(!level().isEmptyBlock(origin.offset(ox,-down,oz))) {
                nearestGround=Math.min(nearestGround,down); break;
            }
        int groundOffset=nearestGround<10?nearestGround-9:0;
        for(int i=0;i<30;i++) {
            int ox=random.nextInt(20)+8,oz=random.nextInt(20)+8;
            if(random.nextBoolean()) ox=-ox; if(random.nextBoolean()) oz=-oz;
            BlockPos p=origin.offset(ox,random.nextInt(7)-1-groundOffset,oz);
            if(level().isEmptyBlock(p)&&hasLineOfSightTo(p)){flightTarget=p;stuckTicks=0;return;}
        }
        flightTarget=null; stuckTicks=0;
    }

    private boolean hasLineOfSightTo(BlockPos p) {
        Vec3 from=new Vec3(getX(),getY()+.75,getZ());
        return level().clip(new net.minecraft.world.level.ClipContext(from,Vec3.atCenterOf(p),
                net.minecraft.world.level.ClipContext.Block.COLLIDER,net.minecraft.world.level.ClipContext.Fluid.NONE,this))
                .getType()==net.minecraft.world.phys.HitResult.Type.MISS;
    }

    private void shoot(LivingEntity target) {
        double sx=getX()-2.25*Math.sin(Math.toRadians(getYRot()));
        double sz=getZ()+2.25*Math.cos(Math.toRadians(getYRot()));
        double dx=target.getX()-sx,dy=target.getY()+.55-getY(),dz=target.getZ()-sz;
        if(level().getDifficulty()==Difficulty.EASY ||
                level().getDifficulty()==Difficulty.NORMAL&&random.nextBoolean()) {
            SmallFireball f=new SmallFireball(level(),this,dx,dy,dz); f.setPos(sx,getY(),sz); level().addFreshEntity(f);
        } else {
            LargeFireball f=new LargeFireball(level(),this,dx,dy,dz,1); f.setPos(sx,getY(),sz); level().addFreshEntity(f);
        }
        playSound(net.minecraft.sounds.SoundEvents.GHAST_SHOOT,1,.8F/(random.nextFloat()*.4F+.8F));
        heal(1);
    }

    @Override public void tick() {
        super.tick();
        Vec3 m=getDeltaMovement(); setDeltaMovement(m.x,m.y*.6,m.z);
        if(++wingTicks>30){ if(!level().isClientSide) playSound(ModSounds.MOTHRA_WINGS.get(),1,1); wingTicks=0; }
        if(--healTicks<=0){ if(getHealth()<getMaxHealth()) heal(1); healTicks=100; }
    }

    public static boolean checkSpawnRules(EntityType<BrutalflyEntity> type,ServerLevelAccessor level,
            MobSpawnType reason,BlockPos pos,RandomSource random) {
        if(pos.getY()<70||level.getLevel().isDay()) return false;
        for(int x=-3;x<=2;x++) for(int z=-4;z<=3;z++) for(int y=1;y<=9;y++)
            if(!level.getBlockState(pos.offset(x,y,z)).isAir()) return false;
        return level.getLevel().getEntitiesOfClass(BrutalflyEntity.class,
                new AABB(pos).inflate(64,32,64)).isEmpty()
                && Mob.checkMobSpawnRules(type,level,reason,pos,random);
    }
}