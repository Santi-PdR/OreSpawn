# PORTING_PLAN.md

## Objetivo

Un único mod Forge 1.20.1/Java 17 con modid **`copyl`**. El paquete client-side original de
CopyL se conserva; OreSpawn se porta como contenido `common`/server y todos sus IDs pasan de
`orespawn:*` a `copyl:*`.

## Baselines reales

- CopyL source: `Lclient-main.zip` (3.3.0).
- OreSpawn: `Orespawn-1.12.2-V0.8-ConquerantFix.jar`.
- Forge objetivo: 1.20.1 / 47.4.10.
- Build: Gradle 8.1.1, Java toolchain 17.

## Arquitectura

```text
com.santipdr.copyl
├── client/                 # CopyL original; macros/keybinds/screens
└── common/
    ├── registry/           # DeferredRegister centralizados
    ├── block/              # bloques portados
    ├── item/
    │   ├── material/
    │   ├── tool/
    │   └── armor/
    ├── entity/             # Fase 4
    ├── blockentity/        # Fase 6
    ├── worldgen/           # Fase 5
    ├── structure/          # Fase 5
    └── network/            # cuando una mecánica necesite sync explícito
```

## Reglas del port

1. Entender el bytecode/comportamiento 1.12.2 antes de reimplementar.
2. No conservar APIs legacy mediante wrappers falsos: usar APIs nativas 1.20.1.
3. Reusar assets originales suministrados cuando son compatibles.
4. No inventar valores si se pueden recuperar de bytecode/assets/configs.
5. No transformar entidades complejas en mobs genéricos para “completar” una checklist.
6. Cliente/servidor separados; gameplay importante server-authoritative.
7. Cada fase actualiza inventario/status/report y pasa validación de resources.

## Mapeo API 1.12.2 → 1.20.1

| 1.12.2 | 1.20.1 |
|---|---|
| `GameRegistry` / `EntityRegistry` | `DeferredRegister` + `RegistryObject` |
| `TileEntity` | `BlockEntity` |
| `Container` | `AbstractContainerMenu` |
| `GuiScreen` | `Screen` |
| `IBlockState` / metadata | `BlockState` + properties / IDs separados |
| IDs numéricos | `ResourceLocation` |
| `OreDictionary` | tags Minecraft/Forge |
| `EntityAIBase` / custom AI | `Goal`/`TargetGoal` o implementación moderna equivalente |
| `WorldProvider` legacy | dimension/biome/worldgen registries + datapack JSON |
| recipes/smelting en Java | `data/copyl/recipes/*.json` / datagen |
| `ModelBase`/`ModelRenderer` legacy | model/render moderno; GeckoLib solo donde sea útil |
| comando legacy | Brigadier/Forge command registration |

## Namespace

Regla general: `orespawn:<id>` → `copyl:<id>`. Los modelos, texturas, loot, recipes, sonidos y
referencias internas se reescriben al nuevo namespace.

Ejemplos ya aplicados en Fase 3:

| Original | Port |
|---|---|
| `orespawn:titanium_ore` | `copyl:titanium_ore` |
| `orespawn:uranium_ore` | `copyl:uranium_ore` |
| `orespawn:amethyst_ore` | `copyl:amethyst_ore` |
| `orespawn:ultimate_sword` | `copyl:ultimate_sword` |
| `orespawn:moth_chestplate` | `copyl:moth_chestplate` |
| `orespawn:extreme_torch` | `copyl:extreme_torch` |

## GeckoLib

Permitida como dependencia externa. No se usa todavía en Fase 3. En Fase 4 se decidirá por
entidad según el modelo/animación real; no se obligará a todas las criaturas a depender de
GeckoLib si el renderer/modelo moderno estándar es suficiente.

## Orden

- **Fase 3**: base/materiales/tools/armor/resources — cerrada a nivel de source/resources.
- **Fase 4**: entidades + creature ores/eggs/cages + AI + render.
- **Fase 5**: worldgen, Mining Dimension, biome, structures.
- **Fase 6**: colonias, plantas/BlockEntity, command y otras mecánicas especiales.
- **Fase 7**: auditoría global de assets (los assets se van incorporando desde fases anteriores).
- **Fase 8**: integración y eliminación de duplicación/dead code.
- **Fase 9**: client/server/multiplayer testing.
- **Fase 10**: limpieza y release.

## Primer objetivo de Fase 4

Antes de portar mobs al azar: decompilar/estudiar `OreGenericEgg`, `ItemGenericEgg`,
`MyEntityRegistry`, `ModEntities`, `EntitySpawns` y el sistema de jaulas. Los **32 creature
`*_ore`** dependen de esa lógica y no deben registrarse como simples ores vanilla.

## Código premium legacy

`danger.orespawn.util.premium` fue inspeccionado: en el JAR suministrado el checker está anulado
y siempre acepta al jugador. No se porta; no es necesario para ninguna mecánica.


### Immediate next work after 2026-09-23 checkpoint
1. Port/register the entity base needed by Generic Dungeon.
2. Fully port Alien, Gamma Metroid and Cryolophosaurus before treating the dungeon spawner as complete.
3. Port ItemGenericEgg + original shapeless egg recipes for the 32 dried creature blocks.
4. Port CritterCage/EntityCage and then enable the unmodified Generic Dungeon loot contents.
5. Continue remaining entities/bosses/projectiles/plants/items.

Mining Dimension remains deferred by user; do not hook `StructureGenerator` to Overworld.
