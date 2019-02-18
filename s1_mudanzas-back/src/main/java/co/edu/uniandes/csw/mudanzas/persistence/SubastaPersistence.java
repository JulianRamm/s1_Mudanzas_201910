/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
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
public class SubastaPersistence {
    
    
     @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;
     
     public SubastaEntity create ( SubastaEntity subastaEntity)
     {
         
         em.persist(subastaEntity);
         return subastaEntity;
     }
     
     public SubastaEntity find(Long subastaId)
    {
        return em.find(SubastaEntity.class, subastaId);
    }
     
     public List<SubastaEntity> findAll(){
         TypedQuery<SubastaEntity> query = em.createQuery("select u from SubastaEntity u", SubastaEntity.class);
         return query.getResultList();
         
     }
}
