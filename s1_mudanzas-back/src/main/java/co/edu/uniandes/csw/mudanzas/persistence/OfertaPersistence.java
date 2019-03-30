/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
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
public class OfertaPersistence {

    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     *
     * @param ofertaEntity para crear en la BD
     * @return objeto persistido
     */
    public OfertaEntity create(OfertaEntity ofertaEntity) {
        em.persist(ofertaEntity);
        return ofertaEntity;
    }
/**
     *
     * @param ofertaId  de la oferta
     * @return oferta encontrada
     */
    public OfertaEntity find(Long ofertaId) {
        return em.find(OfertaEntity.class, ofertaId);
    }

    /**
     *
     * @return todas las ofertas encontrada
     */
   
    public List<OfertaEntity> findAll() {
        TypedQuery<OfertaEntity> query = em.createQuery("select u from OfertaEntity u", OfertaEntity.class);
        return query.getResultList();

    }
    
     /**
     *
     * @param LoginProveedor de un proveedor que hace una oferta
     * @return ofertas encontrada
     */
    public List<OfertaEntity> findByProveedor(String LoginProveedor)
    {
        List<OfertaEntity> retornable = null;
        TypedQuery<OfertaEntity> query = em.createQuery("select e from OfertaEntity e where e.proveedor.login = :provLogin", OfertaEntity.class);
        query = query.setParameter("provLogin", LoginProveedor);
        retornable = query.getResultList();
        if(retornable == null)
            retornable = new LinkedList<OfertaEntity>();

        return retornable;
        
    }
    
     /**
     *
     * @param loginProv de un proveedor que hace una oferta
     * @param pSubId  de la oferta
     * @return oferta encontrada
     */
    public OfertaEntity findOneByProveedor(String loginProv, Long pId)
    {
        OfertaEntity retornable = null;
        if(!findByProveedor(loginProv).isEmpty() && findByProveedor(loginProv).get(0)!=null)
        for (OfertaEntity ofertaEntity : findByProveedor(loginProv))
        {
             if(ofertaEntity.getId()== pId)
                 return ofertaEntity;
        }
        return retornable;
    }
    
    
    
     /**
     *
     * @param subLogin de un proveedor que hace una oferta
     * @return ofertas encontrada
     */
    public List<OfertaEntity> findBySubasta(Long subLogin)
    {
        List<OfertaEntity> retornable = null;
        TypedQuery<OfertaEntity> query = em.createQuery("select e from OfertaEntity e where e.subasta.id = :subLogin", OfertaEntity.class);
        query = query.setParameter("subLogin", subLogin);
        retornable = query.getResultList();
        if(retornable == null)
            retornable = new LinkedList<OfertaEntity>();

        return retornable;
        
    }
    
     /**
     *
     * @param loginSubas de una subasta a la que se le hace una oferta
     * @param pSubId  de la oferta
     * @return oferta encontrada
     */
    public OfertaEntity findOneBySubasta(Long loginSubas, Long pId)
    {
        OfertaEntity retornable = null;
        if(!findBySubasta(loginSubas).isEmpty() && findBySubasta(loginSubas).get(0)!=null)
        for (OfertaEntity ofertaEntity : findBySubasta(loginSubas))
        {
             if(ofertaEntity.getId()== pId)
                 return ofertaEntity;
        }
        return retornable;
    }
    
    
    /*
     * Se elimina una oferta
     * @param ofertaId  de la oferta a eliminar
     * @return oferta encontrada
     */
    public void delete(Long ofertaId) {
        em.remove(find(ofertaId));
    }
    
    public OfertaEntity update(OfertaEntity oferCambiar)
    {
       return em.merge(oferCambiar);
    }

}
