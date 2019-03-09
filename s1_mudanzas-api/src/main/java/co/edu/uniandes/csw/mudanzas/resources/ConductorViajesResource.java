/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.ViajeDTO;
import co.edu.uniandes.csw.mudanzas.ejb.CargaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
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
    public ViajeDTO createViaje(ViajeDTO viajeDTO) throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity = viajeDTO.toEntity();
        ViajesEntity nuevoViajeEntity;
//        try{
//            nuevoViajeEntity = viajesLogic.createViaje(viajesEntity);
//        }
//        catch(BusinessLogicException e){
//            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
//        }
        ViajeDTO nuevoViajeDTO = new ViajeDTO();
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
    public ViajeDTO getViajeDTOPorId(@PathParam("id") Long id) throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity;
        try{
        viajesEntity = viajesLogic.getViaje(id);
       
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ViajeDTO(viajesEntity);
    }
    /**
     * retornar todos los viajes
     * @return
     * @throws BusinessLogicException
     * @throws WebApplicationException 
     */
    @GET
    public List<ViajeDTO> getViajes()throws BusinessLogicException, WebApplicationException{
        List<ViajesEntity> viajesEntity;
//        try{
        viajesEntity = viajesLogic.getViajes();
//       
//        }
//        catch(BusinessLogicException e){
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
        return List2Entity(viajesEntity);
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
    public List<ViajeDTO> List2Entity(List<ViajesEntity> entity){
        List<ViajeDTO> Viajes= new LinkedList<>();
        for(ViajesEntity enti: entity){
            Viajes.add(new ViajeDTO(enti));
        }
        return Viajes;
    }
   
}
