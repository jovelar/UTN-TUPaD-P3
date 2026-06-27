# Parcial 2 — Programación III: Repositorios y ABM de Categorías y Productos

Aplicación de consola en Java que implementa, sobre el proyecto Gradle del TP de la Unidad 8, los repositorios JPA para las entidades `Categoria` y `Producto`. Expone un menú que permite realizar el ABM (alta, baja lógica, modificación, listado) de ambas entidades y una consulta JPQL que filtra los productos activos por categoría.

## Características

- `BaseRepository<T>`: repositorio genérico con CRUD común (`guardar`, `buscarPorId`, `listarActivos`, `eliminarLogico`), manejo de transacciones y cierre del `EntityManager`.
- `CategoriaRepository` y `ProductoRepository`: extienden el repositorio base.
- `buscarPorCategoria(Long categoriaId)`: consulta JPQL tipada con parámetro nombrado que devuelve los productos activos de una categoría.
- Baja lógica en ambas entidades mediante el campo `eliminado`.

## Requisitos

- JDK 17 o superior
- Gradle (o el wrapper `gradlew` incluido en el proyecto)
- Base de datos H2 (embebida, no requiere instalación adicional)

## Estructura del proyecto

```
src/main/java/com/tp/jpa/
├── model/          # entidades (TP base, no modificar)
├── model/enums/    # enums (TP base, no modificar)
├── util/           # JPAUtil (TP base, no modificar)
├── repository/     # BaseRepository, CategoriaRepository, ProductoRepository
└── Main.java       # menú de consola
```

## Cómo ejecutarlo

Desde la raíz del proyecto:

```bash
# Linux / macOS
./gradlew run

# Windows
gradlew.bat run
```

También se puede ejecutar la clase `Main` directamente desde el IDE (IntelliJ IDEA).

Al iniciar, la aplicación muestra el menú principal con los submenús de **Categorías**, **Productos** y **Reportes** (consulta de productos por categoría).

## Uso del menú

1. **Categorías** → alta, baja lógica, modificación y listado de categorías activas.
2. **Productos** → alta (asociando una categoría existente), baja lógica, modificación y listado de productos activos.
3. **Reportes → Productos por categoría** → selecciona una categoría activa y lista sus productos (ID, nombre, precio y stock).

## Video de presentación

[Ver video de presentación](https://youtu.be/AbcKO4Cuc0Y)

## Autor

[Ovelar, Isaias Javier]
