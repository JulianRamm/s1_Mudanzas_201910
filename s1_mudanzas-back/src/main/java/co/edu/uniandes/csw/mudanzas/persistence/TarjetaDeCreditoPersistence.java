/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para la Tarjeta de Credito. Se conecta a
 * través del Entity Manager de javax.persistance con la base de datos SQL.
 *
 * @author Luis Miguel
 */
@Stateless
public class TarjetaDeCreditoPersistence {

    private static final Logger LOGGER = Logger.getLogger(TarjetaDeCreditoPersistence.class.getName());

    /**
     * El manejador de la base de datos.
     */
    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     * Crea una tarjeta en la BD
     *
     * @param tarjeta la instancia de la tarjeta que se quiere crear
     * @return la tarjeta creada con algunas variaciones adaptadas a la BD como
     * por ejemplo el id.
     */
    public TarjetaDeCreditoEntity create(TarjetaDeCreditoEntity tarjeta) {
        em.persist(tarjeta);
        return tarjeta;
    }

    /**
     * Encuentra a una tarjeta en la BD con su id
     *
     * @param tarjetaId el id de la tarjeta que estamos buscando
     * @return la entidad de la tarjeta encontrado
     */
    public TarjetaDeCreditoEntity find(Long tarjetaId) {
        return em.find(TarjetaDeCreditoEntity.class, tarjetaId);
    }

    /**
     * Encuentra todas las tarjetas que existen en la BD
     *
     * @return una lista con todos los usuarios.
     */
    public List<TarjetaDeCreditoEntity> findAll() {
        TypedQuery<TarjetaDeCreditoEntity> query = em.createQuery("select u from TarjetaDeCreditoEntity u", TarjetaDeCreditoEntity.class);
        return query.getResultList();
    }
    
    /**
     * Elimina una tarjeta con
     * @param tarjetaId de la base de datos.
     */
    public void delete(Long tarjetaId)
    {
        TarjetaDeCreditoEntity entidad = em.find(TarjetaDeCreditoEntity.class, tarjetaId);
        em.remove(entidad);
    }

    
    /**
     * Actualiza una tarjeta de credito con la bd
     * @param cambiada
     * @return la tarjeta de credito actualizada
     */
    public TarjetaDeCreditoEntity update(TarjetaDeCreditoEntity cambiada) {
        return em.merge(cambiada);
    }

    /**
     * Busca una tarjeta de credito por el nombre del titular de la cuenta.
     * @param titularCuenta el nombre del titular de la cuenta.
     * @return  la tarjeta de credito que pertenece al usuario que entra por parametro.
     */
    public TarjetaDeCreditoEntity buscarTarjetaPorPropietario(String titularCuenta) {
       TypedQuery query = em.createQuery("Select e From TarjetaDeCreditoEntity e where e.titularCuenta = :titularCuenta", TarjetaDeCreditoEntity.class);
       query = query.setParameter("titularCuenta", titularCuenta);
       List<TarjetaDeCreditoEntity> duenio = query.getResultList();
       TarjetaDeCreditoEntity resultado;
       if(duenio == null)
           resultado = null;
       else if(duenio.isEmpty())
           resultado = null;
       else
           resultado = duenio.get(0);
       return resultado;
    }
    
}
