package com.tup.programacion3.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(of={"nombre","descripcion"})
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of={"nombre"},callSuper = true)
public class Categoria extends Base{

    private String nombre;
    private String descripcion;
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();

    //Este metodo no esta en el UML

    public void agregarProducto(Producto p){
        productos.add(p);
    }
}
