//Funcion para que se actualize el contador en el link de "Carrito" del nav

export function actualizarContadorCarrito() {
    const storage = localStorage.getItem("carrito");
    const carrito = storage ? JSON.parse(storage) : [];
    const total = carrito.reduce((sum: number, item: any) => sum + item.cantidad, 0);
    const span = document.getElementById("cartCount");
    if (span) span.textContent = String(total);
}