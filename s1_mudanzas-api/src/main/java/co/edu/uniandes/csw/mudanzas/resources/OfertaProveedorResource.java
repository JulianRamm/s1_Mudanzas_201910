/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.OfertaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.OfertaLogic;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author aj.gonzalezt
 */
public class OfertaProveedorResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(OfertaProveedorResource.class.getName());

    private OfertaLogic oferLogic;
    
    /**
     * Busca la oferta con el idOferta asociado dentro del proveedor con el login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @return JSON {@link OfertaDTO} - La idOferta buscada
     */
    @GET
    @Path("{idOferta: \\d+}")
    public OfertaDTO getOfrerta(@PathParam("login") String login) throws BusinessLogicException
    {
        return new OfertaDTO(oferLogic.getOfertaProveedor(Long.MIN_VALUE, login));
    }
    
    /**
     * Guarda una subasta dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la subasta que se guarda en el usuario.
     *
     * @param login del usuario que se esta
     * actualizando.
     * @param idOferta Identificador de la oferta que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link OfertaDTO} - La idOferta guardada en el proveedor.
     */
    @POST
    @Path("{idOferta: \\d+}")
    public OfertaDTO crearOferta(@PathParam("login") String login,@PathParam("idOferta") Long idOferta)
    {
        return null;
    }
    /**
     * Remplaza una instancia de Oferta asociada a una instancia del Proveedor
     *
     * @param login del proveedor que se esta
     * remplazando.
     * @param idOferta Identificador de la oferta que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link OfertaDTO} - La oferta Actualizada
     */
    @PUT
    @Path("{idOferta: \\d+}")
    public OfertaDTO cambiarOferta(@PathParam("login") String login, @PathParam("idOferta") Long idOferta){
        return null;
    }
}
