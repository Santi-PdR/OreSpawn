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
- [~] EntityTypes/atributos/spawn placements: las entidades principales (incluidos Spyro, Mothra, Doomworm, Brutalfly, Nastysaurus y Kyuubi) ya tienen registro y contenido asociado; queda cotejar exhaustivamente cada registro y regla de spawn del JAR.
- [~] AI: algunos Goals de movimiento y ataques migrados; falta portar la conducta completa de varias criaturas y las familias restantes.
- [~] Modelos/renderers/sonidos parciales para las entidades implementadas; faltan las criaturas aún no portadas y verificación visual final.
- [~] Huevos y jaulas registrados para parte de las criaturas implementadas; faltan las entradas de criaturas pendientes y drops asociados.
- [~] Alien, Gamma Metroid y otros bosses tienen clases/renderers base; faltan varias criaturas complejas y paridad de animación.
- [x] `util/premium` auditado: checker inactivo/always-true; excluido por ser código legacy sin gameplay.

## Fase 5 — worldgen/dimensión
- [~] WorldGenOres: registered vein feature now covers the original 28 creature ores plus Uranium, Titanium and Amethyst in Overworld and Mining Dimension; exact 1.12.2 heights, per-chunk attempts and 5–9 vein sizes are preserved.
- [~] Mining Dimension base: datapack dimension/type + fixed mining biome, vanilla noise router and ore/mob spawn tables. Bounds match the original 0–256 world height. Original day rollover and Red Ant empty-hand two-way teleport are ported. The legacy 1.12 noise algorithm is not bit-for-bit matched by the 1.20.1 router and still needs terrain/runtime comparison. AntHillFeature, its configured/placed features and Overworld/Mining biome modifiers are present.
- [~] GenericDungeon is connected to new Mining Dimension chunks at the original 1/16 frequency and Y 5–44; its chest loot table currently includes all registered original items.

## Fase 6 — mecánicas especiales
- [~] Sistema Ant/RedAnt/Termite: `AntHillBlock` libera Red Ants, Red Ant combate/teletransporta y Termite busca/consume madera; falta auditar paridad completa de comportamiento y multiplayer.
- [x] Plantas de Butterfly/Firefly/Mosquito/Moth y crecimiento de Corn con BlockEntity moderno; falta validación runtime final.
- [~] `DimensionTeleport`, teletransporte por Red Ant y el comando independiente `/dimensiontp` están implementados; la paridad multiplayer y de aterrizaje aún requiere verificación runtime.

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
- **IMPLEMENTED (2026-09-24)**: Mining Dimension datapack registration, dimension type, fixed mining biome, vanilla hills-style terrain/features and original Red Ant teleport/day rollover. The 1.12.2 source has no separate portal block. GenericDungeon is connected to new Mining Dimension chunks. The chest table preserves the original 3–5 rolls and all 20 original entries, including Spyro and Nastysaurus eggs; AntHillFeature and its biome modifiers are also present.
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


### Assets y comando de dimensión — 2026-09-24

- **RECURSOS**: se añadieron los modelos de ítem 1.20.1 y las texturas originales del JAR para `worm_food` y `worm_tooth`, que ya estaban registrados pero aparecían sin icono propio.
- **COMANDO**: portado el comando original `dimensiontp` con sus alias `orespawn`, `tpdim` y `dimtp`. Mantiene el permiso de operador y los IDs de dimensión originales: -1 Nether, 0 Overworld, 1 End y 5 Mining Dimension. El aterrizaje conserva el escaneo descendente original desde Y=255.
- **BUILD**: GitHub Actions run 407 pasó para los recursos; run 408 pasó para el comando y el conjunto acumulado, commit `d0aeb8beb369fe65cfb664313d99f922e3faba42`.
- **AVANCE GLOBAL ESTIMADO**: 67 %. Las seis entidades pendientes y la verificación de paridad/runtime siguen abiertas; las capturas de defectos visuales se dejan para la pasada final.


### Auditoría estática de texturas para la pasada final

Al cruzar las rutas de texturas de los 26 renderers con el árbol actual de GitHub, faltan PNG cargables para `alosaurus`, `baryonyx`, `beaver`, `camarasaurus`, `cavefisher`, `dragonfly`, `pointysaurus_original`, `trextexture` y `wormlargetexture`. En varios casos la rama contiene fragmentos o archivos con extensión `.b64`, que Minecraft no carga como texturas PNG. Se conserva este hallazgo para la pasada visual final solicitada; no se cambian estos recursos durante el port funcional.


### Validación de receta y recursos de audio — 2026-09-24

- **RECETA**: corregida la receta Ultimate Boots eliminando la clave de ingrediente sin símbolo usado, que hacía que Forge descartara la receta al cargar datapacks. Auditoría de las 62 recetas: sin símbolos huérfanos ni referencias desconocidas.
- **AUDIO**: restaurados desde el JAR original los 47 OGG ausentes. Los 50 eventos de `sounds.json` ahora referencian 50 archivos existentes.
- **BUILD**: run 412 detectó duplicados entre audios binarios nuevos y fragmentos Base64 de Alosaurus; se retiraron los ocho fragmentos obsoletos. GitHub Actions run 413 pasó para `12224379bb0f687e76c5f9f25746a3e1f2f09378`.
- **AVANCE GLOBAL ESTIMADO**: se mantiene en 67 %; la restauración de sonidos y corrección de receta cierran errores concretos, pero no reducen el alcance pendiente de entidades, paridad de mecánicas y pruebas runtime.


### Seguimiento del crash de carga de mundo — 2026-09-24

- El log de las 16:02 del perfil `test-1` muestra que la creación de mundo falló al parsear `copyl:dimension_type/mining.json`; al fallar esa carga también quedaron sin resolver las placed features vanilla de agua.
- En el log posterior del mismo perfil, Minecraft inició el servidor integrado a las 16:17, cargó el mundo `Mundo nuevo` y guardó repetidamente el nivel `copyl:mining` hasta las 16:23. El crash dejó de reproducirse con un artefacto instalado después.
- **LÍMITE DE LA PRUEBA**: el log no identifica el SHA del artefacto instalado; esto verifica la prueba local histórica, no certifica por sí solo el head actual de GitHub. Se requiere volver a validar runtime con el build actual.


