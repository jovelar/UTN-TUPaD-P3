// TODO: implementar CRUD de productos
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

interface Categoria {
    id: number;
    nombre: string;
    eliminado: boolean;
}

interface Producto {
    id: number;
    nombre: string;
    descripcion: string;
    precio: number;
    stock: number;
    imagen: string;
    disponible: boolean;
    eliminado: boolean;
    categoriaId: number;
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
const tablaCuerpo = document.getElementById("tablaCuerpo") as HTMLElement;
const modal = document.getElementById("modalProducto") as HTMLElement;
const modalTitulo = document.getElementById("modalTitulo") as HTMLElement;
const form = document.getElementById("formProducto") as HTMLFormElement;
const inputId = document.getElementById("productoId") as HTMLInputElement;
const inputNombre = document.getElementById("productoNombre") as HTMLInputElement;
const inputDescripcion = document.getElementById("productoDescripcion") as HTMLTextAreaElement;
const inputPrecio = document.getElementById("productoPrecio") as HTMLInputElement;
const inputStock = document.getElementById("productoStock") as HTMLInputElement;
const selectCategoria = document.getElementById("productoCategoria") as HTMLSelectElement;
const inputImagen = document.getElementById("productoImagen") as HTMLInputElement;
const inputDisponible = document.getElementById("productoDisponible") as HTMLInputElement;

// Estado en memoria
let productos: Producto[] = [];
let categorias: Categoria[] = [];
let catMap: Map<number, string> = new Map();

// ----- Render de la tabla -----
function renderizarTabla() {
    const activos = productos.filter(p => !p.eliminado);
    tablaCuerpo.innerHTML = activos.map(prod => `
        <tr>
            <td>${prod.id}</td>
            <td><img src="${prod.imagen}" width="50" alt="${prod.nombre}"></td>
            <td>${prod.nombre}</td>
            <td>${prod.descripcion}</td>
            <td>$${prod.precio}</td>
            <td>${catMap.get(prod.categoriaId) ?? ""}</td>
            <td>${prod.stock}</td>
            <td>${prod.disponible ? "Disponible" : "No disponible"}</td>
            <td>
                <button class="btn btn--primary" data-accion="editar" data-id="${prod.id}">Editar</button>
                <button class="btn btn--danger" data-accion="eliminar" data-id="${prod.id}">Eliminar</button>
            </td>
        </tr>
    `).join("");

    asignarListenersAcciones();
}

function asignarListenersAcciones() {
    tablaCuerpo.querySelectorAll<HTMLButtonElement>("button[data-accion]").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = Number(btn.dataset.id);
            if (btn.dataset.accion === "editar") abrirModalEditar(id);
            else if (btn.dataset.accion === "eliminar") eliminarProducto(id);
        });
    });
}

// ----- Llenar el select de categorías del modal -----
function llenarSelectCategorias() {
    const activas = categorias.filter(c => !c.eliminado);
    selectCategoria.innerHTML = `<option value="">Seleccione una categoría</option>`;
    activas.forEach(cat => {
        const option = document.createElement("option");
        option.value = String(cat.id);
        option.textContent = cat.nombre;
        selectCategoria.appendChild(option);
    });
}

// ----- Abrir modal "nuevo" -----
function abrirModalNuevo() {
    modalTitulo.textContent = "Nuevo Producto";
    inputId.value = "";
    inputNombre.value = "";
    inputDescripcion.value = "";
    inputPrecio.value = "";
    inputStock.value = "";
    selectCategoria.value = "";
    inputImagen.value = "";
    inputDisponible.checked = true;
    modal.style.display = "flex";
}

// ----- Abrir modal "editar" -----
function abrirModalEditar(id: number) {
    const prod = productos.find(p => p.id === id);
    if (!prod) return;
    modalTitulo.textContent = "Editar Producto";
    inputId.value = String(prod.id);
    inputNombre.value = prod.nombre;
    inputDescripcion.value = prod.descripcion;
    inputPrecio.value = String(prod.precio);
    inputStock.value = String(prod.stock);
    selectCategoria.value = String(prod.categoriaId);
    inputImagen.value = prod.imagen;
    inputDisponible.checked = prod.disponible;
    modal.style.display = "flex";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ----- Guardar (crear o editar) con validaciones -----
form.addEventListener("submit", (e) => {
    e.preventDefault();

    const precio = Number(inputPrecio.value);
    const stock = Number(inputStock.value);
    const categoriaId = Number(selectCategoria.value);

    // Validaciones FHU-09: precio > 0, stock >= 0, categoría existente
    if (precio <= 0) { alert("El precio debe ser mayor a 0."); return; }
    if (stock < 0) { alert("El stock no puede ser negativo."); return; }
    if (!categoriaId) { alert("Seleccioná una categoría."); return; }

    const datos = {
        nombre: inputNombre.value.trim(),
        descripcion: inputDescripcion.value.trim(),
        precio,
        stock,
        imagen: inputImagen.value.trim(),
        disponible: inputDisponible.checked,
        categoriaId,
    };

    const id = inputId.value;
    if (id) {
        const prod = productos.find(p => p.id === Number(id));
        if (prod) Object.assign(prod, datos);
    } else {
        const nuevoId = productos.length ? Math.max(...productos.map(p => p.id)) + 1 : 1;
        productos.push({ id: nuevoId, ...datos, eliminado: false });
    }

    cerrarModal();
    renderizarTabla();
});

// ----- Eliminar (baja lógica en memoria) -----
function eliminarProducto(id: number) {
    if (!confirm("¿Eliminar este producto?")) return;
    const prod = productos.find(p => p.id === id);
    if (prod) prod.eliminado = true;
    renderizarTabla();
}

// ----- Eventos del modal -----
document.getElementById("btnNuevoProducto")?.addEventListener("click", abrirModalNuevo);
document.getElementById("cerrarModal")?.addEventListener("click", cerrarModal);

// ----- Inicio: fetch de productos y categorías -----
async function init() {
    const [resProds, resCats] = await Promise.all([
        fetch("/data/productos.json"),
        fetch("/data/categorias.json"),
    ]);
    productos = await resProds.json();
    categorias = await resCats.json();

    catMap = new Map(categorias.map(c => [c.id, c.nombre]));

    llenarSelectCategorias();
    renderizarTabla();
}

init();