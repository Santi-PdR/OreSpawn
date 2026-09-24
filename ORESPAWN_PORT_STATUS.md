# ORESPAWN_PORT_STATUS.md

`[x]` = implementado en el source actual. `[~]` = implementado pero pendiente de build/runtime
local. `[ ]` = fase todavía no portada.

## Fase 0–2 — recuperación, inventario y arquitectura

- [x] Recuperado el source real de CopyL 3.3.0 desde `Lclient-main.zip`.
- [x] Recuperado el avance parcial que dejó Claude y fusionado sin pisar el paquete `client/` original.
- [x] Analizado el JAR real de OreSpawn con `javap`/bytecode.
- [x] `copyl` convertido de client-only a mod mixto (`side=BOTH`, `MATCH_VERSION`).
- [x] `DeferredRegister` centralizados para blocks/items/entities/sounds/menus/creative tabs.
- [x] Namespace de todo el port fijado a `copyl:`.

## Fase 3 — base/materiales/herramientas/armaduras — CÓDIGO Y RESOURCES CERRADOS

### Bloques
- [x] `titanium_ore` — stats originales: hardness/resistance 5/5, iron-level mining, light 3.
- [x] `uranium_ore` — mismos stats originales.
- [x] `amethyst_ore` — mismos stats; loot moderno 1–2 amethysts con Silk Touch/Fortune.
- [x] `titanium_block`.
- [x] `uranium_block`.
- [x] `amethyst_block`.
- [x] `extreme_torch` + variante moderna `extreme_wall_torch` para colocación lateral.
- [x] Titanium/Uranium ore: reacción a pisar/golpear/usar y partículas rojas, reimplementada por posición.
- [x] Titanium/Uranium storage blocks: partículas flame/smoke/redstone ocasionales.
- [x] `BasicStone` eliminado del port: sus clases existen en el JAR, pero el original no lo registra.

### Materiales
- [x] `titanium_nugget`.
- [x] `uranium_nugget`.
- [x] `titanium_ingot`.
- [x] `uranium_ingot`.
- [x] `amethyst`.
- [x] `moth_scale` (material del set Moth; drop/entity llega en Fase 4).

### Herramientas — 15/15
- [x] Amethyst Sword/Pickaxe/Axe/Shovel/Hoe.
- [x] Emerald Sword/Pickaxe/Axe/Shovel/Hoe.
- [x] Ultimate Sword/Pickaxe/Axe/Shovel/Hoe.
- [x] Stats de tiers recuperados del bytecode original.
- [x] Emerald Pickaxe: Silk Touch I automático.
- [x] Ultimate Sword: Looting VI + Unbreaking VI.
- [x] Ultimate Pickaxe: Efficiency V + Fortune V.
- [x] Ultimate Axe/Shovel: Efficiency V.
- [x] Ultimate Hoe: Efficiency II + labrado 3×3 con tolerancia vertical de 1 bloque.

### Armaduras — 16/16
- [x] Sets Amethyst, Emerald, Moth y Ultimate.
- [x] Durabilidad/protección/enchantability/toughness recuperados del bytecode.
- [x] Ultimate: paquete de auto-encantamientos original recreado.
- [x] Texturas layer 1/2 de los cuatro sets copiadas al namespace `copyl`.

### Data/resources
- [x] Creative tab único `copyl_tab`.
- [x] 41 recipes 1.20.1 (crafting + smelting base recuperado/reconstruido del original).
- [x] 7 loot tables.
- [x] 19 tags Minecraft/Forge (`mineable`, `needs_iron_tool`, ores, ingots, nuggets, gems, storage blocks).
- [x] Blockstates/modelos/item models de todo el contenido de Fase 3.
- [x] 52 PNG de Fase 3 copiados/reutilizados del JAR original.
- [x] Lang actualizado en EN + variantes ES existentes de CopyL.
- [x] 135 JSON validados sintácticamente; 0 errores.
- [x] Validación de referencias custom a modelos/texturas: 0 faltantes.
- [x] Validación de referencias `copyl:` en recipes: 0 IDs desconocidos.

