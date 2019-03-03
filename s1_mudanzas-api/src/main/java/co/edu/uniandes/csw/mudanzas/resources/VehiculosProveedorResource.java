/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.VehiculoDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "proveedores/{login}/vehiculos"
 *
 * @author Luis Miguel
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VehiculosProveedorResource {

    /**
     * Atributo que inyecta la logica del vehiculo en el recurso.
     */
    @Inject
    private VehiculoLogic vehiculoLogic;

    /**
     * Atributo que inyecta la logica del proveedor en el recurso.
     */
    @Inject
    private ProveedorLogic proveedorLogic;

    /**
     * Busca y devuelve todos los vehiculos que existen en el proveedor.
     *
     * @param login del proveedor que se esta buscando.
     * @return JSONArray {@link VehiculoDTO} - Las vehiculos encontradas en el
     * proveedor. Si no hay ninguno retorna una lista vacía.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    public List<VehiculoDTO> getVehiculos(@PathParam("login") String login) throws BusinessLogicException {
        List<VehiculoDTO> listaTarjetas = listEntity2DTO(vehiculoLogic.getVehiculosProveedor(login));
        return listaTarjetas;
    }

    /**
     * Busca el vehiculo con el idVehiculo asociado dentro del proveedor con el
     * login asociado.
     *
     * @param login del priveedor que se esta buscando.
     * @param idVehiculo Identificador del vehiculo que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link VehiculoDTO} - El vehiculo buscado buscada
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{placa}")
    public VehiculoDTO getVehiculo(@PathParam("login") String login, @PathParam("placa") String placa) throws WebApplicationException, BusinessLogicException {
        VehiculoEntity vehiculo = vehiculoLogic.getVehiculoProveedor(login, placa);
        if (vehiculo == null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/vehiculos/" + placa + " no existe.", 404);
        }
        VehiculoDTO vehiculoDTO = new VehiculoDTO(vehiculo);
        return vehiculoDTO;
    }

    /**
     * Guarda una vehiculo dentro de un proveedor con la informacion que recibe
     * el la URL. Se devuelve la vehiculo que se guarda en el proveedor.
     *
     * @param login del proveedor que se esta actualizando.
     * @param vehiculo {@link VehiculoDTO}la vehiculo que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link VehiculoDTO} - La vehiculo guardada en el proveedor.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public VehiculoDTO crearVehiculo(@PathParam("login") String login, VehiculoDTO vehiculo) throws WebApplicationException, BusinessLogicException {
        if (vehiculoLogic.getVehiculoProveedor(login, vehiculo.getPlaca()) != null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + "/vehiculos/" + vehiculo.getPlaca() + " ya existe.", 412);
        }
        VehiculoDTO vehiculoDTO = new VehiculoDTO(vehiculoLogic.crearVehiculo(vehiculo.toEntity(), login));
        return vehiculoDTO;
    }

    /**
     * Remplaza una instancia de Vehiculo asociada a una instancia del Proveedor
     *
     * @param login del priveedor que se esta remplazando.
     * @param placa Identificador de la vehiculo que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link VehiculoDTO} - La Tarjeta de Credito Actualizada
     */
    @PUT
    @Path("{placa}")
    public VehiculoDTO cambiarTarjeta(@PathParam("login") String login, @PathParam("idTarjeta") String placa, VehiculoDTO vehiculo) throws WebApplicationException, BusinessLogicException {
        vehiculo.setPlaca(placa);
        if (vehiculoLogic.getVehiculoProveedor(login, placa) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/vehiculos/" + placa + " no existe.", 404);
        }
        VehiculoDTO dto = new VehiculoDTO(vehiculoLogic.updateVehiculo(vehiculo.toEntity()));
        return dto;
    }

    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param vehiculosList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<VehiculoDTO> listEntity2DTO(List<VehiculoEntity> vehiculosList) {
        List<VehiculoDTO> lista = new ArrayList<>();
        for (VehiculoEntity entidad : vehiculosList) {
            lista.add(new VehiculoDTO(entidad));
        }
        return lista;
    }

}
