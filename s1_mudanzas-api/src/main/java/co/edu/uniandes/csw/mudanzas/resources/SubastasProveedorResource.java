/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.SubastaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.SubastaDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Daniel Machado
 */
public class SubastasProveedorResource {
    
    private static final Logger LOGGER = Logger.getLogger(SubastasProveedorResource.class.getName());

    /**
     * Atributo que inyecta la logica de la tarjeta en el recurso.
     */
    @Inject
    private SubastaLogic subastaLogic;

    /**
     * Atributo que inyecta la logica del proveedor en el recurso.
     */
    @Inject
    private ProveedorLogic proveedorLogic;
    
    /**
     * Busca y devuelve todas las subastas que existen en el proveedor.
     *
     * @param login del proveedor que se esta buscando.
     * @return JSONArray {@link SubastaDTO} - Las subastas encontradas en el
     * proveedor. Si no hay ninguna retorna una lista vacía.
     */
    
    @GET
    public List<SubastaDetailDTO> getSubastas(@PathParam("login") String login)
    {
        List<SubastaDetailDTO> listaSubastas = listEntity2DTO(subastaLogic.getSubastasProveedor(login));
        return listaSubastas;
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
    @Path("{idSubasta: \\d+}")
    public SubastaDTO getSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta) throws WebApplicationException
    {
        try {
            SubastaDetailDTO c = new SubastaDetailDTO(subastaLogic.getSubastaProveedor(login, idSubasta));
            return c;
        } catch(BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/subastas/" + idSubasta + " no existe.", 404);
        }    
    }
    
    /**
     * Guarda una carga dentro de un proveedor con la informacion que recibe el la
     * URL. Se devuelve la carga que se guarda en el proveedor.
     *
     * @param login del proveedor que se esta actualizando.
     * @param subasta
     * @return JSON {@link SubastaDTO} - La carga guardada en el proveedor.
     */
    @POST
    public SubastaDTO crearSubasta(@PathParam("login") String login, SubastaDTO subasta) throws WebApplicationException {
        try {
            SubastaDTO SubastaDTO = new SubastaDTO(subastaLogic.createSubastaProveedor(subasta.toEntity(), login));
            return SubastaDTO;
        }
        catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/subastas/" + subasta.getId() + " ya existe.", 412);
        }
    }
    
    /**
     * Remplaza una instancia de Carga asociada a una instancia del
     * Proveedor
     *
     * @param login del proveedor que se esta remplazando.
     * @param idSubasta Identificador de la tarjeta que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SubastaDTO} - La Tarjeta de Credito
     * Actualizada
     */
    @PUT
    @Path("{idSubasta: \\d+}")
    public SubastaDTO cambiarSubasta(@PathParam("login") String login, @PathParam("idSubasta") Long idSubasta, SubastaDTO subasta) throws WebApplicationException, BusinessLogicException {
        subasta.setId(idSubasta);
        if (subastaLogic.getSubastaProveedor(login, idSubasta) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/cargas/" + idSubasta + " no existe.", 404);
        }
        SubastaDTO dto = new SubastaDTO(subastaLogic.update(subasta.toEntity()));
        return dto;
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param subastasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<SubastaDetailDTO> listEntity2DTO(List<SubastaEntity> subastasList) {
        List<SubastaDetailDTO> lista = new ArrayList<>();
        for (SubastaEntity entidad : subastasList) {
            lista.add(new SubastaDetailDTO(entidad));
        }
        return lista;
    }
}
