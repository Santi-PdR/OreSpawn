package com.santipdr.copyl.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.santipdr.copyl.CopyL;
import com.santipdr.copyl.common.world.DimensionTeleport;
import com.santipdr.copyl.common.world.ModDimensionKeys;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Modern Brigadier port of OreSpawn's original dimensiontp command and aliases.
 * Legacy dimension IDs are retained for the three vanilla dimensions and ID 5
 * (the Mining Dimension registered by OreSpawn 1.12.2).
 */
@Mod.EventBusSubscriber(modid = CopyL.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class DimensionTeleportCommand {
    private DimensionTeleportCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        for (String name : new String[]{"dimensiontp", "orespawn", "tpdim", "dimtp"}) {
            event.getDispatcher().register(command(name));
        }
    }

    private static LiteralArgumentBuilder<CommandSourceStack> command(String name) {
        return Commands.literal(name)
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("id", IntegerArgumentType.integer())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            if (!(source.getEntity() instanceof ServerPlayer player)) {
                                return 0;
                            }

                            int legacyId = IntegerArgumentType.getInteger(context, "id");
                            ResourceKey<Level> target = dimensionForLegacyId(legacyId);
                            if (target == null || !DimensionTeleport.teleportToDimension(
                                    player, target, player.blockPosition().getX(), player.blockPosition().getZ())) {
                                source.sendFailure(Component.literal("Dimension ID Invalid"));
                                return 0;
                            }
                            return 1;
                        }));
    }

    private static ResourceKey<Level> dimensionForLegacyId(int id) {
        return switch (id) {
            case -1 -> Level.NETHER;
            case 0 -> Level.OVERWORLD;
            case 1 -> Level.END;
            case 5 -> ModDimensionKeys.MINING;
            default -> null;
        };
    }
}
