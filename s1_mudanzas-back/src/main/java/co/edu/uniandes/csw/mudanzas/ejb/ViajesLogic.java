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
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author je.osorio
 */
@Stateless
public class ViajesLogic {

    @Inject
    private ViajesPersistence persistence;
    /**
     * método que crea un viaje y verifica las reglas de negocio definidas
     * @param viajesEntity
     * @return
     * @throws BusinessLogicException 
     */
    public ViajesEntity createViajes(ViajesEntity viajesEntity) throws BusinessLogicException {
        double tiempoT = 0.0;
        double distance = 0.0;
        for (CargaEntity carga : viajesEntity.getCargas()) {
            if (!carga.getLugarSalida().equals(viajesEntity.getLugarSalida()) || carga.getLugarSalida() == null || carga.getLugarSalida().equals("")) {
                throw new BusinessLogicException("El lugar de salida del viaje no es el mismo al lugar de salida de las cargas, o es null o es vacío ");
            }
            if (!carga.getLugarLlegada().equals(viajesEntity.getLugarLlegada()) || carga.getLugarLlegada() == null || carga.getLugarLlegada().equals("")) {
                throw new BusinessLogicException("El lugar de llegada del viaje no es el mismo al lugar de llegada de las cargas, o es null o es vacío");
            }
            for (DireccionEntity dir : carga.getDirecciones()) {
                LinkedList<DireccionEntity> lis = carga.encontrarParDirecciones(dir.getIdPar());
                distance += carga.calcularDistancia(lis.get(0).getLatitud(), lis.get(1).getLatitud(), lis.get(0).getLongitud(), lis.get(1).getLongitud());

                if (viajesEntity.getVehiculoDelViaje().getRendimiento() == 0) {
                    throw new BusinessLogicException("El rendimiento es cero");
                } else {
                    tiempoT += (distance/1000) / viajesEntity.getVehiculoDelViaje().getRendimiento();
                }
            }
            viajesEntity.verificarTiempo(tiempoT);
        }
        if (viajesEntity.getGastoGasolina() == distance * viajesEntity.getVehiculoDelViaje().getRendimiento()) {
            throw new BusinessLogicException("El gasto de gasolina no es acorde a la distancia");
        }
        if (viajesEntity.getGastoGasolina() <= 0) {
            throw new BusinessLogicException("El gasto de gasolina no puede ser 0 o negativo");
        }
        double hours = ChronoUnit.HOURS.between(viajesEntity.getHoraPartida(), viajesEntity.getHoraLlegada());
        if (!(tiempoT <=hours+8&&tiempoT>=hours-8)) {
             throw new BusinessLogicException("La hora de llegada y la hora de salida no es acorde a la distancia");
        }     
        if(viajesEntity.getHoraPartida()==null){
            throw new BusinessLogicException("la hora de partida no puede ser null");
        }
         if(viajesEntity.getHoraPartida()==null){
            throw new BusinessLogicException("la hora de llegada no puede ser null");
        }
        if(viajesEntity.getCargas().isEmpty()||viajesEntity.getCargas()==null){
            throw new BusinessLogicException("El viaje no puede no tener cargas");
        }
        persistence.create(viajesEntity);
        return viajesEntity;
    }
    
    /**
     * método que retorna todos los viajes en la BD
     * @return 
     */
    public List<ViajesEntity> getViajes() {
        List<ViajesEntity> viajes = persistence.findAll();
        return viajes;
    }
    
    /**
     * método que devuelve un viaje dado el id del viaje 
     * @param id
     * @return
     * @throws BusinessLogicException 
     */
    public ViajesEntity getViaje(long id)throws BusinessLogicException{
        ViajesEntity viajeEntity=persistence.find(id);
        if(viajeEntity==null){
            throw new BusinessLogicException("No existe un viaje con id: "+ id);
        }
        return viajeEntity;
    }
    /**
     * método que actualiza un viaje dado un objeto que contenga los cambios
     * @param viajesEntity
     * @return 
     */
    public ViajesEntity updateViaje(ViajesEntity viajesEntity){
        ViajesEntity viaje =persistence.update(viajesEntity);
        return viaje;
    }
    
    public void deleteViaje(long id)throws BusinessLogicException{
        persistence.delete(id);
        
    }
    

}
