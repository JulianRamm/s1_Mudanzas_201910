/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author je.osorio
 */
@Stateless
public class ViajesPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ViajesPersistence.class.getName());

    /**
     * El manejador de la base de datos.
     */
    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     * Crea un viaje en la BD
     *
     * @param viaje - la instancia del viaje que se quiere crear
     * @return el viaje creado con algunas variaciones adaptadas a la BD como
     * por ejemplo el id.
     */
    public ViajesEntity create(ViajesEntity viaje) {
        em.persist(viaje);
        return viaje;
    }

    /**
     * Encuentra a un viaje en la BD con su id
     *
     * @param viajeId el id del viaje que estamos buscando
     * @return la entidad del viaje encontrado
     */
    public ViajesEntity find(Long viajeId) {
        return em.find(ViajesEntity.class, viajeId);
    }

    /**
     * Encuentra todos los viajes que existen en la BD
     *
     * @return una lista con todos los viajes.
     */
    public List<ViajesEntity> findAll() {
        TypedQuery<ViajesEntity> query;
        query = em.createQuery("select u from ViajesEntity u" , ViajesEntity.class);
        return query.getResultList();
    }
}
