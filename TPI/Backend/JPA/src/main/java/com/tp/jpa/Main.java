package com.tp.jpa;

import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.*;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.hibernate.sql.model.jdbc.OptionalTableUpdateOperation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": menuCategorias(); break;
                case "2": menuProductos(); break;
                case "3": menuUsuarios(); break;
                case "4": menuPedidos(); break;
                case "5": menuReportes(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────

    private static void menuCategorias() {
        // TODO: Implementar submenú de Categorías.
        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  0-Volver
        String opcMenuCat="asd";
        do{
            System.out.println("\n\n####### CATEGORIAS #######");
            System.out.println("1- Alta");
            System.out.println("2- Modificar");
            System.out.println("3- Baja logica");
            System.out.println("4- Listado");
            System.out.println("\n\n0- Volver");
            opcMenuCat = sc.nextLine();

            switch(opcMenuCat){
                case "0":
                    break;
                case "1":
                        String nuevoNombreCat="";
                        do{
                            System.out.println("Ingrese una nombre: ");
                            nuevoNombreCat=sc.nextLine();
                            if(nuevoNombreCat.isEmpty()){
                                System.out.println("\n Debe ingresar un nombre!");
                            }
                        }while(nuevoNombreCat.isEmpty());

                        System.out.println("Ingrese una descripcion");
                        String nuevoNombreDesc=sc.nextLine();

                        Categoria nuevaCat=Categoria.builder().nombre(nuevoNombreCat)
                                .descripcion(nuevoNombreDesc)
                                .build();
                        categoriaRepo.guardar(nuevaCat);
                        System.out.println("Categoria agregada con el id: "+nuevaCat.getId()+"!");

                    break;
                case "2":
                    System.out.println("Mostrando categorias activas: \n");
                    List<Categoria> cat=categoriaRepo.listarActivos();
                    if(!cat.isEmpty()){
                        mostrarCategorias(cat);
                        System.out.println("Seleccione el ID de la categoria que desea modificar");

                        //Ya que tenemos en memoria las categorias, trabajamos con ellas
                        boolean encontrado=false;
                        try{
                            Long idAModificar=Long.parseLong(sc.nextLine().trim());
                            for(Categoria c:cat){
                                if(c.getId().equals(idAModificar)){
                                    encontrado=true;
                                    Categoria catModi=c;
                                    System.out.println("Ingrese un nuevo nombre [ENTER] para mantener el actual ("+c.getNombre()+")");
                                    String nombreTemp=sc.nextLine();
                                    if(!nombreTemp.isEmpty()){
                                        catModi.setNombre(nombreTemp);
                                    }
                                    System.out.println("Ingrese una nueva descripcion [ENTER] para mantener el actual ("+c.getDescripcion()+")");
                                    String descripTemp=sc.nextLine();
                                    if(!descripTemp.isEmpty()){
                                        catModi.setDescripcion(descripTemp);
                                    }
                                    categoriaRepo.guardar(catModi);
                                    System.out.println("Categoria actualizada!");
                                    break;
                                }
                            }
                        }catch (NumberFormatException e){
                            System.out.print("Fomato de ID invalido");

                            //Para que no muestre el error de no encontrado!
                            encontrado=true;
                        }
                        if(!encontrado){
                            System.out.println("Id no encontrado!\n");
                        }

                    }else{
                        System.out.println("No hay categorias para mostrar");
                    }
                    break;
                case "3":
                    mostrarCategorias(categoriaRepo.listarActivos());
                    System.out.println("Ingrese el ID de la categoria a eliminar: ");
                    try{
                        Long idAEliminar=Long.parseLong(sc.nextLine().trim());
                        if(categoriaRepo.eliminarLogico(idAEliminar)){

                            //Se llama directamente sin usar Optional por que se sabe que existe
                            Categoria cAux=categoriaRepo.buscarPorId(idAEliminar).get();
                            System.out.println("Catagoria \""+cAux.getNombre()+"\" eliminado exitosamente");
                        }else {
                            System.out.println("Id invalido");
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Formaro de id invalido");
                }
                    break;
                case "4":
                    mostrarCategorias(categoriaRepo.listarActivos());
                    break;
                default:
                    System.out.println("\n Opcion invalida!");
                    break;
            }
        }while(!opcMenuCat.equals("0"));
    }

    private static void menuProductos() {
        // TODO: Implementar submenú de Productos.
        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  0-Volver
        String opcProductos="asd";
        do{
            System.out.println("####### PRODUCTOS #######");
            System.out.println("1- Alta");
            System.out.println("2- Modificar");
            System.out.println("3- Baja");
            System.out.println("4- listado");
            System.out.println("\n0- Volver");

            opcProductos=sc.nextLine();
            switch(opcProductos){
                case "0":
                    break;
                case "1":
                    //Si no hay categorias no se pueden cargar items
                    if(!categoriaRepo.listarActivos().isEmpty()){
                        mostrarCategorias(categoriaRepo.listarActivos());
                        System.out.println("Seleccione una categoria de la lista");
                        Long idCategoriaElegida=Long.parseLong(sc.nextLine().trim());
                        Optional<Categoria>cat=categoriaRepo.buscarPorId(idCategoriaElegida);

                        //Solo si la categoria existe
                        if(!cat.isEmpty()){
                            String nombreProductoNuevo="";
                            do{
                                System.out.println("Ingrese el nombre del producto: ");
                                nombreProductoNuevo=sc.nextLine();
                                if(nombreProductoNuevo.isEmpty()){
                                    System.out.println("Debe ingresar un nombre");
                                }
                            }while(nombreProductoNuevo.isEmpty());

                            System.out.println("Ingrese una descripcion:");
                            String descProductoNuevo=sc.nextLine();

                            Double precio=0.0;
                            do{
                                try{
                                    System.out.println("Ingrese el precio (double)");
                                    precio=Double.parseDouble(sc.nextLine().trim());
                                }catch (NumberFormatException e){
                                    System.out.println("Formato de precio invalido");
                                }
                            }while(precio<=0.0);

                            int stock=0;
                            do{
                                try{
                                    System.out.println("Ingrese el stock");
                                    stock=Integer.parseInt(sc.nextLine().trim());
                                }catch (NumberFormatException e){
                                    System.out.println("Formato de numero invalido");
                                }
                                if(stock<0){
                                    System.out.println("El stock debe ser 0 o mayor");
                                }
                            }while(stock<0);

                            System.out.println("Ingrese el nombre del archivo de imagen");
                            String nuevoArchivoImagen=sc.nextLine().trim();

                            Producto nuevoProducto=Producto.builder()
                                    .nombre(nombreProductoNuevo)
                                    .precio(precio)
                                    .descripcion(descProductoNuevo)
                                    .stock(stock)
                                    .imagen(nuevoArchivoImagen)
                                    .disponible(true)
                                    .eliminado(false)
                                    .build();
                            productoRepo.guardar(nuevoProducto);

                            //Se actualiza el set de producto en categorias
                            List<Producto>productosExistentes=categoriaRepo.buscarProductosPorCategoria(idCategoriaElegida);
                            Set<Producto>productos=new HashSet<>(productosExistentes);
                            productos.add(nuevoProducto);

                            Categoria categoria=cat.get();
                            categoria.setProductos(productos);
                            categoriaRepo.guardar(categoria);

                            System.out.println("Producto agregado con el id"+nuevoProducto.getId()+" en la categoria "+cat.get().getNombre()+"");

                        }else{
                            System.out.println("Id categoria invalido");
                        }

                    }else{
                        System.out.println("No hay categorias para asignar");
                    }
                    break;

                case "2":
                    List<Producto>productosActivos=productoRepo.listarActivos();
                    for(Producto p:productosActivos){
                        System.out.println("ID: " + p.getId()+ ",NOMBRE: " + p.getNombre()
                                + ",PRECIO: "+ p.getPrecio() + ",STOCK: " + p.getStock());
                    }
                    System.out.println("Ingrese el id del producto a modificar: ");
                    try{
                        Long idAModificar=Long.parseLong(sc.nextLine().trim());
                        Optional<Producto> productoAModificar=productoRepo.buscarPorId(idAModificar);
                        if(!productoAModificar.isEmpty()){
                            Producto prodAux=productoAModificar.get();

                            //Si no se encuentra eliminado
                            if(!prodAux.isEliminado()){
                                System.out.println("Ingrese el nuevo nombre [ENTER] para mantener ("+prodAux.getNombre()+"");
                                String nuevoNombre=sc.nextLine();
                                if(!nuevoNombre.isEmpty()){
                                    prodAux.setNombre(nuevoNombre);
                                }

                                boolean precioValido=true;
                                double nuevoPrecio=0.0; //solo para inicializar
                                do{
                                    try{
                                        System.out.println("Ingrese el nuevo precio (actual:"+prodAux.getPrecio()+")");
                                        nuevoPrecio=Double.parseDouble(sc.nextLine().trim());
                                        precioValido=true;
                                        if(nuevoPrecio<=0){
                                            System.out.println("El precio debe ser mayor a  0");
                                            precioValido=false;
                                        }
                                    }catch(NumberFormatException e){
                                        System.out.println("Formato de precio invalido");
                                        precioValido=false;
                                    }
                                }while(precioValido==false);
                                prodAux.setPrecio(nuevoPrecio);

                                int nuevoStock=0; //Solo para inicialiar
                                boolean stockValido=true;
                                do{
                                    try{
                                        System.out.println("ingrese el nuevo stock (actual:"+prodAux.getStock()+"): ");
                                        nuevoStock=Integer.parseInt(sc.nextLine().trim());
                                        stockValido=true;
                                    }catch (NumberFormatException e){
                                        System.out.println("Formato de stock invalido");
                                    }
                                    if(nuevoStock<0){
                                        System.out.println("Stock debe ser igual-mayor a 0");
                                        stockValido=false;
                                    }
                                }while(stockValido==false);
                                prodAux.setStock(nuevoStock);
                                productoRepo.guardar(prodAux);
                                System.out.println("Producto actualizado!");


                            }else{
                                System.out.println("El producto se encuentra eliminado");
                            }

                        }else{
                            System.out.println("El id no existe");
                        }


                    }catch (NumberFormatException e){
                        System.out.println("Formato de ID invalido");
                    }


                    break;
                case "3":
                    System.out.println("Ingrese el ID del producto a eliminar");
                    try{
                        Long idAEliminar=Long.parseLong(sc.nextLine().trim());
                        Boolean eliminado=productoRepo.eliminarLogico(idAEliminar);
                        if(eliminado){
                            System.out.println("Producto Eliminado!");
                        }else{
                            System.out.println("Id no encontrado");
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Formato de ID invalido");
                    }
                    break;
                case "4":
                    mostrarProductosCategorias();
                    break;
                default:
                    System.out.println("Opcion invalida!");
                    break;
            }
        }while(!opcProductos.equals("0"));

        //System.out.println("[Productos] → TODO: implementar");
    }

    private static void menuUsuarios() {
        // TODO: Implementar submenú de Usuarios.
        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  5-Buscar por mail  0-Volver
        String opcUsuarios="";
        do{
            System.out.println("####### USUARIOS #######");
            System.out.println("1-Alta");
            System.out.println("2-Modificar");
            System.out.println("3-Baja logica");
            System.out.println("4-Listado");
            System.out.println("5- Buscar por mail");
            System.out.println("\n0-volver");

            opcUsuarios=sc.nextLine().trim();
            switch(opcUsuarios){
                case "0":
                    break;
                case "1":
                    String nombreUsuario="";
                    do{
                        System.out.println("Ingrese el nombre: ");
                        nombreUsuario=sc.nextLine().trim();
                    }while(nombreUsuario.isEmpty());

                    String apellidoUsuario="";
                    do{
                        System.out.println("Ingrese el apellido: ");
                        apellidoUsuario=sc.nextLine().trim();
                    }while(apellidoUsuario.isEmpty());

                    String mailUsuario="";
                    do{
                        System.out.println("ingrese el mail: ");
                        mailUsuario=sc.nextLine().trim();
                    }while(mailUsuario.isEmpty());

                    if(usuarioRepo.buscarPorMail(mailUsuario).isEmpty()){

                        //Los telefonos pueden empezar por 0
                        String celularUsuario="";
                        do{
                            System.out.println("Ingrese el numero de telefono");
                           celularUsuario=sc.nextLine().trim();
                        }while (celularUsuario.isEmpty());

                        String contrasenaUsuario="";
                        do{
                            System.out.println("Ingrese su contraseña");
                            contrasenaUsuario=sc.nextLine().trim();
                        }while(contrasenaUsuario.isEmpty());

                        String rol="3";
                        do{
                            System.out.println("Ingrese un rol:");
                            System.out.println("1- Admin");
                            System.out.println("2- Usuario");
                            rol=sc.nextLine().trim();
                            if(!rol.equals("1") && !rol.equals("2")){
                                System.out.println("Rol invalido");
                            }
                        }while(!rol.equals("1") && !rol.equals("2"));

                        Rol nuevoRol=(rol.equals("1"))? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevoUsuario=Usuario.builder()
                                .nombre(nombreUsuario)
                                .apellido(apellidoUsuario)
                                .mail(mailUsuario)
                                .celular(celularUsuario)
                                .contraseña(contrasenaUsuario)
                                .rol(nuevoRol)
                                .build();
                        usuarioRepo.guardar(nuevoUsuario);

                        System.out.println("Se creo un nuevo usuario con el ID: "+nuevoUsuario.getId());

                    }else{
                        System.out.println("El mail ya esta en uso, elija otro");
                    }
                    break;
                case "2":
                    mostrarUsuarios(usuarioRepo.listarActivos());
                    Long idAModificar= 0L;
                    boolean idValido=false;
                    do{
                        try{
                            System.out.println("Ingrese el ID a modificar");
                            idAModificar=Long.parseLong(sc.nextLine().trim());
                            idValido=true;
                        }catch (NumberFormatException e){
                            System.out.println("Formato de ID invalido");
                            idValido=false;
                        }
                    }while(idValido==false);

                    Optional<Usuario>usuarioBuscado=usuarioRepo.buscarPorId(idAModificar);
                    if(usuarioBuscado.isEmpty()){
                        System.out.println("El usuario no existe");

                    }else if(usuarioBuscado.get().isEliminado()){
                        System.out.println("El usuario se encuentra dado de baja");
                    }else{
                        Usuario usuarioAEditar=usuarioBuscado.get();
                        System.out.println("Ingrese un nuevo nombre (actual:"+usuarioAEditar.getNombre()+"");
                        String nombreTemp=sc.nextLine().trim();
                        if(!nombreTemp.isEmpty()){
                            usuarioAEditar.setNombre(nombreTemp);
                        }

                        System.out.println("Ingrese un nuevo apellido (actual:"+usuarioAEditar.getApellido()+")");
                        String apellidoTemp=sc.nextLine().trim();
                        if(!apellidoTemp.isEmpty()){
                            usuarioAEditar.setApellido(apellidoTemp);
                        }
                        System.out.println("Ingrese una nueva contraseña (actual:"+usuarioAEditar.getContraseña()+")");
                        String contrasenaTemp=sc.nextLine().trim();
                        if(!contrasenaTemp.isEmpty()){
                            usuarioAEditar.setContraseña(contrasenaTemp);
                        }
                        System.out.println("Ingrese un nuevo numero celular (actual:"+usuarioAEditar.getCelular()+")");
                        String celularTemp=sc.nextLine().trim();
                        if(!celularTemp.isEmpty()){
                            usuarioAEditar.setCelular(celularTemp);
                        }

                        Optional<Usuario>usuarioExiste=null;
                        //String mailTemp="";
                        boolean mailListo = false;
                        do {
                            System.out.println("Ingrese el nuevo email (actual: " + usuarioAEditar.getMail() + "):");
                            String mailTemp = sc.nextLine().trim();
                            if (mailTemp.isEmpty() || mailTemp.equals(usuarioAEditar.getMail())) {
                                mailListo = true;
                            } else if (usuarioRepo.buscarPorMail(mailTemp).isPresent()) {
                                System.out.println("El mail ya está en uso, elija otro");
                            } else {
                                usuarioAEditar.setMail(mailTemp);
                                mailListo = true;
                            }
                        } while (!mailListo);

                        usuarioRepo.guardar(usuarioAEditar);
                        System.out.println("Se actualizo el usuario");
                    }
                    break;
                case "3":
                    mostrarUsuarios(usuarioRepo.listarActivos());
                    System.out.println("Ingrese el id de usuario a eliminar (logica): ");
                    Long idAEliminar=0l;
                    try{
                        idAEliminar=Long.parseLong(sc.nextLine().trim());
                    }catch (NumberFormatException e){
                        System.out.println("Formato de Id invalido");
                    }

                    if(usuarioRepo.eliminarLogico(idAEliminar)==true){
                        System.out.println("El usuario ha sido dado de baja");
                    }else{
                        System.out.println("Usuario no encontdao");
                    }
                    break;
                case "4":
                    mostrarUsuarios(usuarioRepo.listarActivos());
                    break;
                case "5":
                    System.out.println("Ingrese el mail del usuario a buscar: ");
                    String mailBuscado=sc.nextLine().trim();
                    if(!mailBuscado.isEmpty()){
                        Optional<Usuario> usuarioBuscaMail=usuarioRepo.buscarPorMail(mailBuscado);
                        if(usuarioBuscaMail.isPresent()){
                            Usuario aux=usuarioBuscaMail.get();
                            System.out.println("\nDatos del usuario:");
                            System.out.println("ID:"+aux.getId()+",APELLIDO: "+aux.getApellido()+",NOMBRE: "+aux.getNombre()
                            +",CELULAR: "+aux.getCelular()+",MAIL: "+aux.getMail());
                        }else{
                            System.out.println("Usuario con el mail ingresado no existe");
                        }
                    }else{
                        System.out.println("Debe ingresar un mail");
                    }
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }while(!opcUsuarios.equals("0"));
        System.out.println("[Usuarios] → TODO: implementar");
    }

    private static void menuPedidos() {
        // TODO: Implementar submenú de Pedidos.
        // Opciones: 1-Alta  2-Cambiar estado  3-Baja lógica  4-Listado
        //           5-Por usuario  6-Por estado  0-Volvero
        String opcMenuPedidos="0";
        do{
            System.out.println("####### PEDIDOS #######");
            System.out.println("1-Alta");
            System.out.println("2-Cambiar");
            System.out.println("3-Baja logica");
            System.out.println("4-Listado");
            System.out.println("5-por Usuario");
            System.out.println("6-Por estado");
            System.out.println("\n0-Volver");

            opcMenuPedidos=sc.nextLine().trim();

            switch(opcMenuPedidos){
                case "0":
                    break;
                case "1":
                    List<Usuario>usuariosActivos=usuarioRepo.listarActivos();
                    if(usuariosActivos.size()>0) {
                        mostrarUsuarios(usuariosActivos);
                        System.out.println("Ingrese el ID del cliente:");
                        Long idUsuario;
                        try{
                            idUsuario=Long.parseLong(sc.nextLine().trim());

                        }catch (NumberFormatException e){
                            System.out.println("Formato de usuario invalido");
                            break;
                        }
                        Optional<Usuario>validaUsuario=usuarioRepo.buscarPorId(idUsuario);
                        if(validaUsuario.isPresent()){

                            //Seleccion de forma de pago
                            int tipoPago=4;
                            FormaPago pago=null;
                            do{
                                try{
                                    System.out.println("Seleccione una forma de pago: ");
                                    System.out.println("1-Tarjeta \n2-Transferencia \n3-Efectivo");
                                    tipoPago=Integer.parseInt(sc.nextLine().trim());
                                    if(tipoPago==1){
                                        pago=FormaPago.TARJETA;
                                    }
                                    if(tipoPago==2){
                                        pago=FormaPago.TRANSFERENCIA;
                                    }
                                    if(tipoPago==3){
                                        pago=FormaPago.EFECTIVO;
                                    }

                                }catch (NumberFormatException e){
                                    System.out.println("\nFormato de opcion invalida\n");
                                }
                            }while(tipoPago<1 || tipoPago >3);

                            //Lista donse se van a guardar los pedidos
                            Map<Long,Integer>listaTemporal=new HashMap<>();

                            //Seleccion de productos, finaliza al ingresar "S"
                            String inputpedido="";
                            do {

                                mostrarProductosDisponibles(productoRepo.listarActivos());
                                System.out.println("Ingrese el ID del producto que elige:");
                                String inputId = sc.nextLine().trim();
                                try {
                                    Long idProducto = Long.parseLong(inputId);
                                    //validar que existe, que disponible=true, pedir cantidad, agregar a lista temporal ...
                                    Optional<Producto>prodOptional=productoRepo.buscarPorId(idProducto);
                                    if(prodOptional.isPresent()){
                                        int stockDeseado=0;

                                        int existencias=0;
                                        do{
                                            existencias=prodOptional.get().getStock();
                                            System.out.println("Ingrese cuantos items desea: (existencias: "+existencias+")");
                                            try{
                                                stockDeseado= Integer.parseInt(sc.nextLine().trim());
                                            }catch (NumberFormatException e){
                                                System.out.println("Formato de numero invalido");
                                            }
                                        }while(stockDeseado<0 || stockDeseado>=existencias);

                                        listaTemporal.put(idProducto,stockDeseado);
                                        System.out.println("Producto agregado!");

                                    }else{
                                        System.out.println("No existe producto con ese ID");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("ID inválido.");
                                }
                                System.out.println("¿Desea agregar otro producto? (S/N):");
                                inputpedido= sc.nextLine().trim().toUpperCase();
                            } while (inputpedido.equals("S"));

                            if(listaTemporal.size()!=0){
                                EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
                                EntityTransaction tx = em.getTransaction();
                                try {
                                    tx.begin();

                                    Usuario usuario = em.find(Usuario.class, idUsuario);

                                    Pedido pedido = Pedido.builder()
                                            .fecha(LocalDate.now())
                                            .estado(Estado.PENDIENTE)
                                            .formaPago(pago)
                                            .build();

                                    
                                    for (Map.Entry<Long, Integer> valor : listaTemporal.entrySet()) {
                                        Producto producto = em.find(Producto.class, valor.getKey());
                                        pedido.addDetallePedido(valor.getValue(), producto);

                                        //se descuenta del stock.
                                        producto.setStock(producto.getStock() - valor.getValue());
                                    }

                                    pedido.calcularTotal();
                                    em.persist(pedido);

                                    //se fuerza para que la bd asigne un id al pedodp
                                    em.flush();
                                    usuario.getPedidos().add(pedido);
                                    tx.commit();

                                    System.out.println("Pedido creado! ID: " + pedido.getId());
                                    System.out.println("Fecha: " + pedido.getFecha());
                                    System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
                                    System.out.println("Forma de pago: " + pedido.getFormaPago());

                                    //Bucler para listar todos los pedidos
                                    for (DetallePedido d : pedido.getDetalles()) {
                                        System.out.println("  - " + d.getProducto().getNombre()
                                                + " x" + d.getCantidad()
                                                + "  subtotal: $" + d.getSubtotal());
                                    }
                                    System.out.println("Total: $" + pedido.getTotal());

                                } catch (Exception e) {
                                    if (tx.isActive()) tx.rollback();
                                    System.out.println("Error al crear el pedido, se cancelo el pedido.");
                                } finally {
                                    em.close();
                                }
                            }else{
                                System.out.println("La lista esta vacia!, el pedido debe tener al menos 1 unidad de un producto.");
                            }

                        }else{
                            System.out.println("El usuario no existe");
                        }

                    }else{
                        System.out.println("No hay usuarios dispomibles");
                    }
                    break;
                case "2":
                    mostrarPedidos(usuarioRepo.listarActivos());
                    System.out.println("Ingrese el ID del pedido a modificar:");
                    try {
                        Long idPedido = Long.parseLong(sc.nextLine().trim());
                        Optional<Pedido> pedidoOpt = pedidoRepo.buscarPorId(idPedido);
                        if (pedidoOpt.isPresent() && !pedidoOpt.get().isEliminado()) {
                            Pedido pedidoAModificar = pedidoOpt.get();
                            System.out.println("Estado actual: " + pedidoAModificar.getEstado());

                            int opEstado = 0;
                            Estado nuevoEstado = null;
                            do {
                                try {
                                    System.out.println("Seleccione el nuevo estado:");
                                    System.out.println("1-PENDIENTE\n2-CONFIRMADO\n3-TERMINADO\n4-CANCELADO");
                                    opEstado = Integer.parseInt(sc.nextLine().trim());
                                    if (opEstado == 1) nuevoEstado = Estado.PENDIENTE;
                                    if (opEstado == 2) nuevoEstado = Estado.CONFIRMADO;
                                    if (opEstado == 3) nuevoEstado = Estado.TERMINADO;
                                    if (opEstado == 4) nuevoEstado = Estado.CANCELADO;
                                } catch (NumberFormatException e) {
                                    System.out.println("Formato invalido.");
                                }
                            } while (opEstado < 1 || opEstado > 4);

                            pedidoAModificar.setEstado(nuevoEstado);
                            pedidoRepo.guardar(pedidoAModificar);
                            System.out.println("Pedido ID: " + pedidoAModificar.getId() + " actualizado a: " + nuevoEstado);

                        } else {
                            System.out.println("El pedido no existe o esta dado de baja.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Formato de ID invalido.");
                    }
                    break;
                case "3":
                    break;
                case "4":
                    mostrarPedidos(usuarioRepo.listarActivos());
                    break;
                case "5":
                    break;
                case "6":
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        }while(!opcMenuPedidos.equals("0"));
        //System.out.println("[Pedidos] → TODO: implementar");
    }

    private static void menuReportes() {
        // TODO: Implementar submenú de Reportes.
        // Opciones: 1-Productos por categoría  2-Pedidos por usuario
        //           3-Pedidos por estado  4-Total facturado  0-Volver
        System.out.println("[Reportes] → TODO: implementar");
    }

    //METODOS AUXILIARES ADICIONALES

    /**
     * Muestra categorias
     * @param cat
     */
    private static void mostrarCategorias(List<Categoria>cat){
        if(!cat.isEmpty()){
            for(Categoria c: cat){
                System.out.println("iD: "+c.getId()+",Nombre: "+c.getNombre()+",Descripcion: "+c.getDescripcion());
            }
        }
    }

    /**
     * Muestra productos activos
     * @param productos
     */
    private static void mostrarProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(
                    "ID: " + p.getId() + ",NOMBRE: " + p.getNombre()
                            + ",PRECIO: " + p.getPrecio() + ",STOCK: " + p.getStock()
                            + ",DISPONIBLE: " + (p.getDisponible() ? "SI" : "NO"));
        }
    }

    /**
     * Muestra productos activos y disponibles para la venta
     * @param productos
     */
    private static void mostrarProductosDisponibles(List<Producto> productos) {
        List<Producto> disponibles = productos.stream()
                .filter(p -> p.getDisponible())
                .toList();

        if (disponibles.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (Producto p : disponibles) {
            System.out.println(
                    "ID: " + p.getId() + ",NOMBRE: " + p.getNombre()
                            + ",PRECIO: " + p.getPrecio() + ",STOCK: " + p.getStock());
        }
    }

    /**
     * Arma un map entre categorias y productos, para luego podes mostrarlos asociados
     */
    private static void mostrarProductosCategorias() {
        //se recorren todas las categorias y se arma un hash nuevo
        Map<Long, String> categoriaDeProducto = new HashMap<>();
        for (Categoria cat : categoriaRepo.listarActivos()) {
            for (Producto p : categoriaRepo.buscarProductosPorCategoria(cat.getId())) {
                categoriaDeProducto.put(p.getId(), cat.getNombre());
            }
        }

        // lista productos
        List<Producto> productos = productoRepo.listarActivos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        for (Producto p : productos) {
            String nombreCat = categoriaDeProducto.getOrDefault(p.getId(), "(sin categoría)");
            System.out.println(
                    "ID: " + p.getId()+ ",NOMBRE: " + p.getNombre()
                            + ",PRECIO: "+ p.getPrecio() + ",STOCK: " + p.getStock()
                            + ",DISPONIBLE: " + (p.getDisponible() ? "SI" : "NO")
                            + ",CATEGORIA: " + nombreCat);
        }
    }

    /**
     * Muestra todos los usuarios del sistema
     * @param usuarios
     */
    private static void mostrarUsuarios(List<Usuario>usuarios){
        if(!usuarios.isEmpty()){
            for(Usuario u: usuarios){
                System.out.println("ID: "+u.getId()+",APELLIDO:"+u.getApellido()+",NOMBRE: "+u.getNombre()+",MAIL: "+u.getMail()+",ROL:"+u.getRol().toString());
            }
        }
    }

    /**
     * Muestra solo los clientes del sistema, no admins.
     * @param usuarios
     */
    private static void mostrarClientes(List<Usuario>usuarios){
        if(!usuarios.isEmpty()){
            for(Usuario u: usuarios){
                if(u.getRol()==Rol.USUARIO){
                    System.out.println("ID: "+u.getId()+",APELLIDO:"+u.getApellido()+",NOMBRE: "+u.getNombre()+",MAIL: "+u.getMail()+"");
                }
            }
        }
    }

    /**
     * Muestra resumen de pedidos
     * @param usuarios
     */
    private static void mostrarPedidos(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            List<Pedido> pedidos = usuarioRepo.buscarPedidosPorUsuario(u.getId());
            for (Pedido p : pedidos) {
                System.out.println("ID: " + p.getId()
                        + ", FECHA: " + p.getFecha()
                        + ", ESTADO: " + p.getEstado()
                        + ", FORMA DE PAGO: " + p.getFormaPago()
                        + ", USUARIO: " + u.getNombre() + " " + u.getApellido()
                        + ", TOTAL: $" + p.getTotal());
            }
        }
    }

}

