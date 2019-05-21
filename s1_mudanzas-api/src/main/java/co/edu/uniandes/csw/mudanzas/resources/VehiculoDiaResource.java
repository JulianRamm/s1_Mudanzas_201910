/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.DiaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.DiaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author Samuel Bernal Neira
 */
@Path("agenda")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped

public class VehiculoDiaResource 
{
    /**
     * Inyección de la lógica del vehículo
     */
    @Inject
    private VehiculoLogic vLogica;
    /**
     * Inyección de la lógica del día
     */
    @Inject
    private DiaLogic dLogica;
    
    private static final Logger LOGGER = Logger.getLogger(TarjetasUsuarioResource.class.getName());

    /**
     * Busca y devuelve todas las tarjetas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    
    @POST
    public DiaDTO crearAgenda(@PathParam("placa") String pPlaca, DiaDTO dia) throws BusinessLogicException
    {
       try {
            DiaDTO diaDTO = new DiaDTO(dLogica.crearDia(dia.toEntity(), pPlaca));
            return diaDTO;
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso vehiculos/" +pPlaca + "/agenda/" + " ya existe.", 412);
        }

    }
    
    @GET
    public DiaDTO getAgenda( @PathParam("placa") String pPlaca)
    {
        
        try 
        {
            DiaDTO diaDTO = new DiaDTO(dLogica.getDiaPlaca(pPlaca));
            return diaDTO;
        } 
        catch (BusinessLogicException e) 
        {
            throw new WebApplicationException("La agenda asociada al vehiculo con placa" + pPlaca + " no existe.", 404);
        }
    }
    
    
    
    @PUT
    public DiaDTO cambiarAgenda(@PathParam("login") String login, @PathParam("placa") String pPlaca, @PathParam("agenda") Long pId, DiaDTO dia )throws WebApplicationException, BusinessLogicException
    {
       dia.setId(pId);
        if (dLogica.getDiaPlacaVehiculo(pId, pPlaca) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/vehiculos/" + pPlaca + "/agenda/" + pId +   " no existe.", 404);
        }
        DiaDTO dto = new DiaDTO(dLogica.updateDia(dia.toEntity()));
        return dto;
    }
    
    @DELETE
    public void borrarAgenda(@PathParam("login") String login, @PathParam("placa") String pPlaca, @PathParam("agenda") Long pId)throws WebApplicationException, BusinessLogicException
    {
       DiaEntity dia = dLogica.getDiaPlacaVehiculo(pId, pPlaca);
        if(dia == null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/vehiculos/" + pPlaca + " no existe." + "/agenda", 404);
        }
        dLogica.deleteDia(pId);
    }
    
    
}

