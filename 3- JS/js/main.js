const listaCategorias = document.getElementById("lista-categorias");

const cargarCategorias = () => {
    categorias.forEach(categoria => { 
        const li = document.createElement('li');
        li.innerHTML = `<a href="#">${categoria}</a>`;  //backticks
        listaCategorias.appendChild(li);
    });
}

cargarCategorias();