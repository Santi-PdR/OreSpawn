package com.santipdr.copyl.common.block.entity;

import com.santipdr.copyl.common.block.entity.ModBlockEntities;
import com.santipdr.copyl.common.util.LegacyRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Persisted growth counters for each segment of an OreSpawn corn stalk. */
public final class CornPlantBlockEntity extends BlockEntity {
    private int age;
    private int phase = 1;
    private int heightContribution = LegacyRandom.nextInt(5) + 3;

    public CornPlantBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CORN_PLANT.get(), pos, state);
    }

    public int getAge() { return age; }
    public void setAge(int value) { age = value; setChanged(); }
    public int getPhase() { return phase; }
    public void setPhase(int value) { phase = value; setChanged(); }
    public int getHeightContribution() { return heightContribution; }
    public void setHeightContribution(int value) { heightContribution = value; setChanged(); }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Age", age);
        tag.putInt("Phase", phase);
        tag.putInt("HeightContribution", heightContribution);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        age = tag.getInt("Age");
        phase = tag.getInt("Phase");
        heightContribution = tag.contains("HeightContribution")
                ? tag.getInt("HeightContribution")
                : RandomSource.create().nextInt(5) + 3;
    }
}
