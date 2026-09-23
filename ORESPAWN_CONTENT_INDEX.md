# ORESPAWN_CONTENT_INDEX.md

Inventario reconstruido desde el JAR real `Orespawn-1.12.2-V0.8-ConquerantFix.jar` mediante
listado del JAR + `javap`/bytecode. El JAR contiene **191 clases compiladas**, **1131 PNG**,
**54 OGG** y **265 JSON**. Modid original: `orespawn`; package raíz: `danger.orespawn`.

> Este archivo distingue contenido realmente registrado por `ModBlocks`/`ModItems` de clases
> auxiliares presentes en el JAR. No se considera contenido jugable algo solo por existir una
> clase con ese nombre.

## Base registrada en `ModBlocks`

### Bloques/materiales y sistemas no-creature-ore
- [ ] `corn_plant`
- [ ] `butterfly_plant`
- [ ] `mosquito_plant`
- [ ] `firefly_plant`
- [ ] `moth_plant`
- [ ] `uranium_block`
- [ ] `titanium_block`
- [ ] `uranium_ore`
- [ ] `titanium_ore`
- [ ] `ant_block`
- [ ] `extreme_torch`
- [ ] `red_ant_troll_block`
- [ ] `amethyst_ore`
- [ ] `amethyst_block`

### Bloques `*_ore` asociados a criaturas — 32 registrados

Estos **no se tratarán como minerales normales** hasta terminar de portar/analizar
`OreGenericEgg`: forman parte del mecanismo de aparición/obtención de criaturas.

- [ ] `alosaurus_ore`
- [ ] `baryonyx_ore`
- [ ] `camarasaurus_ore`
- [ ] `cryolophosaurus_ore`
- [ ] `pointysaurus_ore`
- [ ] `trex_ore`
- [ ] `cow_ore`
- [ ] `creeper_ore`
- [ ] `ghast_ore`
- [ ] `horse_ore`
- [ ] `pig_ore`
- [ ] `zombie_ore`
- [ ] `bird_ore`
- [ ] `alien_ore`
- [ ] `cavefisher_ore`
- [ ] `nastysaurus_ore`
- [ ] `velocityraptor_ore`
- [ ] `wtf_ore`
- [ ] `spyro_ore`
- [ ] `dragonfly_ore`
- [ ] `smallworm_ore`
- [ ] `mediumworm_ore`
- [ ] `largeworm_ore`
- [ ] `doomworm_ore`
- [ ] `mantis_ore`
- [ ] `beaver_ore`
- [ ] `brutalfly_ore`
- [ ] `kyuubi_ore`
- [ ] `mothra_ore`
- [ ] `cassowary_ore`
- [ ] `redcow_ore`
- [ ] `stinkbug_ore`

## Items registrados por `ModItems` — 105

El registro original incluye materiales, herramientas, armaduras, comida/semillas, huevos,
jaulas y drops especiales. Lista exacta detectada:

- [ ] `uranium_nugget`
- [ ] `titanium_nugget`
- [ ] `uranium_ingot`
- [ ] `titanium_ingot`
- [ ] `trex_tooth`
- [ ] `empty_cage`
- [ ] `alosaurus_cage`
- [ ] `trex_cage`
- [ ] `cow_cage`
- [ ] `creeper_cage`
- [ ] `ghast_cage`
- [ ] `horse_cage`
- [ ] `pig_cage`
- [ ] `zombie_cage`
- [ ] `gammametroid_cage`
- [ ] `spyro_cage`
- [ ] `dragonfly_cage`
- [ ] `firefly_cage`
- [ ] `nastysaurus_cage`
- [ ] `alien_cage`
- [ ] `velocityraptor_cage`
- [ ] `ultimate_helmet`
- [ ] `ultimate_chestplate`
- [ ] `ultimate_leggings`
- [ ] `ultimate_boots`
- [ ] `emerald_helmet`
- [ ] `emerald_chestplate`
- [ ] `emerald_leggings`
- [ ] `emerald_boots`
- [ ] `ultimate_pickaxe`
- [ ] `ultimate_sword`
- [ ] `ultimate_axe`
- [ ] `ultimate_shovel`
- [ ] `ultimate_hoe`
- [ ] `emerald_pickaxe`
- [ ] `emerald_sword`
- [ ] `emerald_axe`
- [ ] `emerald_shovel`
- [ ] `emerald_hoe`
- [ ] `amethyst_pickaxe`
- [ ] `amethyst_sword`
- [ ] `amethyst_axe`
- [ ] `amethyst_shovel`
- [ ] `amethyst_hoe`
- [ ] `corn`
- [ ] `butterfly_seed`
- [ ] `mosquito_seed`
- [ ] `firefly_seed`
- [ ] `moth_seed`
- [ ] `alosaurus_egg`
- [ ] `baryonyx_egg`
- [ ] `camarasaurus_egg`
- [ ] `cryolophosaurus_egg`
- [ ] `pointysaurus_egg`
- [ ] `trex_egg`
- [ ] `cavefisher_egg`
- [ ] `butterfly_egg`
- [ ] `bird_egg`
- [ ] `red_ant_egg`
- [ ] `gammametroid_egg`
- [ ] `spyro_egg`
- [ ] `dragonfly_egg`
- [ ] `firefly_egg`
- [ ] `mosquito_egg`
- [ ] `nastysaurus_egg`
- [ ] `alien_egg`
- [ ] `velocityraptor_egg`
- [ ] `worm_tooth`
- [ ] `worm_food`
- [ ] `smallworm_egg`
- [ ] `mediumworm_egg`
- [ ] `largeworm_egg`
- [ ] `doomworm_egg`
- [ ] `smallworm_cage`
- [ ] `mediumworm_cage`
- [ ] `largeworm_cage`
- [ ] `moth_egg`
- [ ] `kyuubi_egg`
- [ ] `mantis_egg`
- [ ] `monthra_egg`
- [ ] `brutalfly_egg`
- [ ] `beaver_egg`
- [ ] `kyuubi_cage`
- [ ] `mantis_cage`
- [ ] `monthra_cage`
- [ ] `brutalfly_cage`
- [ ] `beaver_cage`
- [ ] `moth_scale`
- [ ] `mantis_claw`
- [ ] `moth_helmet`
- [ ] `moth_chestplate`
- [ ] `moth_leggings`
- [ ] `moth_boots`
- [ ] `amethyst_helmet`
- [ ] `amethyst_chestplate`
- [ ] `amethyst_leggings`
- [ ] `amethyst_boots`
- [ ] `amethyst`
- [ ] `termite_egg`
- [ ] `cassowary_egg`
- [ ] `redcow_egg`
- [ ] `stinkbug_egg`
- [ ] `redcow_cage`
- [ ] `stinkbug_cage`
- [ ] `cassowary_cage`

