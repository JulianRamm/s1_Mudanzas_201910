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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
     * método que crea una carga sigueindo la 
     * @param cargaDTO
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public CargaDTO creteCarga(CargaDTO cargaDTO) throws BusinessLogicException{
        CargaEntity CargaEntity = cargaDTO.toEntity();
        CargaEntity nuevaCargaEntity;
        try{
            nuevaCargaEntity=cargaLogic.createCarga(CargaEntity);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.EXPECTATION_FAILED);
        }
        CargaDTO nuevoViajeDTO = new CargaDTO(nuevaCargaEntity);
        return nuevoViajeDTO;
    }
    
}
