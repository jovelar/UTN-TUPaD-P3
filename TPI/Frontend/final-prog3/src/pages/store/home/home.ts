import { PRODUCTS } from "../../../data/data";
import type { ICategory } from "../../../types/category";
import { getCategories } from "../../../data/data";
import type { CartItem } from "../../../types/product";

const listaDeProductos = document.querySelector<HTMLElement>("#listaDeProductos");
const inputBusqueda = document.querySelector<HTMLInputElement>("#busqueda");
const listaDeCategorias = document.querySelector<HTMLElement>("#listaDeCategorias");


//barra de busqueda
if (inputBusqueda) {
    inputBusqueda.addEventListener("input", () => {
        const textoBusqueda = inputBusqueda.value.toLowerCase();
        buscarProducto(textoBusqueda);
    });
}

function buscarProducto(termino: string) {
    const productosEncontrados = PRODUCTS.filter(producto => 
        producto.nombre.toLowerCase().includes(termino)
    );

    if (productosEncontrados.length === 0) {
        if (listaDeProductos) {
            listaDeProductos.innerHTML = `<p class="error-busqueda">No se encontraron productos que coincidan con "${termino}"</p>`;
        }
    } else {
        cargarProductos(productosEncontrados);
    }
}

function agregarAlCarrito(idProducto: number) {
    const storage = localStorage.getItem("carrito");
    let carrito: CartItem[] = storage ? JSON.parse(storage) : [];

    const itemExistente = carrito.find(item => item.id === idProducto);

    //por defecto se carga con 1
    if (itemExistente) {
        itemExistente.cantidad += 1;
    } else {
        const productoOriginal = PRODUCTS.find(p => p.id === idProducto);
        if (productoOriginal) {
            carrito.push({ ...productoOriginal, cantidad: 1 });
        }
    }

    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert("Producto agregado");
}


// función para activar los botones de agregar de cada producto
function asignarListenersProductos() {

    //se selecciona de forma mas practica con la clase mas que con un id.
    const botonesAgregar = document.querySelectorAll<HTMLButtonElement>(".boton_agregar");
    botonesAgregar.forEach(boton => {
        boton.addEventListener("click", (e) => {
            const id = Number((e.currentTarget as HTMLButtonElement).dataset.id);
            agregarAlCarrito(id);
        });
    });
}


function cargarCategorias() {
    if (listaDeCategorias) {
        const categorias = getCategories();
        
        //Se agrega la categoria que no esta en la lista
        let html = `<li class="filtro" data-id="0">Todas las categorías</li>`;
        
        categorias.forEach(cat => {
            html += `<li class="filtro" data-id="${cat.id}">${cat.nombre}</li>`;
        });
        
        listaDeCategorias.innerHTML = html;
        asignarListeners();
    }
}


//para los botones de las tarjetas
function asignarListeners() {
    const botones = document.querySelectorAll<HTMLElement>(".filtro");

    botones.forEach(boton => {
        boton.addEventListener("click", (e) => {
            const target = e.currentTarget as HTMLElement;
            const idSeleccionado = Number(target.dataset.id);

            if (idSeleccionado === 0) {
                cargarProductos(PRODUCTS);
            } else {
                const filtrados = PRODUCTS.filter(producto => 
                    producto.categorias.some(cat => cat.id === idSeleccionado)
                );
                cargarProductos(filtrados);
            }
        });
    });
}

function cargarProductos(listaProductos: typeof PRODUCTS) {
    if (listaProductos && listaDeProductos) {

        //se limpia antes de cargar la lista
        listaDeProductos.innerHTML = "";

        listaProductos.forEach((producto) => {
            const tarjeta = `
                <article class="producto">
                    <img src="/src/img/${producto.imagen}" width="130">
                    <div class="producto__store__detalles">
                        <span class="categoria">${producto.categorias.map((categoria: ICategory) => categoria.nombre).join(", ")}</span>
                        <h3 class="producto__nombre">${producto.nombre}</h3>
                        <p class="producto__store__descripcion">${producto.descripcion}</p>
                    </div>
                    <div class="inferior__store">
                        <div class="precio__store">$${producto.precio}</div>
                        <div class="agregar">
                            <button class="boton_agregar" data-id="${producto.id}">
                                +AGREGAR
                            </button>
                        </div>
                    </div>
                </article>
            `;
            listaDeProductos.innerHTML += tarjeta;
        });
        asignarListenersProductos();
    }
}



cargarProductos(PRODUCTS);
cargarCategorias();