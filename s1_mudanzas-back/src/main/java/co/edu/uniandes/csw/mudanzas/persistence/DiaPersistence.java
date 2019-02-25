/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
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
public class DiaPersistence 
{
     private static final Logger LOGGER = Logger.getLogger(DiaPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
     protected EntityManager em;  
    
    
   public DiaEntity create(DiaEntity agendaEntity)
   {
       em.persist(agendaEntity);
       return agendaEntity;
   }
   
   public DiaEntity find(Long agendaId)
   {
       return em.find(DiaEntity.class, agendaId);
   }
   
   public List<DiaEntity> findAll()
   {
       TypedQuery<DiaEntity> query = em.createQuery("select u from DiaEntity u", DiaEntity.class);
       return query.getResultList();
   }
    
}
