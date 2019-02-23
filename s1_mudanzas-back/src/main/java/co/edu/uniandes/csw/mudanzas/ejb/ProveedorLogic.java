/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Machado
 */
@Stateless
public class ProveedorLogic {
    
    
     /**
     * Variable para acceder a la persistencia del proveedor.
     */
    @Inject
    private ProveedorPersistence pP;
    
    public ProveedorEntity createProveedor(ProveedorEntity proveedor) throws BusinessLogicException{
        
        if (pP.findProveedorPorLogin(proveedor.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un proveedor con el login \"" + proveedor.getLogin() + "\"");
        }
        
        return pP.create(proveedor);
    }
    
    
}
