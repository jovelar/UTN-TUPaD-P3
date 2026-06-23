import type { CartItem } from "../../../types/product";
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

const raw = getUSer();
if (!raw) navigate("/src/pages/auth/login/login.html");

const user = raw ? JSON.parse(raw) : null;
const spanUserName = document.getElementById("userName");
if (spanUserName && user) spanUserName.textContent = user.nombre;

document.getElementById("logoutButton")?.addEventListener("click", logout);

const cartColumns = document.querySelector<HTMLElement>("#cartColumns");

function modificarCantidad(id: number, accion: "sumar" | "restar") {
    const storage = localStorage.getItem("carrito");
    let carrito: CartItem[] = storage ? JSON.parse(storage) : [];
    const item = carrito.find(p => p.id === id);

    if (item) {
        if (accion === "sumar") item.cantidad += 1;
        else if (accion === "restar" && item.cantidad > 1) item.cantidad -= 1;
        localStorage.setItem("carrito", JSON.stringify(carrito));
        renderizarCarrito();
    }
}

function eliminarDelCarrito(id: number) {
    const storage = localStorage.getItem("carrito");
    let carrito: CartItem[] = storage ? JSON.parse(storage) : [];
    localStorage.setItem("carrito", JSON.stringify(carrito.filter(p => p.id !== id)));
    renderizarCarrito();
}

function asignarListeners() {
    document.querySelectorAll<HTMLElement>(".cart-item__qty-btn--plus").forEach(btn => {
        btn.onclick = () => modificarCantidad(Number(btn.dataset.id), "sumar");
    });
    document.querySelectorAll<HTMLElement>(".cart-item__qty-btn--minus").forEach(btn => {
        btn.onclick = () => modificarCantidad(Number(btn.dataset.id), "restar");
    });
    document.querySelectorAll<HTMLElement>(".cart-item__delete").forEach(btn => {
        btn.onclick = () => eliminarDelCarrito(Number(btn.dataset.id));
    });

    const botonLimpiar = document.querySelector<HTMLButtonElement>("#botonLimpiar");
    if (botonLimpiar) {
        botonLimpiar.onclick = () => {
            localStorage.removeItem("carrito");
            renderizarCarrito();
        };
    }
}

function renderizarCarrito() {
    const storage = localStorage.getItem("carrito");
    const carrito: CartItem[] = storage ? JSON.parse(storage) : [];

    if (!cartColumns) return;

    if (carrito.length === 0) {
        cartColumns.innerHTML = `
            <div class="cart--empty">
                <h2>El carrito está vacío</h2>
                <a href="/src/pages/store/home/home.html" class="btn btn--back">VOLVER A LA TIENDA</a>
            </div>`;
        return;
    }

    let htmlItems = "";
    let total = 0;

    carrito.forEach(item => {
        total += item.precio * item.cantidad;
        htmlItems += `
            <article class="cart-item">
                <img src="/src/img/${item.imagen}" width="50" alt="${item.nombre}">
                <div class="cart-item__details">
                    <h5>${item.nombre}</h5>
                    <h6>${item.categoriaNombre ?? ""}</h6>
                    <p>$${item.precio}</p>
                </div>
                <div class="cart-item__quantity">
                    <button class="cart-item__qty-btn cart-item__qty-btn--plus" data-id="${item.id}">+</button>
                    <span>${item.cantidad}</span>
                    <button class="cart-item__qty-btn cart-item__qty-btn--minus" data-id="${item.id}">-</button>
                </div>
                <button class="cart-item__delete" data-id="${item.id}">Eliminar</button>
            </article>`;
    });

    cartColumns.innerHTML = `
        <section class="cart__items">${htmlItems}</section>
        <aside class="cart__summary">
            <h4>Resumen</h4>
            <span>Subtotal: $${total}</span>
            <hr>
            <strong>TOTAL: $${total}</strong>
            <div class="cart__summary-actions">
                <button class="btn btn--primary" type="button">FINALIZAR PEDIDO</button>
                <button class="btn btn--danger" type="button" id="botonLimpiar">Limpiar carrito</button>
            </div>
        </aside>`;

    asignarListeners();
}

renderizarCarrito();
