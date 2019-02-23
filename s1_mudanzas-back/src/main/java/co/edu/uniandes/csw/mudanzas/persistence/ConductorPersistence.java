/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class ConductorPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ConductorPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
     protected EntityManager em;  
    
    
   public ConductorEntity create(ConductorEntity conductorEntity)
   {
       em.persist(conductorEntity);
       return conductorEntity;
   }
   
   public ConductorEntity find(Long conductorId)
   {
       return em.find(ConductorEntity.class, conductorId);
   }
   
   public List<ConductorEntity> findAll()
   {
       TypedQuery<ConductorEntity> query = em.createQuery("select u from ConductorEntity u", ConductorEntity.class);
       return query.getResultList();
   }
    
}

