import type { Product, CartItem } from "../../../types/product";
import { logout } from "../../../utils/auth";
import { getUSer } from "../../../utils/localStorage";
import { navigate } from "../../../utils/navigate";

interface Categoria { id: number; nombre: string; }

const listaDeProductos = document.querySelector<HTMLElement>("#listaDeProductos");
const inputBusqueda = document.querySelector<HTMLInputElement>("#busqueda");
const selectOrdenar = document.querySelector<HTMLSelectElement>("#ordenar");
const selectCategoria = document.querySelector<HTMLSelectElement>("#filtroCategoria");
const listaDeCategorias = document.querySelector<HTMLElement>("#listaDeCategorias");
const spanUserName = document.getElementById("userName");
const btnLogout = document.getElementById("logoutButton");
const adminLink = document.getElementById("adminLink");

let todosLosProductos: Product[] = [];
let catMap: Map<number, string> = new Map();
let categoriaActiva = 0; // 0 = todas las categorías

function initGuard() {
    console.log("CLASE BODY:", document.body.className, "ROL:", JSON.parse(getUSer()!).role);
    const raw = getUSer();
    if (!raw) {
        navigate("/src/pages/auth/login/login.html");
        return false;
    }
    const user = JSON.parse(raw);
    if (spanUserName) spanUserName.textContent = user.nombre;
    if (user.role === "ADMIN" && adminLink) adminLink.style.display = "";

    //agrega una clase que luego se utilizar para ocultar los botones de agregar al carrito siendo admin
    //dado que un admin no deberia tener carrito
    if (user.role === "ADMIN") document.body.classList.add("es-admin"); 
    return true;
}

btnLogout?.addEventListener("click", logout);

// inputBusqueda?.addEventListener("input", () => {
//     const termino = inputBusqueda.value.toLowerCase();
//     const encontrados = todosLosProductos.filter(p =>
//         p.nombre.toLowerCase().includes(termino)
//     );
//     cargarProductos(encontrados.length ? encontrados : []);
//     if (!encontrados.length && listaDeProductos) {
//         listaDeProductos.innerHTML = `<p>No se encontraron productos para "${inputBusqueda.value}"</p>`;
//     }
// });

// ----- Filtrado central: combina búsqueda + categoría + orden -----
function aplicarFiltros() {
    let resultado = [...todosLosProductos];

    // Búsqueda por nombre
    const texto = inputBusqueda?.value.trim().toLowerCase() ?? "";
    if (texto) {
        resultado = resultado.filter(p => p.nombre.toLowerCase().includes(texto));
    }

    // Filtro por categoría
    if (categoriaActiva !== 0) {
        resultado = resultado.filter(p => p.categoriaId === categoriaActiva);
    }

    // Ordenamiento
    const orden = selectOrdenar?.value ?? "";
    if (orden === "nombre-asc")  resultado.sort((a, b) => a.nombre.localeCompare(b.nombre));
    else if (orden === "nombre-desc") resultado.sort((a, b) => b.nombre.localeCompare(a.nombre));
    else if (orden === "precio-asc")  resultado.sort((a, b) => a.precio - b.precio);
    else if (orden === "precio-desc") resultado.sort((a, b) => b.precio - a.precio);

    // Repintar
    if (resultado.length) {
        cargarProductos(resultado);
    } else if (listaDeProductos) {
        listaDeProductos.innerHTML = `<p>No se encontraron productos</p>`;
    }
}

