/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira 
 */
@Stateless
public class ConductorLogic 
{
    @Inject
    private ConductorPersistence per;
    
    public ConductorEntity crearConductor(ConductorEntity conductor)throws BusinessLogicException
    {
        if(per.find(conductor.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe un conductor con el id: \"" + conductor.getId() + "\"");
        }
        
        if(per.findByName(conductor.getNombre())!= null)
        {
            throw new BusinessLogicException("Ya existe un conductor con el nombre: \"" + conductor.getNombre() + "\"");
        }
        conductor = per.create(conductor);
        
        return conductor;
    }
  //  public ConductorEntity createConductor(ConductorEntity entity)
   // {
     //   LOGGER.log
   // }
    
}
