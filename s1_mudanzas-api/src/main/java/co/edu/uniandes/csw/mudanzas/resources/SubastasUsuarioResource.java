/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.SubastaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "usuarios/{login}/subastas"
 * @author Luis Miguel Gomez
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubastasUsuarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(SubastasUsuarioResource.class.getName());

    /**
     * Busca y devuelve todas las subastas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link SubastaDTO} - Las subastas encontradas en el
     * usuario. Si no hay ninguna retorna una lista vacía.
     */
    
    SubastaLogic subLogic ;
    @GET
    public List<SubastaDTO> getSubastas(@PathParam("login") String login)
    {
        return null;
    }
    
    /**
     * Busca la subasta con el idSubasta asociado dentro del usuario con el login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @param idSubasta Identificador de la subasta que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SubastaDTO} - La Subasta buscada
     */
    @GET
    @Path("{idSubasta: \\d+}")
    public SubastaDTO getSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta)
    {
        return null;
    }
    
    /**
     * Guarda una subasta dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la subasta que se guarda en el usuario.
     *
     * @param login del usuario que se esta
     * actualizando.
     * @param idSubasta Identificador de la subasta que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SubastaDTO} - La subasta guardada en el usuario.
     */
    @POST
    @Path("{idSubasta: \\d+}")
    public SubastaDTO crearSubastaFROMUSUARIO(@PathParam("login") String login,@PathParam("idSubasta") Long idSubasta)
    {
        SubastaPersistence subPersist = new SubastaPersistence();
        return null;
    }
    
    @POST
    public SubastaDTO createSubasta(SubastaDTO subDTO) throws Exception
    {
        SubastaEntity subentity = subDTO.toEntity();
        
        SubastaEntity nuevaSubEntity = subLogic.createSubasta(subentity);
         SubastaDTO nuevoSubastaDTO = new SubastaDTO(nuevaSubEntity);
    return nuevoSubastaDTO; 
    }
    
    /**
     * Remplaza una instancia de Subasta asociada a una instancia del Usuario
     *
     * @param login del usuario que se esta
     * remplazando.
     * @param idSubasta Identificador de la subasta que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SubastaDTO} - La Subasta Actualizada
     */
    @PUT
    @Path("{idSubasta: \\d+}")
    public SubastaDTO cambiarSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta){
        return null;
    }
    
}
