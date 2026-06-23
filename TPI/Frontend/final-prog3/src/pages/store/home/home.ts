import type { Product, CartItem } from "../../../types/product";
import { logout } from "../../../utils/auth";
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";

interface Categoria { id: number; nombre: string; }

const listaDeProductos = document.querySelector<HTMLElement>("#listaDeProductos");
const inputBusqueda = document.querySelector<HTMLInputElement>("#busqueda");
const listaDeCategorias = document.querySelector<HTMLElement>("#listaDeCategorias");
const spanUserName = document.getElementById("userName");
const btnLogout = document.getElementById("logoutButton");
const adminLink = document.getElementById("adminLink");

let todosLosProductos: Product[] = [];
let catMap: Map<number, string> = new Map();

function initGuard() {
    const raw = getUSer();
    if (!raw) {
        navigate("/src/pages/auth/login/login.html");
        return false;
    }
    const user = JSON.parse(raw);
    if (spanUserName) spanUserName.textContent = user.nombre;
    if (user.role === "ADMIN" && adminLink) adminLink.style.display = "";
    return true;
}

btnLogout?.addEventListener("click", logout);

inputBusqueda?.addEventListener("input", () => {
    const termino = inputBusqueda.value.toLowerCase();
    const encontrados = todosLosProductos.filter(p =>
        p.nombre.toLowerCase().includes(termino)
    );
    cargarProductos(encontrados.length ? encontrados : []);
    if (!encontrados.length && listaDeProductos) {
        listaDeProductos.innerHTML = `<p>No se encontraron productos para "${inputBusqueda.value}"</p>`;
    }
});

function agregarAlCarrito(producto: Product) {
    const storage = localStorage.getItem("carrito");
    let carrito: CartItem[] = storage ? JSON.parse(storage) : [];
    const itemExistente = carrito.find(item => item.id === producto.id);

    if (itemExistente) {
        itemExistente.cantidad += 1;
    } else {
        carrito.push({ ...producto, cantidad: 1 });
    }

    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert("Producto agregado al carrito");
}

function asignarListenersProductos() {
    document.querySelectorAll<HTMLButtonElement>(".product-card__btn").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const id = Number((e.currentTarget as HTMLButtonElement).dataset.id);
            const producto = todosLosProductos.find(p => p.id === id);
            if (producto) agregarAlCarrito(producto);
        });
    });
}

function cargarProductos(lista: Product[]) {
    if (!listaDeProductos) return;
    listaDeProductos.innerHTML = "";
    lista.forEach(producto => {
        const nombreCat = catMap.get(producto.categoriaId) ?? "";
        listaDeProductos.innerHTML += `
            <article class="product-card">
                <img src="${producto.imagen}" width="130" alt="${producto.nombre}">
                <div class="product-card__details">
                    <span class="product-card__category">${nombreCat}</span>
                    <h3 class="product-card__name">${producto.nombre}</h3>
                    <p class="product-card__description">${producto.descripcion}</p>
                </div>
                <div class="product-card__footer">
                    <span class="product-card__price">$${producto.precio}</span>
                    <button class="product-card__btn" data-id="${producto.id}">+AGREGAR</button>
                </div>
            </article>`;
    });
    asignarListenersProductos();
}

function cargarCategorias(categorias: Categoria[]) {
    if (!listaDeCategorias) return;
    let html = `<li class="store__category-item store__category-item--active" data-id="0">Todas las categorías</li>`;
    categorias.forEach(cat => {
        html += `<li class="store__category-item" data-id="${cat.id}">${cat.nombre}</li>`;
    });
    listaDeCategorias.innerHTML = html;

    document.querySelectorAll<HTMLElement>(".store__category-item").forEach(item => {
        item.addEventListener("click", (e) => {
            document.querySelectorAll(".store__category-item").forEach(el =>
                el.classList.remove("store__category-item--active")
            );
            (e.currentTarget as HTMLElement).classList.add("store__category-item--active");

            const id = Number((e.currentTarget as HTMLElement).dataset.id);
            if (id === 0) {
                cargarProductos(todosLosProductos);
            } else {
                cargarProductos(todosLosProductos.filter(p => p.categoriaId === id));
            }
        });
    });
}

async function init() {
    if (!initGuard()) return;

    const [resCats, resProds] = await Promise.all([
        fetch("/data/categorias.json"),
        fetch("/data/productos.json"),
    ]);
    const categorias: Categoria[] = await resCats.json();
    const productos: Product[] = await resProds.json();

    catMap = new Map(categorias.map(c => [c.id, c.nombre]));
    todosLosProductos = productos.filter(p => p.disponible && !p.eliminado);

    // Enrich with category name for cart display
    todosLosProductos.forEach(p => { p.categoriaNombre = catMap.get(p.categoriaId) ?? ""; });

    cargarCategorias(categorias);
    cargarProductos(todosLosProductos);
}

init();
