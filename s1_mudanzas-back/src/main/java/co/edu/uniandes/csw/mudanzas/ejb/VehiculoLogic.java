/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.VehiculoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */

@Stateless
public class VehiculoLogic 
{
    @Inject
   private VehiculoPersistence per;
    
    public VehiculoEntity crearVehiculo(VehiculoEntity entity) throws BusinessLogicException
    {
        if(per.find(entity.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con el id: \"" + entity.getId() + "\"");
        }
        
        if(per.findByPlaca(entity.getPlaca())!=null)
        {
            throw new BusinessLogicException("Ya existe un conductor con la placa: \"" + entity + "\"");
        }
        
        entity = per.create(entity);
        return entity;
    }
    
  //  public boolean marcaSinCaracteresEspeciales()
  //  {
        
  //  }
    
}
