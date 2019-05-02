/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.ViajeDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ViajesDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author je.osorio
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ConductorViajesResource {
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    /**
     * atributo de la lógica de viajes
     */
    @Inject
    private ViajesLogic viajesLogic;

   
    
    
    /**
     * mètodo que crea un nuevo viaje dado un json con la informaciòn de sus atributos
     * @param viajeDTO
     * @param idConductor
     * @return un nuevo objeto que hereda de la clase ViajeDTO
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public ViajeDTO createViaje(ViajeDTO viajeDTO, @PathParam("idConductor") Long idConductor) throws BusinessLogicException, WebApplicationException{
        try{
            ViajeDTO nuevoViajeEntity = new ViajeDTO(viajesLogic.createViaje(viajeDTO.toEntity(), idConductor));
            return nuevoViajeEntity;
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException("No se pudo crear el viaje:" + e.getMessage());
        }
    }
    
    /**
     * mètodo que retorna un viaje dado el id 
     * @param idViaje
     * @return el objeto ViajeDTO el cual corresponde al id especificado
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    /*
    @GET
    @Path("{idViaje: \\d+}")
    public ViajesDetailDTO getViajeDTOPorId(@PathParam("idViaje") Long idViaje) throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity;
        try{
        viajesEntity = viajesLogic.getViaje(idViaje);
       
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ViajesDetailDTO(viajesEntity);
    }
    @GET
    @Path("{idViaje: \\d+}")
    public ViajesDetailDTO getViajeDTOPorId(@PathParam("idViaje") Long idViaje) throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity;
        try{
        viajesEntity = viajesLogic.getViaje(idViaje);
       
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ViajesDetailDTO(viajesEntity);
    }
    */
    /**
     * retornar viaje conductor
     * @param idConductor
     * @return
     * @throws BusinessLogicException
     * @throws WebApplicationException 
     */
    @GET
    public ViajesDetailDTO getViajes(@PathParam("idConductor") Long idConductor)throws BusinessLogicException, WebApplicationException{
        ViajesEntity viajesEntity;
        try{
        viajesEntity = viajesLogic.getViajeCon(idConductor);      
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ViajesDetailDTO(viajesEntity);
    }
    /**
     * mètodo que elimina un viaje dado el id 
     * @param idViaje
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{idViaje: \\d+}")
    public void deleteViajeDTO(@PathParam("idViaje") Long idViaje) throws BusinessLogicException, WebApplicationException{
        try{
            viajesLogic.getViaje(idViaje);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        viajesLogic.deleteViaje(idViaje);
    }
    @PUT
    @Path("{idViaje: \\d+}")
    public ViajesDetailDTO updateViajeDTO(@PathParam("idViaje") Long idViaje,ViajesDetailDTO viajeDTO )throws BusinessLogicException, WebApplicationException{
        try{
            viajesLogic.getViaje(idViaje);
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        try{
        ViajesDetailDTO detailDTO = new ViajesDetailDTO(viajesLogic.updateViaje(viajeDTO.toEntity()));
        return detailDTO;    
        }
        catch(Exception e){
            throw new WebApplicationException("No se pudo actualizar el viaje: "+e.getMessage());
        }
    }
    public List<ViajesDetailDTO> List2Entity(List<ViajesEntity> entity){
        List<ViajesDetailDTO> Viajes= new LinkedList<>();
        for(ViajesEntity enti: entity){
            Viajes.add(new ViajesDetailDTO(enti));
        }
        return Viajes;
    }
    /**
     * 
     * @param idViaje
     * @return 
     */
   @Path("{idViaje: \\d+}/cargas")
    public Class<ViajesCargaResource> getViajesCargaResource(@PathParam("idViaje") long idViaje)
    {
        return ViajesCargaResource.class;
    }
}
