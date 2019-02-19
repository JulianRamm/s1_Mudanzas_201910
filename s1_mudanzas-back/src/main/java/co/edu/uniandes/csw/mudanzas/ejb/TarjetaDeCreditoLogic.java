/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Luis Miguel
 */
@Stateless
public class TarjetaDeCreditoLogic {
    
    @Inject
    private TarjetaDeCreditoPersistence persistence;
    
    public TarjetaDeCreditoEntity createTarjeta(TarjetaDeCreditoEntity tarjetaEntity){
        
        
        persistence.create(tarjetaEntity);
        return tarjetaEntity;
    }
    
}
