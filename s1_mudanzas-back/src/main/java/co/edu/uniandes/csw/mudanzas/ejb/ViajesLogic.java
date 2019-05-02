/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.ViajesPersistence;
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
    @Inject
    private ConductorPersistence conductorPersistence;

    /**
     * método que crea un viaje y verifica las reglas de negocio definidas
     *
     * @param viajesEntity
     * @return
     * @throws BusinessLogicException
     */
    public ViajesEntity createViaje(ViajesEntity viaje, Long idConductor) throws BusinessLogicException {
        ConductorEntity conductorEntity = conductorPersistence.find(idConductor);
        if (conductorEntity == null) {
            throw new BusinessLogicException("No existe ningun conductor \"" + idConductor + "\"");
        }
        if (conductorEntity.getViaje() != null) {
            throw new BusinessLogicException("El conductor con id: " + idConductor + " ya tiene un viaje.");
        }
        //Verificacion de existencia
        viaje.setConductorEntity(conductorEntity);

        if (viaje.getGastoGasolina() <= 0) {
            throw new BusinessLogicException("El gasto de gasolina no puede ser 0 onegativo ");
        }

        if (viaje.getHoraPartida() == null) {
            throw new BusinessLogicException("la hora de partida no puede ser null");
        }
        if (viaje.getHoraPartida() == null) {
            throw new BusinessLogicException("la hora de llegada no puede ser null");
        }
        
        conductorEntity.setViaje(viaje);
        persistence.create(viaje);
        conductorPersistence.update(conductorEntity);
        return viaje;
    }

   
    /**
     * método que retorna todos los viajes en la BD
     *
     * @return
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public List<ViajesEntity> getViajes() throws BusinessLogicException {
        List<ViajesEntity> viajes = persistence.findAll();
        if (viajes == null) {
            throw new BusinessLogicException("No hay viajes");
        }

        return viajes;
    }
/**
     * método que retorna todos los viajes del conductor
     *
     * @param idCon
     * @return
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ViajesEntity getViajeCon(Long idCon) throws BusinessLogicException {
        ViajesEntity viajes = persistence.getVajesDeConductor(idCon);
        if (viajes == null) {
            throw new BusinessLogicException("No hay viajes");
        }
        return viajes;
    }
    /**
     * método que devuelve un viaje dado el id del viaje
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */
    public ViajesEntity getViaje(Long id) throws BusinessLogicException {
        ViajesEntity viajeEntity = persistence.find(id);
        if (viajeEntity == null) {
            throw new BusinessLogicException("No existe un viaje con id: " + id);
        }
        return viajeEntity;
    }

    /**
     * método que actualiza un viaje dado un objeto que contenga los cambios
     *
     * @param viajeEntity
     * @return
     */
    public ViajesEntity updateViaje(ViajesEntity viajeEntity) throws BusinessLogicException {
        //        double tiempoT = 0.0;
//        double distance = 0.0;
        
        for (CargaEntity carga : viajeEntity.getCargas()) {
            if (!carga.getLugarSalida().equals(viajeEntity.getLugarSalida()) || carga.getLugarSalida() == null || carga.getLugarSalida().equals("")) {
                throw new BusinessLogicException("El lugar de salida del viaje no es el mismo al lugar de salida de las cargas, o es null o es vacío ");
            }
            if (!carga.getLugarLlegada().equals(viajeEntity.getLugarLlegada()) || carga.getLugarLlegada() == null || carga.getLugarLlegada().equals("")) {
                throw new BusinessLogicException("El lugar de llegada del viaje no es el mismo al lugar de llegada de las cargas, o es null o es vacío");
            }
            //          double hours =(viajeEntity.getHoraLlegada().getTime()-viajeEntity.getHoraPartida().getTime())*1000*3600;
//          if (!(tiempoT <=hours+8&&tiempoT>=hours-8)) { 
//              throw new BusinessLogicException("La hora de llegada y la hora de salida no es acorde a la distancia"); 
//          }

//         for (DireccionEntity dir : carga.getDirecciones()) {
//          LinkedList<DireccionEntity> lis =
//          carga.encontrarParDirecciones(dir.getIdPar()); distance +=
//          carga.calcularDistancia(lis.get(0).getLatitud(),
//          lis.get(1).getLatitud(), lis.get(0).getLongitud(),
//          lis.get(1).getLongitud());
//         }
//         if {
//                tiempoT += (distance / 1000)
//                        / viaje.getVehiculoDelViaje().getRendimiento();
//            }
//          viajeEntity.verificarTiempo(tiempoT); }   
//          if (viaje.getGastoGasolina() != distance * viajeEntity.getVehiculoDelViaje().getRendimiento()) {
//                throw new BusinessLogicException("El gasto de gasolina no es acorde a la distancia"); 
//          }   
        }
        if (viajeEntity.getCargas().isEmpty() || viajeEntity.getCargas() == null) {
            throw new BusinessLogicException("El viaje no puede no tener cargas");
        }
        ConductorEntity conductor = viajeEntity.getConductorEntity();
        conductor.setViaje(viajeEntity);
        conductorPersistence.update(conductor);
        persistence.update(viajeEntity);
        return viajeEntity;
    }
    /**
     * elimina un viaje con id especificado
     *
     * @param id
     * @throws BusinessLogicException
     */
    public void deleteViaje(Long id) throws BusinessLogicException {
        ViajesEntity viaje = persistence.find(id);
        ConductorEntity conductor = viaje.getConductorEntity();
        conductor.setViaje(null);
        conductorPersistence.update(conductor);
        deleteCargasDaodUnId(id);
        persistence.delete(id);
    }

    /**
     * retornar conductor
     *
     * @param idConductor
     * @return
     * @throws BusinessLogicException
     */
    public ViajesEntity getViajeConductor(Long idConductor) throws BusinessLogicException {
        ConductorEntity conductor = conductorPersistence.find(idConductor);
        if (conductor != null) {
            ViajesEntity viaje = conductor.getViaje();
            if (viaje != null) {
                return viaje;
            }
            throw new BusinessLogicException("No existe el viaje de un conductor con id: " + idConductor);
        }
        throw new BusinessLogicException("No existe tal conductor con id: " + idConductor);
    }

    /**
     * devuelve las cargas de un viaje especificado
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */
    public List<CargaEntity> getCargasDadoUnId(Long id) throws BusinessLogicException {
        List<CargaEntity> car = persistence.find(id).getCargas();
        if (car == null) {
            throw new BusinessLogicException("No hay cargas para un id: " + id);
        }
        return car;
    }

    /**
     * elimina las cargas dado un id de viaje
     *
     * @param id
     * @throws BusinessLogicException
     */
    public void deleteCargasDaodUnId(Long id) throws BusinessLogicException {
        List<CargaEntity> car = persistence.getCargasDadoUnId(id);
        if (car == null) {
            throw new BusinessLogicException("No hay cargas que eliminar para un viaje con id: " + id);
        }
        persistence.deleteCargasDadoUnId(id);

    }
}
