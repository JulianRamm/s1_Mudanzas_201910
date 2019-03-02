/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Machado
 */
@Stateless
public class ConductorPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ConductorPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
     protected EntityManager em;  
    
    
    
   /**
     * Crea un Conductor en la BD
     *
     * @param conductor la instancia del conductor que se quiere crear
     * @return el conductor creado con algunas variaciones adaptadas a la BD como
     * por ejemplo el id.
     */ 
   public ConductorEntity create(ConductorEntity conductorEntity)
   {
       em.persist(conductorEntity);
       return conductorEntity;
   }
   
   
   /**
     * Encuentra a un conductor en la BD con su id
     *
     * @param conductorId el id del conductor que estamos buscando
     * @return la entidad del conductor encontrado
     */
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
   
   /**
     * Actualiza un conductor  en la bd
     *
     * @param nuevo
     * @return la tarjeta de credito actualizada
     */
    public ConductorEntity update(ConductorEntity nuevo) {
        return em.merge(nuevo);
    }
   
    /**
     * Encuentra todos los conductores que existen en la BD
     *
     * @return una lista con todos los conductores.
     */
   public List<ConductorEntity> findAll()
   {
       TypedQuery<ConductorEntity> query = em.createQuery("select u from ConductorEntity u", ConductorEntity.class);
       return query.getResultList();
   }
   
   /**
     * Elimina una tarjeta con
     *
     * @param conductorId de la base de datos.
     */
    public void delete(Long conductorId) {
        ConductorEntity entidad = em.find(ConductorEntity.class, conductorId);
        em.remove(entidad);
    }
    
    /**
     * Busca una tarjeta de credito por el login del titular de la cuenta.
     *
     * @param loginProveedor el nombre del titular de la cuenta.
     * @param idConductor el id del conductor que estamos buscando
     * @return la tarjeta de credito que pertenece al usuario que entra por
     * parametro.
     */
    public ConductorEntity findConductorPorLoginProveedor(String loginProveedor, Long idConductor) {
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.login = :login", ProveedorEntity.class);
        query = query.setParameter("login", loginProveedor);
        List<ProveedorEntity> duenio = query.getResultList();
        ConductorEntity resultado = null;
        if (duenio == null) {
            resultado = null;
        } else if (duenio.isEmpty()) {
            resultado = null;
        } else if (duenio.get(0).getConductores() == null) {
            resultado = null;
        } else {
            for (ConductorEntity conductor : duenio.get(0).getConductores()) {
                if (conductor.getId() == idConductor) {
                    resultado = conductor;
                }
            }
        }
        return resultado;
    }
    
}

