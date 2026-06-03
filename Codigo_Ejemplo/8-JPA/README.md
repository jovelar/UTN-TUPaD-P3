# 🚀 Ejercicios JPA - Relaciones entre Entidades

<div align="center">

![Java](https://img.shields.io/badge/Java-21+-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-3.1-007396?style=for-the-badge&logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-6.6+-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![H2](https://img.shields.io/badge/H2-Database-0078D4?style=for-the-badge&logo=database&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8+-02303A?style=for-the-badge&logo=gradle&logoColor=white)

</div>

## 📚 Descripción

Colección completa de ejercicios prácticos para aprender **Java Persistence API (JPA)** y los diferentes tipos de relaciones entre entidades. Cada proyecto está diseñado para demostrar conceptos específicos de mapeo objeto-relacional con ejemplos claros y funcionales.

---

## 🎯 Proyectos Incluidos

### 🔄 Relaciones Uno a Uno

#### 📁 `unoAunoBi` - Relación Uno a Uno Bidireccional

- **Entidades**: `Persona` ↔ `Domicilio`
- **Características**: Mapeo bidireccional con navegación en ambas direcciones
- **Caso de uso**: Una persona tiene un domicilio y un domicilio pertenece a una persona

#### 📁 `unoAunoUniM` - Relación Uno a Uno Unidireccional

- **Entidades**: `Persona` → `Domicilio`
- **Características**: Navegación unidireccional desde Persona hacia Domicilio
- **Caso de uso**: Acceso al domicilio solo desde la persona

### 🌳 Relaciones Uno a Muchos

#### 📁 `oneToManyBi` - Relación Uno a Muchos Bidireccional

- **Entidades**: `Persona` ↔ `Domicilio`
- **Características**: Una persona puede tener múltiples domicilios con navegación bidireccional
- **Caso de uso**: Persona con múltiples direcciones (casa, trabajo, etc.)

#### 📁 `oneToManyUni` - Relación Uno a Muchos Unidireccional

- **Entidades**: `Persona` → `Domicilio`
- **Características**: Navegación unidireccional desde el lado "uno"
- **Caso de uso**: Gestión de domicilios desde la perspectiva de la persona

### 🔗 Relaciones Muchos a Muchos

#### 📁 `ManyToManyBi` - Relación Muchos a Muchos Bidireccional

- **Entidades**: `Persona` ↔ `Curso`
- **Características**: Navegación bidireccional con tabla intermedia automática
- **Caso de uso**: Estudiantes inscritos en múltiples cursos

#### 📁 `manyToManyUni` - Relación Muchos a Muchos Unidireccional

- **Entidades**: `Persona` → `Curso`
- **Características**: Navegación unidireccional con tabla de unión
- **Caso de uso**: Inscripción de estudiantes vista desde la perspectiva del estudiante

---

## 🛠️ Tecnologías Utilizadas

| Tecnología      | Versión | Descripción                    |
| --------------- | ------- | ------------------------------ |
| **Java**        | 21+     | Lenguaje de programación       |
| **JPA**         | 3.1     | Especificación de persistencia |
| **Hibernate**   | 6.6+    | Implementación de JPA          |
| **H2 Database** | 2.3+    | Base de datos en memoria       |
| **Gradle**      | 8+      | Herramienta de construcción    |
| **Jakarta EE**  | 9+      | Especificaciones empresariales |

---

## 🚀 Cómo Ejecutar

### Prerrequisitos

- ☕ **Java 21** o superior
- 🐘 **Gradle 8** o superior
- 💻 **IDE** recomendado: IntelliJ IDEA o Eclipse

### Pasos para ejecutar cualquier proyecto:

1. **Navegar al proyecto deseado**:

   ```bash
   cd [nombre-del-proyecto]
   ```

2. **Construir el proyecto**:

   ```bash
   ./gradlew build
   ```

3. **Ejecutar la aplicación**:
   ```bash
   ./gradlew run
   ```

### Ejemplo rápido:

```bash
cd ManyToManyBi
./gradlew build
./gradlew run
```

---

## 📖 Estructura de Cada Proyecto

```
proyecto/
├── 📁 src/main/java/org/example/
│   ├── 📄 Main.java           # Clase principal con ejemplos
│   ├── 📄 Persona.java        # Entidad Persona
│   └── 📄 [Entidad].java      # Otras entidades (Curso/Domicilio)
├── 📁 src/main/resources/META-INF/
│   └── 📄 persistence.xml     # Configuración de JPA
├── 📁 data/                   # Base de datos H2 (generada automáticamente)
├── 📄 build.gradle           # Configuración de dependencias
└── 📄 settings.gradle        # Configuración del proyecto
```

---

## 💡 Conceptos Demostrados

### 🎯 Anotaciones JPA Principales

- `@Entity` - Marca una clase como entidad JPA
- `@Id` - Define la clave primaria
- `@GeneratedValue` - Estrategia de generación automática de IDs
- `@OneToOne` - Relación uno a uno
- `@OneToMany` - Relación uno a muchos
- `@ManyToMany` - Relación muchos a muchos
- `@JoinColumn` - Especifica la columna de unión
- `@JoinTable` - Define tabla intermedia para relaciones muchos a muchos

### 🔄 Tipos de Fetch

- **EAGER**: Carga inmediata de relaciones
- **LAZY**: Carga perezosa de relaciones

### 🎭 Direccionalidad

- **Unidireccional**: Navegación en una sola dirección
- **Bidireccional**: Navegación en ambas direcciones

---

## 🎨 Características Destacadas

- ✅ **Código limpio y documentado**
- ✅ **Ejemplos prácticos y funcionales**
- ✅ **Gestión automática de transacciones**
- ✅ **Base de datos H2 integrada**
- ✅ **Configuración completa de persistence.xml**
- ✅ **Manejo de excepciones robusto**

---

## 🔍 Casos de Uso Reales

| Proyecto          | Escenario Real                             |
| ----------------- | ------------------------------------------ |
| **unoAunoBi**     | 👤 Usuario con perfil único                |
| **oneToManyBi**   | 🏢 Empresa con múltiples empleados         |
| **ManyToManyBi**  | 🎓 Sistema de inscripciones universitarias |
| **manyToManyUni** | 📚 Biblioteca con préstamos de libros      |

---

## 📝 Notas Importantes

> 💡 **Tip**: Cada proyecto incluye su propia base de datos H2 que se crea automáticamente en la carpeta `data/`

> ⚠️ **Advertencia**: Las bases de datos se recrean en cada ejecución para mantener los ejemplos limpios

> 🔧 **Personalización**: Puedes modificar `persistence.xml` para usar otras bases de datos como MySQL o PostgreSQL

---

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Si tienes ideas para nuevos ejemplos o mejoras:

1. 🍴 Fork del proyecto
2. 🌟 Crea una rama para tu feature
3. 📝 Realiza tus cambios
4. 🚀 Envía un pull request

---

<div align="center">

### ⭐ ¡Si este proyecto te ayuda, considera darle una estrella!

**Desarrollado con ❤️ para aprender JPA**

</div>
