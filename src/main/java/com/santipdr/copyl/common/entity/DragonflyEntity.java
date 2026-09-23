package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.item.material.ModMaterialItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

/**
 * Port de danger.orespawn.entity.Dragonfly del JAR 1.12.2.
 * Conserva el vuelo libre, caza de Bird/Butterfly, daño, drops y reglas de desaparición.
 */
public final class DragonflyEntity extends Animal {
    private BlockPos currentFlightTarget;

    public DragonflyEntity(EntityType<? extends DragonflyEntity> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.33000001311302185D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        // 1.12.2: canDespawn() devolvía false cuando estaba inLove y true en caso contrario.
        return !isInLove();
    }

    @Override
    protected float getSoundVolume() {
        return 0.25F;
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DRAGONFLY_LIVING.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.DRAGONFLY_HURT.get();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void doPush(Entity entity) {
        // applyEntityCollision(Entity) estaba vacío en el original.
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        setDeltaMovement(motion.x, motion.y * 0.6D, motion.z);
    }

    @Override
    protected void customServerAiStep() {
        if (isRemoved()) return;
        super.customServerAiStep();

        if (currentFlightTarget == null) {
            currentFlightTarget = new BlockPos((int) getX(), (int) getY(), (int) getZ());
        }

        boolean chooseNewTarget = random.nextInt(300) == 0
                || currentFlightTarget.distSqr(new BlockPos((int) getX(), (int) getY(), (int) getZ())) < 2.1D;

        if (chooseNewTarget) {
            chooseOpenFlightTarget();
        } else if (random.nextInt(12) == 0 && level().getDifficulty() != Difficulty.PEACEFUL) {
            LivingEntity target = findSomethingToAttack();
            if (target != null) {
                currentFlightTarget = new BlockPos((int) target.getX(), (int) (target.getY() + 1.0D), (int) target.getZ());
                if (distanceToSqr(target) < 6.0D) {
                    doHurtTarget(target);
                }
            }
        }

        if (currentFlightTarget == null) return;

        /*
         * El bytecode ConquerantFix usa getX() del BlockPos en los tres cálculos (X/Y/Z).
         * Parece extraño, pero se conserva literalmente para no "arreglar" comportamiento
         * que sí existe en el JAR que estamos portando.
         */
        double targetCoordinate = currentFlightTarget.getX();
        double dx = targetCoordinate + 0.5D - getX();
        double dy = targetCoordinate + 0.1D - getY();
        double dz = targetCoordinate + 0.5D - getZ();

        Vec3 motion = getDeltaMovement();
        setDeltaMovement(
                motion.x + (Math.signum(dx) * 0.5D - motion.x) * 0.30000000149011613D,
                motion.y + (Math.signum(dy) * 0.699999988079071D - motion.y) * 0.20000000149011612D,
                motion.z + (Math.signum(dz) * 0.5D - motion.z) * 0.30000000149011613D
        );

        Vec3 nextMotion = getDeltaMovement();
        float targetYaw = (float) (Mth.atan2(nextMotion.z, nextMotion.x) * (180.0D / Math.PI)) - 90.0F;
        float wrapped = Mth.wrapDegrees(targetYaw - getYRot());
        this.zza = 1.0F;
        setYRot(getYRot() + wrapped / 4.0F);
        yBodyRot = getYRot();
    }

    private void chooseOpenFlightTarget() {
        for (int attempts = 50; attempts > 0; attempts--) {
            int zOffset = random.nextInt(5) + 5;
            int xOffset = random.nextInt(5) + 5;
            if (random.nextInt(2) == 0) zOffset = -zOffset;
            if (random.nextInt(2) == 0) xOffset = -xOffset;

            BlockPos candidate = new BlockPos(
                    (int) getX() + xOffset,
                    (int) getY() + random.nextInt(5) - 2,
                    (int) getZ() + zOffset
            );

            if (level().getBlockState(candidate).is(Blocks.AIR)
                    && canSeeTarget(candidate.getX(), candidate.getY(), candidate.getZ())) {
                currentFlightTarget = candidate;
                return;
            }
        }
    }

    private boolean canSeeTarget(double x, double y, double z) {
        Vec3 from = new Vec3(getX(), getY() + 0.25D, getZ());
        Vec3 to = new Vec3(x, y, z);
        return level().clip(new ClipContext(from, to, ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
    }

    @Nullable
    private LivingEntity findSomethingToAttack() {
        AABB box = getBoundingBox().inflate(10.0D, 6.0D, 10.0D);
        List<LivingEntity> candidates = level().getEntitiesOfClass(LivingEntity.class, box,
                this::isSuitableTarget);
        candidates.sort(Comparator.comparingDouble(this::distanceToSqr));
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    private boolean isSuitableTarget(LivingEntity target) {
        if (level().getDifficulty() == Difficulty.PEACEFUL || target == this || !target.isAlive()) return false;
        if (!getSensing().hasLineOfSight(target)) return false;
        if (target instanceof BirdEntity) return true;

        // Butterfly todavía no está porteada; comparar por ID deja esta lógica lista sin inventar
        // una entidad provisional. Cuando copyl:butterfly exista empezará a ser presa automáticamente.
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
        return key != null && "copyl".equals(key.getNamespace()) && "butterfly".equals(key.getPath());
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        return target.hurt(damageSources().mobAttack(this), 2.0F);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        Entity attacker = source.getEntity();
        if (attacker != null && currentFlightTarget != null) {
            currentFlightTarget = new BlockPos((int) attacker.getX(), (int) attacker.getY(), (int) attacker.getZ());
        }
        return result;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        switch (random.nextInt(6)) {
            case 0 -> spawnAtLocation(new ItemStack(Items.GOLD_NUGGET));
            case 1 -> spawnAtLocation(new ItemStack(ModMaterialItems.URANIUM_NUGGET.get()));
            case 2 -> spawnAtLocation(new ItemStack(ModMaterialItems.TITANIUM_NUGGET.get()));
            default -> { }
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
        // Los tres hooks de caída del 1.12.2 estaban vacíos.
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(net.minecraft.server.level.ServerLevel level, AgeableMob otherParent) {
        // createChild() devolvía null.
        return null;
    }
}
