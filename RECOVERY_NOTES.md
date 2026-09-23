# RECOVERY_NOTES.md

## Fuentes recuperadas

- `Lclient-main.zip`: source limpio de CopyL 3.3.0.
- `files(1).zip`: notas/avance parcial anterior.
- `Orespawn-1.12.2-V0.8-ConquerantFix.jar`: JAR real usado para bytecode/assets.

## Estrategia

El paquete `client/` proviene del source limpio de CopyL. El contenido nuevo vive bajo
`common/` y se fue corrigiendo contra el bytecode real de OreSpawn.

## Problemas del estado anterior que ya no se arrastran

1. `ExtremeTorchBlock` tenía inicialmente el orden de constructor incorrecto.
2. `Class#getName()` no garantizaba inicializar los `RegistryObject`.
3. `BasicStone` había sido interpretado erróneamente como contenido registrado.
4. Varios stats/XP eran estimaciones; ahora los de Fase 3 provienen del bytecode real.
5. Se creyó que faltaban texturas Emerald/Ultimate chest; estaban bajo nombres distintos.

## Build

El source original no trae `gradle-wrapper.jar`. `gradlew` por sí solo no puede arrancar sin
ese JAR y el sandbox no tiene red para recuperarlo. En la máquina del proyecto, donde ya exista
Gradle 8.1.1, puede usarse `gradle build`; alternativamente se puede regenerar/restaurar el
wrapper y usar `./gradlew build`.
