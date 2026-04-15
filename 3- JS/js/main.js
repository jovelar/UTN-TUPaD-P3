const listaCategorias = document.getElementById("lista-categorias");

const cargarCategorias = () => {
    categorias.forEach(categoria => { 
        const li = document.createElement('li');
        li.innerHTML = `<a href="#">${categoria}</a>`;  //backticks alt 96 
        listaCategorias.appendChild(li);
    });
}

cargarCategorias();

const listaProductos = document.getElementById("contenedor-productos");

const cargarProductos = () => {
    productos.forEach(producto => {
        const article = document.createElement('article');
        article.id = producto.id;
        article.innerHTML = `<h3>${producto.nombre}</h3>
                <img src="${producto.imagen}" width="250">
                <p>${producto.descripcion}</p>
                <p><strong>$${producto.precio}</strong></p>
                <button type="button">Agregar</button>`;

                const botonAgregar = article.querySelector("button");
                botonAgregar.addEventListener("click", () => {
                    //Mas adelante se agregara codigo para guardarlo en un arreglo que seria el "carrito"
                    mensajeAnadido();
                });

        listaProductos.appendChild(article);
    });
}

cargarProductos();

//Mensaje de boton
const mensajeAnadido = () => {
    alert("Agregado!");
}
