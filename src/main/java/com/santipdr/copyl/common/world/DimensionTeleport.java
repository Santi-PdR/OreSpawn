package com.santipdr.copyl.common.world;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Modern server-side equivalent of danger.orespawn.util.Teleport. */
public final class DimensionTeleport {
    private DimensionTeleport() {}

    /**
     * Teleports a server player to the requested dimension while preserving the
     * original OreSpawn landing rule: scan downward from Y=255 at the same X/Z
     * until the first non-air block is found, then place the player one block
     * above it. The legacy Y argument was effectively ignored by that scan, so
     * this method intentionally receives only the coordinates that matter.
     *
     * @return true when the destination level exists and the teleport happened.
     */
    public static boolean teleportToDimension(ServerPlayer player, ResourceKey<Level> target,
                                              double x, double z) {
        ServerLevel destination = player.server.getLevel(target);
        if (destination == null) {
            // Dimension data is supplied by the mod datapack. Keep the failure
            // explicit to callers if the target is absent from the loaded world.
            return false;
        }

        int scanY = Math.min(255, destination.getMaxBuildHeight() - 1);
        while (scanY > 0 && destination.getBlockState(BlockPos.containing(x, scanY, z)).isAir()) {
            --scanY;
        }

        double destinationY = scanY + 1.0D;
        player.teleportTo(destination, x, destinationY, z, player.getYRot(), player.getXRot());
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0.0F;
        return true;
    }
}
