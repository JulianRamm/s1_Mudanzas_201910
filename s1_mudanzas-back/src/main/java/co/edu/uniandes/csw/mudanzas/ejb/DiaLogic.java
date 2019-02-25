/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */
public class DiaLogic 
{
     @Inject
   private DiaPersistence per;
    
    public DiaEntity crearDia(DiaEntity entity) throws BusinessLogicException
    {
        if(per.find(entity.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe una agenda con el id: \"" + entity.getId() + "\"");
        }
        
    //    if(per.findByPlaca(entity.getPlaca())!=null)
    //    {
    //        throw new BusinessLogicException("Ya existe un conductor con la placa: \"" + entity + "\"");
    //   }
        
        entity = per.create(entity);
        return entity;
    }
    
    
}
