/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.SubastaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
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
     * Atributo que inyecta la logica de la tarjeta en el recurso.
     */
    @Inject
    private SubastaLogic subastaLogic;

    /**
     * Atributo que inyecta la logica del usuario en el recurso.
     */
    @Inject
    private UsuarioLogic usuarioLogic;
    
    /**
     * Busca y devuelve todas las subastas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link SubastaDTO} - Las subastas encontradas en el
     * usuario. Si no hay ninguna retorna una lista vacía.
     */
    
    @GET
    public List<SubastaDTO> getSubastas(@PathParam("login") String login)
    {
        List<SubastaDTO> listaSubastas = listEntity2DTO(subastaLogic.getSubastassUsuario(login));
        return listaSubastas;
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
    public SubastaDTO getSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta) throws BusinessLogicException
    {
        return new SubastaDTO(subastaLogic.getSubastaUsuario(idSubasta, login));
    }
    
    
    
    @POST
    public SubastaDTO createSubasta(SubastaDTO subDTO) throws Exception
    {
        SubastaEntity subentity = subDTO.toEntity();
        SubastaEntity nuevaSubEntity = subastaLogic.createSubasta(subentity);
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
    public SubastaDTO cambiarSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta) throws BusinessLogicException{
        SubastaDTO subastaEncontrada = getSubasta(login, idSubasta);
        SubastaEntity subastaEnty = subastaEncontrada.toEntity();
        return new SubastaDTO(subastaLogic.update(subastaEnty));
         
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param subastasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<SubastaDTO> listEntity2DTO(List<SubastaEntity> subastasList) {
        List<SubastaDTO> lista = new ArrayList<>();
        for (SubastaEntity entidad : subastasList) {
            lista.add(new SubastaDTO(entidad));
        }
        return lista;
    }
    
    
    
}
