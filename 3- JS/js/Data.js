//Categorias
const categorias = ["Todas","Hamburguesas","Pizzas","Papas Fritas","Bebidas"];

//Clase producto y arreglo de productos
class Producto{
    constructor(nombre,imagen,precio,descripcion){
        this.nombre=nombre;
        this.imagen=imagen;
        this.precio=precio;
        this.descripcion=descripcion;
    }
}

const listaProductos = [
    new Producto("Hamburguesa Completa","hamburguesa.jpg",12000,"Hamburguesa completa con papas fritas y bebidas")
]