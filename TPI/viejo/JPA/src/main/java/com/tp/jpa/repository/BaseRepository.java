package com.tp.jpa.repository;

import com.tp.jpa.model.Base;
import com.tp.jpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository <T extends Base>{
    private Class<T> clase;

    //para que sea visible a las clases hijas
    protected final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public BaseRepository(Class<T> clase){
        this.clase=clase;
    }

    public T guardar(T objeto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T resultado = em.merge(objeto);
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            //si algo sale mal se hace el rollback
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {

            //mismaa clase que recibe en el constructor
            T entidad = em.find(clase, id);

            //devuelve optional si existe o no
            return Optional.ofNullable(entidad);
        } finally {
            em.close();
        }
    }

    public List<T> listarActivos() {

        EntityManager em = emf.createEntityManager();
        try {
            // Usamos entityClass.getSimpleName() para obtener el nombre de la entidad dinámicamente
            String jpql = "SELECT e FROM " + clase.getSimpleName() + " e WHERE e.eliminado = false";

            // 3. Crear la consulta tipada y retornar la lista de resultados [1, 2]
            return em.createQuery(jpql, clase).getResultList();
        } finally {

            em.close();
        }
    }

    public boolean eliminarLogico(Long id) {

        EntityManager em = emf.createEntityManager();
        try {
            //inicio de la transaccionn
            em.getTransaction().begin();

            //por id
            T entidad = em.find(clase, id);

            if (entidad != null) {
                //lo marca como eliminado
                entidad.setEliminado(true);

                // se guarda el cambio
                em.merge(entidad);

                // confirmacion de la transaccion
                em.getTransaction().commit();
                return true; // si todo salio bien
            }

            return false; // El registro no existe [2]
        } catch (Exception e) {
            // Manejo de rollback en caso de error para proteger la integridad
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            // 7. Cerrar obligatoriamente el EntityManager en un bloque finally [3]
            em.close();
        }
    }
}
