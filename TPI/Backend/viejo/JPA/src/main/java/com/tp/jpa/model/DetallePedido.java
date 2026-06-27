package com.tp.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(of={"cantidad","subTotal"},callSuper=true)
@SuperBuilder
@EqualsAndHashCode(of={"cantidad","subTotal"},callSuper = true)
@NoArgsConstructor

@Table(name="detalle_pedido")
public class DetallePedido extends Base {
    @Column(name="cantidad")
    private int cantidad;
    @Column(name="subtotal")
    private double subTotal;

    //Por que un mismo producto puede estar en pedidos diferentes
    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

}
