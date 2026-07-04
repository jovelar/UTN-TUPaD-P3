package com.tp.jpa.model;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import jakarta.persistence.*;
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

@Entity
@Table(name = "Pedidos")
public class Pedido extends Base implements Calculable {
    @Column(name="fecha")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private Estado estado;

    @Column(name="total")
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(name="forma_pago")
    private FormaPago formaPago;
    @Builder.Default

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="pedido_id")
    private Set<DetallePedido>detalles= new HashSet<>();

    public void addDetallePedido(int cantidad, Producto producto){
        //DetallePedido dp = new DetallePedido(id,LocalDateTime.now(),producto,cantidad);
        DetallePedido dp = DetallePedido.builder()
                .cantidad(cantidad)
                .subTotal(0)
                .producto(producto)

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
