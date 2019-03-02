/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
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
   public VehiculoEntity findByPlaca(String placa)
   {
       VehiculoEntity rta;
       TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.placa = :pPlaca", VehiculoEntity.class);
       query = query.setParameter("pPlaca", placa);
       
       List<VehiculoEntity> samePlaca = query.getResultList();
       
       if(samePlaca == null)
       {
           rta = null;
       }
       else if(samePlaca.isEmpty())
       {
           rta = null;
       }
       else
       {
           rta = samePlaca.get(0);
       }
       return rta;
   }
   public VehiculoEntity findByUbicacionActual(DireccionEntity uAct)
   {
       VehiculoEntity rta;
       TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.ubicacionActual.idPar = :pUbicacionActual", VehiculoEntity.class);
       query = query.setParameter("pUbicacionActual", uAct.getIdPar());
       
       List<VehiculoEntity> sameUAct = query.getResultList();
       
       if(sameUAct== null)
       {
           rta = null;
       }
       else if(sameUAct.isEmpty())
       {
           rta = null;
       }
       else
       {
           rta = sameUAct.get(0);
       }
       return rta;
   }
   
   public VehiculoEntity findByDia(DiaEntity Agenda)
   {
       VehiculoEntity rta;
       TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.agenda.id = :pAgenda", VehiculoEntity.class);
       query = query.setParameter("pAgenda", Agenda.getId());
       
       List<VehiculoEntity> sameAgenda = query.getResultList();
       
       if(sameAgenda== null)
       {
           rta = null;
       }
       else if(sameAgenda.isEmpty())
       {
           rta = null;
       }
       else
       {
           rta = sameAgenda.get(0);
       }
       return rta;
   }
   
    
   public List<VehiculoEntity> findAll()
   {
       TypedQuery<VehiculoEntity> query = em.createQuery("select u from VehiculoEntity u", VehiculoEntity.class);
       return query.getResultList();
   }
}
