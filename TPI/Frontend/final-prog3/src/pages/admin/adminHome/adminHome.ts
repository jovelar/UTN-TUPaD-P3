import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

// Guard: solo ADMIN
const raw = getUSer();
if (!raw) {
    navigate("/src/pages/auth/login/login.html");
}
const user = raw ? JSON.parse(raw) : null;
if (user && user.role !== "ADMIN") {
    navigate("/src/pages/store/home/home.html");
}

const spanUserName = document.getElementById("userName");
if (spanUserName && user) spanUserName.textContent = `${user.nombre} ${user.apellido}`;
document.getElementById("logoutButton")?.addEventListener("click", logout);

interface Categoria { id: number; eliminado: boolean; }
interface Producto { id: number; disponible: boolean; eliminado: boolean; }
interface Pedido { id: number; eliminado: boolean; }

async function cargarEstadisticas() {
    const [resCats, resProds, resPedidos] = await Promise.all([
        fetch("/data/categorias.json"),
        fetch("/data/productos.json"),
        fetch("/data/pedidos.json"),
    ]);

    const categorias: Categoria[] = await resCats.json();
    const productos: Producto[] = await resProds.json();
    const pedidos: Pedido[] = await resPedidos.json();

    // Solo activos (eliminado = false)
    const categoriasActivas = categorias.filter(c => !c.eliminado);
    const productosActivos = productos.filter(p => !p.eliminado);
    const pedidosActivos = pedidos.filter(p => !p.eliminado);
    const disponibles = productosActivos.filter(p => p.disponible);

    // Volcar a los spans del HTML
    document.getElementById("totalCategorias")!.textContent = String(categoriasActivas.length);
    document.getElementById("totalProductos")!.textContent = String(productosActivos.length);
    document.getElementById("totalPedidos")!.textContent = String(pedidosActivos.length);
    document.getElementById("totalDisponibles")!.textContent = String(disponibles.length);
}

cargarEstadisticas();