### Nastysaurus — integración funcional 2026-09-24

- **PORTADO**: estadísticas, IA y condiciones especiales contrastadas con la clase original; registro de EntityType, atributos y spawn placement.
- **CONTENIDO**: huevo, jaula y sus modelos; render layer, renderer y textura del JAR original. La geometría básica se deja marcada para la auditoría visual final solicitada.
- **BUILD**: GitHub Actions run 420 pasó para `7c23e9e79f6fbe82dd7013488241fa432f329967`. Run 417 también compila el bloque de entidad/IA previo; los runs intermedios 418 y 419 fallaron porque el modelo no implementaba `renderToBuffer`, corregido antes de validar el head actual.
- **PENDIENTE**: Spyro, Kyuubi, Mothra, Doom Worm y Brutalfly; prueba runtime y revisión visual final.
- **AVANCE GLOBAL ESTIMADO**: 68 %. El bloque de Nastysaurus cierra una de las seis entidades grandes pendientes; la paridad runtime y los otros cinco port siguen abiertos.


### Kyuubi — integración funcional 2026-09-24

- **PORTADO**: atributos, inmunidad al fuego, AI de objetivo y bolas de fuego pequeñas; daño propio periódico al estar en agua; partículas y drops explícitos cotejados con bytecode.
- **SPAWN**: el registro original `EntitySpawns.addSpawns` asigna peso 15/grupo 1 solo a biomas cuyo ID contiene `hell`. El port usa el tag `minecraft:is_nether` como equivalente 1.20.1; no se agregó spawn al Overworld.
- **CONTENIDO**: EntityType, placement, renderer/modelo inicial, huevo, jaula, localización EN/ES y texturas originales. El JAR no trae archivo de audio Kyuubi pese a referenciar un evento; no se inventó un sonido.
- **BUILD**: GitHub Actions run 425 pasó para `bc96ae330cb67f0c6d457532b9be9b8cad3f0664`; el run del head posterior validará también el biome modifier y el estado.
- **PENDIENTE**: Spyro, Doom Worm, Brutalfly; prueba runtime y revisión visual final.
- **AVANCE GLOBAL ESTIMADO**: 69 %.


### Mothra — integración funcional 2026-09-24

- **PORTADO**: vuelo derivado de Butterfly; vida 150, velocidad 0.35, armadura 12, inmunidad al fuego, curación periódica y ataques de bola de fuego según la dificultad. Se conservan el movimiento aéreo y el objetivo de vuelo del JAR.
- **MUERTE**: sonido genérico de explosión del original, ráfaga de 20 partículas, 20 Moths invocadas y botín original (marco, 53 pepitas de oro, 3 varas de blaze y una estrella del Nether).
- **CONTENIDO**: EntityType/atributos/spawn placement, aparición peso 15/grupo 1 en Extreme Hills, huevo y jaula con texturas originales, renderer y sonido de alas extraído del JAR.
- **SPAWN**: cotejados con el método original el requisito de noche, altura mínima Y=70, columna de aire y separación de 64×32×64 respecto de otra Mothra.
- **BUILD**: GitHub Actions run 428 pasó para el checkpoint inicial y run 429 pasó tras cotejar/corregir placement y selección de objetivos (`844959e4009da5b31fcab1af700ccad65089c56a`).
- **PENDIENTE**: Spyro, Doom Worm y Brutalfly; auditoría de conducta/loot, runtime y revisión visual final.
- **AVANCE GLOBAL ESTIMADO**: 70 %.


### Spyro — integración inicial 2026-09-25

- PORTADO: entidad domesticable con salud 200, velocidad 0.3, armadura 5, daño cuerpo a cuerpo 4 e inmunidad al fuego según la clase original.
- INTERACCIONES: carne para domesticar al 50% y curar; arbusto muerto para soltar; hielo y pedernal para cambiar el estado de bolas de fuego; etiqueta para nombrar; mano vacía para sentarlo/levantarlo.
- ATAQUE: selección de monstruos cercanos, huida con salud inferior al 25% domesticado, golpes y SmallFireball con las probabilidades originales según el estado de fuego. Actividad y fuego persisten en NBT.
- SPAWN: la clase original de Mining Dimension registra Spyro con peso 250 y grupo de 1; no se añadió spawn natural al Overworld.
- CONTENIDO: EntityType, atributos, huevo, jaula, renderer, mesh inicial, texturas originales, loot y nombres EN/ES.
- PENDIENTE DE PARIDAD: el orden exacto de objetivos y la búsqueda/steering de vuelo original aún requieren auditoría; el mesh inicial se revisará con las capturas en la pasada visual final.
- BUILD: Actions #433 validó entidad/modelo, #434 el spawn en Mining Dimension y #435 los ajustes de vuelo/sentado. Head `45dd05d94799ab08e728d79482ba0ce0759ad0d9`.
- SIGUIENTE: Doom Worm y Brutalfly, loot de dungeon pendiente, auditoría AI y pruebas runtime/visual.
- AVANCE GLOBAL ESTIMADO: 71%; el objetivo completo sigue activo.


### Doom Worm — integración funcional inicial 2026-09-25

- **PORTADO**: EntityType, vida 3000, desplazamiento server-authoritative y movimiento del cuerpo de 100 segmentos con giro gradual y ondulación vertical según `WormDoom` del JAR.
- **CONTENIDO**: huevo invocable, entrada creativa, modelo/render por segmentos, textura corporal y icono originales; nombres EN + siete variantes ES.
- **FIDELIDAD**: no se añadió aparición natural, jaula, recipe ni sonidos CaterKiller inexistentes en el JAR. El tamaño y detalle del modelo todavía necesitan comparación visual con el original; el orden de orientación/culling del cuerpo está pendiente de verificación runtime.
- **BUILD**: Actions #438 pasó tras corregir el import del modelo; #440 pasó para el head con textura e idiomas, commit `e9a848c3dba7746ea92a3096cc411b58a0c864bd`.
- **PENDIENTE**: Doom Worm requiere cotejar visual/runtime; quedan Brutalfly y las auditorías finales de IA, contenido, runtime y visuales.
- **AVANCE GLOBAL ESTIMADO**: 72 %.


### Brutalfly — port inicial 2026-09-24

