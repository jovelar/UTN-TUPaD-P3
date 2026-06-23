import type { CartItem } from "../../../types/product";

const columnasCarrito = document.querySelector<HTMLElement>(".columnas_carrito");

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
    const nuevoCarrito = carrito.filter(p => p.id !== id);
    localStorage.setItem("carrito", JSON.stringify(nuevoCarrito));
    renderizarCarrito();
}

function asignarListenersCarrito() {
    const botonesSumar = document.querySelectorAll<HTMLElement>(".boton_sumar");
    const botonesRestar = document.querySelectorAll<HTMLElement>(".boton_restar");
    const botonesEliminar = document.querySelectorAll<HTMLElement>(".boton_eliminar");
    const botonLimpiar = document.querySelector<HTMLButtonElement>("#boton_limpiar");

    botonesSumar.forEach(btn => {
        btn.onclick = () => modificarCantidad(Number(btn.dataset.id), "sumar");
    });

    botonesRestar.forEach(btn => {
        btn.onclick = () => modificarCantidad(Number(btn.dataset.id), "restar");
    });

    botonesEliminar.forEach(btn => {
        btn.onclick = () => eliminarDelCarrito(Number(btn.dataset.id));
    });

    if (botonLimpiar) {
        botonLimpiar.onclick = () => {
            localStorage.removeItem("carrito");
            renderizarCarrito();
        };
    }
}

function renderizarCarrito() {
    const storage = localStorage.getItem("carrito");
    const carrito: CartItem[] = storage ? JSON.parse(storage) : [];

    if (!columnasCarrito){
        return;
    }
    if (carrito.length === 0) {
        columnasCarrito.innerHTML = `
            <div class="carrito_vacio">
                <h2>El carrito está vacío</h2>
                <div class="boton_volver"> 
                    <a href="/src/pages/store/home/home.html">VOLVER</a>
                </div>
            </div>`;
        return;
    }

    let htmlItems = "";
    let totalGeneral = 0;

    carrito.forEach((item) => {
        totalGeneral += item.precio * item.cantidad;
        htmlItems += `
            <article class="item_carrito">
                <div class="imagen_producto">
                    <img src="/src/img/${item.imagen}" width="50" alt="${item.nombre}">
                </div>
                <div class="producto_detalles">
                    <h5>${item.nombre}</h5>
                    <h6>${item.categorias.map(cat => cat.nombre).join(", ")}</h6>
                    <p>$${item.precio}</p>
                </div>
                <div class="producto_botones">
                    <div class="variar_cantidad">                                
                        <div class="boton_numero boton_sumar" data-id="${item.id}">+</div>
                        <div class="numero_producto">${item.cantidad}</div>
                        <div class="boton_numero boton_restar" data-id="${item.id}">-</div>
                    </div>
                    <div class="boton_eliminar" data-id="${item.id}">Eliminar</div>
                </div>
            </article>`;
    });

    columnasCarrito.innerHTML = `
        <section id="contenedor_carrito" class="inventario_carrito">
            ${htmlItems}
        </section>
        <aside class="aside_resumen">
            <h4>Resumen</h4>   
            <span>Subtotal</span>
            <span>$${totalGeneral}</span>
            <hr>
            <strong>TOTAL</strong>
            <strong>$${totalGeneral}</strong>
            <div class="funciones_botones_resumen">
                <button type="button">FINALIZAR</button>
                <button type="button" id="boton_limpiar">Limpiar carrito</button>
            </div>   
        </aside>
    `;

    asignarListenersCarrito();
}

renderizarCarrito();