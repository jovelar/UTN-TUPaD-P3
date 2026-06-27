//URL DEL VIDEO
//https://youtu.be/AbcKO4Cuc0Y

package com.tp.jpa;
import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.repository.*;
import com.tp.jpa.util.JPAUtil;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    //para que sean accesibles a todos
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();

    public static void mostrarProducto(Producto p) {
        System.out.println(p);
    }

    public static void mostrarProductos(Categoria cat) {
        for (Producto p : cat.getProductos()) {
            mostrarProducto(p);
        }
    }

    public static void mostrarCategoriasYProductos(Set<Categoria> categorias) {
        for (Categoria c : categorias) {
            System.out.println("\n");
            System.out.println("###" + c + "###");
            mostrarProductos(c);
        }
    }

    public static void mostrarPedido(Pedido p) {
        System.out.println(p);
    }

    public static void mostrarPedidos(Usuario usuario) {
        for (Pedido p : usuario.getPedidos()) {
            mostrarPedido(p);
        }
    }

    public static void mostrarUsuario(Usuario u) {
        System.out.println(u);
    }

    public static void mostrarUsuarioConMasPedidos(Set<Usuario> usuarios) {
        Usuario usuarioMayor = null;

        for (Usuario u : usuarios) {
            if (usuarioMayor == null || u.getPedidos().size() > usuarioMayor.getPedidos().size()) {
                usuarioMayor = u;
            }
        }
        System.out.println("El usuario con mas pedidos es :\n");
        System.out.println(usuarioMayor);
        System.out.println("\n Sus pedidos: \n");
        mostrarPedidos(usuarioMayor);
    }

    public static void compararConProducto(Producto producto, Set<Categoria> categorias) {
        for (Categoria c : categorias) {
            Set<Producto> listaProductos = c.getProductos();
            for (Producto p : listaProductos) {
                System.out.println("Comparando: " + p.getNombre() + " con " + producto.getNombre() + " : " + p.equals(producto));
            }
        }
    }

    public static void main(String[] args) {

        Scanner scan= new Scanner(System.in);
        List<Categoria> categorias=categoriaRepo.listarActivos();

        int opcion=0;
        do{
            opcion=menuPrincipal(scan);
            switch(opcion){
                case 0:
                    break;

                case 1:
                    int opcCat=0;
                    do{
                        opcCat=menuABMCategorias(scan);
                        switch(opcCat){
                            case 0:
                                break;
                            case 1://Cat Nueva
                                scan.nextLine();//para limpiar el buffer del teclado
                                System.out.println("Ingrese el nombre de la categoria: ");
                                String nuevoNombCat=scan.nextLine();
                                System.out.println("Ingrese una descripcion");
                                String nuevoDescCat=scan.nextLine();
                                Categoria nuevaCat= Categoria.builder()
                                        .nombre(nuevoNombCat)
                                        .descripcion(nuevoDescCat)
                                        .eliminado(false)
                                        .build();


                                boolean existe = false;
                                for (Categoria c : categorias) {
                                    if (c.getNombre().equals(nuevoNombCat)) { // Comparación directa
                                        existe = true;
                                        break;
                                    }
                                }

                                if(!existe){
                                    categoriaRepo.guardar(nuevaCat);
                                    System.out.println("Categoria guardada!");

                                }else{
                                    System.out.println("La categoria ya existe");
                                }
                                break;


                            case 2://baja
                                System.out.println("Ingrese el ID de la categoria a eliminar");
                                Long idAeliminar=scan.nextLong();

                                Boolean eliminado= categoriaRepo.eliminarLogico(idAeliminar);
                                if(eliminado){
                                    System.out.println("La categoria fue eliminada");
                                }else{
                                    System.out.println("La categoria con el ID facilitado no existe");
                                }

                                break;
                            case 3://modificar
                                System.out.println("++++++++ MODIFICAR ++++++++");
                                System.out.println("Ingrese el ID a modificar: ");
                                long idAmodificar=scan.nextLong();

                                Categoria catAmodificar=null;
                                for(Categoria c: categorias){
                                    if(c.getId()==idAmodificar){
                                        catAmodificar=c;
                                        break;
                                    }
                                }
                                if(catAmodificar!=null){
                                    scan.nextLine();//limpia buffer
                                    System.out.println("Ingrese el nuevo nombre de la categoria: (actual es " + catAmodificar.getNombre() + ")");
                                    catAmodificar.setNombre(scan.nextLine());
                                    System.out.println("Ingrese la nueva descripcion: (actual: "+catAmodificar.getDescripcion()+")");
                                    catAmodificar.setDescripcion(scan.nextLine());

                                    categoriaRepo.guardar(catAmodificar);
                                }else{
                                    System.out.println("La categoria no existe");
                                }

                                break;
                            case 4://listar
                                System.out.println("******** LISTA DE CATEGORIAS ********");
                                List<Categoria> categoriasAListar=categoriaRepo.listarActivos();
                                if(categoriasAListar.size()!=0){
                                    mostrarCategorias(categoriasAListar);
                                }else{
                                    System.out.println("No hay categorias para mostrar");
                                }
                                break;
                        }
                    }while(opcCat!=0);
                    break;

                case 2:
                    int opcProd=0;
                    do{
                        opcProd=menuABMproducto(scan);
                        switch(opcProd){
                            case 0:
                                break;
                            case 1://Crear
                                List<Categoria> categoriasNuevoProd = categoriaRepo.listarActivos();
                                if(categoriasNuevoProd.size()>0){
                                    long eleccionID;
                                    Categoria eleccionCategoria=null;
                                    do{

                                        System.out.println("Ingrese el ID de la categoria seleccionada de la siguiente lista");
                                        mostrarCategorias(categoriasNuevoProd);
                                        eleccionID=scan.nextLong();

                                        //Se busca si el ID existe
                                        for(Categoria c :categoriasNuevoProd){
                                            if(c.getId()==eleccionID){
                                                eleccionCategoria=c;
                                            }
                                        }
                                        if(eleccionCategoria==null){
                                            System.out.println("ID incorrecto, seleccione uno de la lista");
                                        }
                                    }while(eleccionCategoria==null);

                                    String nombreNuevoProd=null;

                                    scan.nextLine();//limpiando buffer
                                    do{
                                        System.out.println("Ingrese el nombre");
                                        nombreNuevoProd=scan.nextLine();
                                    }while(nombreNuevoProd.isBlank());
                                    
                                    double nuevoProdPrecio=0.0; //Por defecto
                                    System.out.println("Ingrese el precio");
                                    nuevoProdPrecio=scan.nextInt();


                                    scan.nextLine();//limpiando buffer
                                    System.out.println("Ingrese una descripcion: ");
                                    String nuevoProdDescrip=scan.nextLine();

                                    scan.nextLine();//limpiando buffer
                                    int nuevoProdStock=1;//por defecto
                                    System.out.println("Ingrese el stock, por defecto es 1");
                                    nuevoProdStock=scan.nextInt();

                                    scan.nextLine();//limpiando buffer
                                    System.out.println("Ingrese el nombre del archivo de imagen: ");
                                    String nuevoProdImagen=scan.nextLine();
                                    
                                    Producto nuevoProducto=Producto.builder()
                                            .nombre(nombreNuevoProd)
                                            .precio(nuevoProdPrecio)
                                            .descripcion(nuevoProdDescrip)
                                            .stock(nuevoProdStock).imagen(nuevoProdImagen)
                                            .disponible(true)
                                            .categoria(eleccionCategoria)
                                            .eliminado(false)
                                            .createdAt(LocalDateTime.now())
                                            .build();
                                    productoRepo.guardar(nuevoProducto);
                                    System.out.println("Producto agregado!");

                                }else{
                                    System.out.println("No existen categorias definidas aun, primero debe crear una para poder incorporar un producto");
                                }
                                break;
                            case 2://Borrado logico
                                scan.nextLine();//limpiando buffer
                                List<Producto>listaDeProductos=productoRepo.listarActivos();
                                if(listaDeProductos.size()>0){
                                    Boolean estadoEliminado=false;
                                    Long idAeliminar;
                                    do{
                                        System.out.println("Ingrese el ID del producto a dar de baja de la siguiente lista: ");
                                        mostrarListaProductos(listaDeProductos);
                                        idAeliminar=scan.nextLong();
                                        estadoEliminado=productoRepo.eliminarLogico(idAeliminar);
                                        if(estadoEliminado==false){
                                            System.out.println("ID invalido");
                                        }
                                        else{
                                            System.out.println("\n\n Producto eliminado! \n\n");
                                        }

                                    }while(estadoEliminado==false);

                                }else{
                                    System.out.println("No hay productos para eliminar");
                                }
                                break;
                            case 3://Modificar
                                List<Producto>listaProducto=productoRepo.listarActivos();
                                if(listaProducto.size()>0){
                                    Producto productoAModificar=null;
                                    Long idAModificar;
                                    do{
                                        System.out.println("Ingrese un ID de producto de la siguiente lista: ");
                                        mostrarListaProductos(listaProducto);
                                        idAModificar=scan.nextLong();
                                        for(Producto c: listaProducto){
                                            if(c.getId()==idAModificar){
                                                productoAModificar=c;
                                                break;
                                            }
                                        }
                                    }while (productoAModificar==null);

                                    scan.nextLine();//limpiando buffer
                                    System.out.println("Ingrese el nuevo nombre: (actual: "+productoAModificar.getNombre()+")");
                                    productoAModificar.setNombre(scan.nextLine());

                                    scan.nextLine();//limpiando buffer
                                    System.out.println("Ingrese el nuevo precio: (actual: $"+productoAModificar.getPrecio()+")");
                                    productoAModificar.setPrecio(scan.nextDouble());

                                    scan.nextLine();//limpiando buffer
                                    System.out.println("ingrese la nueva descripcion: (actual: "+productoAModificar.getDescripcion()+ ")");
                                    productoAModificar.setDescripcion(scan.nextLine());

                                    scan.nextLine();//limpiando buffer
                                    System.out.println("Ingrese el nuevo stock: (actual:"+productoAModificar.getStock()+")");
                                    productoAModificar.setStock(scan.nextInt());

                                    System.out.println("Ingrese la nueva imagen: (actual:"+productoAModificar.getImagen()+")");
                                    productoAModificar.setImagen(scan.nextLine());

                                    productoRepo.guardar(productoAModificar);

                                    System.out.println("\nSe actualizo el producto!\n");


                                }else{
                                    System.out.println("No hay productos para modificar");
                                }


                                break;
                            case 4://Listar
                                System.out.println("\n\n\n%%%%%%%% LISTA DE PRODUCTOS DISPONIBLES %%%%%%%%");
                                List<Producto>listaProductos = productoRepo.listarActivos();
                                if(listaProductos.size()>0){
                                    mostrarListaProductos(listaProductos);

                                }else{
                                    System.out.println("\n\nNo hay productos en la categoria\n\n");
                                }
                                break;
                            default:
                                break;
                        }
                    }while(opcProd!=0);
                    break;

                case 3:
                    int opcionReporte=999;
                    do{
                        opcionReporte=menuReportes(scan);
                        switch (opcionReporte){
                            case 0:
                                break;
                            case 1:
                                List<Categoria>categoriasReporte=categoriaRepo.listarActivos();
                                if (categoriasReporte.size() > 0) {
                                    long eleccionID;
                                    Boolean categoriaValida=false;
                                    do{

                                        System.out.println("Ingrese el ID de la categoria seleccionada de la siguiente lista");
                                        mostrarCategorias(categoriasReporte);
                                        eleccionID=scan.nextLong();

                                        //Se busca si el ID existe
                                        for(Categoria c :categoriasReporte){
                                            if(c.getId()==eleccionID){
                                                categoriaValida=true;
                                            }
                                        }
                                        if(categoriaValida==false){
                                            System.out.println("ID incorrecto, seleccione uno de la lista");
                                        }
                                    }while(categoriaValida==false);

                                    List<Producto>listaPorCategoria=productoRepo.buscarPorCategoria(eleccionID);
                                    if(listaPorCategoria.size()>0){
                                        mostrarListaProductos(listaPorCategoria);
                                    }else{
                                        System.out.println("La categoria aun no cuenta con productos para mostrar");
                                    }
                                    String nombreNuevoProd=null;

                                }else{
                                    System.out.println("No hay categorias para mostrar");
                                }

                                break;
                            default:
                                System.out.println("Opcion invalida!");
                        }
                    }while(opcionReporte!=0);

                    break;

                default:
                    System.out.println("Opcion invalida");
                    break;

            }
        }while(opcion!=0);

        //se cierra el EMF unicamente al final
        JPAUtil.getEntityManagerFactory().close();
    }
    public static int menuPrincipal(Scanner scan){
        int opcion=0;
        System.out.println("######## Menu Principal ########");
        System.out.println("1- Categorias");
        System.out.println("2- Productos");
        System.out.println("3- Reportes");
        System.out.println("\n\n0- SALIR");
        opcion=scan.nextInt();
        return opcion;
    }

    public static int menuABMCategorias(Scanner scan){
        int opcion =0;
        System.out.println("******** CATEGORIAS ********");
        System.out.println("1- Agregar nueva categoria");
        System.out.println("2- Dar de baja una categoria");
        System.out.println("3- Modificar una categoria");
        System.out.println("4- Listar categorias");
        System.out.println("\n\n0- VOLVER");
        opcion=scan.nextInt();
        return opcion;
    }

    public static int menuABMproducto(Scanner scan){
        int opcion=0;
        System.out.println("$$$$$$$$ PRODUCTOS $$$$$$$$");
        System.out.println("1- Crear producto");
        System.out.println("2- Borrar producto (logica)");
        System.out.println("3- Modificar producto");
        System.out.println("4- Listar productos activos");
        System.out.println("\n\n0- VOLVER");
        opcion=scan.nextInt();
        return opcion;
    }

    public static int menuReportes(Scanner scan){
        int opcion=0;
        System.out.println("\n\nXXXXXXXX Reportes XXXXXXXX");
        System.out.println("1- Informe de productos por categoria");
        System.out.println("\n\n0- VOLVER ");
        opcion=scan.nextInt();
        return opcion;
    }

    public static void mostrarCategorias(List<Categoria>categorias){
        for(Categoria c: categorias){
            System.out.println("ID:"+c.getId()+", NOMBRE: "+c.getNombre()+", DESCRIPCION:"+c.getDescripcion());
        }
    }

    public static void mostrarListaProductos(List<Producto> productos){
        System.out.println("\n");
        for(Producto p: productos){
            System.out.println("ID:"+p.getId()+" " +
                    ",NOMBRE: "+p.getNombre()+
                    ",PRECIO: "+p.getPrecio()+
                    ",STOCK:"+p.getStock()+
                    ",CATEGORIA: "+p.getCategoria().getNombre());
        }
        System.out.println("\n");
    }
}