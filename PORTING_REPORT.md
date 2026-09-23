# PORTING_REPORT.md

## Checkpoint: Fase 3 integrada

Este checkpoint une el source real de **CopyL 3.3.0** con la primera base gameplay del
OreSpawn 1.12.2 suministrado, usando Forge 1.20.1.

### Trabajo recuperado

El avance de Claude se conservó cuando era correcto (estructura `common`, registries y algunos
bloques), pero se contrastó contra el source limpio de CopyL y el JAR real de OreSpawn. El
paquete `client/` se restauró desde `Lclient-main.zip` para no acumular cambios accidentales.

### Correcciones sobre el avance anterior

- Corregido el constructor de `TorchBlock` a la firma 1.20.1.
- Eliminado el falso patrón `Class#getName()` para forzar registries; ahora se inicializan las
  clases de contenido explícitamente antes de registrar los `DeferredRegister`.
- Eliminado `basic_stone`: clase presente en JAR, pero no registrada por `ModBlocks` original.
- Eliminado XP inventado de Titanium/Uranium/Amethyst ores; el original usa XP 0.4 en smelting
  de Titanium/Uranium ore.
- Sustituidos hardness/resistance/harvest/light estimados por valores recuperados del bytecode.
- Corregida la falsa ausencia de chest textures Emerald/Ultimate: existen con nombres
  `emerald_chest.png` y `ultimate_chest.png`.
- Conteos corregidos: 15 tools y 32 creature `*_ore` registrados.

### Implementado en Fase 3

- 7 bloques registrados (`extreme_wall_torch` es variante técnica adicional sin BlockItem).
- 6 materiales/items base.
- 15 herramientas con tiers reales.
- 16 piezas de armadura con materiales reales.
- Auto-enchantments originales de Ultimate y Emerald Pickaxe.
- Ultimate Hoe 3×3 adaptada a BlockState/Level moderno.
- Comportamiento de partículas/reactividad de ores Titanium/Uranium.
- Partículas ocasionales de Titanium/Uranium storage blocks.
- Extreme Torch moderna con variante standing/wall.
- 41 recipes, 7 loot tables y 19 tags.
- 52 PNG de Fase 3 migrados al namespace `copyl`.
- modelos/blockstates/item models/lang necesarios para el contenido actual.

### Validación estática

- 135 JSON parseados: 0 errores.
- referencias `copyl:` de models/textures: 0 faltantes.
- referencias `copyl:` en recipes: 0 IDs desconocidos.
- todos los IDs jugables de Fase 3 tienen item model esperado.

### Limitación de validación

No se afirma `BUILD SUCCESSFUL`: los archivos fuente suministrados no incluyen
`gradle/wrapper/gradle-wrapper.jar` y este entorno no puede descargar Gradle/Forge/GeckoLib.
El proyecto sí queda listo para compilar con Gradle 8.1.1 + Java 17 en un entorno con red.

### Decisiones de fidelidad

- Se mantiene la falta de recipe original de `amethyst_shovel` hasta encontrar evidencia de
  que deba existir.
- Se añade nombre legible a `amethyst_hoe` aunque el lang original lo omite.
- El bug interno donde `AmethystTools` se llama `emerald` no se replica porque no aporta
  comportamiento jugable en 1.20.1.
- Los creature `*_ore` no entran todavía: deben portarse junto a `OreGenericEgg`/entidades.

### Código legacy excluido

`util/premium` no se porta: `CheckUser()` retorna siempre `true` e `Init()` está vacío en el JAR
suministrado. Mantenerlo solo añadiría código muerto y una dependencia conceptual innecesaria.

### Próximo bloque técnico

Fase 4 debe comenzar por el pipeline entidad ↔ huevo ↔ creature ore ↔ jaula y solo después
registrar criaturas por grupos. Esto evita crear 32 bloques incompletos desconectados de su
mecánica original.


## 2026-09-23 continuation checkpoint

Continued from the existing Phase 3 project, without restarting the port. Added the complete 32-block `OreGenericEgg` set found in the supplied JAR and preserved its original XP behavior. Ported the procedural `GenericDungeon` layout and kept its Mining Dimension generation rule isolated rather than moving it to the Overworld. `WorldGenStructure` was translated to the 1.20.1 structure-template API; no NBT templates were added because none exist in the supplied JAR.

The Extreme Torch visual bug reported during testing was traced to model/UV differences between Minecraft 1.12.2 and 1.20.1. The original PNG was correct; the block and wall models were replaced with a direct translation of the 1.12.2 torch geometry while retaining maximum light level 15.

Next dependency chain: finish the entity base and the three dungeon-spawner creatures (`Alien`, `GammaMetroid`, `Cryolophosaurus`), then wire the dungeon loot table once the original creature eggs and cage items it references exist. The original loot table has deliberately not been simplified.
