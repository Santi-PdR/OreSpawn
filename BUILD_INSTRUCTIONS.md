# BUILD_INSTRUCTIONS.md

## Requisitos

- Java/JDK 17 para Forge 1.20.1.
- Gradle 8.1.1 o un Gradle wrapper completo.
- Acceso a Maven Forge/Maven Central/Cloudsmith para resolver dependencias la primera vez.

## Compilar

Si tu sistema ya tiene Gradle 8.1.1 instalado:

```bash
gradle build
```

Si restaurás/generás `gradle/wrapper/gradle-wrapper.jar`:

```bash
./gradlew build
```

El JAR resultante debería quedar en `build/libs/`.

## Probar cliente

```bash
gradle runClient
```

o, con wrapper completo:

```bash
./gradlew runClient
```

## Probar servidor dedicado

```bash
gradle runServer
```

La primera compilación es el checkpoint antes de empezar Fase 4. Si Gradle devuelve errores,
el archivo completo de salida permite corregir firmas/API antes de sumar entidades.
