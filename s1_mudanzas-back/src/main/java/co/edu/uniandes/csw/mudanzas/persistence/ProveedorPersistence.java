/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import java.util.List;
import java.util.logging.Level;
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
public class ProveedorPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ProveedorPersistence.class.getName());
    
    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;
    
    
    public ProveedorEntity create(ProveedorEntity proveedorEntity) {
        LOGGER.log(Level.INFO, "Creando un proveedor nuevo");
        em.persist(proveedorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un proveedor nuevo");
        return proveedorEntity;
    }
    
    /**
     * Encuentra a un proveedor en la BD con su id
     *
     * @param proveedorId el id del proveedor que estamos buscando
     * @return la entidad del provedor encontrado
     */
    public ProveedorEntity find(Long proveedorId) {
        return em.find(ProveedorEntity.class, proveedorId);
    }
    
     /**
     * Encuentra todos los proveedores que existen en la BD
     *
     * @return una lista con todos los proveedores.
     */
    public List<ProveedorEntity> findAll() {
        TypedQuery<ProveedorEntity> query = em.createQuery("select u from ProveedorEntity u", ProveedorEntity.class);
        return query.getResultList();
    }
    
    /**
     * Borra de la base de datos al proveedor con el:
     *
     * @param proveedorId
     */
    public void delete(Long proveedorId) {
        em.remove(find(proveedorId));
    }
    
    /**
     * Actualiza a un proveedor de la base de datos por el que entra por parametro.
     *
     * @param nuevo
     * @return el proveedor ya cambiado
     */
    public ProveedorEntity update(ProveedorEntity nuevo) {
        return em.merge(nuevo);
    }
    
    /**
     * Busca a un proveedor por su login
     *
     * @param login el nombre de proveedor que se desea buscar
     * @return el proveedor completo con ese login
     */
    public ProveedorEntity findProveedorPorLogin(String login) {
        TypedQuery query = em.createQuery("Select e from ProveedorEntity e where e.login = :login", ProveedorEntity.class);
        query = query.setParameter("login", login);
        List<ProveedorEntity> duenio = query.getResultList();
        ProveedorEntity resultado;
        if (duenio == null) {
            resultado = null;
        }else if(duenio.isEmpty()){
            resultado = null;
        }
        else {
            resultado = duenio.get(0);
        }
        return resultado;
    }
    
    /**
     * Busca a un proveedor por su nombre
     *
     * @param nombre el nombre de proveedor que se desea buscar
     * @return el proveedor completo con ese nombre
     */
    public ProveedorEntity findProveedorPorNombre(String nombre) {
        TypedQuery query = em.createQuery("Select e from ProveedorEntity e where e.nombre = :nombre", ProveedorEntity.class);
        query = query.setParameter("nombre", nombre);
        List<ProveedorEntity> duenio = query.getResultList();
        ProveedorEntity resultado;
        if (duenio == null) {
            resultado = null;
        }else if(duenio.isEmpty()){
            resultado = null;
        }
        else {
            resultado = duenio.get(0);
        }
        return resultado;
    }

}
