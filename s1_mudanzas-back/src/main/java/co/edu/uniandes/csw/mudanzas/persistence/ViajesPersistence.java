/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.persistence.PersistenceContext;
/**
 *
 * @author je.osorio
 */
@Stateless
public class ViajesPersistence {
    
    private static final Logger LOGGER=Logger.getLogger(ViajesPersistence.class.getName());
    
    @PersistenceContext(unitName="mudazasPU")
    protected EntityManager em;
    public ViajesEntity create(ViajesEntity viajesEntity){
        em.persist(viajesEntity);
        return viajesEntity;
    }
}