- **CÓDIGO**: registradas estadísticas originales (110 de vida, velocidad 0,35, ataque 10, armadura 6, inmunidad al fuego y 100 XP), vuelo/selección de blancos, combate cuerpo a cuerpo y de proyectiles, curación cada 100 ticks, retaliación y muerte con 53 pepitas de oro + 20 mariposas.
- **SPAWN**: bosque/selva con peso 10 y grupo 1; se conserva noche, Y≥70, columna de aire y radio de separación de 64×32×64 del original.
- **CONTENIDO**: huevo, jaula, modelos, renderer y cuatro imágenes originales extraídas del JAR, incluida la capa superpuesta alada; traducciones EN y siete variantes ES.
- **FIDELIDAD**: `BetterFireballEntity` reproduce los impactos del JAR: evita Brutalfly/Mothra, inflige 10 y prende fuego 5 s a objetivos pequeños, coloca fuego adyacente al chocar con bloque y explota con potencia 1 respetando `mobGriefing`. El mesh todavía reutiliza la geometría de Butterfly y requiere port del modelo original.
- **BUILD**: Actions #466 pasó el registro/contenido y #469 pasó después del port del proyectil (incluye `gradle build`, validación del JAR final y carga de artefacto).
- **PENDIENTE**: revisar el mesh original y runtime cliente/servidor; la pasada final atenderá también los fallos visuales de las capturas.
- **AVANCE GLOBAL ESTIMADO**: 74%; el objetivo sigue activo.


### Recompensas originales de GenericDungeon — 2026-09-24

- **CORREGIDO**: añadidos `copyl:spyro_egg` y `copyl:nastysaurus_egg` a la loot table, cada uno con peso 15 y respetando el orden del JSON original del JAR. Ambos IDs existen en el registro de ítems actual.
- **VALIDACIÓN**: Actions run 471 compiló correctamente el commit `8e6812e69d968df42a82f75181ff310cfd44ff42`, incluyendo el build del mod, la comprobación del JAR y la carga del artefacto.
- **AUDITORÍA**: confirmados en el source actual los registros/modelos/renderers de las entidades principales antes marcadas como pendientes. Continúa pendiente la comparación completa de IA, spawn y runtime.
- **AVANCE GLOBAL ESTIMADO**: 75%; sigue activo el cotejo de comportamiento y las pruebas runtime antes de la pasada visual final.


### Spawns del bioma Mining — 2026-09-24

- **CORREGIDO**: añadidas al grupo `creature` de `mining_biome.json` las apariciones originales omitidas de Spyro (peso 250) y Nastysaurus (peso 200), manteniendo sus grupos 1–1 y el orden del registro del JAR.
- **VALIDACIÓN**: el JSON parsea y las ocho criaturas del listado quedan registradas; Actions run 473 compiló y empaquetó el commit `ee19a8218b0cff5964ed923fbb8d117fb5d99d5b`.
- **AVANCE GLOBAL ESTIMADO**: 76%; falta prueba de spawn runtime y continúa la auditoría de paridad general.


### Auditoría de spawns globales — 2026-09-24

- **CORREGIDO**: retirado el spawn adicional de Spyro del modifier Mining. El JAR lo añade una sola vez desde la lista de criaturas propia de `BiomeMiningDimension`; el modifier moderno lo duplicaba.
- **COTEJADO**: las 14 entradas globales de `EntitySpawns.addSpawns()` están representadas en los modifiers de Overworld, Mining, Nether, bosque/selva, pantano y colinas extremas, con pesos y tamaños originales. Se conservaron duplicaciones que sí existen en el JAR (Bird/Butterfly en Mining).
- **BUILD**: Actions run 475 pasó para el commit `0221e48819c6341e12035584759f91b759a4995a`, incluido empaquetado del JAR.
- **AVANCE GLOBAL ESTIMADO**: 77%; quedan validación runtime, paridad de terreno/IA y el alcance completo de contenido.


### Rollover de hora al dormir en Mining Dimension — 2026-09-24

- **CORREGIDO**: el rollover ya no se ejecuta automáticamente cada tick después del mediodía. El método 1.12.2 exige que todos estén durmiendo; el port ahora usa `SleepFinishedTimeEvent`, que Forge dispara al completar el sueño colectivo, y sincroniza con las demás dimensiones la hora de despertar propuesta por el evento.
- **VALIDACIÓN**: compilación y empaquetado correctos en Actions run 477, commit `bf6b4c64344b1dcfc60d34667d30c647145214f1`. La prueba runtime de sueño multiplayer sigue pendiente.
- **AVANCE GLOBAL ESTIMADO**: 78%; el comportamiento queda cotejado por bytecode, pero requiere runtime para cerrar paridad práctica.


### Límites verticales de Mining Dimension — 2026-09-24

- **CORREGIDO**: la dimensión usaba −64..319 por heredar el tipo y noise settings del Overworld moderno. El generador original 1.12 trabaja con chunks de 256 bloques desde Y=0. Añadido un noise settings propio copiado de los datos oficiales de Minecraft 1.20.1 con `min_y=0`, `height=256` y nivel del mar 63; la dimensión ahora apunta a esos ajustes y el dimension type declara los mismos límites.
- **VALIDACIÓN**: los tres JSON parsean, la referencia `copyl:mining` coincide y Actions run 481 pasó `gradle build`, comprobación del JAR y carga del artefacto para commit `8c2472c46a68964021cd650376ae727a3d74e5a0`.
- **LÍMITE**: se preserva el volumen vertical; el router moderno no reproduce exactamente el algoritmo de ruido 1.12. Falta comparar terreno y carga en runtime.
- **AVANCE GLOBAL ESTIMADO**: 79%; los bordes verticales ya coinciden con el original.


### Doom Worm — paridad de movimiento y visibilidad — 2026-09-25

