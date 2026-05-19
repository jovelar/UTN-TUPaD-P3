package com.tup.programacion3.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString(of={"cantidad","subTotal"},callSuper=true)
@SuperBuilder
@EqualsAndHashCode(of={"cantidad","subTotal"},callSuper = true)
public class DetallePedido extends Base {

    private int cantidad;
    private double subTotal;
    private Producto producto;

    /*
    public DetallePedido(Long id, LocalDateTime createdAt,Producto producto,int cantidad) {
        super(id, createdAt);
        this.cantidad=cantidad;
        this.producto=producto;
        this.subTotal=cantidad * producto.getPrecio();
    }



    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString(){
        return super.toString()+",Cantidad:"+this.cantidad+",subtotal:"+this.subTotal;
    }


    @Override
    public boolean equals(Object object){
        if(this==object){
            return true;
        }

        if(super.equals(object)==false){
            return false;
        }
        //No creo que la cantiudad y el subtotal sea relevante para determinar que es
        //el mismo objeto, para eso en este caso esta el id
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),this.cantidad,this.subTotal);
    }

     */
}
