package com.santipdr.copyl.common.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Adaptación 1.20.1 de la antigua EntityAIMoveIndoors usada por OreSpawn.
 * Las puertas de aldea de 1.12 ya no existen como sistema; el HOME POI moderno
 * es el equivalente más cercano para conservar la intención original.
 */
public final class MoveIndoorsGoal extends Goal {
    private static final int VILLAGE_RANGE = 46; // radio mínimo antiguo (32) + margen 14.

    private final PathfinderMob mob;
    private BlockPos targetHome;
    private int lastInsideX = Integer.MIN_VALUE;
    private int lastInsideZ = Integer.MIN_VALUE;

    public MoveIndoorsGoal(PathfinderMob mob) {
        this.mob = mob;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!(mob.level() instanceof ServerLevel level) || !level.dimensionType().hasSkyLight()) {
            return false;
        }

        // En el original el intento se hacía al anochecer y muy ocasionalmente con mal tiempo.
        if (level.isDay() && !level.isRaining()) {
            return false;
        }
        if (mob.getRandom().nextInt(50) != 0) {
            return false;
        }
        if (lastInsideX != Integer.MIN_VALUE
                && mob.distanceToSqr(lastInsideX, mob.getY(), lastInsideZ) < 4.0D) {
            return false;
        }

        BlockPos here = mob.blockPosition();
        targetHome = level.getPoiManager()
                .findClosest(holder -> holder.is(PoiTypes.HOME), here, VILLAGE_RANGE, PoiManager.Occupancy.ANY)
                .map(BlockPos::immutable)
                .orElse(null);
        return targetHome != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        lastInsideX = Integer.MIN_VALUE;
        if (targetHome == null) {
            return;
        }

        if (mob.distanceToSqr(Vec3.atCenterOf(targetHome)) > 256.0D) {
            Vec3 toward = DefaultRandomPos.getPosTowards(
                    mob, 14, 3, Vec3.atCenterOf(targetHome), Math.PI / 2.0D);
            if (toward != null) {
                mob.getNavigation().moveTo(toward.x, toward.y, toward.z, 1.0D);
            }
        } else {
            mob.getNavigation().moveTo(
                    targetHome.getX() + 0.5D,
                    targetHome.getY(),
                    targetHome.getZ() + 0.5D,
                    1.0D);
        }
    }

    @Override
    public void stop() {
        if (targetHome != null) {
            lastInsideX = targetHome.getX();
            lastInsideZ = targetHome.getZ();
        }
        targetHome = null;
    }
}
