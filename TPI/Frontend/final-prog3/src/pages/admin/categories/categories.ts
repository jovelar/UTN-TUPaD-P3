import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";
import { logout } from "../../../utils/auth";

interface Categoria {
    id: number;
    nombre: string;
    descripcion: string;
    imagen: string;
    eliminado: boolean;
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
const modal = document.getElementById("modalCategoria") as HTMLElement;
const modalTitulo = document.getElementById("modalTitulo") as HTMLElement;
const form = document.getElementById("formCategoria") as HTMLFormElement;
const inputId = document.getElementById("categoriaId") as HTMLInputElement;
const inputNombre = document.getElementById("categoriaNombre") as HTMLInputElement;
const inputDescripcion = document.getElementById("categoriaDescripcion") as HTMLTextAreaElement;
const inputImagen = document.getElementById("categoriaImagen") as HTMLInputElement;

// Estado en memoria (las operaciones no se persisten, se pierden al recargar)
let categorias: Categoria[] = [];

// ----- Render de la tabla -----
function renderizarTabla() {
    const activas = categorias.filter(c => !c.eliminado);
    tablaCuerpo.innerHTML = activas.map(cat => `
        <tr>
            <td>${cat.id}</td>
            <td><img src="${cat.imagen}" width="50" alt="${cat.nombre}"></td>
            <td>${cat.nombre}</td>
            <td>${cat.descripcion}</td>
            <td>
                <button class="btn btn--primary" data-accion="editar" data-id="${cat.id}">Editar</button>
                <button class="btn btn--danger" data-accion="eliminar" data-id="${cat.id}">Eliminar</button>
            </td>
        </tr>
    `).join("");

    asignarListenersAcciones();
}

// ----- Listeners de los botones de cada fila -----
function asignarListenersAcciones() {
    tablaCuerpo.querySelectorAll<HTMLButtonElement>("button[data-accion]").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = Number(btn.dataset.id);
            if (btn.dataset.accion === "editar") abrirModalEditar(id);
            else if (btn.dataset.accion === "eliminar") eliminarCategoria(id);
        });
    });
}

// ----- Abrir modal en modo "nueva" -----
function abrirModalNueva() {
    modalTitulo.textContent = "Nueva Categoría";
    inputId.value = "";
    inputNombre.value = "";
    inputDescripcion.value = "";
    inputImagen.value = "";
    modal.style.display = "flex";
}

// ----- Abrir modal en modo "editar" -----
function abrirModalEditar(id: number) {
    const cat = categorias.find(c => c.id === id);
    if (!cat) return;
    modalTitulo.textContent = "Editar Categoría";
    inputId.value = String(cat.id);
    inputNombre.value = cat.nombre;
    inputDescripcion.value = cat.descripcion;
    inputImagen.value = cat.imagen;
    modal.style.display = "flex";
}

function cerrarModal() {
    modal.style.display = "none";
}

// ----- Guardar (crear o editar) -----
form.addEventListener("submit", (e) => {
    e.preventDefault();

    const id = inputId.value;
    const datos = {
        nombre: inputNombre.value.trim(),
        descripcion: inputDescripcion.value.trim(),
        imagen: inputImagen.value.trim(),
    };

    if (id) {
        // Editar: actualizar la categoría existente
        const cat = categorias.find(c => c.id === Number(id));
        if (cat) Object.assign(cat, datos);
    } else {
        // Crear: nuevo id = el mayor actual + 1
        const nuevoId = categorias.length ? Math.max(...categorias.map(c => c.id)) + 1 : 1;
        categorias.push({ id: nuevoId, ...datos, eliminado: false });
    }

    cerrarModal();
    renderizarTabla();
});

// ----- Eliminar (baja lógica en memoria) -----
function eliminarCategoria(id: number) {
    if (!confirm("¿Eliminar esta categoría?")) return;
    const cat = categorias.find(c => c.id === id);
    if (cat) cat.eliminado = true;
    renderizarTabla();
}

// ----- Eventos del modal -----
document.getElementById("btnNuevaCategoria")?.addEventListener("click", abrirModalNueva);
document.getElementById("cerrarModal")?.addEventListener("click", cerrarModal);

// ----- Inicio: fetch de categorías -----
async function init() {
    const res = await fetch("/data/categorias.json");
    categorias = await res.json();
    renderizarTabla();
}

init();