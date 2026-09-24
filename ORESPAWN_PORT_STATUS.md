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

- [x] `OreGenericEgg`: los 32 bloques `*_ore` usan el bloque de criatura con stats y XP originales.
- [~] EntityTypes/atributos/spawn placements: registrados para las criaturas implementadas; Spyro, Nastysaurus, Kyuubi, Mothra, Doomworm y Brutalfly siguen pendientes.
- [~] AI: algunos Goals de movimiento y ataques migrados; falta portar la conducta completa de varias criaturas y las familias restantes.
- [~] Modelos/renderers/sonidos parciales para las entidades implementadas; faltan las criaturas aún no portadas y verificación visual final.
- [~] Huevos y jaulas registrados para parte de las criaturas implementadas; faltan las entradas de criaturas pendientes y drops asociados.
- [~] Alien, Gamma Metroid y otros bosses tienen clases/renderers base; faltan varias criaturas complejas y paridad de animación.
- [x] `util/premium` auditado: checker inactivo/always-true; excluido por ser código legacy sin gameplay.

## Fase 5 — worldgen/dimensión
- [~] WorldGenOres: registered vein feature now covers the original 28 creature ores plus Uranium, Titanium and Amethyst in Overworld and Mining Dimension; exact 1.12.2 heights, per-chunk attempts and 5–9 vein sizes are preserved.
- [~] Mining Dimension base: datapack dimension/type + fixed mining biome, hills-style vanilla noise and ore/mob spawn tables. Original day rollover is ported; Red Ant provides the original empty-hand two-way teleport. Exact custom chunk-noise parity and the two missing Spyro/Nastysaurus dungeon-loot entries remain in progress. AntHillFeature, its configured/placed features and Overworld/Mining biome modifiers are now present; original-frequency/runtime parity still needs verification.
- [~] GenericDungeon is connected to new Mining Dimension chunks at the original 1/16 frequency and Y 5–44; its chest loot table currently includes all registered original items.

## Fase 6 — mecánicas especiales
- [~] Sistema Ant/RedAnt/Termite: `AntHillBlock` libera Red Ants, Red Ant combate/teletransporta y Termite busca/consume madera; falta auditar paridad completa de comportamiento y multiplayer.
- [x] Plantas de Butterfly/Firefly/Mosquito/Moth y crecimiento de Corn con BlockEntity moderno; falta validación runtime final.
- [~] `DimensionTeleport` y teletransporte por Red Ant están implementados; el comando independiente de teletransporte aún falta.

## Fase 7–10
- [ ] Migración del resto de assets al avanzar cada sistema.
- [ ] Integración/limpieza final.
- [ ] Testing multiplayer/client/server.
- [ ] Release final.


## Checkpoint 2026-09-23 — structures + creature ores + Extreme Torch

- **PORTED**: `OreGenericEgg` block behavior for all 32 creature ores present in the provided 1.12.2 JAR. Hardness/resistance and the original 50% XP roll (5–9 XP) are preserved.
- **PORTED**: original creature-ore block/item models, blockstates, textures and self-drop loot tables moved to the `copyl` namespace.
- **PORTED**: Extreme Torch visual model now uses the 1.12.2 torch geometry/UVs with the original OreSpawn texture. Light level remains 15, equivalent to original `setLightLevel(1.0F)`.
- **PARTIAL**: `GenericDungeon` logic ported: 12×6×12 clear volume, obsidian floor, random stone/cobblestone walls+roof, center spawner and south-facing chest. The spawner randomly selects the registered `copyl:alien`, `copyl:gammametroid`, or `copyl:cryolophosaurus` types.
- **PORTED**: `StructureGenerator` runs only for new Mining Dimension chunks (1/16 chance, Y 5–44). No Overworld fallback was added.
- **PORTED**: `WorldGenStructure` modern template helper. The provided JAR contains no `.nbt` structure templates, so no templates were invented.
- **IMPLEMENTED (2026-09-24)**: Mining Dimension datapack registration, dimension type, fixed mining biome, vanilla hills-style terrain/features and original Red Ant teleport/day rollover. The 1.12.2 source has no separate portal block. GenericDungeon is connected to new Mining Dimension chunks. The chest table has the original 3–5 rolls and all currently registered original rewards; Spyro/Nastysaurus eggs remain pending; AntHillFeature and its biome modifiers were added afterward.
- **BUILD VERIFIED**: GitHub Actions runs 373, 374 and 375 passed; run 375 built commit `1644b6ccfc56d60c7eed77f49af165ba0798b5f1` with Java 17 and Gradle 8.1.1.

## Seguimiento — 2026-09-24

- **Build**: GitHub Actions run 387 completó correctamente para `a40c3f970b61c0044a7db6a157caf4e85e26f490` en `work/eggs-cages-20260923`.
- **Prioridad activa**: cerrar el port funcional antes de la auditoría visual final. Siguen sin portearse Spyro, Nastysaurus, Doom Worm, Kyuubi, Mothra y Brutalfly, incluyendo sus registros y contenido dependiente.
- **Defectos visuales reportados para revisar después del port**:
  - Dos capturas del inventario muestran iconos magenta/cuadriculados donde faltan texturas/modelos de objetos.
  - Una captura en primera persona muestra la mano/objeto sostenido con geometría plana o mal orientada.
  - Una captura de mundo muestra un mob/insecto con partes del modelo estiradas o separadas.
