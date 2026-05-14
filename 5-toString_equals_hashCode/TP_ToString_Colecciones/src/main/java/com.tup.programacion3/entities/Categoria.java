package com.tup.programacion3.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Categoria extends Base{

    private String nombre;
    private String descripcion;

    public Categoria(Long id, LocalDateTime createdAt,String nombre, String descripcion) {
        super(id, createdAt);
        this.nombre=nombre;
        this.descripcion=descripcion;
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
}
