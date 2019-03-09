/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.DiaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import co.edu.uniandes.csw.mudanzas.ejb.DiaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Samuel Bernal Neira
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VehiculoDiaResource 
{
    @Inject
    private VehiculoLogic vLogica;
    
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
    public DiaDTO crearAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca, DiaDTO dia) throws BusinessLogicException
    {
        DiaEntity dEntity = dia.toEntity();
        dEntity = dLogica.crearDia(dEntity ,pPlaca );
        
        return new DiaDTO(dEntity);
    }
    
    @GET
    public DiaDTO getAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca)
    {
        return null;
    }
    
    
    
    @PUT
    public DiaDTO cambiarAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca, @PathParam("Agenda") DiaDTO pAgenda)
    {
        return null;
    }
    
    @DELETE
    public DiaDTO borrarAgenda(@PathParam("login") String login)
    {
        return null;
    }
    
    
}
