/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.CargaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.CargaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author je.osorio
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ViajesCargaResource {
    
    @Inject
    private ViajesLogic viajesLogic;
    @Inject 
    private CargaLogic cargaLogic;
    /**
     * método que elimina una carga con un id especificado 
     * @param idCarga
     */
    @DELETE
    @Path("{idc: \\d+}")
    public void deleteCargasViaje(@PathParam("idc") Long idCarga)throws WebApplicationException{
        try{
            viajesLogic.getCargasDadoUnId(idCarga);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        cargaLogic.deleteCarga(idCarga);
    }
    /**
     * método que retorna una carga en específico
     * @param idCarga
     * @return 
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException 
     */
    @GET
    @Path("{idc: \\d+}")
    public CargaDTO getCargaIdViaje(@PathParam("idc") Long idCarga)throws WebApplicationException, BusinessLogicException{
        CargaEntity carga;
        try{
            carga=cargaLogic.getCarga(idCarga);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new CargaDTO(carga);
    }
    /**
     * mètodo que retorna las cargas de un viaje dado el id del viaje
     * @param idViaje
     * @return la lista de cargas que corresponden al viaje con id especificado
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    public List<CargaDTO> getCargasViajeDadoUnID(@PathParam("idv") Long idViaje)throws WebApplicationException, BusinessLogicException{
        List<CargaEntity> cargas;
        try{
            cargas = viajesLogic.getCargasDadoUnId(idViaje);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return List2Entity(cargas);
    }   
    
     public List<CargaDTO> List2Entity(List<CargaEntity> entity){
        List<CargaDTO> cargas= new LinkedList<>();
        for(CargaEntity enti: entity){
            cargas.add(new CargaDTO(enti));
        }
        return cargas;
    }
}
