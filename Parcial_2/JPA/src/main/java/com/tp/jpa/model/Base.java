package com.tp.jpa.model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
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



@MappedSuperclass
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

}
