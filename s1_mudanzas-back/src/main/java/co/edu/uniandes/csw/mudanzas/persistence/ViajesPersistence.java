/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.inject.Inject;
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

    @Inject
    CargaPersistence cargaP;
    @Inject
    ConductorPersistence conP;
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
        query = em.createQuery("select u from ViajesEntity u", ViajesEntity.class);
        return query.getResultList();
    }

    /**
     * eleimina un viaje dado su id
     *
     * @param viajeId
     */
    public void delete(Long viajeId) {
        em.remove(find(viajeId));
    }

    /**
     * método que actualiza un viaje dado el objeto con los cambios nuevos
     *
     * @param viajesEntity
     * @return
     */
    public ViajesEntity update(ViajesEntity viajesEntity) {
        return em.merge(viajesEntity);
    }

    /**
     * devuelve todas las cargas con id especificado
     *
     * @param id
     * @return
     */
    public List<CargaEntity> getCargasDadoUnId(Long id) {
        ViajesEntity viaje = find(id);
        LinkedList<CargaEntity> cargas = new LinkedList<>();
        if (viaje == null || viaje.getCargas().isEmpty() ) {
            cargas = null;
        } else {
            for (CargaEntity cargae : viaje.getCargas()) {
                cargas.add(cargae);
            }
        }
        return cargas;
    }
    public ViajesEntity getVajesDeConductor(Long idCon) {
        TypedQuery query = em.createQuery("Select e From ViajesEntity e where e.conductor = :conductor", ViajesEntity.class);
        query = query.setParameter("conductor", conP.find(idCon));
        List<ViajesEntity> viaje = query.getResultList();
        if (viaje == null || viaje.isEmpty() ) {
            viaje = null;
        } 
        return viaje.get(0);
    }
    public void deleteCargasDadoUnId(Long id) {
        List<CargaEntity> cargas = getCargasDadoUnId(id);
        if (cargas != null) {
            for (CargaEntity carga : cargas) {
                cargaP.delete(carga.getId());
            }
        }
    }

}
