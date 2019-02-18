/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Andres Gonzalez
 */
@Stateless
public class OfertaPersistence {

    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     *
     * @param ofertaEntity para crear en la BD
     * @return objeto persistido
     */
    public OfertaEntity create(OfertaEntity ofertaEntity) {
        em.persist(ofertaEntity);
        return ofertaEntity;
    }
    
    public OfertaEntity find(Long ofertaId)
    {
        return em.find(OfertaEntity.class, ofertaId);
    }
    
      public List<OfertaEntity> findAll(){
         TypedQuery<OfertaEntity> query = em.createQuery("select u from OfertaEntity u", OfertaEntity.class);
         return query.getResultList();
         
     }
    
    
}
