package com.tp.jpa;
import com.tp.jpa.entities.*;
import com.tp.jpa.entities.*;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

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
/*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();
        //USUARIOS
        Usuario us1=Usuario.builder()
                .nombre("Roberto")
                .apellido("Roberto")
                .mail("rcarlos@gmail.com")
                .celular("012254545556")
                .contrasena("milanesas007")
                .rol(Rol.USUARIO)
                .eliminado(false)
                .createdAt(LocalDateTime.now())

                .build();



        Usuario us2 = Usuario.builder()
                .nombre("Pepe")
                .apellido("Mujica")
                .mail("pepemujica@gmail.com")
                .celular("01145469971")
                .contrasena("mujica001")
                .rol(Rol.USUARIO)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();



        Usuario adm1 = Usuario.builder()
                .nombre("Ppepe")
                .apellido("Argento")
                .mail("peperacing@gmai.com")
                .celular("011997355448899")
                .contrasena("recing1234")
                .rol(Rol.ADMIN)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();





        //CATEGORIAS

        Categoria limpieza = Categoria.builder().nombre("Limpieza").descripcion("Productos de limpieza").eliminado(false).createdAt(LocalDateTime.now()).build();


        Categoria lacteos = Categoria.builder().nombre("lacteos").descripcion("Derivados de la leche").eliminado(false).createdAt(LocalDateTime.now()).build();


        Categoria gaseosas = Categoria.builder().nombre("Gaseosas").descripcion("Bebidas azucaradas con gas").eliminado(false).createdAt(LocalDateTime.now()).build();


        //PRODUCTOS


        Producto limp1 = Producto.builder()
                .nombre("Harpic")
                .precio(25000.00)
                .descripcion("Harpic_limpiador_1.jpg")
                .stock(4).imagen("Harpic_limpiador_1.jpg")
                .disponible(true)
                .categoria(limpieza)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto limp2 = Producto.builder()
                .nombre("Detergente Ala")
                .precio(4500.00)
                .descripcion("Detergente con aroma a limon")
                .stock(8000)
                .imagen("Detergente_ala.jpg")
                .disponible(true).categoria(limpieza)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();



        Producto limp3 = Producto.builder()
                .nombre("Esponja Ala")
                .precio(1300.00)
                .descripcion("Esponja sintetica doble")
                .stock(1600)
                .imagen("Esponja_ALA.jpg")
                .disponible(true)
                .categoria(limpieza)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto limp4 = Producto.builder()
                .nombre("Esponja de acero ALA")
                .precio(1800.00)
                .descripcion("Esponja de acero gruesa")
                .stock(1000)
                .imagen("Esponja_Acero_ALA.jpg")
                .disponible(true)
                .categoria(limpieza)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto lacteo1 = Producto.builder()
                .nombre("Leche entera la Serenisima")
                .precio(1200.00)
                .descripcion("Leche entera en caja de 1 litro")
                .stock(5000)
                .imagen("Leche_entera_LS_Caja_litro.jpg")
                .disponible(true)
                .categoria(lacteos)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto lacteo2 = Producto.builder()
                .nombre("Queso cremoso la Serenisima")
                .precio(4600.00)
                .descripcion("Queso cremoso la serenisima, por 500 gramos")
                .stock(2500)
                .imagen("Queso_ls_500.jpg")
                .disponible(true)
                .categoria(lacteos)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto lacteo3 = Producto.builder()
                .nombre("Yogurt la Serenisima 1L")
                .precio(2000.00)
                .descripcion("Yogurt La Serenisima sabor vainilla, por 1 litro")
                .stock(1)
                .imagen("Yogurt_LS_1l_jpg")
                .disponible(true)
                .categoria(lacteos)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();



        Producto gas1 = Producto.builder()
                .nombre("Manaos 500cc ")
                .precio(900.00)
                .descripcion( "Gaseosa Manos sabor coca por 500cc")
                .stock(9000).imagen("Manaos_500cc.jpg")
                .disponible(true)
                .categoria(gaseosas)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto gas2 = Producto.builder()
                .nombre("Sprite 500cc")
                .precio(1100.00)
                .descripcion("Gaseosa Sprite 500cc")
                .stock(11000).imagen("Sprite_500.jpg")
                .disponible(true)
                .categoria(gaseosas)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();



        Producto gas3 = Producto.builder()
                .nombre("Fanta 500cc")
                .precio(1100.00)
                .descripcion("Fanta_500cc.jpg")
                .stock(8500)
                .imagen("Fanta_500cc.jpg")
                .disponible(true)
                .categoria(gaseosas)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        //SE CARGAN LOS PRODUCTOS A LAS CATEGORIAS
        limpieza.agregarProducto(limp1);
        limpieza.agregarProducto(limp2);
        limpieza.agregarProducto(limp3);
        limpieza.agregarProducto(limp4);

        lacteos.agregarProducto(lacteo1);
        lacteos.agregarProducto(lacteo2);
        lacteos.agregarProducto(lacteo3);

        gaseosas.agregarProducto(gas1);
        gaseosas.agregarProducto(gas2);
        gaseosas.agregarProducto(gas3);

        //PEDIDOS


        Pedido pedido1 = Pedido.builder()
                .fecha(LocalDate.of(2026,06,01))
                .estado(Estado.PENDIENTE)
                .total(0.0)
                .formaPago(FormaPago.EFECTIVO)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido1.addDetallePedido(4,lacteo1);
        pedido1.addDetallePedido(4,gas3);
        pedido1.addDetallePedido(1,limp4);



        Pedido pedido2 = Pedido.builder()
                .fecha(LocalDate.of(2026,06,01))
                .estado(Estado.PENDIENTE).total(0.0)
                .formaPago(FormaPago.EFECTIVO)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido2.addDetallePedido(1,limp3);
        pedido2.addDetallePedido(5,limp1);
        pedido2.addDetallePedido(2,limp4);



        Pedido pedido3 = Pedido.builder()
                .fecha(LocalDate.of(2026,06,01))
                .estado(Estado.CONFIRMADO)
                .total(0.0)
                .formaPago(FormaPago.TARJETA)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido3.addDetallePedido(1,gas3);
        pedido3.addDetallePedido(5,lacteo1);
        pedido3.addDetallePedido(6,limp3);

        /*
        Pedido pedido4 = new Pedido(9000004L,
                LocalDateTime.now(),LocalDate.of(2026,05,25),
                Estado.TERMINADO,FormaPago.TRANSFERENCIA);
        */
        /*
        Pedido pedido4 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO).total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido4.addDetallePedido(5,lacteo3);
        pedido4.addDetallePedido(5,gas2);
        //pedido4.addDetallePedido();


        Pedido pedido5 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO)
                .total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Pedido pedido6 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO)
                .total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        //CARGANDO LOS PEDIDOS A LOS CLIENTES
        us1.agregarPedido(pedido1);
        us1.agregarPedido(pedido2);
        us2.agregarPedido(pedido3);
        us2.agregarPedido(pedido4);
        us2.agregarPedido(pedido5);
        us2.agregarPedido(pedido6);

        //ARREGLO DE CATEGORIAS PARA MOSTRAR TODO

        Set<Categoria>categorias = new HashSet<>();
        categorias.add(limpieza);
        categorias.add(lacteos);
        categorias.add(gaseosas);

        //ARREGLO DE USUARIOS PARA REALIZAR COMPARACIONES
        Set<Usuario> usuarios = new HashSet<>();
        usuarios.add(us1);
        usuarios.add(us2);

        //CREANDO EL PRODUCTO PARA LA COMPARACION DEL PUNTO 5

        Producto productoAComparar = Producto.builder()
                .nombre("Harpic")
                .precio(25000.00)
                .descripcion("Limpiador de sarro")
                .stock(2000)
                .imagen( "Harpic_limpiador_1.jpg")
                .disponible(true)
                .categoria(limpieza)

                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        /*
        System.out.println("\n Mostrando un producto\n");
        mostrarProducto(limp1);

        System.out.println("\n Mostrando categorias y productos\n");
        mostrarCategoriasYProductos(categorias);

        System.out.println("\n Mostrando el usuario con mas pedidos y sus pedidos\n");
        mostrarUsuarioConMasPedidos(usuarios);

        System.out.println("\nComparando productos con un producto nuevo:\n");
        compararConProducto(productoAComparar,categorias);
         */
        /*
        //Mostrar todos los productos habilitados
        categorias.stream()
                .flatMap(c->c.getProductos().stream()) //Junta todos los productos de todas las categorias en un unico stream
                .filter(Producto::isDisponible)
                .forEach(p -> System.out.println(p.getNombre()));


        //Mostrar todos los productos de un pedido
        System.out.println("\nHay "+ pedido1.getDetalles().stream().mapToInt(DetallePedido::getCantidad).sum()+" items \n");

        //Mostrar items que tengan menos de 5 en stock
        categorias.stream().flatMap(c->c.getProductos().stream())
                .filter(i -> i.getStock() <5)
                .forEach(p -> System.out.println(p.getNombre() + " Tiene menos de 5 items"));


        //4- persistiendo los objetos
        em.getTransaction().begin();

       //categorias primero porque productos dependen de ellas
        em.persist(limpieza);
        em.persist(lacteos);
        em.persist(gaseosas);

        //productos
        em.persist(limp1);
        em.persist(limp2);
        em.persist(limp3);
        em.persist(limp4);
        em.persist(lacteo1);
        em.persist(lacteo2);
        em.persist(lacteo3);
        em.persist(gas1);
        em.persist(gas2);
        em.persist(gas3);

        // Usuarios antes que pedidos porque pedidos dependen de ellos
        em.persist(us1);
        em.persist(us2);
        em.persist(adm1);

         // Pedidos
        em.persist(pedido1);
        em.persist(pedido2);
        em.persist(pedido3);
        em.persist(pedido4);
        em.persist(pedido5);
        em.persist(pedido6);

        em.getTransaction().commit();


        //5- Moodificando 2 productos

        em.getTransaction().begin();

        Producto prod1 = em.find(Producto.class,3l);
        //Si existe
        if(prod1!=null){
            prod1.setPrecio(666.00);
        }

        Producto prod2 = em.find(Producto.class,5l);
        //Si existe
        if(prod2!=null){
            prod2.setPrecio(77777.00);
        }
        em.getTransaction().commit();

        Scanner scan = new Scanner(System.in);

        //6 Buscar un usuario por id
        em.getTransaction().begin();
        System.out.println("Ingrese un id a buscar: ");
        Long idAbuscar=scan.nextLong();

        Usuario usBusqueda = em.find(Usuario.class,idAbuscar);
        if(usBusqueda!=null){
            System.out.println(usBusqueda.toString());
        }else{
            System.out.println("El Usuario no existe");
        }

        em.getTransaction().commit();

        //7 Buscando por email

        em.getTransaction().begin();
        System.out.println("Ingrese un email a buscar: ");
        String emailABuscar = scan.next();

        Usuario usuarioBuscado = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                .setParameter("mail", emailABuscar).getSingleResult();

        if(usuarioBuscado!=null){
            System.out.println(usuarioBuscado.toString());
        }else{
            System.out.println("No existe usuario con ese email");
        }

        em.getTransaction().commit();

         //8 Borrando un producto de forma logica

        em.getTransaction().begin();
        System.out.println("Ingrese el id del producto a eliminar: ");
        Long idProductoAEliminar = scan.nextLong();
        Producto productoBajaLogica = em.find(Producto.class,idProductoAEliminar);
        if(productoBajaLogica!=null){
            productoBajaLogica.setDisponible(false);
            System.out.println("Producto dado de baja!");
        }
        else{
            System.out.println("El producto no existe");
        }
        em.getTransaction().commit();

        em.close();
        emf.close();

    }*/
    }
}