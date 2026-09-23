package com.santipdr.copyl.common.item.armor;

import com.santipdr.copyl.CopyL;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.Map;

/** Armor materials reconstruidos exactamente desde OrespawnArmorMaterial.class. */
public enum CopyLArmorMaterials implements ArmorMaterial {
    ULTIMATE("ultimate", 200, new int[]{6, 12, 10, 6}, 100, 3.0F),
    EMERALD("emerald", 100, new int[]{3, 8, 6, 3}, 12, 3.0F),
    MOTH("moth", 100, new int[]{2, 7, 5, 2}, 12, 3.0F),
    AMETHYST("amethyst", 100, new int[]{4, 8, 7, 3}, 12, 3.0F);

    private static final Map<ArmorItem.Type, Integer> BASE_DURABILITY = new EnumMap<>(ArmorItem.Type.class);
    static {
        BASE_DURABILITY.put(ArmorItem.Type.BOOTS, 13);
        BASE_DURABILITY.put(ArmorItem.Type.LEGGINGS, 15);
        BASE_DURABILITY.put(ArmorItem.Type.CHESTPLATE, 16);
        BASE_DURABILITY.put(ArmorItem.Type.HELMET, 11);
    }

    private final String name;
    private final int durabilityMultiplier;
    /** Orden original 1.12.2: boots, leggings, chestplate, helmet. */
    private final int[] protection;
    private final int enchantability;
    private final float toughness;

    CopyLArmorMaterials(String name, int durabilityMultiplier, int[] protection, int enchantability, float toughness) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantability = enchantability;
        this.toughness = toughness;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case BOOTS -> protection[0];
            case LEGGINGS -> protection[1];
            case CHESTPLATE -> protection[2];
            case HELMET -> protection[3];
        };
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        // El original usaba SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND para los cuatro materiales.
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        // EnumHelper.addArmorMaterial se usó sin repair item en el original.
        return Ingredient.EMPTY;
    }

    @Override
    public String getName() {
        return CopyL.MOD_ID + ":" + name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F;
    }
}
