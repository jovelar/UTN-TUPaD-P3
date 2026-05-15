package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Rol;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private Set<Pedido> pedidos= new HashSet<>();

    public Usuario(Long id, LocalDateTime createdAt,String nombre, String apellido, String mail, String celular, String contrasena, Rol rol){
        super(id,createdAt);
        this.nombre=nombre;
        this.apellido=apellido;
        this.mail=mail;
        this.celular=celular;
        this.contrasena=contrasena;
        this.rol=rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString(){
        return this.nombre+","+this.apellido;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;

        if(obj==null || getClass()!=obj.getClass()) return false;

        Usuario usuario=(Usuario)obj;
        if(this.nombre==usuario.nombre && this.apellido==usuario.apellido && this.rol==usuario.rol) return true;

        //Si no cumple ninguna condicion, por defecto es false
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.nombre,this.apellido);
    }


    //Metodo adicional, no figura en el UML
    public void agregarPedido(Pedido p){
        pedidos.add(p);
    }
}
