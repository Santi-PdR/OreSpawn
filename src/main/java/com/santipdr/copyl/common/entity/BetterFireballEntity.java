package com.santipdr.copyl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/** The 1.12.2 BetterFireball impact rules, carried by the vanilla large-fireball flight/render type. */
public final class BetterFireballEntity extends LargeFireball {
    private final int explosionPower;

    public BetterFireballEntity(Level level, LivingEntity owner, double dx, double dy, double dz, int explosionPower) {
        super(level, owner, dx, dy, dz, explosionPower);
        this.explosionPower = explosionPower;
    }

    @Override
    protected void onHit(HitResult result) {
        if (level().isClientSide) return;
        if (result instanceof EntityHitResult entityHit) {
            Entity target = entityHit.getEntity();
            if (target instanceof BetterFireballEntity || target instanceof MothraEntity) return;
            if (target.getBbWidth() * target.getBbHeight() <= 30.0F) {
                target.hurt(damageSources().fireball(this, getOwner()), 10.0F);
                target.setSecondsOnFire(5);
            }
        } else if (result instanceof BlockHitResult blockHit) {
            BlockPos firePos = blockHit.getBlockPos().relative(blockHit.getDirection());
            if (level().isEmptyBlock(firePos)) level().setBlockAndUpdate(firePos, Blocks.FIRE.defaultBlockState());
        }

        level().explode(null, getX(), getY(), getZ(), (float) explosionPower, true,
                level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
                        ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
        discard();
    }
}
