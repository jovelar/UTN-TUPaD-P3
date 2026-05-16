package com.tup.programacion3.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    public Base(Long id,LocalDateTime createdAt){
        this.id=id;
        this.createdAt=createdAt;
        //por defecto al crear, no esta como "eliminado"
        eliminado=false;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString(){
        return "id:"+this.id+",creado:"+this.createdAt+",eliminado:"+this.eliminado;
    }

    @Override
    public boolean equals(Object object){
        if(this==object){
            return true;
        }

        if(object==null || getClass()!=object.getClass()){
            return false;
        }

        Base compara=(Base)object;

        //return id == compara.id && Objects.equals(createdAt,compara.createdAt);
        //SE ELIMINO A LOCALDATETIME COMO ELEMENTO PARA COMPARAR DEBIDO A QUE ES IMPOSIBLE
        //QUE UN OBJECTO SE CREE AL MISMO MICROSEGUNDO QUE OTRO, PUDIENDO TENER 2 OBJETOS EL MISMO ID

        return Objects.equals(id,compara.getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,createdAt);
    }

}
