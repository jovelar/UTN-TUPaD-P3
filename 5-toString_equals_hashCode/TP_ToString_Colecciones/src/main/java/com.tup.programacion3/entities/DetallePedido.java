package com.tup.programacion3.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class DetallePedido extends Base {

    private int cantidad;
    private double subTotal;
    private Producto producto;

    public DetallePedido(Long id, LocalDateTime createdAt,Producto producto,int cantidad, double subTotal) {
        super(id, createdAt);
        this.cantidad=cantidad;
        this.subTotal=subTotal;
        this.producto=producto;
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
}
