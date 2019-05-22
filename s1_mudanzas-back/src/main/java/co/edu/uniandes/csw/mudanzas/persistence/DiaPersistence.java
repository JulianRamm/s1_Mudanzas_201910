/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
     
    @Inject
    VehiculoPersistence vPer;
    
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
   
   public DiaEntity findByPlacaVehiculo(String placa)
   {
       TypedQuery<DiaEntity> query = em.createQuery("select e from DiaEntity e where e.vehiculo = :vehiculo", DiaEntity.class);
       query = query.setParameter("vehiculo", vPer.findByPlaca(placa));
       
       List<DiaEntity> sameDia = query.getResultList();
       
       if(sameDia == null||sameDia.isEmpty())
       {
           sameDia = null;
       }
       return sameDia.get(0);
   }
   
   /**
     * Elimina un vehiculo con
     *
     * @param vehiculoId de la base de datos.
     */
    public void delete(Long DiaId) {
        em.remove(find(DiaId));
    }

    /**
     * Actualiza una vehiculo con la bd
     *
     * @param cambiada
     * @return la vehiculo actualizada
     */
    public DiaEntity update(DiaEntity cambiada) {
        return em.merge(cambiada);
    }
    
}
