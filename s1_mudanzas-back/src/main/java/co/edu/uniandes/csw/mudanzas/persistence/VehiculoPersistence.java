/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
public class VehiculoPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(VehiculoPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
     protected EntityManager em;  
    
       public VehiculoEntity create(VehiculoEntity vehiculoEntity)
   {
       em.persist(vehiculoEntity);
       return vehiculoEntity;
   }
   
   public VehiculoEntity find(Long vehiculoId)
   {
       return em.find(VehiculoEntity.class, vehiculoId);
   }
   
   public List<VehiculoEntity> findAll()
   {
       TypedQuery<VehiculoEntity> query = em.createQuery("select u from VehiculoEntity u", VehiculoEntity.class);
       return query.getResultList();
   }
}
