package com.santipdr.copyl.common.item.material;

import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

/** Materiales base recuperados del OreSpawn 1.12.2 original. */
public final class ModMaterialItems {
    public static final RegistryObject<Item> TITANIUM_NUGGET = item("titanium_nugget");
    public static final RegistryObject<Item> URANIUM_NUGGET = item("uranium_nugget");
    public static final RegistryObject<Item> TITANIUM_INGOT = item("titanium_ingot");
    public static final RegistryObject<Item> URANIUM_INGOT = item("uranium_ingot");
    public static final RegistryObject<Item> AMETHYST = item("amethyst");

    // Drop real del Moth y material de su set de armadura. La entidad llega en Fase 4.
    public static final RegistryObject<Item> MOTH_SCALE = item("moth_scale");
    public static final RegistryObject<Item> WORM_TOOTH = item("worm_tooth");
    public static final RegistryObject<Item> WORM_FOOD = item("worm_food");

    private static RegistryObject<Item> item(String id) {
        return ModRegistries.ITEMS.register(id, () -> new Item(new Item.Properties()));
    }

    public static void bootstrap() {
    }

    private ModMaterialItems() {
    }
}
