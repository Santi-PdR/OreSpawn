package com.santipdr.copyl.common.item;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.RegistryObject;

/** BlockItems y sistemas de items portados de OreSpawn. */
public final class ModItems {
    public static final RegistryObject<Item> TITANIUM_ORE = blockItem("titanium_ore", ModBlocks.TITANIUM_ORE);
    public static final RegistryObject<Item> URANIUM_ORE = blockItem("uranium_ore", ModBlocks.URANIUM_ORE);
    public static final RegistryObject<Item> AMETHYST_ORE = blockItem("amethyst_ore", ModBlocks.AMETHYST_ORE);
    public static final RegistryObject<Item> TITANIUM_BLOCK = blockItem("titanium_block", ModBlocks.TITANIUM_BLOCK);
    public static final RegistryObject<Item> URANIUM_BLOCK = blockItem("uranium_block", ModBlocks.URANIUM_BLOCK);
    public static final RegistryObject<Item> AMETHYST_BLOCK = blockItem("amethyst_block", ModBlocks.AMETHYST_BLOCK);

    // BlockItems de los 32 OreGenericEgg/creature ores del JAR.
    public static final RegistryObject<Item> ALIEN_ORE = blockItem("alien_ore", ModBlocks.ALIEN_ORE);
    public static final RegistryObject<Item> ALOSAURUS_ORE = blockItem("alosaurus_ore", ModBlocks.ALOSAURUS_ORE);
    public static final RegistryObject<Item> BARYONYX_ORE = blockItem("baryonyx_ore", ModBlocks.BARYONYX_ORE);
    public static final RegistryObject<Item> BEAVER_ORE = blockItem("beaver_ore", ModBlocks.BEAVER_ORE);
    public static final RegistryObject<Item> BIRD_ORE = blockItem("bird_ore", ModBlocks.BIRD_ORE);
    public static final RegistryObject<Item> BRUTALFLY_ORE = blockItem("brutalfly_ore", ModBlocks.BRUTALFLY_ORE);
    public static final RegistryObject<Item> CAMARASAURUS_ORE = blockItem("camarasaurus_ore", ModBlocks.CAMARASAURUS_ORE);
    public static final RegistryObject<Item> CASSOWARY_ORE = blockItem("cassowary_ore", ModBlocks.CASSOWARY_ORE);
    public static final RegistryObject<Item> CAVEFISHER_ORE = blockItem("cavefisher_ore", ModBlocks.CAVEFISHER_ORE);
    public static final RegistryObject<Item> COW_ORE = blockItem("cow_ore", ModBlocks.COW_ORE);
    public static final RegistryObject<Item> CREEPER_ORE = blockItem("creeper_ore", ModBlocks.CREEPER_ORE);
    public static final RegistryObject<Item> CRYOLOPHOSAURUS_ORE = blockItem("cryolophosaurus_ore", ModBlocks.CRYOLOPHOSAURUS_ORE);
    public static final RegistryObject<Item> DOOMWORM_ORE = blockItem("doomworm_ore", ModBlocks.DOOMWORM_ORE);
    public static final RegistryObject<Item> DRAGONFLY_ORE = blockItem("dragonfly_ore", ModBlocks.DRAGONFLY_ORE);
    public static final RegistryObject<Item> GAMMAMETROID_ORE = blockItem("gammametroid_ore", ModBlocks.GAMMAMETROID_ORE);
    public static final RegistryObject<Item> GHAST_ORE = blockItem("ghast_ore", ModBlocks.GHAST_ORE);
    public static final RegistryObject<Item> HORSE_ORE = blockItem("horse_ore", ModBlocks.HORSE_ORE);
    public static final RegistryObject<Item> KYUUBI_ORE = blockItem("kyuubi_ore", ModBlocks.KYUUBI_ORE);
    public static final RegistryObject<Item> LARGEWORM_ORE = blockItem("largeworm_ore", ModBlocks.LARGEWORM_ORE);
    public static final RegistryObject<Item> MANTIS_ORE = blockItem("mantis_ore", ModBlocks.MANTIS_ORE);
    public static final RegistryObject<Item> MEDIUMWORM_ORE = blockItem("mediumworm_ore", ModBlocks.MEDIUMWORM_ORE);
    public static final RegistryObject<Item> MOTHRA_ORE = blockItem("mothra_ore", ModBlocks.MOTHRA_ORE);
    public static final RegistryObject<Item> NASTYSAURUS_ORE = blockItem("nastysaurus_ore", ModBlocks.NASTYSAURUS_ORE);
    public static final RegistryObject<Item> PIG_ORE = blockItem("pig_ore", ModBlocks.PIG_ORE);
    public static final RegistryObject<Item> POINTYSAURUS_ORE = blockItem("pointysaurus_ore", ModBlocks.POINTYSAURUS_ORE);
    public static final RegistryObject<Item> REDCOW_ORE = blockItem("redcow_ore", ModBlocks.REDCOW_ORE);
    public static final RegistryObject<Item> SMALLWORM_ORE = blockItem("smallworm_ore", ModBlocks.SMALLWORM_ORE);
    public static final RegistryObject<Item> SPYRO_ORE = blockItem("spyro_ore", ModBlocks.SPYRO_ORE);
    public static final RegistryObject<Item> STINKBUG_ORE = blockItem("stinkbug_ore", ModBlocks.STINKBUG_ORE);
    public static final RegistryObject<Item> TREX_ORE = blockItem("trex_ore", ModBlocks.TREX_ORE);
    public static final RegistryObject<Item> VELOCITYRAPTOR_ORE = blockItem("velocityraptor_ore", ModBlocks.VELOCITYRAPTOR_ORE);
    public static final RegistryObject<Item> ZOMBIE_ORE = blockItem("zombie_ore", ModBlocks.ZOMBIE_ORE);

