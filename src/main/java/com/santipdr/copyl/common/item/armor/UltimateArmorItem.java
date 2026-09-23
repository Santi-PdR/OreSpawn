package com.santipdr.copyl.common.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/** Conserva el paquete de auto-encantamientos aplicado por ArmorBase al set Ultimate. */
public final class UltimateArmorItem extends ArmorItem {
    public UltimateArmorItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && !stack.isEnchanted()) {
            stack.enchant(Enchantments.RESPIRATION, 2);
            stack.enchant(Enchantments.AQUA_AFFINITY, 2);
            stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
            stack.enchant(Enchantments.FIRE_PROTECTION, 5);
            stack.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
            stack.enchant(Enchantments.UNBREAKING, 5);
            stack.enchant(Enchantments.FALL_PROTECTION, 3);
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }
}
