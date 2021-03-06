/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author je.osorio
 */
@Stateless
public class CargaPersistence {

    private static final Logger LOGGER = Logger.getLogger(CargaPersistence.class.getName());

    /**
     * El manejador de la base de datos.
     */
    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;
    private UsuarioPersistence usuPer;

    /**
     * Crea un viaje en la BD
     *
     * @param carga - la instancia de la carga que se quiere crear
     * @return la carga creada con algunas variaciones adaptadas a la BD como
     * por ejemplo el id.
     */
    public CargaEntity create(CargaEntity carga) {
        em.persist(carga);
        return carga;
    }

    /**
     * Encuentra a una carga en la BD con su id
     *
     * @param cargaId el id del viaje que estamos buscando
     * @return la entidad de la carga encontrada
     */
    public CargaEntity find(Long cargaId) {
        return em.find(CargaEntity.class, cargaId);
    }

    /**
     * Encuentra todos los Carga que existen en la BD
     *
     * @return una lista con todas lss Carga .
     */
    public List<CargaEntity> findAll() {
        TypedQuery<CargaEntity> query;
        query = em.createQuery("select u from CargaEntity u", CargaEntity.class);
        return query.getResultList();
    }

    /**
     * eleimina una carga dada su id
     *
     * @param cargaId
     */
    public void delete(Long cargaId) {
        em.remove(find(cargaId));
    }

    /**
     * método que actualiza una carga dado el objeto con los cambios nuevo
     *
     * @param cargaEntity
     * @return
     */
    public CargaEntity update(CargaEntity cargaEntity) {
        return em.merge(cargaEntity);
    }

    /**
     * Busca una carga por el login del titular de la cuenta.
     *
     * @param login del usuario que queremos buscar
     * @param idCarga
     * @return la carga que pertenece al usuario que entra por parametro.
     */
    public CargaEntity findCargaPorLoginPropietario(String login, Long idCarga) {
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.login = :login", UsuarioEntity.class);
        query = query.setParameter("login", login);
        List<UsuarioEntity> duenio = query.getResultList();
        CargaEntity resultado = null;
        if (duenio == null) {
            resultado = null;
        } else if (duenio.isEmpty()) {
            resultado = null;
        } else if (duenio.get(0).getCargas() == null) {
            resultado = null;
        } else {
            for (CargaEntity t : duenio.get(0).getCargas()) {
                if (t.getId() == idCarga) {
                    resultado = t;
                }
            }
        }
        return resultado;
    }

    public List<CargaEntity> getCargasUsuario(String login) {
        TypedQuery query = em.createQuery("Select e From UsuarioEntity e where e.login = :login", UsuarioEntity.class);
        query = query.setParameter("login", login);
        List<UsuarioEntity> duenio = query.getResultList();
        List<CargaEntity> resultado = new ArrayList<CargaEntity>();
        if (duenio == null) {
            resultado = null;
        } else if (duenio.isEmpty()) {
            resultado = null;
        } else if (duenio.get(0).getCargas() == null) {
            resultado = null;
        } else {
            for (CargaEntity t : duenio.get(0).getCargas()) {
               resultado.add(t);
                    
                }
            }
        return resultado;
    }
}
