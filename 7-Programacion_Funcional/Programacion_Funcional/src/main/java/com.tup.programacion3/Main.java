package com.tup.programacion3;
import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Pedido;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.entities.Usuario;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import com.tup.programacion3.enums.Rol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void mostrarProducto(Producto p){
        System.out.println(p);
    }

    public static void mostrarProductos(Categoria cat){
        for(Producto p : cat.getProductos()){
            mostrarProducto(p);
        }
    }

    public static void mostrarCategoriasYProductos(Set<Categoria>categorias){
        for(Categoria c: categorias){
            System.out.println("\n");
            System.out.println("###"+c+"###");
            mostrarProductos(c);
        }
    }

    public static void mostrarPedido(Pedido p){
        System.out.println(p);
    }

    public static void mostrarPedidos(Usuario usuario){
        for(Pedido p: usuario.getPedidos()){
            mostrarPedido(p);
        }
    }

    public static void mostrarUsuario(Usuario u){
        System.out.println(u);
    }

    public static void mostrarUsuarioConMasPedidos(Set<Usuario> usuarios){
        Usuario usuarioMayor=null;

        for(Usuario u: usuarios){
            if(usuarioMayor==null || u.getPedidos().size()>usuarioMayor.getPedidos().size()){
                usuarioMayor=u;
            }
        }
        System.out.println("El usuario con mas pedidos es :\n");
        System.out.println(usuarioMayor);
        System.out.println("\n Sus pedidos: \n");
        mostrarPedidos(usuarioMayor);
    }

    public static void compararConProducto(Producto producto,Set<Categoria>categorias){
        for(Categoria c: categorias){
            Set<Producto>listaProductos=c.getProductos();
            for(Producto p:listaProductos){
                System.out.println("Comparando: "+p.getNombre()+" con "+producto.getNombre()+" : "+p.equals(producto));
            }
        }
    }

    public static void main(String[] args) {

        //USUARIOS


        Usuario us1=Usuario.builder()
                .nombre("Roberto")
                .apellido("Roberto")
                .mail("rcarlos@gmail.com")
                .celular("012254545556")
                .contrasena("milanesas007")
                .rol(Rol.USUARIO)
                .id(2000001L)
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
                .id(2000002L)
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
                .id(3000001L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();





        //CATEGORIAS

        Categoria limpieza = Categoria.builder().nombre("Limpieza").descripcion("Productos de limpieza").id(1000001L).eliminado(false).createdAt(LocalDateTime.now()).build();


        Categoria lacteos = Categoria.builder().nombre("lacteos").descripcion("Derivados de la leche").id(1000002L).eliminado(false).createdAt(LocalDateTime.now()).build();


        Categoria gaseosas = Categoria.builder().nombre("Gaseosas").descripcion("Bebidas azucaradas con gas").id(1000003L).eliminado(false).createdAt(LocalDateTime.now()).build();


        //PRODUCTOS


        Producto limp1 = Producto.builder()
                .nombre("Harpic")
                .precio(25000.00)
                .descripcion("Harpic_limpiador_1.jpg")
                .stock(2000).imagen("Harpic_limpiador_1.jpg")
                .disponible(true)
                .categoria(limpieza)
                .id(5000001L)
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
                .id(5000002L)
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
                .id(5000003L)
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
                .id(5000004L)
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
                .id(6000001L)
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
                .id(6000002L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Producto lacteo3 = Producto.builder()
                .nombre("Yogurt la Serenisima 1L")
                .precio(2000.00)
                .descripcion("Yogurt La Serenisima sabor vainilla, por 1 litro")
                .stock(6000)
                .imagen("Yogurt_LS_1l_jpg")
                .disponible(true)
                .categoria(lacteos)
                .id(6000003L)
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
                .id(7000001L)
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
                .id(7000002L)
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
                .id(7000003L)
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
                .id(9000001L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido1.addDetallePedido(8000001L,4,lacteo1);
        pedido1.addDetallePedido(8000002L,4,gas3);
        pedido1.addDetallePedido(8000003L,1,limp4);



        Pedido pedido2 = Pedido.builder()
                .fecha(LocalDate.of(2026,06,01))
                .estado(Estado.PENDIENTE).total(0.0)
                .formaPago(FormaPago.EFECTIVO)
                .id(9000002L).eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido2.addDetallePedido(800004L,1,limp3);
        pedido2.addDetallePedido(800005L,5,limp1);
        pedido2.addDetallePedido(800006L,2,limp4);



        Pedido pedido3 = Pedido.builder()
                .fecha(LocalDate.of(2026,06,01))
                .estado(Estado.CONFIRMADO)
                .total(0.0)
                .formaPago(FormaPago.TARJETA)
                .id(9000003L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido3.addDetallePedido(8000007L,1,gas3);
        pedido3.addDetallePedido(8000008L,5,lacteo1);
        pedido3.addDetallePedido(8000008L,6,limp3);

        /*
        Pedido pedido4 = new Pedido(9000004L,
                LocalDateTime.now(),LocalDate.of(2026,05,25),
                Estado.TERMINADO,FormaPago.TRANSFERENCIA);
        */
        Pedido pedido4 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO).total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)
                .id(9000004L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();

        pedido4.addDetallePedido(8000009L,5,lacteo3);
        pedido4.addDetallePedido(8000010L,5,gas2);
        //pedido4.addDetallePedido();


        Pedido pedido5 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO)
                .total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)
                .id(9000005L)
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .build();


        Pedido pedido6 = Pedido.builder()
                .fecha(LocalDate.of(2026,05,25))
                .estado(Estado.TERMINADO)
                .total(0.0)
                .formaPago(FormaPago.TRANSFERENCIA)
                .id(9000006L)
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
                .id(5000001L)
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

        Consumer<List<Producto>> mostrarProductos = 
    }
}