package com.santipdr.copyl.common.item.tool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/** Subclases pequeñas para conservar los auto-encantamientos del OreSpawn original. */
public final class AutoEnchantingTools {
    private AutoEnchantingTools() {
    }

    private static boolean shouldEnchant(Level level, ItemStack stack) {
        return !level.isClientSide && !stack.isEnchanted();
    }

    public static final class UltimateSword extends SwordItem {
        public UltimateSword(Tier tier, Item.Properties properties) {
            super(tier, 3, -2.4F, properties);
        }
        @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
            if (shouldEnchant(level, stack)) {
                stack.enchant(Enchantments.MOB_LOOTING, 6);
                stack.enchant(Enchantments.UNBREAKING, 6);
            }
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }

    public static final class UltimatePickaxe extends PickaxeItem {
        public UltimatePickaxe(Tier tier, Item.Properties properties) {
            super(tier, 1, -2.8F, properties);
        }
        @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
            if (shouldEnchant(level, stack)) {
                stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
                stack.enchant(Enchantments.BLOCK_FORTUNE, 5);
            }
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }

    public static final class UltimateAxe extends AxeItem {
        public UltimateAxe(Tier tier, Item.Properties properties) {
            // El 1.12.2 pasaba material.Damage como daño adicional del AxeItem.
            super(tier, tier.getAttackDamageBonus(), -3.0F, properties);
        }
        @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
            if (shouldEnchant(level, stack)) stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }

    public static final class UltimateShovel extends ShovelItem {
        public UltimateShovel(Tier tier, Item.Properties properties) {
            super(tier, 1.5F, -3.0F, properties);
        }
        @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
            if (shouldEnchant(level, stack)) stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }

    public static final class EmeraldPickaxe extends PickaxeItem {
        public EmeraldPickaxe(Tier tier, Item.Properties properties) {
            super(tier, 1, -2.8F, properties);
        }
        @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
            if (shouldEnchant(level, stack)) stack.enchant(Enchantments.SILK_TOUCH, 1);
            super.inventoryTick(stack, level, entity, slot, selected);
        }
    }
}