### Fidelidad / excepciones documentadas
- [x] El original no registra recipe Java para `amethyst_shovel`; se conserva esa omisión por fidelidad.
- [x] El lang original omite el nombre de `amethyst_hoe`; CopyL añade una traducción útil en el port moderno.
- [x] Los ores Titanium/Uranium no entregan XP al minarse; el XP original está en smelting (0.4).
- [x] `AmethystTools` usa accidentalmente el nombre interno `emerald` en 1.12.2; no se replica el bug.

### Validación ejecutable
- [~] Revisión estática completa hecha.
- [ ] `BUILD SUCCESSFUL` local todavía no verificable en este sandbox: los ZIP fuente no traían
      `gradle-wrapper.jar` y el entorno de ejecución no puede resolver/descargar la distribución/dependencias.
- [ ] `runClient` / `runServer` pendientes de un entorno Forge con dependencias descargables.

## Fase 4 — entidades

- [ ] Analizar/portar `OreGenericEgg` antes de registrar los 32 creature `*_ore` como bloques normales.
- [ ] Registrar EntityTypes por grupos.
- [ ] Migrar AI legacy `MyEntityAI*` a Goals modernos preservando comportamiento.
- [ ] Portar modelos/renderers y sonidos por criatura.
- [ ] Portar huevos, jaulas y drops vinculados.
- [ ] Bosses/entidades complejas: decidir caso por caso dónde GeckoLib aporta valor real.
- [x] `util/premium` auditado: checker inactivo/always-true; excluido por ser código legacy sin gameplay.

## Fase 5 — worldgen/dimensión
- [ ] Ores worldgen.
- [~] Mining Dimension base: datapack dimension/type + fixed mining biome, hills-style vanilla noise and ore/mob spawn tables. Original day rollover is ported; Red Ant provides the original empty-hand two-way teleport. Exact custom chunk-noise parity, OreGenericEgg ore placement and dungeon integration remain in progress.
- [ ] Dungeons/structures/AntHillGenerator.

## Fase 6 — mecánicas especiales
- [ ] Colonias Ant/RedAnt/Termite.
- [ ] Plantas de insectos + BlockEntity moderno.
- [ ] Teleport command.

## Fase 7–10
- [ ] Migración del resto de assets al avanzar cada sistema.
- [ ] Integración/limpieza final.
- [ ] Testing multiplayer/client/server.
- [ ] Release final.


## Checkpoint 2026-09-23 — structures + creature ores + Extreme Torch

- **PORTED**: `OreGenericEgg` block behavior for all 32 creature ores present in the provided 1.12.2 JAR. Hardness/resistance and the original 50% XP roll (5–9 XP) are preserved.
- **PORTED**: original creature-ore block/item models, blockstates, textures and self-drop loot tables moved to the `copyl` namespace.
- **PORTED**: Extreme Torch visual model now uses the 1.12.2 torch geometry/UVs with the original OreSpawn texture. Light level remains 15, equivalent to original `setLightLevel(1.0F)`.
- **PARTIAL**: `GenericDungeon` logic ported: 12×6×12 clear volume, obsidian floor, random stone/cobblestone walls+roof, center spawner and south-facing chest. The spawner resolves `copyl:alien`, `copyl:gammametroid`, or `copyl:cryolophosaurus`; those entity types are still pending, so spawner final behavior is blocked by the entity port.
- **PORTED / DEFERRED BY USER**: `StructureGenerator` mining-dimension generation rule preserved (1/16 per chunk, Y 5–44) but intentionally not hooked to any world/dimension. No Overworld fallback was added.
- **PORTED**: `WorldGenStructure` modern template helper. The provided JAR contains no `.nbt` structure templates, so no templates were invented.
- **IMPLEMENTED (2026-09-24)**: Mining Dimension datapack registration, dimension type, fixed mining biome, vanilla hills-style terrain/features and original Red Ant teleport/day rollover. The 1.12.2 source has no separate portal block. Custom noise parity, mob-ore placement and GenericDungeon hook remain pending.
- **BUILD NOT VERIFIED IN THIS ENVIRONMENT**: Gradle wrapper bootstrap attempted but this environment cannot resolve `raw.githubusercontent.com`, so `compileJava/build` could not start. This is not reported as BUILD SUCCESSFUL.