inputBusqueda?.addEventListener("input", aplicarFiltros);
selectOrdenar?.addEventListener("change", aplicarFiltros);
selectCategoria?.addEventListener("change", () => {
    categoriaActiva = Number(selectCategoria.value) || 0;
    sincronizarSidebar();
    aplicarFiltros();
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

// function asignarListenersProductos() {
//     document.querySelectorAll<HTMLButtonElement>(".product-card__btn").forEach(btn => {
//         btn.addEventListener("click", (e) => {
//             const id = Number((e.currentTarget as HTMLButtonElement).dataset.id);
//             const producto = todosLosProductos.find(p => p.id === id);
//             if (producto) agregarAlCarrito(producto);
//         });
//     });
// }

function asignarListenersProductos() {
    // Listener del botón agregar (ya lo tenías)
    document.querySelectorAll<HTMLButtonElement>(".product-card__btn").forEach(btn => {
        btn.addEventListener("click", (e) => {
            e.stopPropagation();   // ← evita que el click llegue a la tarjeta
            const id = Number((e.currentTarget as HTMLButtonElement).dataset.id);
            const producto = todosLosProductos.find(p => p.id === id);
            if (producto) agregarAlCarrito(producto);
        });
    });

    // Listener de la tarjeta (nuevo) → redirige al detalle
    document.querySelectorAll<HTMLElement>(".product-card").forEach(card => {
        card.addEventListener("click", (e) => {
            const id = Number((e.currentTarget as HTMLElement).dataset.id);
            window.location.href = `/src/pages/store/productDetail/productDetail.html?id=${id}`;
        });
    });
}

// function cargarProductos(lista: Product[]) {
//     if (!listaDeProductos) return;
//     listaDeProductos.innerHTML = "";
//     lista.forEach(producto => {
//         const nombreCat = catMap.get(producto.categoriaId) ?? "";
//         listaDeProductos.innerHTML += `
//             <article class="product-card">
//                 <img src="${producto.imagen}" width="130" alt="${producto.nombre}">
//                 <div class="product-card__details">
//                     <span class="product-card__category">${nombreCat}</span>
//                     <h3 class="product-card__name">${producto.nombre}</h3>
//                     <p class="product-card__description">${producto.descripcion}</p>
//                 </div>
//                 <div class="product-card__footer">
//                     <span class="product-card__price">$${producto.precio}</span>
//                     <button class="product-card__btn" data-id="${producto.id}">+AGREGAR</button>
//                 </div>
//             </article>`;
//     });
//     asignarListenersProductos();
// }

function cargarProductos(lista: Product[]) {
    if (!listaDeProductos) return;
    listaDeProductos.innerHTML = "";
    lista.forEach(producto => {
        const nombreCat = catMap.get(producto.categoriaId) ?? "";
        listaDeProductos.innerHTML += `
            <article class="product-card" data-id="${producto.id}">
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

/*
function cargarCategorias(categorias: Categoria[]) {
    if (!listaDeCategorias) return;
    let html = `<li class="store__category-item store__category-item--active" data-id="0">Todas las categorías</li>`;
    categorias.forEach(cat => {
        html += `<li class="store__category-item" data-id="${cat.id}">${cat.nombre}</li>`;
    });
    listaDeCategorias.innerHTML = html;
*/
function cargarCategorias(categorias: Categoria[]) {
    if (!listaDeCategorias) return;

    // CORRECCIÓN: Quitamos la clase '--active' para que inicie limpio
    let html = `<li class="store__category-item" data-id="0">Todas las categorías</li>`;

    categorias.forEach(cat => {
        html += `<li class="store__category-item" data-id="${cat.id}">${cat.nombre}</li>`;
    });
    listaDeCategorias.innerHTML = html;

    // ----- listener viejo (reemplazado por aplicarFiltros) -----
    // document.querySelectorAll<HTMLElement>(".store__category-item").forEach(item => {
    //     item.addEventListener("click", (e) => {
    //         document.querySelectorAll(".store__category-item").forEach(el =>
    //             el.classList.remove("store__category-item--active")
    //         );
    //         (e.currentTarget as HTMLElement).classList.add("store__category-item--active");

    //         const id = Number((e.currentTarget as HTMLElement).dataset.id);
    //         if (id === 0) {
    //             cargarProductos(todosLosProductos);
    //         } else {
    //             cargarProductos(todosLosProductos.filter(p => p.categoriaId === id));
    //         }
    //     });
    // });

    document.querySelectorAll<HTMLElement>(".store__category-item").forEach(item => {
        item.addEventListener("click", (e) => {
            categoriaActiva = Number((e.currentTarget as HTMLElement).dataset.id);
            if (selectCategoria) selectCategoria.value = categoriaActiva === 0 ? "" : String(categoriaActiva);
            sincronizarSidebar();
            aplicarFiltros();
        });
    });
}

// ----- Llenar el select de categorías del toolbar -----
function llenarSelectCategorias(categorias: Categoria[]) {
    if (!selectCategoria) return;
    categorias.forEach(cat => {
        const option = document.createElement("option");
        option.value = String(cat.id);
        option.textContent = cat.nombre;
        selectCategoria.appendChild(option);
    });
}

// ----- Mantener sidebar y select sincronizados -----
function sincronizarSidebar() {
    document.querySelectorAll<HTMLElement>(".store__category-item").forEach(el => {
        el.classList.toggle(
            "store__category-item--active",
            Number(el.dataset.id) === categoriaActiva
        );
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
    llenarSelectCategorias(categorias);
    cargarProductos(todosLosProductos);
}

init();