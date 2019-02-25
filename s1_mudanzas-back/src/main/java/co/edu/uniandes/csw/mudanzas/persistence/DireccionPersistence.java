/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author je.osorio
 */
@Stateless
public class DireccionPersistence {

    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     * Crea un viaje en la BD
     *
     * @param Direccion - la instancia de la Direccion que se quiere crear
     * @return la Direccion creada con algunas variaciones adaptadas a la BD
     * como por ejemplo el id.
     */
    public DireccionEntity create(DireccionEntity Direccion) {
        em.persist(Direccion);
        return Direccion;
    }

    /**
     * Encuentra a una Direccion en la BD con su id
     *
     * @param DireccionId el id del viaje que estamos buscando
     * @return la entidad de la Direccion encontrada
     */
    public DireccionEntity find(Long DireccionId) {
        return em.find(DireccionEntity.class, DireccionId);
    }

    /**
     * Encuentra todos los Direccion que existen en la BD
     *
     * @return una lista con todas lss Direccion .
     */
    public List<DireccionEntity> findAll() {
        TypedQuery<DireccionEntity> query;
        query = em.createQuery("Select u From DireccionEntity u", DireccionEntity.class);
        return query.getResultList();
    }

    /**
     * eleimina una Direccion dada su id
     *
     * @param DireccionId
     */
    public void delete(Long DireccionId) {
        em.remove(find(DireccionId));
    }

    /**
     * método que actualiza una Direccion dado el objeto con los cambios nuevos
     *
     * @param DireccionEntity
     * @return
     */
    public DireccionEntity update(DireccionEntity DireccionEntity) {
        return em.merge(DireccionEntity);
    }
}