- **CORREGIDO**: cotejado el constructor y `tick()` de `WormDoom` con el JAR: segmentos iniciales cada 0.5625 bloques (antes el doble), fase vertical aleatoria, giro inicial de 1°, elección del objetivo de giro con probabilidad 1/100 por tick, velocidades/ondas y exploración vertical distintas para adulto y cría. Si la columna está vacía, se preserva el offset `-rango-1` del bucle original.
- **VISIBILIDAD**: el renderer moderno ahora evita el descarte por frustum del cuerpo largo segmentado, correspondiente a las anulaciones de distancia siempre visible del original.
- **BUILD**: Actions #483 validó el movimiento y #484 validó el renderer; ambas pasaron `gradle build` y empaquetado en commits `db290a84e1e48c349912d5204e12cb4295a1b940` y `6046f089bf68398d9f5263f3a1ae2db3716bc75a`.
- **PENDIENTE**: validar movimiento/culling dentro de juego y seguir cotejando la conducta, el modelo y el resto del contenido contra el JAR. Las capturas de defectos visuales siguen reservadas para la pasada final.
- **AVANCE GLOBAL ESTIMADO**: 80%; la compilación verde confirma build, no runtime ni cierre del port.


### Inicialización de mobs invocados por huevo — 2026-09-25

- **CORREGIDO**: `ItemGenericEgg` del JAR llama `EntityLiving.onInitialSpawn` después de insertar la entidad. `CreatureEggItem` ahora ejecuta `Mob.finalizeSpawn` con la dificultad local y `MobSpawnType.SPAWN_EGG`, preservando la inicialización moderna de variantes/equipo para todos los huevos genéricos; posición, consumo y uso en creativo quedan iguales.
- **VALIDACIÓN**: Actions #486 pasó `gradle build`, la comprobación del JAR final y la carga del artefacto para `f16b7117813c03bca8e90510b5ee901c41ac22a1`.
- **PENDIENTE**: comprobar en runtime las variantes/spawn data; continúan abiertas paridad de IA, dimensión/terreno y pruebas client/server antes de la revisión visual final.
- **AVANCE GLOBAL ESTIMADO**: 81%; el bloque de invocación por huevos ahora conserva la fase original de inicialización.


### Spyro — fidelidad de vida y selección de objetivos — 2026-09-25

- **CORREGIDO**: cotejado `onUpdate`/`updateAITasks` del JAR. Spyro ahora gana `+0.07` de velocidad vertical por tick en agua (sin curarse cada tick); recupera 1 punto con una tirada de 1/100 por tick, independientemente del agua.
- **CORREGIDO**: se portó el reemplazo automático de Spyros no persistentes con probabilidad 1/100000 por tick, conservando posición, orientación aleatoria, inicialización natural, transferencia del estado domesticado y eliminación del original. El orden de objetivos ahora también aplica el peso original de distancia a Creepers (×0.5), además de distancia por área.
- **VALIDACIÓN**: Actions #488 pasó las reglas de agua/curación; #489 y #491 fallaron por el import omitido de `ServerLevel`; se corrigió y Actions #492 pasó el build, verificación del JAR y carga del artefacto para `ed567a3d1e4d36e41d41d696de2e575d302aefb1`.
- **PENDIENTE**: el algoritmo de búsqueda/steering de vuelo aún es una simplificación moderna pendiente de cotejo; runtime y restantes entidades/mecánicas siguen abiertos.
- **AVANCE GLOBAL ESTIMADO**: 82%; mejoró la paridad de vida/objetivos, pero Spyro aún no está completamente cotejado.


### Spyro — búsqueda y steering de vuelo cotejados — 2026-09-25

- **CORREGIDO**: la búsqueda de objetivos solo se ejecuta cuando Spyro está en actividad de vuelo/ataque; la cadencia de selección, el reinicio de actividad en reposo y el seguimiento del dueño ahora respetan los disparadores del JAR.
- **VUELO**: portados los rangos originales para elegir puntos libres y visibles, los umbrales de distancia al dueño/objetivo, el modo de vuelo del dueño, la huida de Spyro domesticado con poca salud, la navegación hacia el objetivo y los factores de aceleración/velocidad. La visibilidad se comprueba con raycast moderno.
- **ATAQUE**: recuperada la probabilidad original de bola de fuego y quitado el cooldown que no existía en el bytecode.
- **BUILD**: GitHub Actions run #495 pasó para el commit `9a65401d576a1d07320725d4d4cfa477e7940387`, incluyendo build y empaquetado del JAR. El run #494 del commit intermedio se canceló al ser supersedido.
- **PENDIENTE**: falta cotejar la búsqueda de líquidos/recuperación de ruta y probar vuelo, selección de blancos y combate en runtime. La pasada de defectos visuales sigue aplazada hasta cerrar el port funcional.
- **AVANCE GLOBAL ESTIMADO**: 83%; el build valida compilación y empaquetado, no el cierre de runtime ni de las mecánicas pendientes.


### Spyro — búsqueda de agua — 2026-09-25

- **IMPLEMENTADO EN SOURCE**: el bytecode muestra que los Spyros salvajes buscan agua con tirada 1/20 en capas crecientes, hasta 10 bloques en horizontal y 4 en vertical. El port busca agua vanilla, elige la más cercana de la capa hallada y navega al bloque bajo ella a velocidad 1. También conserva el borrado ocasional del objetivo (1/200) y la curación/sonido cuando ya está en el agua.
- **VALIDACIÓN**: cotejado directamente con `Spyro.scan_it()` y `func_70030_z()` del JAR de referencia. El source se actualizó en commit `5f2cc6ec1f2d7df74b45fbb8c77dc2c78a498e33`. El workflow build está configurado para cada push, pero el endpoint GitHub disponible aquí solo expone runs de pull request; no hay evidencia del resultado de build de este push todavía.
- **PENDIENTE**: confirmar compilación; probar navegación al agua y comportamiento de curación en runtime. La fidelidad completa de AI y la pasada visual final siguen abiertas.
- **AVANCE GLOBAL ESTIMADO**: 84%; este bloque restaura una mecánica faltante de Spyro, sujeto a validación de build/runtime.


### Auditoría de jaulas originales — 2026-09-25

- **CORREGIDO**: comparación del registro moderno con `ModItems.registerEntities()` del JAR. Retiradas tres jaulas que el original no registraba (Baryonyx, Camarasaurus y CaveFisher); agregadas a la pestaña creativa las diez jaulas originales que no eran accesibles allí (las seis vanilla, Spyro, Nastysaurus, Kyuubi y Mothra).
- **VALIDACIÓN ESTÁTICA**: cotejados los 27 IDs originales. Los 27 están registrados y expuestos en la pestaña; no hay IDs de jaula adicionales. Cambios de source en commits `cc808f41d5b308072988c2f7a403c5396cd3f878` y `956413d24d550b1bacbc4fda220ea9c818eac8f3`.
- **BUILD**: pendiente de evidencia. El workflow se activa en push, pero el conector GitHub disponible no lista los runs de push y el estado combinado de estos commits aún no ofrece checks.
- **AVANCE GLOBAL ESTIMADO**: 85%; corregido el contenido funcional accesible y alineado al registro original. La compilación y runtime de estos cambios siguen pendientes.


