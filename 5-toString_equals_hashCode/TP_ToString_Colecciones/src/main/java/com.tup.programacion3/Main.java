package com.tup.programacion3;

import com.tup.programacion3.entities.Categoria;
import com.tup.programacion3.entities.Producto;
import com.tup.programacion3.entities.Usuario;
import com.tup.programacion3.enums.Rol;

import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //USUARIOS
        Usuario us1 = new Usuario(2000001L,
                LocalDateTime.now(),
                "Roberto",
                "Carlos",
                "rcarlos@gmail.com",
                "012254545556",
                "milanesas007",
                Rol.USUARIO);

        Usuario us2 = new Usuario(2000002L,
                LocalDateTime.now(),
                "Pepe",
                "Mujica",
                "pepemujica@gmail.com",
                "01145469971",
                "mujica001",
                Rol.USUARIO);

        Usuario adm1 = new Usuario(3000001L,
                LocalDateTime.now(),
                "Pepe",
                "Argento",
                "peperacing@gmai.com",
                "011997355448899",
                "recing1234",
                Rol.ADMIN);

        //CATEGORIAS
        Categoria limpieza = new Categoria(1000001L, LocalDateTime.now(),"Limpieza","Productos de limpieza");
        Categoria lacteos = new Categoria(1000002L,LocalDateTime.now(),"lacteos","Derivados de la leche");
        Categoria gaseosas = new Categoria(1000003L,LocalDateTime.now(),"Gaseosas","Bebidas azucaradas con gas");

        //PRODUCTOS
        Producto limp1 = new Producto(5000001L,
                LocalDateTime.now(),
                "Harpic",
                25000.00,
                "Limpiador de sarro",
                2000,
                "Harpic_limpiador_1.jpg",
                true,
                limpieza);

        Producto limp2 = new Producto(5000002L,
                LocalDateTime.now(),
                "Detergente Ala",
                4500.00,
                "Detergente con aroma a limon",
                8000,
                "Detergente_ala.jpg",
                true,
                limpieza);

        Producto limp3 = new Producto(5000003L,
                LocalDateTime.now(),
                "Esponja Ala",
                1300.00,
                "Esponja sintetica doble",
                1600,
                "Esponja_ALA.jpg",
                true,
                limpieza);

        Producto limp4 = new Producto(5000004L,
                LocalDateTime.now(),
                "Esponja de acero ALA",
                1800.00,
                "Esponja de acero gruesa",
                1000,
                "Esponja_Acero_ALA.jpg",
                true,
                limpieza);

        Producto lacteo1 = new Producto(6000001L,
                LocalDateTime.now(),
                "Leche entera la Serenisima",
                1200.00,
                "Leche entera en caja de 1 litro",
                5000,
                "Leche_entera_LS_Caja_litro.jpg",
                true,
                lacteos);
    }

}