- Estas capturas quedan como regresiones visuales pendientes; se revisarán tras completar registros, comportamiento, recursos y build del port.

### Checkpoint run 388 — 2026-09-24

- **PORTED**: las seis jaulas vanilla registradas por el original: vaca, creeper, ghast, caballo, cerdo y zombi. Se conectan al mismo sistema de jaula lanzada y liberación que usa el resto del port.
- **RECURSOS**: modelos de item y nombres EN/ES añadidos. Los seis modelos reutilizan temporalmente el icono base de jaula vacía; sus iconos originales quedan pendientes de la pasada visual.
- **SPAWNS**: auditoría de las reglas documentadas contra los biome modifiers actuales; los grupos comunes, bosque/selva, pantano y colinas ya están cubiertos, sin añadir reglas nuevas.
- **BUILD**: GitHub Actions run 388 compiló correctamente `0c677bb3dce78b31843facb48bf33bcafa18ae15`.
- **AVANCE GLOBAL ESTIMADO**: 64 %. Siguen pendientes las seis entidades indicadas arriba y los sistemas especiales/worldgen restantes.

### Auditoría del source actual — 2026-09-24

El árbol actual ya contiene `AntHillFeature`, `DimensionTeleport`, los bloques/entidades Ant, RedAnt y Termite, las cuatro plantas de insectos y `CornPlantBlockEntity`. Se corrigieron arriba casillas antiguas que los describían como ausentes. Esto confirma que el código existe; no equivale a una prueba runtime ni a una auditoría de paridad contra el JAR.


### Checkpoint de subtítulos — 2026-09-24

- **RECURSOS**: se añadieron traducciones para los 28 subtítulos definidos en `sounds.json` (inglés y las siete variantes españolas). Antes, las claves no existían en los archivos de idioma.
- **BUILD**: GitHub Actions run 397 compiló correctamente el commit `d3cab775758bddfb56e5b44bddbb09c9c935e424`.
- **VISUAL QA POST-PORT**: las cuatro capturas nuevas detallan iconos magenta/faltantes para huevos y jaulas, un objeto/parte de mob con geometría plana o mal orientada en primera persona y un mob alado de modelo deformado con piezas estiradas/separadas. Se mantienen para la pasada visual una vez cerrada la paridad funcional.
- **AVANCE GLOBAL ESTIMADO**: 64 %; este bloque solo cerró recursos de localización y no cambia el cálculo funcional.

### Recetas de huevos de criaturas — 2026-09-24

- **PORTADO**: diez recetas shapeless que faltaban para Alosaurus, Baryonyx, Bird, Beaver, Cave Fisher, Dragonfly, Mantis, Pointysaurus, Small Worm y Red Cow. Cada una conserva el patrón ya verificado en las recetas originales del port: huevo vanilla + su bloque `*_ore` = huevo correspondiente.
- **VALIDACIÓN**: los diez JSON parsean y las referencias de bloques e ítems existen en los registros actuales.
- **BUILD**: GitHub Actions run 399 pasó para `35c53037f5eefd012508635fa8cdcdee1a0b19f3`.
- **AVANCE GLOBAL ESTIMADO**: 65 %; sube un punto por cerrar recetas funcionales pendientes.

- **COMPLEMENTO**: auditoría de registros encontró la receta pendiente de Cassowary; se añadió también. Ahora las 20 parejas registradas huevo/bloque `*_ore` tienen receta específica.
- **BUILD**: GitHub Actions run 401 pasó para `a7d9322c0905bcdd17180a04c393263347eeb23f`.

### Materiales de gusano — 2026-09-24

- **REGISTRADO**: `worm_tooth` y `worm_food` estaban en el inventario del JAR pero ausentes del registro moderno. Se añadieron como items base, a la pestaña creativa y a los ocho archivos de idioma. No se les asignaron efectos porque el código de las entidades actual no los referencia y no hay evidencia de su uso original disponible en esta rama.
- **VISUAL**: sus modelos/texturas originales quedan para la pasada de assets; se evita reutilizar iconos de otros objetos.
- **BUILD**: GitHub Actions run 403 pasó para `c7e6093bea82a2a1adcd9f7153bcc53ffb0e5063`.
- **AVANCE GLOBAL ESTIMADO**: 66 %.

### Ajuste de generación de vetas — 2026-09-24

- **CORREGIDO**: `WorldGenOresFeature` recibía el origen del feature con solo `minecraft:biome` como placement modifier; se quitó el desplazamiento horizontal extra de +8 para que los intentos queden anclados en las coordenadas aleatorias completas del chunk.
- **CORREGIDO**: el rasterizado de cada veta procesaba `size + 1` pasos; ahora procesa exactamente `size` segmentos.
- **BUILD**: GitHub Actions run 405 pasó para `45a58dea9524754ab94519d162a87bd93a7c59b7`. La paridad de generación aún requiere verificación runtime.
- **AVANCE GLOBAL ESTIMADO**: se mantiene en 66 % mientras falta esa verificación y continúan pendientes las criaturas restantes.
