package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.util.LegacyRandom;
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

    @Override public boolean fireImmune() { return true; }
    @Override public boolean removeWhenFarAway(double distanceToClosestPlayer) { return !isPersistenceRequired(); }
    @Override protected net.minecraft.sounds.SoundEvent getAmbientSound() { return null; }
    @Override protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) { return null; }
    @Override protected net.minecraft.sounds.SoundEvent getDeathSound() { return net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE; }
    @Override public boolean isPushable() { return true; }
    @Override public void push(Entity entity) { }
    @Override public boolean causeFallDamage(float distance, float multiplier, DamageSource source) { return false; }
    @Override protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) { }

    @Override public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() == this) return false;
        boolean result = super.hurt(source, amount);
        if (result && source.getEntity() instanceof LivingEntity attacker && attacker != this && !level().isClientSide)
            flightTarget = new BlockPos((int) attacker.getX(), (int) attacker.getY() + 2, (int) attacker.getZ());
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
            for (int i = 0; i < 53; i++) {
                double x = getX() + LegacyRandom.nextInt(8) - LegacyRandom.nextInt(8);
                double z = getZ() + LegacyRandom.nextInt(8) - LegacyRandom.nextInt(8);
                net.minecraft.world.entity.item.ItemEntity nugget =
                        new net.minecraft.world.entity.item.ItemEntity(level(), x, getY() + 1.0D, z,
                                new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.GOLD_NUGGET));
                server.addFreshEntity(nugget);
            }
        }
    }

    @Override protected void customServerAiStep() {
        super.customServerAiStep();
        if (level().isClientSide) return;
        int x=(int)getX(), y=(int)getY(), z=(int)getZ();
        if (lastX==x && lastY==y && lastZ==z) stuckTicks++;
        else { lastX=x; lastY=y; lastZ=z; stuckTicks=0; }
        if (flightTarget == null) {
            flightTarget = new BlockPos((int) getX(), (int) getY(), (int) getZ());
        }
        if (stuckTicks > 30 || level().getRandom().nextInt(200) == 0
                || distanceFromLegacyPositionSquared() < 9.0D) {
            chooseFlightTarget();
        }

        if (level().getRandom().nextInt(6)==0 && level().getDifficulty()!=Difficulty.PEACEFUL) {
            Player player = level().getEntitiesOfClass(Player.class,getBoundingBox().inflate(30,20,30))
                    .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
            if (player != null && player.getAbilities().instabuild) {
                player = null;
            }
            LivingEntity target = player;
            if (target==null && level().getRandom().nextInt(3)==0)
                target=level().getEntitiesOfClass(LivingEntity.class,getBoundingBox().inflate(25,20,25),this::isOriginalHostile)
                        .stream().min(Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
            if (target!=null) {
                flightTarget=new BlockPos((int)target.getX(),(int)target.getY()+(player!=null?4:5),(int)target.getZ());
                if (distanceToSqr(target)<=25) doHurtTarget(target);
                else if ((player != null
                        ? random.nextInt(level().getDifficulty()==Difficulty.HARD?2:3)
                        : level().getRandom().nextInt(level().getDifficulty()==Difficulty.HARD?2:3)) == 0) shoot(target);
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

    private double distanceFromLegacyPositionSquared() {
        double dx = flightTarget.getX() - (int) getX();
        double dy = flightTarget.getY() - (int) getY();
        double dz = flightTarget.getZ() - (int) getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    private void chooseFlightTarget() {
        BlockPos origin=new BlockPos((int)getX(),(int)getY(),(int)getZ());
        int nearestGround=20;
        for(int ox=-5;ox<=5;ox+=5) for(int oz=-5;oz<=5;oz+=5)
            for(int down=1;down<=19;down++) if(!level().isEmptyBlock(origin.offset(ox,-down,oz))) {
                nearestGround=Math.min(nearestGround,down); break;
            }
        int groundOffset=nearestGround>10?nearestGround-9:0;
        for(int i=0;i<30;i++) {
            int xSign = level().getRandom().nextInt(2) == 0 ? -1 : 1;
            int zSign = level().getRandom().nextInt(2) == 0 ? -1 : 1;
            int oz = (random.nextInt(20) + 8) * zSign;
            int ox = (random.nextInt(20) + 8) * xSign;
            int y = origin.getY() + level().getRandom().nextInt(7) - 1 - groundOffset;
            BlockPos p = new BlockPos(origin.getX() + ox, y, origin.getZ() + oz);
            flightTarget = p;
            if (level().isEmptyBlock(p) && hasLineOfSightTo(p)) { stuckTicks=0; return; }
        }
        stuckTicks=0;
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
            BetterFireballEntity f=new BetterFireballEntity(level(),this,dx,dy,dz,1); f.setPos(sx,getY(),sz); level().addFreshEntity(f);
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