### Herramientas verificadas por bytecode

Tres tiers reales, 5 herramientas por tier = **15 tools**:

- `amethyst`: harvest 2, 1000 usos, speed 6.5, damage bonus 3, enchantability 12.
- `emerald`: harvest 2, 1000 usos, speed 6.5, damage bonus 3, enchantability 12.
- `ultimate`: harvest 10, 3000 usos, speed 15, damage bonus 36, enchantability 100.

Nota: en el bytecode original `AmethystTools` fue creado accidentalmente con el nombre interno
`"emerald"`; sus stats sí son los de Amethyst/Emerald y el bug de nombre no tiene utilidad en
1.20.1, por lo que no se replica.

### Armaduras verificadas por bytecode

Cuatro materiales/sets completos:

- `ultimate`: durability multiplier 200, protecciones boots/legs/chest/helmet `6/12/10/6`, enchant 100, toughness 3.
- `emerald`: multiplier 100, `3/8/6/3`, enchant 12, toughness 3.
- `moth`: multiplier 100, `2/7/5/2`, enchant 12, toughness 3.
- `amethyst`: multiplier 100, `4/8/7/3`, enchant 12, toughness 3.

## Entidades y soporte de Fase 4

El JAR contiene **39 clases directamente bajo `danger/orespawn/entity/`**, además de
**34 clases Render/RenderInfo/handler** y **28 clases Model**. También contiene 6 clases de IA
custom en `util/ai/`.

Familias/contenido detectado: Alien, Alosaurus, Ant/RedAnt, Baryonyx, Beaver, Bird,
Brutalfly, Butterfly, Camarasaurus, Cassowary, CaveFisher, Cryolophosaurus, Dragonfly,
EntityCage, Firefly, GammaMetroid, Kyuubi, Mantis, Mosquito, Moth, Mothra, Nastysaurus,
Pointysaurus, RedCow, Spyro, StinkBug, Termite, TRex, VelocityRaptor, WormSmall/Medium/Large/Doom,
BetterFireball y utilidades de targeting/registro.

IA legacy detectada:

- `MyEntityAITarget`
- `MyEntityAINearestAttackableTarget`
- `MyEntityAINearestAttackableTargetSorter`
- `MyEntityAIWander`
- `MyEntityAIWanderALot`
- `MyEntityAIFollowOwner`

## Mundo / sistemas posteriores

- Mining Dimension (`WorldProviderMiningDimension`, `ChunkGeneratorMiningDimension`).
- Bioma de Mining Dimension.
- `WorldGenOres`.
- `AntHillGenerator`.
- `GenericDungeon`, `StructureGenerator`, `WorldGenStructure`.
- Plantas Butterfly/Firefly/Mosquito/Moth + `TileEntityPlant`.
- Sistema Ant/RedAnt/Termite y bloques troll.
- `CommandDimensionTeleport`.
- `CraftingRecipes` y `SmeltingRecipes` legacy.

## Hallazgos que corrigen el inventario anterior

- `BlockBasic`/`OreBasicStone` existen como clases, pero **Basic Stone no está registrado en
  `ModBlocks`**. No se porta como bloque jugable en Fase 3.
- Las texturas de chestplate Emerald/Ultimate **sí existen**; los nombres reales son
  `emerald_chest.png` y `ultimate_chest.png`.
- Hay **32** creature `*_ore` registrados, no 39. El número 39 corresponde a clases del paquete
  de entidades, no a bloques `*_ore`.
- El OreSpawn original tiene 15 herramientas registradas, no 16.

## Assets

- 1131 PNG.
- 54 OGG.
- 265 JSON totales en el JAR.
- 4 recipe JSON legacy dentro del JAR, además de recetas registradas por Java.
- 2 loot-table JSON legacy detectados.
- Modelos, blockstates y lang presentes.

Los assets binarios se reutilizan cuando corresponden; modelos/JSON legacy se convierten o
regeneran bajo `assets/copyl` / `data/copyl` para 1.20.1.

## Paquete `util/premium`

Inspeccionado por bytecode: `PremiumChecker.CheckUser(...)` devuelve siempre `true` e `Init()`
retorna sin hacer nada en este build. No aporta gameplay y se excluye del port como código legacy
inactivo.
