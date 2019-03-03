/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.ViajesDTO;
import co.edu.uniandes.csw.mudanzas.ejb.CargaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
/**
 *
 * @author je.osorio
 */
@Produces("application/JSON")
@Consumes("application/JSON")
@RequestScoped
public class ConductorViajesResource {
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    /**
     * atributo de la lógica de viajes
     */
    @Inject
    private ViajesLogic viajesLogic;
    @Inject 
    private CargaLogic cargalogic;
    /**
     * mètodo que crea un nuevo viaje dado un json con la informaciòn de sus atributos
     * @param viajeDTO
     * @return un nuevo objeto que hereda de la clase ViajeDTO
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public ViajesDTO createVije(ViajesDTO viajeDTO) throws BusinessLogicException{
        ViajesEntity viajesEntity = viajeDTO.toEntity();
        ViajesEntity nuevoViajeEntity = viajesLogic.createViajes(viajesEntity);
        ViajesDTO nuevoViajeDTO = new ViajesDTO(nuevoViajeEntity);
        return nuevoViajeDTO;
    }
    
   
    /**
     * mètodo que retorna un viaje dado el id 
     * @param id
     * @return el objeto ViajeDTO el cual corresponde al id especificado
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public ViajesDTO getViajeDTOPorId(@PathParam("id") Long id) throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity;
        try{
        viajesEntity = viajesLogic.getViaje(id);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ViajesDTO(viajesEntity);
    }
    /**
     * mètodo que elimina un viaje dado el id 
     * @param id
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteViajeDTO(@PathParam("id") Long id) throws BusinessLogicException, WebApplicationException{
        try{
            viajesLogic.getViaje(id);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        viajesLogic.deleteViaje(id);
    }
    
    @Path("{id: \\d+}/cargas")   
    public Class<ViajesCargaResource> getConductorViaje(@PathParam("id")Long id ){
        return ViajesCargaResource.class;
    }
    
   
}