    // ItemGenericEgg: solo se habilitan los huevos de las entidades ya portadas.
    public static final RegistryObject<Item> CRYOLOPHOSAURUS_EGG = ModRegistries.ITEMS.register(
            "cryolophosaurus_egg", () -> new CreatureEggItem(ModEntities.CRYOLOPHOSAURUS));
    public static final RegistryObject<Item> GAMMAMETROID_EGG = ModRegistries.ITEMS.register(
            "gammametroid_egg", () -> new CreatureEggItem(ModEntities.GAMMA_METROID));
    public static final RegistryObject<Item> ALIEN_EGG = ModRegistries.ITEMS.register(
            "alien_egg", () -> new CreatureEggItem(ModEntities.ALIEN));

    // CritterCage original: jaula vacía + jaulas de entidades ya portadas.
    public static final RegistryObject<Item> EMPTY_CAGE = ModRegistries.ITEMS.register(
            "empty_cage", CritterCageItem::empty);
    public static final RegistryObject<Item> GAMMAMETROID_CAGE = ModRegistries.ITEMS.register(
            "gammametroid_cage", () -> new CritterCageItem(ModEntities.GAMMA_METROID, 0.4F));
    public static final RegistryObject<Item> ALIEN_CAGE = ModRegistries.ITEMS.register(
            "alien_cage", () -> new CritterCageItem(ModEntities.ALIEN, 0.4F));

    public static final RegistryObject<Item> EXTREME_TORCH = ModRegistries.ITEMS.register(
            "extreme_torch",
            () -> new StandingAndWallBlockItem(
                    ModBlocks.EXTREME_TORCH.get(),
                    ModBlocks.EXTREME_WALL_TORCH.get(),
                    new Item.Properties(),
                    Direction.DOWN
            )
    );

    private static RegistryObject<Item> blockItem(String id, RegistryObject<? extends net.minecraft.world.level.block.Block> block) {
        return ModRegistries.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void bootstrap() {
    }

    private ModItems() {
    }
}
