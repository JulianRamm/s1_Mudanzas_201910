/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import co.edu.uniandes.csw.mudanzas.dtos.TarjetaDeCreditoDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author estudiante
 */
public class ProveedorConductorResource 
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
    public List<ConductorDTO> getConductores(@PathParam("login") String login)
    {
        return null;
    }
    
    @POST
    public List<ConductorDTO> crearConductor(@PathParam("login") String login, @PathParam("conductor") ConductorDTO pConductor)
    {
        return null;
    }
    
    @GET
    @Path("{idConductor: \\d+}")
    public List<ConductorDTO> darConductorConId(@PathParam("login") String login, @PathParam("idConductor") Long pIdConductor)
    {
        return null;
    }
    
    @POST
    @Path("{idConductor: \\d+}")
    public List<ConductorDTO> CrearConductorConId(@PathParam("login") String login, @PathParam("Conductor") ConductorDTO pConductor, @PathParam("idConductor") Long pIdConductor)
    {
        return null;
    }
    
    @PUT
    @Path("{idConductor: \\d+}")
    public ConductorDTO CambiarConductorConId(@PathParam("login") String login, @PathParam("Conductor") ConductorDTO pConductor, @PathParam("idConductor") Long pIdConductor)
    {
        return null;
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
}
