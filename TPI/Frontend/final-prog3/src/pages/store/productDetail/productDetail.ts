// // TODO: implementar detalle de producto
// import type { Product, CartItem } from "../../../types/product";
// import { getUSer } from "../../../utils/localStorage";
// import { navigate } from "../../../utils/navigate";

// const contenedor = document.querySelector<HTMLElement>("#detalleProducto");

// function initGuard() {
//     const raw = getUSer();
//     if (!raw) {
//         navigate("/src/pages/auth/login/login.html");
//         return false;
//     }
//     return true;
// }

// function agregarAlCarrito(producto: Product, cantidad: number) {
//     const storage = localStorage.getItem("carrito");
//     let carrito: CartItem[] = storage ? JSON.parse(storage) : [];
//     const itemExistente = carrito.find(item => item.id === producto.id);

//     if (itemExistente) {
//         itemExistente.cantidad += cantidad;
//     } else {
//         carrito.push({ ...producto, cantidad });
//     }

//     localStorage.setItem("carrito", JSON.stringify(carrito));
//     alert("Producto agregado al carrito");
// }

// function renderizarProducto(producto: Product) {
//     if (!contenedor) return;

//     const agotado = !producto.disponible || producto.stock === 0;

//     contenedor.innerHTML = `
//         <img src="${producto.imagen}" width="300" alt="${producto.nombre}">
//         <div class="product-detail__info">
//             <h2>${producto.nombre}</h2>
//             <p>${producto.descripcion}</p>
//             <p class="product-detail__price">$${producto.precio}</p>
//             <p>Stock disponible: ${producto.stock}</p>
//             <p>Estado: ${agotado ? "No disponible" : "Disponible"}</p>

//             <div class="product-detail__quantity">
//                 <label for="cantidad">Cantidad:</label>
//                 <input type="number" id="cantidad" value="1" min="1" max="${producto.stock}" ${agotado ? "disabled" : ""}>
//             </div>

//             <button id="btnAgregar" class="btn btn--primary" ${agotado ? "disabled" : ""}>
//                 Agregar al Carrito
//             </button>
//             <a href="/src/pages/store/home/home.html" class="btn--back">Volver</a>
//         </div>
//     `;

//     const inputCantidad = document.querySelector<HTMLInputElement>("#cantidad");
//     const btnAgregar = document.querySelector<HTMLButtonElement>("#btnAgregar");

//     btnAgregar?.addEventListener("click", () => {
//         const cantidad = Number(inputCantidad?.value ?? 1);
//         if (cantidad < 1 || cantidad > producto.stock) {
//             alert("Cantidad inválida");
//             return;
//         }
//         agregarAlCarrito(producto, cantidad);
//     });
// }

// async function renderizarPagina() {
//     if (!initGuard()) return;
//     if (!contenedor) return;

//     // 1. Leer el id de la URL
//     const params = new URLSearchParams(window.location.search);
//     const id = Number(params.get("id"));

//     // 2. Fetch y buscar el producto
//     const res = await fetch("/data/productos.json");
//     const productos: Product[] = await res.json();
//     const producto = productos.find(p => p.id === id && !p.eliminado);

//     // 3. Si no existe, mensaje de error
//     if (!producto) {
//         contenedor.innerHTML = `<p>Producto no encontrado. <a href="/src/pages/store/home/home.html">Volver al catálogo</a></p>`;
//         return;
//     }

//     renderizarProducto(producto);
// }

// renderizarPagina();

import type { Product, CartItem } from "../../../types/product";
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";

const contenedor = document.querySelector<HTMLElement>("#detalleProducto");

function initGuard() {
    const raw = getUSer();
    if (!raw) {
        navigate("/src/pages/auth/login/login.html");
        return false;
    }
    const user = JSON.parse(raw);
    if (user.role === "ADMIN") document.body.classList.add("es-admin");
    return true;
}

function agregarAlCarrito(producto: Product, cantidad: number) {
    const storage = localStorage.getItem("carrito");
    let carrito: CartItem[] = storage ? JSON.parse(storage) : [];
    const itemExistente = carrito.find(item => item.id === producto.id);

    if (itemExistente) {
        itemExistente.cantidad += cantidad;
    } else {
        carrito.push({ ...producto, cantidad });
    }

    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert("Producto agregado al carrito");
}

function renderizarProducto(producto: Product) {
    if (!contenedor) return;

    const agotado = !producto.disponible || producto.stock === 0;
    const badgeClass = agotado ? "badge--cancelado" : "badge--terminado";
    const badgeTexto = agotado ? "No disponible" : `Disponible (Stock: ${producto.stock})`;

    contenedor.innerHTML = `
        <img src="${producto.imagen}" alt="${producto.nombre}">
        <div class="product-detail__info">
            <h2>${producto.nombre}</h2>
            <p class="product-detail__price">$${producto.precio}</p>
            <span class="badge ${badgeClass}">${badgeTexto}</span>
            <p class="product-detail__description">${producto.descripcion}</p>

            <div class="product-detail__quantity">
                <label for="cantidad">Cantidad:</label>
                <div class="product-detail__stepper">
                    <button id="btnMenos" type="button" ${agotado ? "disabled" : ""}>-</button>
                    <input type="number" id="cantidad" value="1" min="1" max="${producto.stock}" ${agotado ? "disabled" : ""}>
                    <button id="btnMas" type="button" ${agotado ? "disabled" : ""}>+</button>
                </div>
            </div>

            <div class="product-detail__actions">
                <button id="btnAgregar" class="btn btn--primary" ${agotado ? "disabled" : ""}>
                    Agregar al Carrito
                </button>
                <a href="/src/pages/store/home/home.html" class="btn btn--back">← Volver</a>
            </div>
        </div>
    `;

    const inputCantidad = document.querySelector<HTMLInputElement>("#cantidad");
    const btnMenos = document.querySelector<HTMLButtonElement>("#btnMenos");
    const btnMas = document.querySelector<HTMLButtonElement>("#btnMas");
    const btnAgregar = document.querySelector<HTMLButtonElement>("#btnAgregar");

    // Botón "-" : baja la cantidad sin bajar de 1
    btnMenos?.addEventListener("click", () => {
        const actual = Number(inputCantidad?.value ?? 1);
        if (inputCantidad && actual > 1) inputCantidad.value = String(actual - 1);
    });

    // Botón "+" : sube la cantidad sin superar el stock
    btnMas?.addEventListener("click", () => {
        const actual = Number(inputCantidad?.value ?? 1);
        if (inputCantidad && actual < producto.stock) inputCantidad.value = String(actual + 1);
    });

    btnAgregar?.addEventListener("click", () => {
        const cantidad = Number(inputCantidad?.value ?? 1);
        if (cantidad < 1 || cantidad > producto.stock) {
            alert("Cantidad inválida");
            return;
        }
        agregarAlCarrito(producto, cantidad);
    });
}

async function renderizarPagina() {
    if (!initGuard()) return;
    if (!contenedor) return;

    // 1. Leer el id de la URL
    const params = new URLSearchParams(window.location.search);
    const id = Number(params.get("id"));

    // 2. Fetch y buscar el producto
    const res = await fetch("/data/productos.json");
    const productos: Product[] = await res.json();
    const producto = productos.find(p => p.id === id && !p.eliminado);

    // 3. Si no existe, mensaje de error
    if (!producto) {
        contenedor.innerHTML = `<p>Producto no encontrado. <a href="/src/pages/store/home/home.html">Volver al catálogo</a></p>`;
        return;
    }

    renderizarProducto(producto);
}

renderizarPagina();