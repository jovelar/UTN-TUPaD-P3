// TODO: implementar gestión de pedidos admin
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

interface Usuario {
    id: number;
    nombre: string;
    apellido: string;
}

// ----- Guard: solo ADMIN -----
const raw = getUSer();
if (!raw) navigate("/src/pages/auth/login/login.html");
const user = raw ? JSON.parse(raw) : null;
if (user && user.role !== "ADMIN") navigate("/src/pages/store/home/home.html");

const spanUserName = document.getElementById("userName");
if (spanUserName && user) spanUserName.textContent = user.nombre;
document.getElementById("logoutButton")?.addEventListener("click", logout);

// ----- Referencias al DOM -----
const listaPedidos = document.getElementById("listaPedidos") as HTMLElement;
const filtroEstado = document.getElementById("filtroEstado") as HTMLSelectElement;
const modal = document.getElementById("modalDetalle") as HTMLElement;
const modalContenido = document.getElementById("modalContenido") as HTMLElement;

// Estado en memoria
let pedidos: Pedido[] = [];
let usuarioMap: Map<number, string> = new Map();

// Clase de badge según estado
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

    // Ordenar por fecha, más recientes primero
    lista.sort((a, b) => b.fecha.localeCompare(a.fecha));

    if (!lista.length) {
        listaPedidos.innerHTML = `<p>No hay pedidos para mostrar.</p>`;
        return;
    }

    listaPedidos.innerHTML = lista.map(pedido => {
        const cliente = usuarioMap.get(pedido.idUsuario) ?? "Cliente desconocido";
        const cantProductos = pedido.detalles.reduce((sum, d) => sum + d.cantidad, 0);
        return `
            <article class="order-card" data-id="${pedido.id}">
                <div class="order-card__header">
                    <span class="order-card__id">Pedido #${pedido.id}</span>
                    <span class="badge ${badgeClase[pedido.estado]}">${pedido.estado}</span>
                </div>
                <p>Cliente: ${cliente}</p>
                <p>Fecha: ${pedido.fecha}</p>
                <p>${cantProductos} producto(s)</p>
                <span class="order-card__total">$${pedido.total}</span>
            </article>
        `;
    }).join("");

    // Click en tarjeta abre el modal de detalle
    listaPedidos.querySelectorAll<HTMLElement>(".order-card").forEach(card => {
        card.addEventListener("click", () => abrirModalDetalle(Number(card.dataset.id)));
    });
}

// ----- Modal de detalle con cambio de estado -----
function abrirModalDetalle(id: number) {
    const pedido = pedidos.find(p => p.id === id);
    if (!pedido) return;

    const cliente = usuarioMap.get(pedido.idUsuario) ?? "Cliente desconocido";

    const productosHtml = pedido.detalles.map(d =>
        `<li>Producto #${d.idProducto} — Cantidad: ${d.cantidad} — Subtotal: $${d.subtotal}</li>`
    ).join("");

    modalContenido.innerHTML = `
        <h3>Detalle del Pedido #${pedido.id}</h3>
        <p>Cliente: ${cliente}</p>
        <p>Fecha: ${pedido.fecha}</p>
        <p>Forma de pago: ${pedido.formaPago}</p>
        <ul>${productosHtml}</ul>
        <p class="order-card__total">Total: $${pedido.total}</p>

        <div class="form__group">
            <label for="nuevoEstado">Cambiar estado:</label>
            <select id="nuevoEstado">
                <option value="PENDIENTE">Pendiente</option>
                <option value="CONFIRMADO">Confirmado</option>
                <option value="TERMINADO">Terminado</option>
                <option value="CANCELADO">Cancelado</option>
            </select>
        </div>
        <button id="btnGuardarEstado" class="btn btn--primary">Actualizar Estado</button>
    `;

    // Dejar seleccionado el estado actual
    const selectEstado = document.getElementById("nuevoEstado") as HTMLSelectElement;
    selectEstado.value = pedido.estado;

    // Guardar el cambio de estado (en memoria)
    document.getElementById("btnGuardarEstado")?.addEventListener("click", () => {
        pedido.estado = selectEstado.value as Estado;
        cerrarModal();
        renderizarPedidos();
    });

    modal.style.display = "flex";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ----- Eventos -----
filtroEstado.addEventListener("change", renderizarPedidos);
document.getElementById("cerrarModal")?.addEventListener("click", cerrarModal);

// ----- Inicio: fetch de pedidos y usuarios -----
async function init() {
    const [resPedidos, resUsuarios] = await Promise.all([
        fetch("/data/pedidos.json"),
        fetch("/data/usuarios.json"),
    ]);
    pedidos = await resPedidos.json();
    const usuarios: Usuario[] = await resUsuarios.json();

    usuarioMap = new Map(usuarios.map(u => [u.id, `${u.nombre} ${u.apellido}`]));

    renderizarPedidos();
}

init();