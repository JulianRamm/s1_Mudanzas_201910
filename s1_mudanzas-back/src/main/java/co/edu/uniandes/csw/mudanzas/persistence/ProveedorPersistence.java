/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        LOGGER.log(Level.INFO, "Creando una editorial nueva");
        em.persist(proveedorEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una editorial nueva");
        return proveedorEntity;
    }
    
}
