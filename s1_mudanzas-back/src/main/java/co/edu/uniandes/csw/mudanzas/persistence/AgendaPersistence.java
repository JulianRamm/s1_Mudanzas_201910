/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.AgendaEntity;
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
public class AgendaPersistence 
{
     private static final Logger LOGGER = Logger.getLogger(AgendaPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
     protected EntityManager em;  
    
    
   public AgendaEntity create(AgendaEntity agendaEntity)
   {
       em.persist(agendaEntity);
       return agendaEntity;
   }
   
   public AgendaEntity find(Long agendaId)
   {
       return em.find(AgendaEntity.class, agendaId);
   }
   
   public List<AgendaEntity> findAll()
   {
       TypedQuery<AgendaEntity> query = em.createQuery("select u from AgendaEntity u", AgendaEntity.class);
       return query.getResultList();
   }
    
}
