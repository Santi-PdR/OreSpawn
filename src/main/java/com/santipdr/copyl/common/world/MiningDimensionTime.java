package com.santipdr.copyl.common.world;

import com.santipdr.copyl.CopyL;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Reproduces the original Mining WorldProvider daylight rollover when every player sleeps. */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MiningDimensionTime {
    private MiningDimensionTime() {}

    @SubscribeEvent
    public static void onSleepFinished(SleepFinishedTimeEvent event) {
        if (!(event.getLevel() instanceof ServerLevel mining)
                || !mining.dimension().equals(ModDimensionKeys.MINING)
                || !mining.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            return;
        }

        long wakeTime = event.getNewTime();
        for (ServerLevel level : mining.getServer().getAllLevels()) {
            if (level != mining) {
                level.setDayTime(wakeTime);
            }
        }
    }
}
