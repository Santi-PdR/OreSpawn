package com.santipdr.copyl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Port directo de OreSpawn 1.12.2 OreGenericEgg.
 *
 * El bloque se rompe y se dropea normalmente. Tras romperse, tiene 50 % de
 * probabilidad de soltar entre 5 y 9 XP (5 + nextInt(3) + nextInt(3)).
 */
public final class CreatureOreBlock extends Block {
    public CreatureOreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, tool, dropExperience);
        RandomSource random = level.getRandom();
        int xp = 5 + random.nextInt(3) + random.nextInt(3);
        if (random.nextInt(2) == 1) {
            this.popExperience(level, pos, xp);
        }
    }
}
