package com.santipdr.copyl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Port moderno del comportamiento reactivo de OreTitanium/OreUranium.
 * El original guardaba un booleano global por instancia; aquí se usa un BlockState para que
 * cada posición tenga su propio estado y se apague tras 10 ticks.
 */
public final class ReactiveOreBlock extends Block {
    public static final BooleanProperty GLOWING = BlockStateProperties.LIT;

    public ReactiveOreBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(GLOWING, false));
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        activate(level, pos, state);
        super.attack(state, level, pos, player);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        activate(level, pos, state);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        activate(level, pos, state);
        return super.use(state, level, pos, player, hand, hit);
    }

    private void activate(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            ParticleSparkle.redstoneOnExposedFaces(level, pos, level.getRandom());
            return;
        }
        if (!state.getValue(GLOWING)) {
            level.setBlock(pos, state.setValue(GLOWING, true), Block.UPDATE_CLIENTS);
        }
        level.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(GLOWING)) {
            level.setBlock(pos, state.setValue(GLOWING, false), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(GLOWING)) {
            ParticleSparkle.redstoneOnExposedFaces(level, pos, random);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLOWING);
    }
}
