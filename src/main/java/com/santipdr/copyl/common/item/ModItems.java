package com.santipdr.copyl.common.item;

import com.santipdr.copyl.common.block.ModBlocks;
import com.santipdr.copyl.common.entity.ModEntities;
import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final RegistryObject<Item> TITANIUM_ORE = blockItem("titanium_ore", ModBlocks.TITANIUM_ORE);
    public static final RegistryObject<Item> URANIUM_ORE = blockItem("uranium_ore", ModBlocks.URANIUM_ORE);
    public static final RegistryObject<Item> AMETHYST_ORE = blockItem("amethyst_ore", ModBlocks.AMETHYST_ORE);
    public static final RegistryObject<Item> TITANIUM_BLOCK = blockItem("titanium_block", ModBlocks.TITANIUM_BLOCK);
    public static final RegistryObject<Item> URANIUM_BLOCK = blockItem("uranium_block", ModBlocks.URANIUM_BLOCK);
    public static final RegistryObject<Item> AMETHYST_BLOCK = blockItem("amethyst_block", ModBlocks.AMETHYST_BLOCK);
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

    public static final RegistryObject<Item> CRYOLOPHOSAURUS_EGG = ModRegistries.ITEMS.register("cryolophosaurus_egg", () -> new CreatureEggItem(ModEntities.CRYOLOPHOSAURUS));
    public static final RegistryObject<Item> GAMMAMETROID_EGG = ModRegistries.ITEMS.register("gammametroid_egg", () -> new CreatureEggItem(ModEntities.GAMMA_METROID));
    public static final RegistryObject<Item> ALIEN_EGG = ModRegistries.ITEMS.register("alien_egg", () -> new CreatureEggItem(ModEntities.ALIEN));
    public static final RegistryObject<Item> ALOSAURUS_EGG = ModRegistries.ITEMS.register("alosaurus_egg", () -> new CreatureEggItem(ModEntities.ALOSAURUS));
    public static final RegistryObject<Item> BARYONYX_EGG = ModRegistries.ITEMS.register("baryonyx_egg", () -> new CreatureEggItem(ModEntities.BARYONYX));
    public static final RegistryObject<Item> BEAVER_EGG = ModRegistries.ITEMS.register("beaver_egg", () -> new CreatureEggItem(ModEntities.BEAVER));
    public static final RegistryObject<Item> BIRD_EGG = ModRegistries.ITEMS.register("bird_egg", () -> new CreatureEggItem(ModEntities.BIRD));
    public static final RegistryObject<Item> BUTTERFLY_EGG = ModRegistries.ITEMS.register("butterfly_egg", () -> new CreatureEggItem(ModEntities.BUTTERFLY));
    public static final RegistryObject<Item> CASSOWARY_EGG = ModRegistries.ITEMS.register("cassowary_egg", () -> new CreatureEggItem(ModEntities.CASSOWARY));
    public static final RegistryObject<Item> CAMARASAURUS_EGG = ModRegistries.ITEMS.register("camarasaurus_egg", () -> new CreatureEggItem(ModEntities.CAMARASAURUS));
    public static final RegistryObject<Item> CAVEFISHER_EGG = ModRegistries.ITEMS.register("cavefisher_egg", () -> new CreatureEggItem(ModEntities.CAVE_FISHER));
    public static final RegistryObject<Item> DRAGONFLY_EGG = ModRegistries.ITEMS.register("dragonfly_egg", () -> new CreatureEggItem(ModEntities.DRAGONFLY));
    public static final RegistryObject<Item> POINTYSAURUS_EGG = ModRegistries.ITEMS.register("pointysaurus_egg", () -> new CreatureEggItem(ModEntities.POINTYSAURUS));
    public static final RegistryObject<Item> FIREFLY_EGG = ModRegistries.ITEMS.register("firefly_egg", () -> new CreatureEggItem(ModEntities.FIREFLY));
    public static final RegistryObject<Item> MOSQUITO_EGG = ModRegistries.ITEMS.register("mosquito_egg", () -> new CreatureEggItem(ModEntities.MOSQUITO));
    public static final RegistryObject<Item> MOTH_EGG = ModRegistries.ITEMS.register("moth_egg", () -> new CreatureEggItem(ModEntities.MOTH));
    public static final RegistryObject<Item> MANTIS_EGG = ModRegistries.ITEMS.register("mantis_egg", () -> new CreatureEggItem(ModEntities.MANTIS));
    public static final RegistryObject<Item> REDCOW_EGG = ModRegistries.ITEMS.register("redcow_egg", () -> new CreatureEggItem(ModEntities.RED_COW));
    public static final RegistryObject<Item> STINKBUG_EGG = ModRegistries.ITEMS.register("stinkbug_egg", () -> new CreatureEggItem(ModEntities.STINK_BUG));
    public static final RegistryObject<Item> RED_ANT_EGG = ModRegistries.ITEMS.register("red_ant_egg", () -> new CreatureEggItem(ModEntities.RED_ANT));
    public static final RegistryObject<Item> TERMITE_EGG = ModRegistries.ITEMS.register("termite_egg", () -> new CreatureEggItem(ModEntities.TERMITE));
    public static final RegistryObject<Item> SMALLWORM_EGG = ModRegistries.ITEMS.register("smallworm_egg", () -> new CreatureEggItem(ModEntities.SMALL_WORM));
    public static final RegistryObject<Item> MEDIUMWORM_EGG = ModRegistries.ITEMS.register("mediumworm_egg", () -> new CreatureEggItem(ModEntities.MEDIUM_WORM));
    public static final RegistryObject<Item> LARGEWORM_EGG = ModRegistries.ITEMS.register("largeworm_egg", () -> new CreatureEggItem(ModEntities.LARGE_WORM));
    public static final RegistryObject<Item> TREX_EGG = ModRegistries.ITEMS.register("trex_egg", () -> new CreatureEggItem(ModEntities.TREX));
    public static final RegistryObject<Item> VELOCITYRAPTOR_EGG = ModRegistries.ITEMS.register("velocityraptor_egg", () -> new CreatureEggItem(ModEntities.VELOCITY_RAPTOR));

    public static final RegistryObject<Item> EMPTY_CAGE = ModRegistries.ITEMS.register("empty_cage", CritterCageItem::empty);
    public static final RegistryObject<Item> GAMMAMETROID_CAGE = ModRegistries.ITEMS.register("gammametroid_cage", () -> new CritterCageItem(ModEntities.GAMMA_METROID, 0.4F));
    public static final RegistryObject<Item> ALIEN_CAGE = ModRegistries.ITEMS.register("alien_cage", () -> new CritterCageItem(ModEntities.ALIEN, 0.4F));
    public static final RegistryObject<Item> ALOSAURUS_CAGE = ModRegistries.ITEMS.register("alosaurus_cage", () -> new CritterCageItem(ModEntities.ALOSAURUS, 0.4F));
    public static final RegistryObject<Item> BARYONYX_CAGE = ModRegistries.ITEMS.register("baryonyx_cage", () -> new CritterCageItem(ModEntities.BARYONYX, 0.4F));
    public static final RegistryObject<Item> BEAVER_CAGE = ModRegistries.ITEMS.register("beaver_cage", () -> new CritterCageItem(ModEntities.BEAVER, 0.4F));
    public static final RegistryObject<Item> CASSOWARY_CAGE = ModRegistries.ITEMS.register("cassowary_cage", () -> new CritterCageItem(ModEntities.CASSOWARY, 0.4F));
    public static final RegistryObject<Item> CAMARASAURUS_CAGE = ModRegistries.ITEMS.register("camarasaurus_cage", () -> new CritterCageItem(ModEntities.CAMARASAURUS, 0.4F));
    public static final RegistryObject<Item> CAVEFISHER_CAGE = ModRegistries.ITEMS.register("cavefisher_cage", () -> new CritterCageItem(ModEntities.CAVE_FISHER, 0.4F));
    public static final RegistryObject<Item> DRAGONFLY_CAGE = ModRegistries.ITEMS.register("dragonfly_cage", () -> new CritterCageItem(ModEntities.DRAGONFLY, 0.4F));
    public static final RegistryObject<Item> FIREFLY_CAGE = ModRegistries.ITEMS.register("firefly_cage", () -> new CritterCageItem(ModEntities.FIREFLY, 0.4F));
    public static final RegistryObject<Item> MANTIS_CAGE = ModRegistries.ITEMS.register("mantis_cage", () -> new CritterCageItem(ModEntities.MANTIS, 0.4F));
    public static final RegistryObject<Item> REDCOW_CAGE = ModRegistries.ITEMS.register("redcow_cage", () -> new CritterCageItem(ModEntities.RED_COW, 0.4F));
    public static final RegistryObject<Item> STINKBUG_CAGE = ModRegistries.ITEMS.register("stinkbug_cage", () -> new CritterCageItem(ModEntities.STINK_BUG, 0.4F));
    public static final RegistryObject<Item> SMALLWORM_CAGE = ModRegistries.ITEMS.register("smallworm_cage", () -> new CritterCageItem(ModEntities.SMALL_WORM, 0.4F));
    public static final RegistryObject<Item> MEDIUMWORM_CAGE = ModRegistries.ITEMS.register("mediumworm_cage", () -> new CritterCageItem(ModEntities.MEDIUM_WORM, 0.4F));
    public static final RegistryObject<Item> LARGEWORM_CAGE = ModRegistries.ITEMS.register("largeworm_cage", () -> new CritterCageItem(ModEntities.LARGE_WORM, 0.4F));
    public static final RegistryObject<Item> TREX_CAGE = ModRegistries.ITEMS.register("trex_cage", () -> new CritterCageItem(ModEntities.TREX, 0.4F));
    public static final RegistryObject<Item> VELOCITYRAPTOR_CAGE = ModRegistries.ITEMS.register("velocityraptor_cage", () -> new CritterCageItem(ModEntities.VELOCITY_RAPTOR, 0.4F));

    public static final RegistryObject<Item> MANTIS_CLAW = ModRegistries.ITEMS.register("mantis_claw", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREX_TOOTH = ModRegistries.ITEMS.register("trextooth", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANT_BLOCK = blockItem("ant_block", ModBlocks.ANT_BLOCK);
    public static final RegistryObject<Item> EXTREME_TORCH = ModRegistries.ITEMS.register("extreme_torch", () -> new StandingAndWallBlockItem(ModBlocks.EXTREME_TORCH.get(), ModBlocks.EXTREME_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));

    private static RegistryObject<Item> blockItem(String id, RegistryObject<? extends net.minecraft.world.level.block.Block> block) {
        return ModRegistries.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void bootstrap() {}
    private ModItems() {}
}
