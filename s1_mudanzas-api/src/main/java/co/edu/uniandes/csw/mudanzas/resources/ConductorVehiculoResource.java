/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import co.edu.uniandes.csw.mudanzas.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.mudanzas.dtos.VehiculoDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ConductorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
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

/**
 *
 * @author Daniel Machado
 */
public class ConductorVehiculoResource 
{
    private static final Logger LOGGER = Logger.getLogger(TarjetasUsuarioResource.class.getName());
    
    @Inject
    private ConductorLogic conLogic;
    
    @Inject
    private VehiculoLogic vehLogic;

    /**
     * Busca y devuelve todas las tarjetas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<VehiculoDTO> getVehiculosConductor(@PathParam("login") String login, @PathParam("idConductor") Long pId)
    {
        return null;
    }
    
    @POST
    public VehiculoDTO crearVehiculoConductor(@PathParam("login") String login, @PathParam("idConductor") Long pId,@PathParam("vehiculo") VehiculoDTO pVehiculo)
    {
        return null;
    }
    
    @DELETE
    public List<VehiculoDTO> BorrarVehiculosConductor(@PathParam("login") String login, @PathParam("idConductor") Long pId)
    {
        return null;
    }
    
    @GET
    @Path("{placa: \\d+}")
    public VehiculoDTO GetVehiculoPlaca(@PathParam("login") String login, @PathParam("idConductor") ConductorDTO pIdConductor, @PathParam("placa") String pPlaca)
    {
        return null;
    }
    
    @POST
    @Path("{placa: \\d+}")
    public VehiculoDTO CrearVehiculoPlaca(@PathParam("login") String login, @PathParam("idConductor") ConductorDTO pIdConductor, @PathParam("placa") String pPlaca, @PathParam("vehiculo") VehiculoDTO pVehiculo)
    {
        return null;
    }
    
    @PUT
    @Path("{placa: \\d+}")
    public VehiculoDTO BorrarVehiculoPlaca(@PathParam("login") String login, @PathParam("idConductor") ConductorDTO pIdConductor, @PathParam("placa") String pPlaca, @PathParam("vehiculo") VehiculoDTO pVehiculo)
    {
        return null;
    }
    
    @DELETE
    @Path("{placa: \\d+}")
    public VehiculoDTO BorrarVehiculoPlaca(@PathParam("login") String login, @PathParam("idConductor") ConductorDTO pIdConductor, @PathParam("placa") String pPlaca)
    {
        return null;
    }
    
    @Path("{placa}/agenda")
    public Class<VehiculoDiaResource> getProveedorConductorResource(@PathParam("placa") String pPlaca)
    {
        return VehiculoDiaResource.class;
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param vehiculosList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<VehiculoDTO> vehiculosListEntity2DTO(List<VehiculoEntity> vehiculosList) {
        List<VehiculoDTO> lista = new ArrayList<>();
        for (VehiculoEntity entidad : vehiculosList) {
            lista.add(new VehiculoDTO(entidad));
        }
        return lista;
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param conductorList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<ConductorDTO> conductorListEntity2DTO(List<ConductorEntity> conductorList) {
        List<ConductorDTO> lista = new ArrayList<>();
        for (ConductorEntity entidad : conductorList) {
            lista.add(new ConductorDTO(entidad));
        }
        return lista;
    }
    
}
