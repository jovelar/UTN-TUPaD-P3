package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuario. Además del CRUD heredado implementa la búsqueda de
 * un usuario activo por su mail y la consulta de los pedidos de un usuario.
 *
 * Nota de diseño: como la relación es unidireccional y Usuario es el dueño de
 * la colección Set<Pedido>, la navegación se hace desde Usuario hacia sus
 * pedidos (p. ej. JPQL con JOIN sobre u.pedidos).
 */
public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    /**
     * Retorna el usuario activo con el mail indicado.
     */
    public Optional<Usuario> buscarPorMail(String mail) {
        EntityManager em=emf.createEntityManager();

        //busca todas las columnas relacionadas con el usuario cuyo mail coincida con el mail
        //buscado y que no haya sido dado de baja (logica)
        String jpql="SELECT u FROM Usuario u WHERE u.mail=:mail AND u.eliminado =false";
        try{
            TypedQuery <Usuario> q=em.createQuery(jpql,Usuario.class);
            q.setParameter("mail",mail);
            List<Usuario> res=q.getResultList();
            return res.isEmpty() ? Optional.empty() :Optional.of(res.get(0));
        }finally {
            em.close();
        }
        //throw new UnsupportedOperationException("Método no implementado aún");
    }

    /**
     * Retorna los pedidos activos del usuario indicado.
     */
    public List<Pedido> buscarPedidosPorUsuario(Long idUsuario) {
        EntityManager em=emf.createEntityManager();

        //hace un inner join entre la tabla Usuarios y la tabla pedidos y selecciona aquellos en donde la columna idUsuario coincida con el isUsuario buscado y no
        //haya sido de baja (logica)
        String jpql="SELECT p FROM Usuario u JOIN u.pedidos p WHERE u.id=:uid and p.eliminado =false";
        try{
            List<Pedido> q=em.createQuery(jpql,Pedido.class).setParameter("uid",idUsuario).getResultList();
            return q;
        }finally {
            em.close();;
        }
        //throw new UnsupportedOperationException("Método no implementado aún");
    }
}