### Auditoría del inventario creativo: huevos — 2026-09-25

- **CORREGIDO**: añadidos a la pestaña creativa los huevos de Spyro, Nastysaurus, Kyuubi y Mothra, ya registrados pero omitidos de la lista visible.
- **VALIDACIÓN ESTÁTICA**: comparación del JAR con `ModItems` y `ModCreativeTabs`: los 32 huevos originales están registrados y los 32 son accesibles desde la pestaña, sin duplicados ni extras. Cambio en commit `525c67316c496fa337ebbd0eb0b60b29eefe3dba`.
- **BUILD**: el workflow corre en pushes, pero los estados combinados siguen sin checks y el conector actual solo lista ejecuciones de PR; compilación de este head aún no confirmada.
- **AVANCE GLOBAL ESTIMADO**: 86%; accesibilidad de huevos/jaulas ajustada al inventario original. Runtime y build siguen pendientes.


### Auditoría del resto del inventario creativo — 2026-09-25

- **VALIDACIÓN ESTÁTICA**: comparados los campos registrados en `ModItems` (112), `ModMaterialItems` (8), `ModTools` (15) y `ModArmor` (16) con las referencias de `ModCreativeTabs`. No faltan entradas ni hay referencias huérfanas. La validación de huevos y jaulas se registra en los checkpoints anteriores.
- **BUILD/RUNTIME**: esta comprobación solo cubre la lista de contenido en código; no sustituye compilación ni prueba en juego. El resultado de Actions para los últimos pushes continúa sin poder verificarse mediante el endpoint de runs disponible.
- **AVANCE GLOBAL ESTIMADO**: se mantiene en 86%; esta es una auditoría de cobertura del inventario ya portado, no un bloque nuevo de gameplay.


### Paridad de Ant/Red Ant/Termite — 2026-09-25

- **ANT/TERMITE**: las tiradas periódicas de limpiar objetivo, buscar madera, seleccionar su consumo y orientar termitas invocadas usan el RNG del mundo, como las rutinas cotejadas. Termite conserva los radios, capas, avance de búsqueda, coste de madera y regeneración; el orden de caras y desempate queda corregido en el checkpoint siguiente.
- **RED ANT**: el constructor del JAR registra Panic, MeleeAttack y Wander y añade objetivo de jugador cercano cuando `OreSpawnMain.PlayNicely == 0`; el bytecode inicializa esa bandera a 0. La persecución/ataque se restauró en el port en commit `e017fdc84e10b0bbd8393fd8464460359189feda`, manteniendo además el ataque manual cada 20 ticks y el teletransporte con mano vacía.
- **RNG PENDIENTE**: la tirada de daño Red Ant original usa `OreSpawnMain.OreSpawnRand`, un flujo global sembrado con 151, mientras que el port conserva la probabilidad 1/15 pero usa RNG del mundo. Varias clases originales consumen la misma fuente global; falta completar esa auditoría antes de afirmar paridad exacta de secuencias. El caso no predeterminado de `PlayNicely` también queda pendiente.
- **SOURCE**: commits `98bf49164e119e2f207215dcc905ab518ce8a725`, `b3d15947265d20d083c91af94eacb020777b2029`, `8c9f85ef54419903af5f44da5630bfcf7e12ee40`, `f5fd17155b6d5ea36ca9e8400499cfb76731949b` y `e017fdc84e10b0bbd8393fd8464460359189feda`.
- **PENDIENTE**: verificar build/runtime, multiplayer y terminar auditoría de objetivos pasivos y del ciclo de madera. GitHub Actions #528 pasó para el head que contiene estas correcciones; sus pasos `Build mod`, `Find final JAR` y `Upload compiled JAR` terminaron correctamente.
- **AVANCE GLOBAL ESTIMADO**: 87%; el avance refleja correcciones de contenido/comportamiento, pero quedan diferencias RNG, paridad global, pruebas y bloques del port.



### Jaulas — paridad de captura/liberación — 2026-09-25

- **COTEJO CON BYTECODE**: revisados `EntityCage.func_70184_a` y `CritterCage.handleRightClick` del JAR original. El proyectil de jaula llena libera exactamente en X/Z del bloque y Y+1 (no en el centro); se conserva esa coordenada del source actual. La jaula aleatoria de caballo se selecciona con `World.rand.nextInt()` y el nombre custom se aplica tras insertar la criatura.
- **CORREGIDO**: la captura ya contempla `LivingEntity` (el JAR no limita a `Mob`); la partícula de captura usa explosión normal en vez de nube; liberar desde jaula directa o proyectil restaura la variante aleatoria de `Horse`; la liberación usa el emisor de explosión grande del original. Source en commits `846a3b8d1216c154bedb8acba8786d56106c7d05`, `4c88a446ea87eb51e3947af389e6c80c9b09318c` y `84ed9389a9ed4d287264a533db1e3e46a662a5bb`.
- **VALIDACIÓN**: comprobados los archivos guardados en la rama y comparadas las coordenadas/orden de efectos con el bytecode del JAR. GitHub Actions #528 compiló y encontró/subió el JAR para el head actual `c75b1c0221298f8b43299ef8c6706fe4cc50854`; runtime y el resto de paridad aún siguen pendientes.
- **AVANCE GLOBAL ESTIMADO**: 87%; avance acotado de paridad en jaulas, con validación build/runtime aún pendiente.

- **API 1.20.1**: las liberaciones decodifican el byte bajo del `nextInt()` original y aplican `net.minecraft.world.entity.animal.horse.Variant.byId(valor % 7)` mediante `Horse#setVariant(Variant)`. Minecraft moderno separa las marcas del color y no expone un setter público de marcas; esa parte del valor combinado legacy aún no queda preservada. El build #525 detectó que `Variant` es un tipo de nivel superior, no `Horse.Variant`; se corrigió en commits `023f8e5362f8cd1cd5200ef3539e926a601efd48` y `9100779d7e08b36667994b75616819c7c4ff993c`. Run #525 falló en esa referencia; el import se corrigió y GitHub Actions #528 pasó `gradle build`, detección y publicación del JAR para el head `c75b1c0221298f8b43299ef8c6706fe4cc50854`.


