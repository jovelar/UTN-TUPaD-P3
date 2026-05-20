package com.tup.programacion3.entities;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(of = {"id","createdAt","eliminado"})
@EqualsAndHashCode(of={"id"})
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

}
