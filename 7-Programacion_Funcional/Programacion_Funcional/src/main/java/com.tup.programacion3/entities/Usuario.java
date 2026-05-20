package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(of={"nombre","apellido","rol"},callSuper = true)
@ToString(of={"nombre","apellido","rol"})

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    @Builder.Default
    private Set<Pedido> pedidos= new HashSet<>();



    //Metodo adicional, no figura en el UML
    public void agregarPedido(Pedido p){
        pedidos.add(p);
    }
}
