package com.santipdr.copyl.common.block;

import com.santipdr.copyl.common.block.entity.CornPlantBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

/** Multi-block corn stalk with the age, phase and height cycle from 1.12.2. */
public final class CornPlantBlock extends Block implements EntityBlock {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public CornPlantBlock() {
        super(Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().sound(SoundType.CROP));
        registerDefaultState(defaultBlockState().setValue(STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(this) || below.is(Blocks.GRASS_BLOCK) || below.is(Blocks.DIRT) || below.is(Blocks.FARMLAND);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbor,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !canSurvive(state, level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighbor, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CornPlantBlockEntity(pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isEmptyBlock(pos.above())) {
            return;
        }
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }

        int accumulatedHeight = 0;
        int below = 1;
        while (level.getBlockState(pos.below(below)).is(this)) {
            CornPlantBlockEntity tile = getPlantEntity(level, pos);
            if (tile == null) return;
            accumulatedHeight += tile.getHeightContribution();
            below++;
        }

        if (!ForgeHooks.onCropsGrowPre(level, pos, state, true)) {
            return;
        }
        CornPlantBlockEntity tile = getPlantEntity(level, pos);
        if (tile == null) return;

        if (accumulatedHeight < 21) {
            if (tile.getAge() == 15) {
                tile.setAge(0);
                level.setBlock(pos.above(), defaultBlockState(), 2);
                BlockState current = level.getBlockState(pos);
                level.setBlock(pos, current.getValue(STAGE) != 0 ? current : state.setValue(STAGE, 1), 2);
            } else {
                tile.setAge(tile.getAge() + 1);
            }
        } else if (tile.getAge() == 15) {
            int phase = tile.getPhase();
            if (phase != 4) {
                if (phase <= 1) {
                    tile.setPhase(2);
                    phase = tile.getPhase();
                }
                int index = 1;
                while (level.getBlockState(pos.below(index)).is(this)) {
                    index++;
                }
                index--;
                while (index != 0 && level.getBlockState(pos.below(index)).getValue(STAGE) >= phase) {
                    index--;
                }
                if (index >= 1) {
                    BlockPos target = pos.below(index);
                    BlockState targetState = level.getBlockState(target);
                    level.setBlock(target, defaultBlockState().setValue(STAGE, Math.min(targetState.getValue(STAGE) + 1, 3)), 2);
                } else {
                    tile.setPhase(tile.getPhase() + 1);
                }
            }
        } else {
            tile.setAge(tile.getAge() + 1);
        }
        ForgeHooks.onCropsGrowPost(level, pos, state);
    }

    @Nullable
    private static CornPlantBlockEntity getPlantEntity(Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        return entity instanceof CornPlantBlockEntity plant ? plant : null;
    }
}
