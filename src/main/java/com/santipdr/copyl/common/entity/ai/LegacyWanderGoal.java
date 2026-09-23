package com.santipdr.copyl.common.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/** Port directo de MyEntityAIWander de OreSpawn 1.12.2. */
public final class LegacyWanderGoal extends Goal {
    private final PathfinderMob mob;
    private final double speed;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    public LegacyWanderGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getRandom().nextInt(90) != 0) {
            return false;
        }
        if (mob instanceof TamableAnimal tamable && tamable.isOrderedToSit()) {
            return false;
        }

        Vec3 target = LandRandomPos.getPos(mob, 10, 7);
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
        if (mob instanceof TamableAnimal tamable) {
            LivingEntity owner = tamable.getOwner();
            if (owner != null
                    && (int) tamable.getX() == (int) owner.getX()
                    && (int) tamable.getZ() == (int) owner.getZ()
                    && (int) tamable.getY() < (int) owner.getY() + 2
                    && (int) tamable.getY() > (int) owner.getY() - 2) {
                return false;
            }
        }
        return !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speed);
    }
}
