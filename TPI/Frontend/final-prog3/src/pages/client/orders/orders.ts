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
    telefono?: string;
    direccion?: string;
    notas?: string;
}

interface Producto {
    id: number;
    nombre: string;
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
let productoMap: Map<number, string> = new Map();

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

// ----- Mensajes del banner según estado del pedido -----
const bannerEstado: Record<Estado, { titulo: string; texto: string }> = {
    PENDIENTE: { titulo: " Tu pedido está siendo procesado", texto: "Te notificaremos cuando esté listo para entrega." },
    CONFIRMADO: { titulo: "Tu pedido fue confirmado", texto: "Lo estamos preparando." },
    TERMINADO: { titulo: " Tu pedido fue entregado", texto: "¡Gracias por tu compra!" },
    CANCELADO: { titulo: " Tu pedido fue cancelado", texto: "Si fue un error, contactanos." },
};

// ----- Modal de detalle -----
function abrirModalDetalle(id: number) {
    const pedido = pedidos.find(p => p.id === id);
    if (!pedido) return;

    // Lista de productos con su nombre real (cruzado contra productos.json)
    const productosHtml = pedido.detalles.map(d => {
        const nombre = productoMap.get(d.idProducto) ?? `Producto #${d.idProducto}`;
        const precioUnit = d.cantidad > 0 ? d.subtotal / d.cantidad : 0;
        return `
            <div class="order-detail__row">
                <div>
                    <div class="order-detail__row-name">${nombre}</div>
                    <div class="order-detail__row-sub">Cantidad: ${d.cantidad} × $${precioUnit}</div>
                </div>
                <span class="order-detail__row-price">$${d.subtotal}</span>
            </div>`;
    }).join("");

    // Desglose: subtotal = suma de subtotales de los detalles; envío = total - subtotal
    const subtotal = pedido.detalles.reduce((sum, d) => sum + d.subtotal, 0);
    const envio = pedido.total - subtotal;

    const banner = bannerEstado[pedido.estado];

    modalContenido.innerHTML = `
        <div class="order-detail__status">
            <span class="badge ${badgeClase[pedido.estado]}">${pedido.estado}</span>
        </div>
        <p class="order-detail__date"> ${pedido.fecha}</p>

        <div class="order-detail__section">
            <p class="order-detail__section-title"> Información de Entrega</p>
            <p class="order-detail__info-line"><strong>Dirección:</strong> ${pedido.direccion ?? "No especificada"}</p>
            <p class="order-detail__info-line"><strong>Teléfono:</strong> ${pedido.telefono ?? "No especificado"}</p>
            <p class="order-detail__info-line"><strong>Método de pago:</strong> ${pedido.formaPago}</p>
            <p class="order-detail__info-line"><strong>Notas:</strong> ${pedido.notas ? pedido.notas : "Sin notas"}</p>
        </div>

        <p class="order-detail__section-title"> Productos</p>
        ${productosHtml}

        <div class="order-detail__costs">
            <div class="order-detail__cost-line"><span>Subtotal:</span><span>$${subtotal}</span></div>
            <div class="order-detail__cost-line"><span>Envío:</span><span>$${envio}</span></div>
            <div class="order-detail__cost-total"><span>Total:</span><span>$${pedido.total}</span></div>
        </div>

        <div class="order-detail__banner">
            <strong>${banner.titulo}</strong>
            ${banner.texto}
        </div>
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
    const [resPedidos, resProductos] = await Promise.all([
        fetch("/data/pedidos.json"),
        fetch("/data/productos.json"),
    ]);
    const pedidosJSON: Pedido[] = await resPedidos.json();
    const productos: Producto[] = await resProductos.json();

    productoMap = new Map(productos.map(p => [p.id, p.nombre]));

    // Pedidos generados en el checkout (guardados en localStorage)
    const guardados = localStorage.getItem("pedidos");
    const pedidosLocal: Pedido[] = guardados ? JSON.parse(guardados) : [];

    // Combinar ambas fuentes y filtrar por el usuario en sesión
    pedidos = [...pedidosJSON, ...pedidosLocal].filter(p => p.idUsuario === user.id);

    renderizarPedidos();
}

init();