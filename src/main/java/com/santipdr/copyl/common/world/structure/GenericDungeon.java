package com.santipdr.copyl.common.world.structure;

import com.santipdr.copyl.CopyL;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/** Port fiel de danger.orespawn.world.structures.GenericDungeon (1.12.2). */
public final class GenericDungeon {
    public static final ResourceLocation LOOT_TABLE = new ResourceLocation(CopyL.MOD_ID, "generic_dungeon");
    private static final int SIZE = 12;
    private static final int HEIGHT = 6;
    private static final String[] SPAWNER_IDS = {"alien", "gammametroid", "cryolophosaurus"};

    private static void setRandomWallBlock(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, (level.random.nextInt(2) == 1 ? Blocks.COBBLESTONE : Blocks.STONE).defaultBlockState(), 3);
    }

    public void makeDungeon(ServerLevel level, int x, int y, int z) {
        BlockPos origin = new BlockPos(x, y, z);

        // El original primero limpia todo el volumen 12 x 6 x 12.
        for (int dx = 0; dx < SIZE; dx++) {
            for (int dy = 0; dy < HEIGHT; dy++) {
                for (int dz = 0; dz < SIZE; dz++) {
                    level.setBlock(origin.offset(dx, dy, dz), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }

        // Piso completo de obsidiana.
        for (int dx = 0; dx < SIZE; dx++) {
            for (int dz = 0; dz < SIZE; dz++) {
                level.setBlock(origin.offset(dx, 0, dz), Blocks.OBSIDIAN.defaultBlockState(), 3);
            }
        }

        // Techo stone/cobble aleatorio.
        for (int dx = 0; dx < SIZE; dx++) {
            for (int dz = 0; dz < SIZE; dz++) {
                setRandomWallBlock(level, origin.offset(dx, HEIGHT - 1, dz));
            }
        }

        // Paredes Z = 0 / 11.
        for (int dx = 0; dx < SIZE; dx++) {
            for (int dy = 0; dy < HEIGHT; dy++) {
                setRandomWallBlock(level, origin.offset(dx, dy, 0));
                setRandomWallBlock(level, origin.offset(dx, dy, SIZE - 1));
            }
        }

        // Paredes X = 0 / 11.
        for (int dz = 0; dz < SIZE; dz++) {
            for (int dy = 0; dy < HEIGHT; dy++) {
                setRandomWallBlock(level, origin.offset(0, dy, dz));
                setRandomWallBlock(level, origin.offset(SIZE - 1, dy, dz));
            }
        }

        // Spawner central: (x+6, y+1, z+6).
        BlockPos spawnerPos = origin.offset(SIZE / 2, 1, SIZE / 2);
        level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 3);
        BlockEntity spawnerBe = level.getBlockEntity(spawnerPos);
        if (spawnerBe instanceof SpawnerBlockEntity spawner) {
            String id = SPAWNER_IDS[level.random.nextInt(SPAWNER_IDS.length)];
            var type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(CopyL.MOD_ID, id));
            if (type != null) {
                // 1.20.1 necesita también Level y posición para inicializar correctamente
                // el SpawnData del BaseSpawner.
                spawner.getSpawner().setEntityId(type, level, level.getRandom(), spawnerPos);
                spawner.setChanged();
            }
        }

        // Cofre exactamente al sur interior del dungeon: (x+6, y+1, z+1), mirando SOUTH.
        BlockPos chestPos = origin.offset(SIZE / 2, 1, 1);
        BlockState chestState = Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        level.setBlock(chestPos, chestState, 3);
        BlockEntity chestBe = level.getBlockEntity(chestPos);
        if (chestBe instanceof ChestBlockEntity chest) {
            long seed = (long) x * y * z * level.random.nextInt();
            chest.setLootTable(LOOT_TABLE, seed);
        }
    }
}
