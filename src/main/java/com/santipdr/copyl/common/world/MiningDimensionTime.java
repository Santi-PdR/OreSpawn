package com.santipdr.copyl.common.world;

import com.santipdr.copyl.CopyL;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Reproduces the original Mining WorldProvider daylight rollover. */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MiningDimensionTime {
    private static final long DAY_LENGTH = 24_000L;
    private static final long DAYLIGHT_END = 12_000L;

    private MiningDimensionTime() {}

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel mining)
                || !mining.dimension().equals(ModDimensionKeys.MINING)
                || !mining.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            return;
        }

        long time = mining.getDayTime();
        if (time % DAY_LENGTH <= DAYLIGHT_END) {
            return;
        }

        long nextDay = time + DAY_LENGTH;
        nextDay -= nextDay % DAY_LENGTH;
        for (ServerLevel level : mining.getServer().getAllLevels()) {
            level.setDayTime(nextDay);
        }
    }
}
