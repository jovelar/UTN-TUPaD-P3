package com.tup.programacion3.entities;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuperBuilder
public class Categoria extends Base{

    private String nombre;
    private String descripcion;
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();

    /*
    public Categoria(Long id, LocalDateTime createdAt,String nombre, String descripcion) {
        super(id, createdAt);
        this.nombre=nombre;
        this.descripcion=descripcion;
    }
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString(){
        return super.toString()+",nombre:"+this.nombre+",descripcion:"+this.descripcion;
    }

    @Override
    public boolean equals(Object object){
        if(super.equals(object)==false){
            return false;
        }
        Categoria cat=(Categoria) object;

        //Solo se tomo como campo para determinar si es el mismo el nombre
        //No considere que la descripcion sea relevante para comparar
        return Objects.equals(nombre,cat.nombre);
    }
    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(),nombre);
    }

    //Este metodo no esta en el UML

    public void agregarProducto(Producto p){
        productos.add(p);
    }
}
