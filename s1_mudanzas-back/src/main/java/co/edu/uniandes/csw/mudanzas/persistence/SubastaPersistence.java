/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import java.util.LinkedList;
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
     
     
     /**
     * @param subastaEntity  de la subasta a persistir
     * @return subasta perisistida
     */
     public SubastaEntity create( SubastaEntity subastaEntity)
     {
         
         em.persist(subastaEntity);
         return subastaEntity;
     }
     
     /**
     * @param subastaId  de la subasta
     * @return subasta encontrada
     */
     public SubastaEntity find(Long subastaId)
    {
        return em.find(SubastaEntity.class, subastaId);
    }
     
     /**
     * @return todas las subastas 
     */
     public List<SubastaEntity> findAll(){
         TypedQuery<SubastaEntity> query = em.createQuery("select u from SubastaEntity u", SubastaEntity.class);
         return query.getResultList();
         
     }
     
     /**
     *
     * @param plogin del duenio de la subasta
     * @return subasta encontrada
     */
     public List<SubastaEntity> findByUser( String plogin)
     {
         List<SubastaEntity> retornable =null;
         TypedQuery<SubastaEntity> query = em.createQuery("select e from SubastaEntity e where e.usuario.login = :login", SubastaEntity.class);
         query = query.setParameter("login", plogin);
        retornable = query.getResultList();
        if(retornable==null)
            retornable = new LinkedList<SubastaEntity>();
        
         return retornable;
     }
     
     /**
     *
     * @param pLogin del duenio de la subasta
     * @param pSubId  de la subasta
     * @return subasta encontrada
     */
     public SubastaEntity findOneByUser(String pLogin, Long pSubId)
     {
         SubastaEntity retornable = null;
         if(!findByUser(pLogin).isEmpty()&&findByUser(pLogin).get(0) !=null)
         {
             for (SubastaEntity subastaEntity : findByUser(pLogin)) {
                 if(subastaEntity.getId() == pSubId)
                     return subastaEntity;
             }
         }
         return retornable;
     }
     
      /**
     *
     * @param plogin de un proveedor perteneciente de la subasta
     * @return subasta encontrada
     */
       public List<SubastaEntity> findByProveedor( String plogin)
     {
         List<SubastaEntity> retornable =null;
         TypedQuery<SubastaEntity> query = em.createQuery("select e from SubastaEntity e where e.proveedor.login = :login", SubastaEntity.class);
         query = query.setParameter("login", plogin);
        retornable = query.getResultList();
        if(retornable==null)
            retornable = new LinkedList<SubastaEntity>();
        
         return retornable;
     }
     
     /**
     *
     * @param pLogin de un proveedor perteneciente de la subasta
     * @param pSubId  de la subasta
     * @return subasta encontrada
     */
     public SubastaEntity findOneByProveedor(String pLogin, Long pSubId)
     {
         SubastaEntity retornable = null;
         if(!findByProveedor(pLogin).isEmpty()&&findByProveedor(pLogin).get(0) !=null)
         {
             for (SubastaEntity subastaEntity : findByProveedor(pLogin)) {
                 if(subastaEntity.getId() == pSubId)
                     return subastaEntity;
             }
         }
         return retornable;
     }
     
     
     
     /**
     * Elimina una subasta
     * @param subastaId   de la subasta a borrar
     */
     public void delete(Long subastaId)
     {
         em.remove(find(subastaId));
     }
     
     
     public SubastaEntity update(SubastaEntity cambio)
     {
         return em.merge(cambio);
     }
     
}
