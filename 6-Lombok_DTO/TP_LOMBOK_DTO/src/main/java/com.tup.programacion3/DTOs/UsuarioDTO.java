package com.tup.programacion3.DTOs;

import com.tup.programacion3.entities.Pedido;
import java.time.LocalDateTime;
import java.util.Set;

public record UsuarioDTO(
        //sin contraseña ni rol según lo que pedia el enunciado
        Long id,
        LocalDateTime createdAt,
        String nombre,
        String apellido,
        String mail,
        String celular,
        Set<Pedido> pedidos
) {}