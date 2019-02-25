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
   /**
    * Método que busca la entidad del conductor que tiene un nombre ingresado por parametro.
    * @param nombre , es el nombre que se va a usar para buscar
    * @return la entidad del conductor que tiene el nombre que se ingresa por parametro
    */
   public ConductorEntity findByName(String nombre)
   {
       //rta: respuesta que se va a devolver
       ConductorEntity rta;
       //Compara el nombre de las entidades de la base de datos con el parámetro
       TypedQuery<ConductorEntity> query = em.createQuery("select e from ConductorEntity e where e.nombre = :name", ConductorEntity.class);
       // asignación del valor del parámetro
       query = query.setParameter("name", nombre);
       //Comparaciones del resultado de la comparacion y asignacion del valor de rta
       List<ConductorEntity> sameName = query.getResultList();
       if(sameName == null)
       {
           rta=null;
       }
       else if(sameName.isEmpty())
       {
           rta = null;
       }
       else
       {
           rta = sameName.get(0);
       }
               
       return rta;
   }
   
   public List<ConductorEntity> findAll()
   {
       TypedQuery<ConductorEntity> query = em.createQuery("select u from ConductorEntity u", ConductorEntity.class);
       return query.getResultList();
   }
    
}