### Termite — búsqueda original de madera — 2026-09-25

- **CORREGIDO**: la whitelist de madera ahora reconoce las seis variantes modernas de cartel de pie, equivalentes al bloque legacy `standing_sign` con sus variantes de madera.
- **CORREGIDO**: el barrido replica el orden de las seis caras del método `scan_it` (X, Y, Z), manteniendo el desempate de bloques a igual distancia. El origen usa conversión int→bloque por truncamiento como el bytecode `d2i`, incluso con coordenadas negativas.
- **VALIDACIÓN**: cotejo estático de `scan_it`, `updateAITick` e `isWood` del JAR con `TermiteEntity`; source commits `293c14e90fec19a0b55ac832aab17918024568ca` y `dff2630aa3bef1904809fbc7604676cb6ecfe15e`. GitHub Actions #528 valida el build, detección y publicación del JAR para el head actual; la prueba runtime sigue pendiente.
- **AVANCE GLOBAL ESTIMADO**: 87%; avanza la paridad de la familia, pero siguen pendientes build/runtime y el resto de entidades/sistemas.

### Alcance de seguimiento de entidades — 2026-09-25

- **CORREGIDO**: cotejado `ModEntities.registerEntities()` del JAR. Los registros ordinarios usan `trackingRange=50`; en 1.20.1 se aproximan con 4 chunks (64 bloques). Mothra y Brutalfly ya usan ese alcance, en lugar de 8 chunks.
- **CORREGIDO**: Doom Worm usa el registro especial de 325 bloques del JAR; `clientTrackingRange` moderno queda en 21 chunks (336 bloques), en lugar de 12 (192 bloques).
- **SOURCE**: commits `19d983f553044e5e328d54b1ec176f9f537c22b6` y `9d24b2046dd116468d5aa32a7622a1dd35a02f48`.
- **BUILD**: GitHub Actions #530 pasó para Doom Worm; #531 pasó para el conjunto final, con `Build mod`, detección del JAR final y carga del artefacto completadas.
- **AVANCE GLOBAL ESTIMADO**: 87%; esta corrección mejora la distancia de sincronización de tres criaturas. La paridad restante y las pruebas runtime/client/server siguen abiertas; los fallos visuales de las capturas se revisarán tras cerrar el port funcional.

### Sonidos de Doom Worm — 2026-09-25

- **CORREGIDO**: el bytecode de `WormDoom` retorna los eventos registrados `entity.alosaurus.hurt` y `entity.alosaurus.death`; `DoomWormEntity` ahora reproduce ambos sonidos originales.
- **FIDELIDAD**: el método ambiental hace la tirada `world.rand.nextInt(4)` y retorna uno de cuatro campos Caterkiller no inicializados por `SoundsHandler.registerSounds()`. El resultado original es silencioso; el port conserva la tirada y retorna `null` en vez de inventar eventos/audio.
- **RECURSOS**: comprobados los OGG originales de Alosaurus y sus rutas en los recursos modernos.
- **BUILD**: GitHub Actions #533 pasó para `4b53ebccacefb3c23a1eaec1baec57cb7aa3cfc8`, incluyendo empaquetado del JAR.
- **AVANCE GLOBAL ESTIMADO**: 87%; queda continuar la auditoría de mecánicas y entidades, además de runtime/client/server antes de la pasada visual final.

### Doom Worm — sondeo de columna en coordenadas negativas — 2026-09-25

- **CORREGIDO**: `WormDoom.tick()` construye el origen del barrido vertical con las conversiones JVM `d2i` para X/Y/Z (truncamiento hacia cero). El port ya usa `(int)getX/Y/Z` en vez de `blockPosition()`, que redondea hacia abajo en valores negativos.
- **VALIDACIÓN**: confirmado contra las instrucciones `d2i` del bytecode original. Actions #535 pasó `Build mod`, detección y carga del JAR para `4a0b5b2623c88d0ecfde2c65daa407780dd500ae`; falta prueba en mundo con coordenadas negativas.
- **AVANCE GLOBAL ESTIMADO**: 87%; continúa la revisión de paridad restante y runtime/client/server antes de la auditoría visual final.

### Auditoría de sonidos registrados — 2026-09-25

- **COTEJADO**: extraído `SoundsHandler.registerSounds()` del bytecode. El original inicializa 47 eventos; el port conserva el mismo conjunto (incluidos 23 llamados de pájaros, nueve farts y los dos sonidos de splat con el namespace moderno).
- **CORREGIDO**: las clases Dragonfly y Mosquito referencian en el JAR campos que nunca son inicializados por el registro original; sus sonidos resultan silenciosos. Se eliminaron del registro moderno los tres eventos Dragonfly y el evento Mosquito, incluido el evento de muerte Dragonfly que no tiene campo original. Los métodos modernos ahora retornan `null`, fiel al original.
- **VALIDACIÓN**: comparados los campos estáticos referenciados por el bytecode, la lista de registro original y las rutas de audio existentes. Actions #540 pasó compilación, detección y carga del JAR para `73e830ffa7523f259c252378cb68516f9680a47c`.
- **AVANCE GLOBAL ESTIMADO**: 87%; continúan abiertas otras auditorías de paridad y la verificación runtime/client/server antes de la pasada visual final.

### Mining Dimension — capas inferiores sin deepslate — 2026-09-25

- **CORREGIDO**: el `noise_settings/mining.json` moderno heredaba una regla del Overworld 1.20 que sustituía la piedra de Y=0–7 por deepslate. El generador original 1.12.2 usa piedra y bedrock; se retiró esa sustitución moderna sin cambiar el resto de las reglas de superficie.
- **VALIDACIÓN**: bytecode de `ChunkGeneratorMiningDimension.generateBiomeTerrain()` cotejado; JSON parseado y confirmado sin reglas de deepslate. Actions #542 pasó `Build mod`, detección y carga del JAR para `8ab113e9134685496a3e1fc697f797759430a06f`.
- **PENDIENTE**: el router de ruido y las cuevas modernos aún no reproducen en bits el generador 1.12.2; falta comparar mundo generado en runtime.
- **AVANCE GLOBAL ESTIMADO**: 87%; se corrigió una sustitución de bloques no original en la dimensión y continúa la auditoría del terreno.


