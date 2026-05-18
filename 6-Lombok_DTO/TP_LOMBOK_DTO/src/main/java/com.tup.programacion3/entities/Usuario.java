package com.tup.programacion3.entities;

import com.tup.programacion3.enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(of = {"nombre", "apellido"})
@EqualsAndHashCode(of = {"nombre", "apellido", "rol"})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private Set<Pedido> pedidos = new HashSet<>();

    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }
}