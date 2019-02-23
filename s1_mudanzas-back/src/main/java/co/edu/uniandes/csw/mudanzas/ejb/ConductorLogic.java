/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ConductorLogic 
{
    @Inject
    private ConductorPersistence per;
    
  //  public ConductorEntity createConductor(ConductorEntity entity)
   // {
     //   LOGGER.log
   // }
    
}
