/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;
import co.edu.uniandes.csw.mudanzas.dtos.OfertaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.OfertaLogic;
import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author aj.gonzalezt
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OfertaProveedorResource {
    
    
    private static final Logger LOGGER = Logger.getLogger(OfertaProveedorResource.class.getName());

     @Inject
    private OfertaLogic oferLogic;
    
    /**
     * Busca la oferta con el idOferta asociado dentro del proveedor con el login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @return JSON {@link OfertaDTO} - La idOferta buscada
     */
    @GET
    public List<OfertaDTO> getOfertas(@PathParam("login") String login) 
    {
       List<OfertaDTO> listaOfertas = listEntity2DTO(oferLogic.getOfertasProveedor(login));
       return listaOfertas;
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
    public OfertaDTO crearOferta(@PathParam("login") String login, OfertaDTO oferta)
    {
        try {
            OfertaDTO ofertaDTO = new OfertaDTO(oferLogic.createOfertaProveedor(oferta.toEntity(), login));
            return ofertaDTO;
        }
        catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/subastas/" + oferta.getId()+ " ya existe.", 412);
        }

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
    public OfertaDTO cambiarOferta(@PathParam("login") String login, OfertaDTO oferta){
 try {
            OfertaDTO SubastaDTO = new OfertaDTO(oferLogic.createOfertaProveedor(oferta.toEntity(), login));
            return SubastaDTO;
        }
        catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/subastas/" + oferta.getId()+ " ya existe.", 412);
        }    
    }
    public List<OfertaDTO> listEntity2DTO(List<OfertaEntity> ofertasList) {
        List<OfertaDTO> lista = new ArrayList<>();
        for (OfertaEntity entidad : ofertasList) {
            lista.add(new OfertaDTO(entidad));
        }
        return lista;
    }
    
}

