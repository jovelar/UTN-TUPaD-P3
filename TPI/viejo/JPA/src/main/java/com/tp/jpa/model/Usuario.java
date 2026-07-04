package com.tp.jpa.model;
import com.tp.jpa.model.enums.Rol;
import jakarta.persistence.*;
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

@Entity
@Table(name="Usuarios")
public class Usuario extends Base {
    @Column(name="nombre")
    private String nombre;
    @Column(name="apellido")
    private String apellido;
    @Column(name="email")
    private String mail;
    @Column(name="celular")
    private String celular;
    @Column(name="contrasena")
    private String contrasena;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_pedidos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    @Builder.Default
    private Set<Pedido> pedidos= new HashSet<>();



    //Metodo adicional, no figura en el UML
    public void agregarPedido(Pedido p){
        pedidos.add(p);
    }
}
