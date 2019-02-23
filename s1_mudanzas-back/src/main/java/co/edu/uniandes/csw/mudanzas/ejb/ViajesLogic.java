/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ViajesPersistence;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author je.osorio
 */
@Stateless
public class ViajesLogic {
    @Inject
    private ViajesPersistence persisitence;
    
    public ViajesEntity createViajes(ViajesEntity viajesEntity) throws BusinessLogicException{
        for(CargaEntity a:viajesEntity.getCargas()){
            if(!a.getLugarSalida().equals(viajesEntity.getLugarSalida()) || a.getLugarSalida()==null || a.getLugarSalida().equals("")){
                throw new BusinessLogicException("El lugar de salida del viaje no es el mismo al lugar de salida de las cargas");
            }
            if(!a.getLugarLlegada().equals(viajesEntity.getLugarLlegada()) || a.getLugarLlegada()==null || a.getLugarLlegada().equals("")){
                throw new BusinessLogicException("El lugar de llegada del viaje no es el mismo al lugar de llegada de las cargas");
            }           
            for(DireccionEntity dir: a.getDirecciones()){
                LinkedList<DireccionEntity> lis=a.encontrarParDirecciones(dir.getIdPar());
                double distance1=a.calcularDistancia(lis.get(0).getLatitud(),lis.get(1).getLatitud(), lis.get(0).getLongitud(), lis.get(1).getLongitud());
            }
        }
        
        
        persisitence.create(viajesEntity);
        return viajesEntity;
    }
    
}
