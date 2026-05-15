package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pedido extends Base {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Set<DetallePedido>detalles = new HashSet<>();

    public Pedido(Long id, LocalDateTime createdAt, LocalDate fecha, Estado estado, Double total, FormaPago formaPago) {
        super(id, createdAt);
        this.fecha=fecha;
        this.estado=estado;
        this.total=total;
        this.formaPago=formaPago;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void addDetalleEnvio(int canto,Producto producto){

    }

    @Override
    public String toString(){
        return super.toString()+",fecha:"+this.fecha+",estado:"+this.estado+",total:"+this.total+",forma de pago:"+this.formaPago;
    }

    @Override
    public boolean equals(Object object){
        if(super.equals(object)==false){
            return true;
        }

        if(object==null || getClass()!=object.getClass()){
            return false;
        }

        Pedido p=(Pedido) object;
        return Objects.equals(fecha,p.fecha) && Objects.equals(estado,p.estado);
    }

    public int hashCode(){
        return Objects.hash(super.hashCode(),fecha,estado);
    }
}
