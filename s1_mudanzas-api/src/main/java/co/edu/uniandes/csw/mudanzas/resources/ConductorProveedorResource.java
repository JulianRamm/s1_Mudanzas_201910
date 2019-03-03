/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import co.edu.uniandes.csw.mudanzas.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ConductorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Daniel MAchado
 */
public class ConductorProveedorResource 
{
    
     private static final Logger LOGGER = Logger.getLogger(TarjetasUsuarioResource.class.getName());
     
     @Inject
     private ConductorLogic conductorLogic;
     
     @Inject
     private ProveedorLogic proveedorLogic;
     
     

    /**
     * Busca y devuelve todas las tarjetas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
    */ 
    @GET
    public List<ConductorDTO> getConductores(@PathParam("login") String login) throws BusinessLogicException {
        List<ConductorDTO> listaTarjetas = listEntity2DTO(conductorLogic.getConductoresProveedor(login));
        return listaTarjetas;
    }
    
    /**
     * Busca la tarjeta con el idTarjeta asociado dentro del usuario con el
     * login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @param idConductor Identificador de la tarjeta que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La Tarjeta buscada
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public ConductorDTO getConductor(@PathParam("login") String login, @PathParam("idConductor") Long idConductor) throws WebApplicationException, BusinessLogicException {
        
        if (conductorLogic.getConductor(login, idConductor) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + idConductor + " no existe.", 404);
        }
        ConductorDTO conductorDTO = new ConductorDTO(conductorLogic.getConductor(login, idConductor));
        return conductorDTO;
    }
    
    
    /**
     * Guarda una tarjeta dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la tarjeta que se guarda en el usuario.
     *
     * @param login del usuario que se esta actualizando.
     * @param conductor {@link TarjetaDeCreditoDTO}la tarjeta que se desea
     * guardar. Este debe ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La tarjeta guardada en el
     * usuario.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public ConductorDTO crearConductor(@PathParam("login") String login, ConductorDTO conductor) throws WebApplicationException, BusinessLogicException {
        if (conductorLogic.getConductor(login, conductor.getId()) != null) {
            throw new WebApplicationException("El recurso /tarjetas/" + conductor.getId() + " ya existe.", 412);
        }
        ConductorDTO conductorDTO = new ConductorDTO(conductorLogic.crearConductor(conductor.toEntity(), login));
        return conductorDTO;
    }
    
    /**
     * Remplaza una instancia de Tarjeta de Credito asociada a una instancia del
     * Usuario
     *
     * @param login del usuario que se esta remplazando.
     * @param idConductor Identificador de la tarjeta que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param conductor
     * @return JSON {@link TarjetaDeCreditoDTO} - La Tarjeta de Credito
     * Actualizada
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{idConductor: \\d+}")
    public ConductorDTO cambiarConductor(@PathParam("login") String login, @PathParam("idConductor") Long idConductor, ConductorDTO conductor) throws WebApplicationException, BusinessLogicException {
        conductor.setId(idConductor);
        if (conductorLogic.getConductor(login, idConductor) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + idConductor + " no existe.", 404);
        }
        ConductorDTO dto = new ConductorDTO(conductorLogic.updateConductor(conductor.toEntity()));
        return dto;
    }
    
    @DELETE
    @Path("{idConductor: \\d+}")
    public ConductorDTO BorrarConductorConId(@PathParam("login") String login, @PathParam("idConductor") Long pIdConductor)
    {
        return null;
    }
    
    @Path("{idConductor}/vehiculos")
    public Class<ConductorVehiculoResource> getConductorVehiculoResource(@PathParam("idConductor") Long pIdConductor)
    {
        return ConductorVehiculoResource.class;
    }
    
    public List<ConductorDTO> listEntity2DTO(List<ConductorEntity> conductoresList) {
        List<ConductorDTO> lista = new ArrayList<>();
        for (ConductorEntity entidad : conductoresList) {
            lista.add(new ConductorDTO(entidad));
        }
        return lista;
    }
}
