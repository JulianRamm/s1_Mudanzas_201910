/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.OfertaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.SubastaDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.OfertaLogic;
import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Andres Gonzalez
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OfertasSubastaResource {
    
    private static final Logger LOGGER = Logger.getLogger(OfertasSubastaResource.class.getName());

    /**
     * Atributo que inyecta la logica de la Oferta en el recurso.
     */
    @Inject
    private OfertaLogic ofertaLogic;

    
    

    /**
     * Busca y devuelve todas las ofertas que existen en la subasta.
     *
     * @param idSubasta  .
     * @return JSONArray {@link OfertaDTO} - Las ofertas encontradas en la subasta.
     * Si no hay ninguna retorna una lista vacía.
     */
    
    @GET
    public List<OfertaDTO> getOfertas(@PathParam("idSubasta") Long idSubasta)
    {
        List<OfertaDTO> listaOfertas = listEntity2DTO(ofertaLogic.getOfertasSubasta(idSubasta));
        return listaOfertas;
    }
    
    /**
     * Busca la subasta con el idSubasta asociado dentro del proveedor con el login asociado.
     *
     * @param login del proveedor que se esta buscando.
     * @param idSubasta Identificador de la subasta que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SubastaDTO} - La Subasta buscada
     */
    @GET
    @Path("{idOferta: \\d+}")
    public OfertaDTO getoferta(@PathParam("idSubasta") Long idSubasta, @PathParam("idOferta") Long idOferta) 
    {
  
        try {
                        OfertaDTO c;
            c = new OfertaDTO(ofertaLogic.getOfertaSubasta(idOferta, idSubasta));
                        return c;

        } catch (BusinessLogicException ex) {
            Logger.getLogger(OfertasSubastaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
         
    }
    
    
    
   
    
    
    
//    @PUT
//    @Path("{idOferta: \\d+}")
//    public OfertaDTO asociarOferta(@PathParam("idOferta") Long idOferta, @PathParam("idSubasta") Long idSubasta, SubastaDetailDTO subasta, OfertaDTO ofertaDTO ) throws WebApplicationException, BusinessLogicException 
//    {
//        ofertaDTO.setId(idSubasta);
//             
//        
//     if (ofertaLogic.getOfertaSubasta(idOferta, idSubasta) == null) {
//            throw new WebApplicationException("El recurso /Subastas/" + idSubasta + "/ofertas/" + idOferta + " no existe.", 404);
//        }
//        OfertaDTO dto = new OfertaDTO(ofertaLogic.updateOferta(ofertaDTO.toEntity()));
//        return dto;
//        
//        }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param subastasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<OfertaDTO> listEntity2DTO(List<OfertaEntity> ofertasList) {
        List<OfertaDTO> lista = new ArrayList<>();
        for (OfertaEntity entidad : ofertasList) {
            lista.add(new OfertaDTO(entidad));
        }
        return lista;
    }
    
}
