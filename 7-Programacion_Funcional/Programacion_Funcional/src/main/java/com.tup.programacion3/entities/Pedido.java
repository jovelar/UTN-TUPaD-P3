package com.tup.programacion3.entities;
import com.tup.programacion3.enums.Estado;
import com.tup.programacion3.enums.FormaPago;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"fecha","estado"},callSuper = true)
@SuperBuilder

public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    @Builder.Default
    private Set<DetallePedido>detalles= new HashSet<>();

    public void addDetallePedido(long id, int cantidad,Producto producto){
        //DetallePedido dp = new DetallePedido(id,LocalDateTime.now(),producto,cantidad);
        DetallePedido dp = DetallePedido.builder()
                .cantidad(cantidad)
                .subTotal(0)
                .producto(producto)
                .id(id)
                .eliminado(false)
                .createdAt(LocalDateTime.now())

                .build();
        detalles.add(dp);
    }

    public DetallePedido findDetallePedidoByProducto(Producto p){
        DetallePedido detalleBuscado = null;

        for(DetallePedido d : detalles ){
            if(d.getProducto().equals(p)){
                detalleBuscado=d;
                break;
            }
        }
        return detalleBuscado;
    }

    public void deleteDetallePedidoByProducto(Producto producto){
        DetallePedido aEliminar=null;
        for(DetallePedido d: detalles){
            if(d.getProducto().equals(producto)){
                //guardar referencia para luego eliminar fuera del bucle
                aEliminar=d;
                break;
            }
        }

        if(aEliminar!=null){
            detalles.remove(aEliminar);
        }
    }

    @Override
    public void calcularTotal() {
        total=detalles.stream()
                .mapToDouble(DetallePedido::getSubTotal)
                .sum();
    }
}
