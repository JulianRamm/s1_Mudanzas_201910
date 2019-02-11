/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Miguel
 */
@Stateless
public class UsuarioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioPersistence.class.getName());    

    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    public UsuarioEntity create(UsuarioEntity usuarioEntity) {
        
        em.persist(usuarioEntity);
        return usuarioEntity;
        
    }
    
}
