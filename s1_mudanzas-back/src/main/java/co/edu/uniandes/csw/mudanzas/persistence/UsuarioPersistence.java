/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para la Usuario. Se conecta a través del
 * Entity Manager de javax.persistance con la base de datos SQL.
 *
 * @author Luis Miguel
 */
@Stateless
public class UsuarioPersistence {

    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());

    /**
     * El manejador de la base de datos.
     */
    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     * Crea un usuario en la BD
     *
     * @param usuarioEntity la instancia del usuario que se quiere crear
     * @return el usuario creado con algunas variaciones adaptadas a la BD como
     * por ejemplo el id.
     */
    public UsuarioEntity create(UsuarioEntity usuarioEntity) {
        em.persist(usuarioEntity);
        return usuarioEntity;
    }

    /**
     * Encuentra a un usuario en la BD con su id
     *
     * @param usuarioId el id del usuario que estamos buscando
     * @return la entidad del usuario encontrado
     */
    public UsuarioEntity find(Long usuarioId) {
        return em.find(UsuarioEntity.class, usuarioId);
    }

    /**
     * Encuentra todos los usuarios que existen en la BD
     *
     * @return una lista con todos los usuarios.
     */
    public List<UsuarioEntity> findAll() {
        TypedQuery<UsuarioEntity> query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        return query.getResultList();
    }
    
    public void delete(Long usuarioId)
    {
        em.remove(find(usuarioId));
    }

}
