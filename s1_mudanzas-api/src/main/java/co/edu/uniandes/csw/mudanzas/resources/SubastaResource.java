/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.SubastaDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Andres Gonzalez
 */
@Path("Subastas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubastaResource {
    
     /**
     * Atributo que inyecta la logica del usuario en el recurso.
     */
   @Inject
    private SubastaLogic subastaLogic;
   
   
   /**
     * Busca y devuelve todas las subastas que existen en la aplicacion.
     *
     * @return JSONArray {@link SubastaDTO} - Las subastas encontradas en la
     * aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SubastaDetailDTO> getSubasta() {
        List<SubastaDetailDTO> listaUsuarios = listEntity2DetailDTO(subastaLogic.getSubastas());
        return listaUsuarios;
    }
    
    
     /**
     * Busca la Subasta asociada recibida en la URL y lo devuelve.
     *
     * @param id de la Subasta que se esta buscando.
     * @return JSON {@link SubastaDTO} - La Subasta buscada.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{id}")
    public SubastaDetailDTO getSubasta(@PathParam("id") Long subastaId) throws WebApplicationException, BusinessLogicException {
        SubastaEntity subastaEntity = subastaLogic.getSubasta(subastaId);
        if (subastaEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + subastaId + " no existe.", 404);
        }
        SubastaDetailDTO detailDTO = new SubastaDetailDTO(subastaEntity);
        return detailDTO;
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param subastasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<SubastaDetailDTO> listEntity2DetailDTO(List<SubastaEntity> subastasList) {
        List<SubastaDetailDTO> lista = new ArrayList<>();
        for (SubastaEntity entidad : subastasList) {
            lista.add(new SubastaDetailDTO(entidad));
        }
        return lista;
    }
}

