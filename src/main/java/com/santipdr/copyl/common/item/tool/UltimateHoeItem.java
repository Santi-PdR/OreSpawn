package com.santipdr.copyl.common.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;

/**
 * Port moderno de UltimateHoe: Efficiency II automática y labrado 3x3 que tolera un desnivel
 * de un bloque, tal como hacía la implementación 1.12.2.
 */
public final class UltimateHoeItem extends HoeItem {
    public UltimateHoeItem(Tier tier, Item.Properties properties) {
        super(tier, -4, 0.0F, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && !stack.isEnchanted()) {
            stack.enchant(Enchantments.BLOCK_EFFICIENCY, 2);
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        if (!isTillable(level, center) || !level.isEmptyBlock(center.above())) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            till(level, center);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) continue;
                    BlockPos pos = center.offset(dx, 0, dz);
                    if (isTillable(level, pos) && level.isEmptyBlock(pos.above())) {
                        till(level, pos);
                    } else if (level.isEmptyBlock(pos)) {
                        BlockPos below = pos.below();
                        if (isTillable(level, below) && level.isEmptyBlock(pos)) till(level, below);
                    } else if (isTillable(level, pos.above()) && level.isEmptyBlock(pos.above(2))) {
                        // Equivale al ajuste vertical positivo del algoritmo original.
                        till(level, pos.above());
                    }
                }
            }
            level.playSound(null, center, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static boolean isTillable(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.DIRT) || level.getBlockState(pos).is(Blocks.GRASS_BLOCK);
    }

    private static void till(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 7));
    }
}
