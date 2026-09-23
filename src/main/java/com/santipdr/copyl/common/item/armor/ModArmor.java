package com.santipdr.copyl.common.item.armor;

import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/** Los cuatro sets de armadura registrados por OreSpawn. */
public final class ModArmor {
    public static final RegistryObject<Item> AMETHYST_HELMET = armor("amethyst_helmet", CopyLArmorMaterials.AMETHYST, ArmorItem.Type.HELMET, false);
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = armor("amethyst_chestplate", CopyLArmorMaterials.AMETHYST, ArmorItem.Type.CHESTPLATE, false);
    public static final RegistryObject<Item> AMETHYST_LEGGINGS = armor("amethyst_leggings", CopyLArmorMaterials.AMETHYST, ArmorItem.Type.LEGGINGS, false);
    public static final RegistryObject<Item> AMETHYST_BOOTS = armor("amethyst_boots", CopyLArmorMaterials.AMETHYST, ArmorItem.Type.BOOTS, false);

    public static final RegistryObject<Item> EMERALD_HELMET = armor("emerald_helmet", CopyLArmorMaterials.EMERALD, ArmorItem.Type.HELMET, false);
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = armor("emerald_chestplate", CopyLArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE, false);
    public static final RegistryObject<Item> EMERALD_LEGGINGS = armor("emerald_leggings", CopyLArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS, false);
    public static final RegistryObject<Item> EMERALD_BOOTS = armor("emerald_boots", CopyLArmorMaterials.EMERALD, ArmorItem.Type.BOOTS, false);

    public static final RegistryObject<Item> MOTH_HELMET = armor("moth_helmet", CopyLArmorMaterials.MOTH, ArmorItem.Type.HELMET, false);
    public static final RegistryObject<Item> MOTH_CHESTPLATE = armor("moth_chestplate", CopyLArmorMaterials.MOTH, ArmorItem.Type.CHESTPLATE, false);
    public static final RegistryObject<Item> MOTH_LEGGINGS = armor("moth_leggings", CopyLArmorMaterials.MOTH, ArmorItem.Type.LEGGINGS, false);
    public static final RegistryObject<Item> MOTH_BOOTS = armor("moth_boots", CopyLArmorMaterials.MOTH, ArmorItem.Type.BOOTS, false);

    public static final RegistryObject<Item> ULTIMATE_HELMET = armor("ultimate_helmet", CopyLArmorMaterials.ULTIMATE, ArmorItem.Type.HELMET, true);
    public static final RegistryObject<Item> ULTIMATE_CHESTPLATE = armor("ultimate_chestplate", CopyLArmorMaterials.ULTIMATE, ArmorItem.Type.CHESTPLATE, true);
    public static final RegistryObject<Item> ULTIMATE_LEGGINGS = armor("ultimate_leggings", CopyLArmorMaterials.ULTIMATE, ArmorItem.Type.LEGGINGS, true);
    public static final RegistryObject<Item> ULTIMATE_BOOTS = armor("ultimate_boots", CopyLArmorMaterials.ULTIMATE, ArmorItem.Type.BOOTS, true);

    private static RegistryObject<Item> armor(String id, CopyLArmorMaterials material, ArmorItem.Type type, boolean ultimate) {
        return ModRegistries.ITEMS.register(id, () -> ultimate
                ? new UltimateArmorItem(material, type, new Item.Properties())
                : new ArmorItem(material, type, new Item.Properties()));
    }

    public static void bootstrap() {
    }

    private ModArmor() {
    }
}
