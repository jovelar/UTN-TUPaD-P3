package com.tup.programacion3.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(of={"nombre","precio"}, callSuper=true)
@NoArgsConstructor
@ToString

@Entity
@Table(name ="producto")
public class Producto extends Base{
    @Column(name="nombre")
    private String nombre;
    @Column(name="precio")
    private Double precio;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="stock")
    private int stock;
    @Column(name="imagen")
    private String imagen;
    @Column(name="disponible")
    private boolean disponible;
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

}
