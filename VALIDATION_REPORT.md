# VALIDATION_REPORT.md

## Fase 3 — validación estática

Ejecutada sobre el workspace integrado antes de empaquetar.

- Java source files: 28.
- JSON resources: 135.
- PNG migrados para Fase 3: 52.
- Recipes: 41.
- Loot tables: 7.
- Tag JSON: 19.
- JSON inválidos: 0.
- Referencias `copyl:` a modelos/texturas inexistentes: 0.
- Referencias `copyl:` desconocidas dentro de recipes: 0.
- IDs jugables detectados sin item model esperado: 0.
- Imports/API legacy 1.12.2 en `src/main/java`: 0 detectados por búsqueda.
- Workflow YAML: parse válido.

## Lo que esta validación NO sustituye

No sustituye `gradle build`, `runClient` ni `runServer`. El source recuperado no incluía
`gradle-wrapper.jar`, y el sandbox actual no puede descargar la distribución/dependencias para
ejecutar ForgeGradle. Por eso el checkpoint no afirma `BUILD SUCCESSFUL` todavía.

El workflow de GitHub fue adaptado para instalar Gradle 8.1.1 mediante `gradle/actions/setup-gradle`
y no depender del wrapper incompleto del source recuperado.
