# Food Store — Frontend (TPI Programación 3)

## ✍️ Descripción

Este es el parcial 1 de programacion 3, en donde se buscar mostrar el uso de la tienda de productos, y el carrito de compras

Se utilizo como base el repositorio https://github.com/chiro45/proteger_rutas

Hace uso de **Vite** y **TypeScript**.

Video de funcionamiento y explicacion https://youtu.be/3lvr2IRDGX0

---

## 🚀 Instalación y Uso

### 1. Instalar Dependencias del Proyecto

En la carpeta raíz del proyecto, instala las dependencias:

```bash
npm install
```

### 2. Ejecutar el Proyecto

Para iniciar el servidor de desarrollo de Vite, ejecuta:

```bash
npm run dev
```

La aplicación estará disponible en la URL que aparezca en la terminal (generalmente `http://localhost:5173`).

---

## 🔑 Credenciales de prueba

| Rol | Email | Contraseña |
|---|---|---|
| ADMIN | admin@admin.com | 123456 |
| USUARIO | cliente@food.com | cliente123 |

## 🚚 Envío

El costo de envío es un valor fijo definido en el frontend (`ENVIO = 500` en `src/pages/store/cart/cart.ts`), ya que en esta iteración no hay backend que lo calcule.

---


## 📁 Estructura del Proyecto

```
/
├── src/
│   ├── pages/                # Contiene las páginas de la aplicación
│   │   ├── admin/            # Páginas solo para administradores
│   │   ├── auth/             # Páginas de autenticación (login, registro)
│   │   ├── client/           # Páginas solo para clientes
|   |   └── Store/            # Paginas de store y cart
│   ├── types/                # Define las interfaces y tipos (IUser, Rol)
│   └── utils/                # Lógica reutilizable
│       ├── auth.ts           # Función principal de verificación de rol y sesión
│       ├── localStorage.ts   # Funciones para leer/escribir en localStorage
│       └── navigate.ts       # Función para redirigir al usuario
├── package.json              # Dependencias y scripts
└── README.md                 # Este archivo
```