### Recetas de huevos — cotejo integral con CraftingRecipes — 2026-09-25

- **AUDITORÍA**: se extrajeron los 28 IDs de recetas de huevo registrados por `danger.orespawn.recipes.CraftingRecipes` en el JAR original y se compararon con `data/copyl/recipes`.
- **CORREGIDO**: se añadieron las 11 recetas del original que faltaban: las seis recetas de spawn eggs vanilla y las recetas de Nastysaurus, Spyro, Kyuubi, Brutalfly y Mothra. Cada una usa `minecraft:egg` más su bloque `copyl:*_ore`; las vanilla producen el spawn egg vanilla y las demás su item de criatura.
- **CORREGIDO**: se eliminaron las tres recetas modernas `largeworm_egg`, `mediumworm_egg` y `smallworm_egg`. El JAR sí registra esos items, pero su registro completo de recetas no contiene esas recetas.
- **VALIDACIÓN**: los 11 JSON nuevos parsean y se contrastó el conjunto final de 28 IDs con los 28 IDs del bytecode original. Cambios en commits `7c44a329`–`26b0e178`; GitHub Actions #564 pasó para el head de esta auditoría (`ab35f07ac10af45a66f9407dd92e1bd560d0c11b`), incluyendo build, detección del JAR y carga del artefacto.
- **AVANCE GLOBAL ESTIMADO**: 87%; se cierra una brecha concreta de recetas, mientras siguen pendientes paridad completa de criaturas/sistemas y runtime/client/server.


### RNG global — tirada de combate Red Ant — 2026-09-25

- **CORREGIDO**: `OreSpawnMain.OreSpawnRand` se inicializa como `java.util.Random(151L)`; `RedAnt.func_70652_k` usa esa fuente para `nextInt(15)`. El port ahora usa `LegacyRandom.nextInt(15)` con el mismo flujo compartido, en vez del RNG de cada mundo.
- **SOURCE**: commits `b1a5558f` y `6ad911bb`.
- **ALCANCE PENDIENTE**: el mismo RNG global aparece en otros 22 tipos/clases del JAR. Además de Red Ant, ya se migraron Moth (variante), las probabilidades compartidas por las cuatro plantas de insecto y la altura inicial/fallback del maíz; el resto sigue en auditoría. No se afirma todavía paridad de la secuencia global.
- **BUILD**: GitHub Actions #566 pasó para el cambio de Red Ant. El run #570 detectó una referencia `RandomSource` que quedaba en el fallback de carga del maíz; se corrigió y Actions #571 pasó el head `43f26a5c52eba85299b7ac0e20c8d9a94e00de14`, con build y artefacto completados.
- **AVANCE GLOBAL ESTIMADO**: 87%; sigue abierta la auditoría del resto de llamadas aleatorias, además de IA, spawn y runtime.


### RNG global — cotejo estático completo — 2026-09-25

- **AUDITORÍA**: se compararon las referencias a `OreSpawnMain.OreSpawnRand` en el JAR con las llamadas modernas a `LegacyRandom`. El flujo sigue inicializado con `java.util.Random(151L)`.
- **MIGRADO**: Ant Hill; probabilidades compartidas de las cuatro plantas de insectos; valor inicial/fallback de maíz; ataque de Red Ant y Termite; variante de Moth; selección de Mosquito; dispersión del botín de Alien, Alosaurus, Beaver, Gamma Metroid, Kyuubi, Nastysaurus, Pointysaurus y T-Rex; gotas individuales de Brutalfly y Mothra; y las tres familias de Worm.
- **COTEJO**: el uso de plantas está compartido por una sola implementación moderna; el helper de dispersión de WormLarge consolida dos sitios originales; Corn conserva además un fallback moderno para partidas antiguas. Estas diferencias de conteo estático no demuestran diferencia de secuencia.
- **LÍMITE**: quedaron mapeados los puntos de consumo estáticos. No se afirma equivalencia de secuencia entre cargas, ticks, entidades o clientes; eso requiere prueba runtime.
- **BUILD**: GitHub Actions #590 pasó para `c55e806b522e8c6eb58ade5afe1ec1b42d39e7f8`, incluyendo compilación y artefacto.
- **AVANCE GLOBAL ESTIMADO**: 87%; quedan auditorías de comportamiento y pruebas runtime/client/server.

### Alien — selección de blancos, represalia y RNG — 2026-09-25

- **SELECCIÓN**: la búsqueda considera cualquier `LivingEntity` viva en el AABB original de 12×4×12, excluye jugadores creativos y el propio Alien, y selecciona la entidad más cercana. Se quitaron los filtros extra de línea de visión y espectador que el JAR no aplica.
- **REPRESALIA**: `hurt()` ahora inicia persecución inmediata solo ante `Mob`, equivalente a `EntityLiving` en el bytecode 1.12.2; jugadores y otras entidades vivas conservan el resultado normal del daño.
- **RNG**: se restauró `Level.getRandom()` para las tiradas que el original hace con `World.rand`: partículas y su orden de consumo, selección/ataque de objetivo, probabilidad de hambre, curación, sonido ambiental y cantidades de botín. La búsqueda de antorchas conserva el RNG individual que el original lee desde `Entity.rand`.
- **COTEJO**: comparados `findSomethingToAttack()`, `isSuitableTarget()`, `attackEntityFrom()`, `attackEntityAsMob()`, actualización de partículas y los accesos RNG del bytecode de `danger.orespawn.entity.Alien`.
- **BUILD**: Actions #591 pasó para la selección de blancos, #593 para el tipo de atacante y #594 para el flujo RNG; los tres compilaron y empaquetaron el JAR.
- **PENDIENTE**: verificar combate, efectos aleatorios y selección de blancos en runtime.
- **AVANCE GLOBAL ESTIMADO**: 87%; queda paridad de otras mecánicas y validación runtime.


## Checkpoint 2026-09-25 — PlayNicely + RNG parity

