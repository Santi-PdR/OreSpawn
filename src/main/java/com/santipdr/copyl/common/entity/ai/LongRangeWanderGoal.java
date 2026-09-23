package com.santipdr.copyl.common.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * Port moderno de MyEntityAIWanderALot de OreSpawn 1.12.2.
 *
 * El original intenta empezar 1 de cada 30 ticks, busca una posición aleatoria
 * con rango horizontal configurable y 7 bloques verticales, y no se ejecuta si
 * una criatura domesticable está sentada.
 */
public final class LongRangeWanderGoal extends Goal {
    private final PathfinderMob mob;
    private final int horizontalRange;
    private final double speed;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private boolean busy;

    public LongRangeWanderGoal(PathfinderMob mob, int horizontalRange, double speed) {
        this.mob = mob;
        this.horizontalRange = horizontalRange;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public boolean canUse() {
        if (busy) {
            return false;
        }
        if (mob.getRandom().nextInt(30) != 0) {
            return false;
        }
        if (mob instanceof TamableAnimal tamable && tamable.isOrderedToSit()) {
            return false;
        }

        Vec3 target = LandRandomPos.getPos(mob, horizontalRange, 7);
        if (target == null) {
            return false;
        }

        wantedX = target.x;
        wantedY = target.y;
        wantedZ = target.z;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speed);
    }
}
