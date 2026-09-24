package com.santipdr.copyl.common.block;

import com.santipdr.copyl.common.registry.ModRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

/** Bloques materiales de OreSpawn porteados con los valores originales verificados por bytecode. */
public final class ModBlocks {
    private static BlockBehaviour.Properties oreProperties() {
        // 1.12.2: ROCK, hardness 5, resistance 5, harvest level 2, light value 0.2.
        // 0.2 * 15 = 3 niveles de luz en el sistema discreto moderno.
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(5.0F, 5.0F)
                .lightLevel(state -> 3)
                .sound(SoundType.STONE);
    }


    private static BlockBehaviour.Properties creatureOreProperties() {
        // OreGenericEgg 1.12.2: ROCK, hardness 0.5, resistance 1.0, stone sound.
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(0.5F, 1.0F)
                .sound(SoundType.STONE);
    }

    private static BlockBehaviour.Properties storageProperties() {
        // 1.12.2: IRON, hardness 5, resistance 5, harvest level 2, light value 0.2.
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(5.0F, 5.0F)
                .lightLevel(state -> 3)
                .sound(SoundType.METAL);
    }

    public static final RegistryObject<Block> TITANIUM_ORE =
            ModRegistries.BLOCKS.register("titanium_ore", () -> new ReactiveOreBlock(oreProperties()));
    public static final RegistryObject<Block> URANIUM_ORE =
            ModRegistries.BLOCKS.register("uranium_ore", () -> new ReactiveOreBlock(oreProperties()));
    public static final RegistryObject<Block> AMETHYST_ORE =
            ModRegistries.BLOCKS.register("amethyst_ore", () -> new Block(oreProperties()));

    public static final RegistryObject<Block> TITANIUM_BLOCK =
            ModRegistries.BLOCKS.register("titanium_block", () -> new SparklingStorageBlock(storageProperties()));
    public static final RegistryObject<Block> URANIUM_BLOCK =
            ModRegistries.BLOCKS.register("uranium_block", () -> new SparklingStorageBlock(storageProperties()));
    public static final RegistryObject<Block> AMETHYST_BLOCK =
            ModRegistries.BLOCKS.register("amethyst_block", () -> new Block(storageProperties()));


