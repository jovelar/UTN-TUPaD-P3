package com.tup.programacion3.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@ToString(of={"cantidad","subTotal"},callSuper=true)
@SuperBuilder
@EqualsAndHashCode(of={"cantidad","subTotal"},callSuper = true)
public class DetallePedido extends Base {

    private int cantidad;
    private double subTotal;
    private Producto producto;

}
