package com.tp.jpa.repository;

import com.tp.jpa.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductRepository extends BaseRepository<Producto> {

    public ProductRepository() {
        // Obligatorio: llama al constructor padre pasando la clase Producto [3]
        super(Producto.class);
    }

    /**
     * Busca productos activos pertenecientes a una categoría específica.
     * La consulta utiliza JPQL con un parámetro nombrado ':categoriaId' para filtrar
     * por el ID de la categoría y asegura que el producto no esté marcado como eliminado [4, 5].
     */
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        // Cada método debe abrir su propio EntityManager [2]
        EntityManager em = emf.createEntityManager();
        try {
            // Consulta JPQL filtrando por categoría y estado activo [5]
            String jpql = "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false";

            // Uso de TypedQuery para evitar casteos manuales [3, 6]
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);

            // Seteo del parámetro nombrado [5, 6]
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();
        } finally {
            // Cierre obligatorio en bloque finally para asegurar la liberación de recursos [2, 7]
            em.close();
        }
    }
}