    // Bloques OreGenericEgg (creature ores) presentes realmente en el JAR 1.12.2.
    public static final RegistryObject<Block> ALIEN_ORE = ModRegistries.BLOCKS.register("alien_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> ALOSAURUS_ORE = ModRegistries.BLOCKS.register("alosaurus_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> BARYONYX_ORE = ModRegistries.BLOCKS.register("baryonyx_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> BEAVER_ORE = ModRegistries.BLOCKS.register("beaver_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> BIRD_ORE = ModRegistries.BLOCKS.register("bird_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> BRUTALFLY_ORE = ModRegistries.BLOCKS.register("brutalfly_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> CAMARASAURUS_ORE = ModRegistries.BLOCKS.register("camarasaurus_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> CASSOWARY_ORE = ModRegistries.BLOCKS.register("cassowary_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> CAVEFISHER_ORE = ModRegistries.BLOCKS.register("cavefisher_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> COW_ORE = ModRegistries.BLOCKS.register("cow_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> CREEPER_ORE = ModRegistries.BLOCKS.register("creeper_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> CRYOLOPHOSAURUS_ORE = ModRegistries.BLOCKS.register("cryolophosaurus_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> DOOMWORM_ORE = ModRegistries.BLOCKS.register("doomworm_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> DRAGONFLY_ORE = ModRegistries.BLOCKS.register("dragonfly_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> GAMMAMETROID_ORE = ModRegistries.BLOCKS.register("gammametroid_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> GHAST_ORE = ModRegistries.BLOCKS.register("ghast_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> HORSE_ORE = ModRegistries.BLOCKS.register("horse_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> KYUUBI_ORE = ModRegistries.BLOCKS.register("kyuubi_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> LARGEWORM_ORE = ModRegistries.BLOCKS.register("largeworm_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> MANTIS_ORE = ModRegistries.BLOCKS.register("mantis_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> MEDIUMWORM_ORE = ModRegistries.BLOCKS.register("mediumworm_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> MOTHRA_ORE = ModRegistries.BLOCKS.register("mothra_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> NASTYSAURUS_ORE = ModRegistries.BLOCKS.register("nastysaurus_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> PIG_ORE = ModRegistries.BLOCKS.register("pig_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> POINTYSAURUS_ORE = ModRegistries.BLOCKS.register("pointysaurus_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> REDCOW_ORE = ModRegistries.BLOCKS.register("redcow_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> SMALLWORM_ORE = ModRegistries.BLOCKS.register("smallworm_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> SPYRO_ORE = ModRegistries.BLOCKS.register("spyro_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> STINKBUG_ORE = ModRegistries.BLOCKS.register("stinkbug_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> TREX_ORE = ModRegistries.BLOCKS.register("trex_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> VELOCITYRAPTOR_ORE = ModRegistries.BLOCKS.register("velocityraptor_ore", () -> new CreatureOreBlock(creatureOreProperties()));
    public static final RegistryObject<Block> ZOMBIE_ORE = ModRegistries.BLOCKS.register("zombie_ore", () -> new CreatureOreBlock(creatureOreProperties()));

    public static final RegistryObject<Block> BUTTERFLY_PLANT = ModRegistries.BLOCKS.register("butterfly_plant", () -> new InsectSpawnPlant(() -> com.santipdr.copyl.common.item.ModItems.BUTTERFLY_SEED.get(), () -> com.santipdr.copyl.common.entity.ModEntities.BUTTERFLY.get(), true, 7));
    public static final RegistryObject<Block> FIREFLY_PLANT = ModRegistries.BLOCKS.register("firefly_plant", () -> new InsectSpawnPlant(() -> com.santipdr.copyl.common.item.ModItems.FIREFLY_SEED.get(), () -> com.santipdr.copyl.common.entity.ModEntities.FIREFLY.get(), false, 6));
    public static final RegistryObject<Block> MOSQUITO_PLANT = ModRegistries.BLOCKS.register("mosquito_plant", () -> new InsectSpawnPlant(() -> com.santipdr.copyl.common.item.ModItems.MOSQUITO_SEED.get(), () -> com.santipdr.copyl.common.entity.ModEntities.MOSQUITO.get(), true, 6));
    public static final RegistryObject<Block> MOTH_PLANT = ModRegistries.BLOCKS.register("moth_plant", () -> new InsectSpawnPlant(() -> com.santipdr.copyl.common.item.ModItems.MOTH_SEED.get(), () -> com.santipdr.copyl.common.entity.ModEntities.MOTH.get(), false, 6));

    public static final RegistryObject<Block> RED_ANT_TROLL_BLOCK = ModRegistries.BLOCKS.register("red_ant_troll_block", RedAntTrollBlock::new);

    public static final RegistryObject<Block> ANT_BLOCK = ModRegistries.BLOCKS.register("ant_block", AntHillBlock::new);

    public static final RegistryObject<Block> EXTREME_TORCH = ModRegistries.BLOCKS.register(
            "extreme_torch",
            () -> new ExtremeTorchBlock(
                    BlockBehaviour.Properties.of()
                            .noCollission()
                            .instabreak()
                            .lightLevel(state -> 15)
                            .sound(SoundType.WOOD)
            )
    );

    public static final RegistryObject<Block> EXTREME_WALL_TORCH = ModRegistries.BLOCKS.register(
            "extreme_wall_torch",
            () -> new ExtremeWallTorchBlock(
                    BlockBehaviour.Properties.of()
                            .noCollission()
                            .instabreak()
                            .lightLevel(state -> 15)
                            .sound(SoundType.WOOD)
                            .lootFrom(EXTREME_TORCH)
            )
    );

    public static void bootstrap() {
    }

    private ModBlocks() {
    }
}
