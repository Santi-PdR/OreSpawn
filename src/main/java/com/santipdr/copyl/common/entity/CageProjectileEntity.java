package com.santipdr.copyl.common.entity;

import com.santipdr.copyl.common.item.CritterCageItem;
import com.santipdr.copyl.common.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

/** Port de danger.orespawn.entity.EntityCage. */
public final class CageProjectileEntity extends ThrowableProjectile {
    @Nullable
    private EntityType<?> containedType;
    @Nullable
    private String customName;

    public CageProjectileEntity(EntityType<? extends CageProjectileEntity> type, Level level) {
        super(type, level);
    }

    public CageProjectileEntity(Level level, LivingEntity owner,
                                @Nullable EntityType<?> containedType,
                                @Nullable String customName) {
        super(ModEntities.CAGE_PROJECTILE.get(), owner, level);
        this.containedType = containedType;
        this.customName = customName;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        level().addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (level().isClientSide) {
            return;
        }

        if (!(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }

        if (containedType == null) {
            tryCapture(serverLevel, result);
        } else {
            releaseContained(serverLevel, result);
        }
        discard();
    }

    private void tryCapture(ServerLevel level, HitResult result) {
        if (!(result instanceof EntityHitResult entityHit)) {
            return;
        }

        // El original descarta 2 resultados de 10 antes de intentar la captura: 80%.
        if (random.nextInt(10) < 2) {
            return;
        }

        Entity target = entityHit.getEntity();
        emitCaptureEffects(level, target);
        level.playSound(null, target.blockPosition(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.PLAYERS, 1.0F, 1.5F);

        if (!(target instanceof Mob)) {
            return;
        }

        CritterCageItem cage = CritterCageItem.getCageFromEntity(target);
        if (cage == null || random.nextFloat() >= cage.getChance()) {
            return;
        }

        spawnAtLocation(cage);
        // El 1.12.2 eliminaba la criatura sin soltar sus drops.
        target.discard();
    }

    private void releaseContained(ServerLevel level, HitResult result) {
        BlockPos pos;
        if (result instanceof EntityHitResult entityHit) {
            pos = entityHit.getEntity().blockPosition();
        } else if (result instanceof BlockHitResult blockHit) {
            pos = blockHit.getBlockPos();
        } else {
            pos = blockPosition();
        }

        emitReleaseEffects(level, pos);
        level.playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.PLAYERS, 1.0F, 1.5F);

        Entity entity = containedType.create(level);
        if (entity != null) {
            entity.moveTo(pos.getX(), pos.getY() + 1.0D, pos.getZ(), entity.getYRot(), entity.getXRot());
            if (customName != null) {
                entity.setCustomName(net.minecraft.network.chat.Component.literal(customName));
            }
            level.addFreshEntity(entity);
        }
        dropEmptyCage(level, pos);
    }

    private static void emitCaptureEffects(ServerLevel level, Entity target) {
        for (int i = 0; i < 4; i++) {
            level.sendParticles(ParticleTypes.SMOKE,
                    target.getX(), target.getY() + 0.25D, target.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(ParticleTypes.POOF,
                    target.getX(), target.getY() + 0.25D, target.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(DustParticleOptions.REDSTONE,
                    target.getX(), target.getY() + 0.25D, target.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void emitReleaseEffects(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 6; i++) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + 1.25D;
            double z = pos.getZ() + 0.5D;
            level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(ParticleTypes.EXPLOSION, x, y, z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(DustParticleOptions.REDSTONE, x, y, z,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void dropEmptyCage(ServerLevel level, BlockPos pos) {
        ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(),
                new ItemStack(ModItems.EMPTY_CAGE.get()));
        level.addFreshEntity(item);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
