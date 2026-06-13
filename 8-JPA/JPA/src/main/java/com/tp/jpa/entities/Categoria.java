package com.tp.jpa.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(of={"nombre","descripcion"})
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of={"nombre"},callSuper = true)
@Table(name="categoria")
public class Categoria extends Base{

    @Column(name="nombre")
    private String nombre;
    @Column(name="descripcion")
    private String descripcion;
    @Builder.Default

    @OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Producto> productos = new HashSet<>();

    //Este metodo no esta en el UML

    public void agregarProducto(Producto p){
        productos.add(p);
    }
}
