/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.AgendaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 *
 * @author estudiante
 */
public class VehiculoAgendaResource 
{
    private static final Logger LOGGER = Logger.getLogger(TarjetasUsuarioResource.class.getName());

    /**
     * Busca y devuelve todas las tarjetas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public AgendaDTO getAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca)
    {
        return null;
    }
    
    @POST
    public AgendaDTO crearAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca)
    {
        return null;
    }
    
    @PUT
    public AgendaDTO cambiarAgenda(@PathParam("login") String login, @PathParam("idConductor") Long pId, @PathParam("placa") String pPlaca, @PathParam("Agenda") AgendaDTO pAgenda)
    {
        return null;
    }
    
    @DELETE
    public AgendaDTO borrarAgenda(@PathParam("login") String login)
    {
        return null;
    }
    
    
}
