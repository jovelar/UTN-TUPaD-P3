package com.tp.jpa.repository;

import com.tp.jpa.entities.Categoria;

public class CategoriaRepository extends BaseRepository{
    public CategoriaRepository(Class clase) {
        super(Categoria.class);
    }

}