- **PORTED**: Added the mutable `LegacyGameplayFlags.PLAY_NICELY` compatibility field (default 0) and checked all 21 original entity classes that reference `OreSpawnMain.PlayNicely` against the JAR. Preserved both target acquisition and stored-retaliation behavior, including the original RNG rolls before gates.
- **PORTED**: Corrected world-vs-entity RNG sources in the audited AI paths for Alien, Alosaurus, Beaver, Baryonyx, Camarasaurus, CaveFisher, Cryolophosaurus, GammaMetroid, Kyuubi, Mothra, Nastysaurus, Pointysaurus, Spyro, T-Rex, VelocityRaptor, Termite and WormLarge/Medium/Small. Beaver also keeps the original world-RNG pitch draw for its uninitialized chainsaw sound event.
- **VALIDATED**: GitHub Actions builds #597–#607 pass on `work/eggs-cages-20260923`, including compilation, final JAR detection and artifact upload.
- This closes the `PlayNicely` call-site audit; it does **not** complete full entity AI parity. Original-vs-port behavior, Mining Dimension terrain at runtime, client/server and multiplayer testing, and remaining resource/render checks remain open. Overall progress stays at approximately **87%** until the wider port audit changes materially.

## Checkpoint 2026-09-25 — Mining Dimension resource validation and flight-coordinate parity

- **MINING DIMENSION**: clarified that the dimension and its type are dynamic datapack registry entries in `data/copyl/dimension/mining.json` and `data/copyl/dimension_type/mining.json`; removed comments that incorrectly described the feature as paused.
- **CI VALIDATION**: Actions #612 now checks the final built JAR for all four dimension resources (dimension, dimension type, fixed biome, noise settings), parses each as JSON, verifies registry references, and asserts matching Y=0..255 bounds. Actions #612 passed.
- **BEHAVIOR PARITY**: bytecode comparison found that Firefly uses world RNG for its daytime despawn roll and that Firefly, Bird, Butterfly, Moth, and Mosquito truncate coordinates toward zero before flight-target distance and target selection. The port now preserves those sources/conversions, including negative coordinates.
- **BUILD VALIDATION**: Actions #613, #615, #616, and #617 passed with compilation, final JAR checks, dimension-resource validation, and artifact upload. Action #614 was canceled by the workflow concurrency rule; #615 successfully validated the same head.
- **LIMITS**: this verifies packaging and cross-references for the dimension but does not replace a live-world terrain, teleport, client/server, or multiplayer test. Entity behavior audits and the final visual/textures pass remain open.
- **OVERALL PROGRESS**: remains approximately 87%; this batch closes narrow compatibility gaps without changing the estimate for the larger unfinished port.


### Paridad de vuelo — Mothra y Brutalfly — 2026-09-25

- **CORREGIDO CONTRA BYTECODE**: ambas criaturas vuelven a seleccionar destino si la tirada aleatoria tiene éxito **o** el destino está a menos de 3 bloques; antes el port exigía ambas condiciones. Se conserva la conversión `d2i` y el consumo de RNG previo al chequeo de distancia.
- **MOTHRA**: el combate solo corre en el camino donde no se recalcula el destino. La selección de jugador creativo habilita la búsqueda de otro blanco; un jugador visible bloqueado por terreno sigue siendo el seleccionado y no provoca búsqueda de mobs. `PlayNicely` continúa afectando solo a la adquisición de objetivos no jugador.
- **BRUTALFLY**: el jugador creativo se descarta antes del fallback de mobs; un jugador más cercano oculto por terreno conserva la selección original, sin redirigir el vuelo ni atacar ese tick. El combate original no bloquea la dificultad Pacífica y usa el disparo probabilístico para jugadores.
- **VUELO**: si se agotan los 30 intentos de Brutalfly o los 50 de Mothra, se conserva el último candidato, igual que el campo `currentFlightTarget` del original. Los rayos de visibilidad terminan en las coordenadas enteras del bloque, no en su centro.
- **SOURCE**: commits `5989e5af`, `6e0fcc37`, `e115f8a4`, `5fe46033`, `2b7e15bc`, `b7c527c9`, `d83b45c2`, `2b240ad3` y `da74b1a1`.
- **VALIDACIÓN**: comparados los métodos de IA y selección de objetivos con `Mothra.func_70619_bc`, `Brutalfly.func_70619_bc` y `findSomethingToAttack` del JAR de referencia; los archivos actualizados se releyeron desde la rama GitHub.
- **BUILD/RUNTIME**: el conector devuelve listas vacías tanto para el estado combinado como para runs asociados a estos commits; no hay evidencia para declarar verde este bloque. Runtime y verificación visual siguen abiertos.
- **AVANCE GLOBAL ESTIMADO**: 87%; estas correcciones cierran diferencias puntuales de IA, sin sustituir la auditoría del resto del port.


### Spyro — coordenadas, RNG de vuelo y búsqueda de agua — 2026-09-25

- **CORREGIDO**: posiciones de Spyro, dueño, blanco y punto de vuelo ahora usan truncamiento JVM `d2i`; el chequeo de llegada conserva la distancia del JAR desde la posición truncada y el umbral 2.1. Actividad normal ya no elige rutas de vuelo; seguir al dueño y la amortiguación vertical se alinearon con `do_movement`/`onUpdate`.
- **CORREGIDO**: los offsets de vuelo consumen primero Z, luego X, después los signos Z/X y finalmente Y, usando tiradas del mundo como el bytecode. El tono del disparo consume el RNG de entidad, como en el original.
- **CORREGIDO**: búsqueda de agua parte de `(int)x, (int)y - 1, (int)z` y replica el salto de radios del original: 1, 2, 3, 4, 5, 6, 8, 10.
- **VALIDACIÓN**: comparados `Spyro.func_70030_z`, `do_movement`, `scan_it`, `findSomethingToAttack` e `isSuitableTarget` del JAR; el port ahora conserva la prueba de sentidos más el segundo raycast a coordenadas exactas.
- **SOURCE**: commits `a045e1ee`, `ea5e74ce`, `bd031fa6`, `2a324f42` y `4f84801c`.
- **BUILD/RUNTIME**: estado combinado y ejecuciones asociadas no aparecen en la respuesta del conector GitHub para estos commits. No se marca el build verde; runtime y multiplayer siguen pendientes.
- **AVANCE GLOBAL ESTIMADO**: 87%; continúa pendiente la auditoría del resto de entidades y sistemas.
