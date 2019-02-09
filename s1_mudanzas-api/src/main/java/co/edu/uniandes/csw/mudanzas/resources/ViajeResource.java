/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.CargaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ViajeDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ViajeDetailDTO;
import java.util.LinkedList;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.logging.Logger;
/**
 *
 * @author je.osorio
 */
@Path("viajes")
@Produces("application/JSON")
@Consumes("application/JSON")
@RequestScoped
public class ViajeResource {
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    /**
     * mètodo que crea un nuevo viaje dado un json con la informaciòn de sus atributos
     * @return un nuevo objeto que hereda de la clase ViajeDTO
     */
    @POST
    public ViajeDTO crearVijeJson(){
        return new ViajeDTO();
    }
    /**
     * mètodo que crea un viaje dado el id 
     * @param id
     * @return 
     */
    @POST
    @Path("{id: \\d+}")
    public ViajeDTO crearVije(@PathParam("id") Long id){
        return new ViajeDTO();
    }
    /**
     * mètodo que retorna un viaje dado el id 
     * @param id
     * @return el objeto ViajeDTO el cual corresponde al id especificado
     */
    @GET
    @Path("{id: \\d+}")
    public ViajeDTO getViajeDTOPorId(@PathParam("id") Long id){
        return new ViajeDTO();
    }
    /**
     * mètodo que elimina un viaje dado el id 
     * @param id
     * @return indormaciòn del viaje eliminado
     */
    @DELETE
    @Path("{id: \\d+}")
    public ViajeDTO deleteVIajeDTO(@PathParam("id") Long id){
        return new ViajeDTO();
    }
    /**
     * mètodo que retorna las cargas de un viaje dado el id del viaje
     * @param id
     * @return la lista de cargas que corresponden al viaje con id especificado
     */
    @GET
    @Path("{id: \\d+}/cargas")
    public LinkedList<CargaDTO> getCargasDadoUnID(@PathParam("id") Long id){
        return new ViajeDetailDTO().getCargas();
    }
    /**
     * mètodo que elimina las cargas de un viaje dado el id del viaje
     * @param id
     */
    @DELETE 
    @Path("{id: \\d+}/cargas")
    public void eliminarCargasIdEspecificado(@PathParam("id") Long id){
        return;
    }
    
    
}
