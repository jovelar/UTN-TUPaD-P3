package com.tup.programacion3.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(of={"nombre","precio"}, callSuper=true)
@ToString

public class Producto extends Base{
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    /*
    public Producto(Long id, LocalDateTime createdAt,String nombre,Double precio, String descripcion,int stock,String imagen,boolean disponible, Categoria categoria) {
        super(id, createdAt);
        this.nombre=nombre;
        this.precio=precio;
        this.descripcion=descripcion;
        this.stock=stock;
        this.imagen=imagen;
        this.disponible=disponible;
        this.categoria=categoria;
    }
     */
/*
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString(){
        return super.toString()+",Nombre:"+this.nombre+
                ",Descripcion:"+this.descripcion+
                ",Precio:"+this.precio+
                ",Imagen:"+ this.imagen+
                ",Stock:"+this.stock+
                ",Disponible:"+this.disponible;
    }

    @Override
    public boolean equals(Object object){
        if(super.equals(object)==false){
            return false;
        }

        Producto p = (Producto) object;

        return Objects.equals(nombre,p.nombre) && Objects.equals(precio,p.precio);
    }

    @Override
     public int hashCode(){
        return Objects.hash(super.hashCode(),nombre,precio);
    }

 */
}
