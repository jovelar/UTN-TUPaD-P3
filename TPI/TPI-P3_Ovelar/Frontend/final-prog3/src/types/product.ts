export interface Product {
  id: number;
  nombre: string;
  precio: number;
  descripcion: string;
  stock: number;
  imagen: string;
  disponible: boolean;
  eliminado: boolean;
  categoriaId: number;
  categoriaNombre?: string;
}

export interface CartItem extends Product {
    cantidad: number;
}
