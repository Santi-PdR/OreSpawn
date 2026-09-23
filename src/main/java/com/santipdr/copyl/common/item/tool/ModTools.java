package com.santipdr.copyl.common.item.tool;

import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

/** Los 15 tools del OreSpawn original, con IDs originales. */
public final class ModTools {
    // Amethyst
    public static final RegistryObject<Item> AMETHYST_SWORD = reg("amethyst_sword", () -> new SwordItem(CopyLToolTiers.AMETHYST, 3, -2.4F, props()));
    public static final RegistryObject<Item> AMETHYST_PICKAXE = reg("amethyst_pickaxe", () -> new PickaxeItem(CopyLToolTiers.AMETHYST, 1, -2.8F, props()));
    public static final RegistryObject<Item> AMETHYST_AXE = reg("amethyst_axe", () -> new AxeItem(CopyLToolTiers.AMETHYST, CopyLToolTiers.AMETHYST.getAttackDamageBonus(), -3.0F, props()));
    public static final RegistryObject<Item> AMETHYST_SHOVEL = reg("amethyst_shovel", () -> new ShovelItem(CopyLToolTiers.AMETHYST, 1.5F, -3.0F, props()));
    public static final RegistryObject<Item> AMETHYST_HOE = reg("amethyst_hoe", () -> new HoeItem(CopyLToolTiers.AMETHYST, -2, -1.0F, props()));

    // Emerald
    public static final RegistryObject<Item> EMERALD_SWORD = reg("emerald_sword", () -> new SwordItem(CopyLToolTiers.EMERALD, 3, -2.4F, props()));
    public static final RegistryObject<Item> EMERALD_PICKAXE = reg("emerald_pickaxe", () -> new AutoEnchantingTools.EmeraldPickaxe(CopyLToolTiers.EMERALD, props()));
    public static final RegistryObject<Item> EMERALD_AXE = reg("emerald_axe", () -> new AxeItem(CopyLToolTiers.EMERALD, CopyLToolTiers.EMERALD.getAttackDamageBonus(), -3.0F, props()));
    public static final RegistryObject<Item> EMERALD_SHOVEL = reg("emerald_shovel", () -> new ShovelItem(CopyLToolTiers.EMERALD, 1.5F, -3.0F, props()));
    public static final RegistryObject<Item> EMERALD_HOE = reg("emerald_hoe", () -> new HoeItem(CopyLToolTiers.EMERALD, -2, -1.0F, props()));

    // Ultimate
    public static final RegistryObject<Item> ULTIMATE_SWORD = reg("ultimate_sword", () -> new AutoEnchantingTools.UltimateSword(CopyLToolTiers.ULTIMATE, props()));
    public static final RegistryObject<Item> ULTIMATE_PICKAXE = reg("ultimate_pickaxe", () -> new AutoEnchantingTools.UltimatePickaxe(CopyLToolTiers.ULTIMATE, props()));
    public static final RegistryObject<Item> ULTIMATE_AXE = reg("ultimate_axe", () -> new AutoEnchantingTools.UltimateAxe(CopyLToolTiers.ULTIMATE, props()));
    public static final RegistryObject<Item> ULTIMATE_SHOVEL = reg("ultimate_shovel", () -> new AutoEnchantingTools.UltimateShovel(CopyLToolTiers.ULTIMATE, props()));
    public static final RegistryObject<Item> ULTIMATE_HOE = reg("ultimate_hoe", () -> new UltimateHoeItem(CopyLToolTiers.ULTIMATE, props()));

    private static Item.Properties props() {
        return new Item.Properties();
    }

    private static RegistryObject<Item> reg(String id, java.util.function.Supplier<? extends Item> supplier) {
        return ModRegistries.ITEMS.register(id, supplier);
    }

    public static void bootstrap() {
    }

    private ModTools() {
    }
}
