# CopyL + OreSpawn Forge 1.20.1

Repositorio de trabajo para integrar el contenido del OreSpawn 1.12.2 proporcionado dentro de CopyL Forge 1.20.1.

La Mining Dimension permanece pausada intencionalmente. El contenido dependiente de ella puede portarse y quedar preparado, pero no debe generarse artificialmente en el Overworld.

## Build automático

GitHub Actions compila el proyecto con:

- Java 17 (Temurin)
- Gradle 8.1.1
- Forge 1.20.1 / 47.4.10

Se ejecuta con cada push a `main`, `work/**` y `feature/**`, además de pull requests contra `main` y ejecuciones manuales.

Cuando el build termina correctamente, el JAR queda disponible como artifact de la ejecución en la pestaña **Actions**.

## Build local

Desde la raíz del proyecto:

```bash
gradle build --no-daemon --stacktrace
```

El JAR queda en:

```text
build/libs/
```

Si se está trabajando desde un ZIP/checkpoint, primero hay que extraerlo y ejecutar el mismo comando dentro de la carpeta extraída.

## Estado del repositorio

El workflow de compilación ya está preparado. El siguiente paso es subir al repositorio el contenido del checkpoint actual del port para continuar trabajando directamente desde GitHub.
