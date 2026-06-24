// TODO: implementar historial de pedidos del cliente
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

type Estado = "PENDIENTE" | "CONFIRMADO" | "TERMINADO" | "CANCELADO";

interface DetallePedido {
    idProducto: number;
    cantidad: number;
    subtotal: number;
}

interface Pedido {
    id: number;
    fecha: string;
    estado: Estado;
    total: number;
    formaPago: string;
    idUsuario: number;
    detalles: DetallePedido[];
    eliminado?: boolean;
}

// ----- Guard -----
const raw = getUSer();
if (!raw) navigate("/src/pages/auth/login/login.html");
const user = raw ? JSON.parse(raw) : null;

const spanUserName = document.getElementById("userName");
if (spanUserName && user) spanUserName.textContent = user.nombre;
document.getElementById("logoutButton")?.addEventListener("click", logout);

const adminLink = document.getElementById("adminLink");
if (user?.role === "ADMIN" && adminLink) adminLink.style.display = "";

// ----- Referencias al DOM -----
const listaPedidos = document.getElementById("listaPedidos") as HTMLElement;
const filtroEstado = document.getElementById("filtroEstado") as HTMLSelectElement;
const modal = document.getElementById("modalDetalle") as HTMLElement;
const modalContenido = document.getElementById("modalContenido") as HTMLElement;

let pedidos: Pedido[] = [];

const badgeClase: Record<Estado, string> = {
    PENDIENTE: "badge--pendiente",
    CONFIRMADO: "badge--confirmado",
    TERMINADO: "badge--terminado",
    CANCELADO: "badge--cancelado",
};

// ----- Render de las tarjetas -----
function renderizarPedidos() {
    const estadoFiltro = filtroEstado.value;

    let lista = pedidos.filter(p => !p.eliminado);
    if (estadoFiltro) {
        lista = lista.filter(p => p.estado === estadoFiltro);
    }

    // Más recientes primero
    lista.sort((a, b) => b.fecha.localeCompare(a.fecha));

    if (!lista.length) {
        listaPedidos.innerHTML = `
            <div class="cart--empty">
                <h2>No tenés pedidos todavía</h2>
                <a href="/src/pages/store/home/home.html" class="btn btn--back">IR A LA TIENDA</a>
            </div>`;
        return;
    }

    listaPedidos.innerHTML = lista.map(pedido => {
        const cantProductos = pedido.detalles.reduce((sum, d) => sum + d.cantidad, 0);
        return `
            <article class="order-card" data-id="${pedido.id}">
                <div class="order-card__header">
                    <span class="order-card__id">Pedido #${pedido.id}</span>
                    <span class="badge ${badgeClase[pedido.estado]}">${pedido.estado}</span>
                </div>
                <p>Fecha: ${pedido.fecha}</p>
                <p>${cantProductos} producto(s)</p>
                <span class="order-card__total">$${pedido.total}</span>
            </article>
        `;
    }).join("");

    listaPedidos.querySelectorAll<HTMLElement>(".order-card").forEach(card => {
        card.addEventListener("click", () => abrirModalDetalle(Number(card.dataset.id)));
    });
}

// ----- Modal de detalle -----
function abrirModalDetalle(id: number) {
    const pedido = pedidos.find(p => p.id === id);
    if (!pedido) return;

    const productosHtml = pedido.detalles.map(d =>
        `<li>Producto #${d.idProducto} — Cantidad: ${d.cantidad} — Subtotal: $${d.subtotal}</li>`
    ).join("");

    modalContenido.innerHTML = `
        <h3>Detalle del Pedido #${pedido.id}</h3>
        <p>Estado: <span class="badge ${badgeClase[pedido.estado]}">${pedido.estado}</span></p>
        <p>Fecha: ${pedido.fecha}</p>
        <p>Forma de pago: ${pedido.formaPago}</p>
        <ul>${productosHtml}</ul>
        <p class="order-card__total">Total: $${pedido.total}</p>
    `;

    modal.style.display = "flex";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ----- Eventos -----
filtroEstado.addEventListener("change", renderizarPedidos);
document.getElementById("cerrarModal")?.addEventListener("click", cerrarModal);

// ----- Inicio: fetch + pedidos de localStorage, filtrados por usuario -----
async function init() {
    const res = await fetch("/data/pedidos.json");
    const pedidosJSON: Pedido[] = await res.json();

    // Pedidos generados en el checkout (guardados en localStorage)
    const guardados = localStorage.getItem("pedidos");
    const pedidosLocal: Pedido[] = guardados ? JSON.parse(guardados) : [];

    // Combinar ambas fuentes y filtrar por el usuario en sesión
    pedidos = [...pedidosJSON, ...pedidosLocal].filter(p => p.idUsuario === user.id);

    renderizarPedidos();
}

init();