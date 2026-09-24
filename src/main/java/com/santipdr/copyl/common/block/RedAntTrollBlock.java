package com.santipdr.copyl.common.block;

import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.entity.RedAntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

/** The original troll block releases twenty red ants when broken. */
public final class RedAntTrollBlock extends Block {
    public RedAntTrollBlock() {
        super(Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState replacement, boolean moving) {
        super.onRemove(state, level, pos, replacement, moving);
        if (!level.isClientSide && !replacement.is(this) && level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                RedAntEntity ant = ModEntities.RED_ANT.get().create(serverLevel);
                if (ant == null) {
                    continue;
                }
                ant.moveTo(pos.getX(), pos.getY(), pos.getZ(), level.random.nextFloat() * 360.0F, 0.0F);
                ant.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null, null);
                serverLevel.addFreshEntity(ant);
            }
        }
    }
}
