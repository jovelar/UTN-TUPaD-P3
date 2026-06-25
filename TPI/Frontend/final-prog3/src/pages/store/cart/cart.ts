import type { CartItem } from "../../../types/product";
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

const ENVIO = 500; // costo fijo de envío (documentar en el README)

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

    const botonPagar = document.querySelector<HTMLButtonElement>("#botonPagar");
    if (botonPagar) {
        botonPagar.onclick = abrirCheckout;
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
    let subtotal = 0;

    carrito.forEach(item => {
        subtotal += item.precio * item.cantidad;
        htmlItems += `
            <article class="cart-item">
                <img src="${item.imagen}" width="50" alt="${item.nombre}">
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

    const total = subtotal + ENVIO;

    cartColumns.innerHTML = `
        <section class="cart__items">${htmlItems}</section>
        <aside class="cart__summary">
            <h4>Resumen</h4>
            <span>Subtotal: $${subtotal}</span>
            <span>Envío: $${ENVIO}</span>
            <hr>
            <strong>TOTAL: $${total}</strong>
            <div class="cart__summary-actions">
                <button class="btn btn--primary" type="button" id="botonPagar">PROCEDER AL PAGO</button>
                <button class="btn btn--danger" type="button" id="botonLimpiar">Limpiar carrito</button>
            </div>
        </aside>`;

    asignarListeners();
}

// ----- Checkout -----
function abrirCheckout() {
    // Crear el modal si no existe
    let modal = document.getElementById("modalCheckout");
    if (!modal) {
        modal = document.createElement("div");
        modal.id = "modalCheckout";
        modal.className = "modal";
        modal.innerHTML = `
            <div class="modal__content">
                <button class="modal__close" id="cerrarCheckout">×</button>
                <h3>Completar Pedido</h3>
                <form id="formCheckout">
                    <div class="form__group">
                        <label for="telefono">Teléfono</label>
                        <input type="text" id="telefono" required placeholder="Ej: 1122334455">
                    </div>
                    <div class="form__group">
                        <label for="direccion">Dirección de Entrega</label>
                        <textarea id="direccion" required placeholder="Calle, número, piso, depto"></textarea>
                    </div>
                    <div class="form__group">
                        <label for="formaPago">Forma de pago</label>
                        <select id="formaPago" required>
                            <option value="">Seleccione una opción</option>
                            <option value="TARJETA">Tarjeta</option>
                            <option value="TRANSFERENCIA">Transferencia</option>
                            <option value="EFECTIVO">Efectivo</option>
                        </select>
                    </div>
                    <div class="form__group">
                        <label for="notas">Notas adicionales (opcional)</label>
                        <textarea id="notas" placeholder="Instrucciones especiales, timbre, etc."></textarea>
                    </div>
                    <button type="submit" class="btn btn--primary">Confirmar Pedido</button>
                </form>
            </div>`;
        document.body.appendChild(modal);

        modal.querySelector("#cerrarCheckout")?.addEventListener("click", () => {
            modal!.style.display = "none";
        });

        modal.querySelector("#formCheckout")?.addEventListener("submit", (e) => {
            e.preventDefault();
            confirmarPedido();
        });
    }

    modal.style.display = "flex";
}

function confirmarPedido() {
    const storage = localStorage.getItem("carrito");
    const carrito: CartItem[] = storage ? JSON.parse(storage) : [];
    if (carrito.length === 0) return;

    const formaPago = (document.getElementById("formaPago") as HTMLSelectElement).value;
    if (!formaPago) { alert("Seleccioná una forma de pago."); return; }

    const telefono = (document.getElementById("telefono") as HTMLInputElement).value;
    if (!/^\d+$/.test(telefono)) { alert("El teléfono solo puede contener números."); return; }

    const direccion = (document.getElementById("direccion") as HTMLTextAreaElement).value;
    const notas = (document.getElementById("notas") as HTMLTextAreaElement).value;

    const subtotal = carrito.reduce((sum, item) => sum + item.precio * item.cantidad, 0);
    const total = subtotal + ENVIO;

    // Generar el objeto pedido con la estructura de pedidos.json
    const pedido = {
        id: Date.now(), // id único simple
        fecha: new Date().toISOString().slice(0, 10), // YYYY-MM-DD
        estado: "PENDIENTE",
        total,
        formaPago,
        idUsuario: user.id,
        telefono,
        direccion,
        notas,
        detalles: carrito.map(item => ({
            idProducto: item.id,
            cantidad: item.cantidad,
            subtotal: item.precio * item.cantidad,
        })),
    };

    // Guardar el pedido en localStorage (se acumulan en "pedidos")
    const pedidosGuardados = localStorage.getItem("pedidos");
    const pedidos = pedidosGuardados ? JSON.parse(pedidosGuardados) : [];
    pedidos.push(pedido);
    localStorage.setItem("pedidos", JSON.stringify(pedidos));

    // Vaciar el carrito
    localStorage.removeItem("carrito");

    alert("¡Pedido confirmado!");
    navigate("/src/pages/client/orders/orders.html");
}

renderizarCarrito();