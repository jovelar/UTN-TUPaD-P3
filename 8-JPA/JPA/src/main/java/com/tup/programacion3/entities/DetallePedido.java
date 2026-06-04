package com.tup.programacion3.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(of={"cantidad","subTotal"},callSuper=true)
@SuperBuilder
@EqualsAndHashCode(of={"cantidad","subTotal"},callSuper = true)
@NoArgsConstructor
public class DetallePedido extends Base {

    private int cantidad;
    private double subTotal;
    private Producto producto;

}
