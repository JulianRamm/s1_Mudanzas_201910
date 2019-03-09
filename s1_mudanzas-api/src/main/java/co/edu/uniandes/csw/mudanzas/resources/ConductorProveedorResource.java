/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ConductorDetailDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ConductorDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ConductorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
 * @author Daniel MAchado
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConductorProveedorResource 
{
    
     private static final Logger LOGGER = Logger.getLogger(TarjetasUsuarioResource.class.getName());
     
     /**
     * Atributo que inyecta la logica de la conductor en el recurso.
     */
    @Inject
    private ConductorLogic conductorLogic;

    /**
     * Atributo que inyecta la logica del proveedor en el recurso.
     */
    @Inject
    private ProveedorLogic proveedorLogic;

    /**
     * Busca y devuelve todas las conductores que existen en el proveedor.
     *
     * @param loginProveedor del proveedor que se esta buscando.
     * @return JSONArray {@link ConductorDTO} - Las conductores encontradas
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    public List<ConductorDetailDTO> getConductores(@PathParam("login") String loginProveedor) throws BusinessLogicException {
        List<ConductorDetailDTO> listaConductores = listEntity2DTO(conductorLogic.getConductoresProveedor(loginProveedor));
        return listaConductores;
    }

    /**
     * Busca la conductor con el idConductor asociado dentro del proveedor con el
     * login asociado.
     *
     * @param loginProveedor del proveedor que se esta buscando.
     * @param idConductor Identificador de la conductor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ConductorDTO} - La Tarjeta buscada
     */
    @GET
    @Path("{idConductor: \\d+}")
    public ConductorDTO getConductor(@PathParam("login") String loginProveedor, @PathParam("idConductor") Long idConductor) throws WebApplicationException {
        try {
            ConductorDTO conductorDTO = new ConductorDTO(conductorLogic.getConductorProveedor(loginProveedor, idConductor));
            return conductorDTO;
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + loginProveedor + "/conductores/" + idConductor + " no existe.", 404);
        }
    }

    /**
     * Guarda una conductor dentro de un proveedor con la informacion que recibe el
     * la URL. Se devuelve la conductor que se guarda en el proveedor.
     *
     * @param loginProveedor del proveedor que se esta actualizando.
     * @param conductor {@link ConductorDTO}la conductor que se desea
     * guardar. Este debe ser una cadena de dígitos.
     * @return JSON {@link ConductorDTO} - La conductor guardada en el
     * proveedor.
     */
    @POST
    public ConductorDTO crearConductor(@PathParam("login") String loginProveedor, ConductorDTO conductor) throws WebApplicationException {
        try {
            ConductorDTO conductorDTO = new ConductorDTO(conductorLogic.crearConductor(conductor.toEntity(), loginProveedor));
            return conductorDTO;
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /proveedores/" + loginProveedor + "/conductores/" + conductor.getId() + " ya existe.", 412);
        }
    }

    /**
     * Remplaza una instancia de Tarjeta de Credito asociada a una instancia del
     * Usuario
     *
     * @param loginProveedor del proveedor que se esta remplazando.
     * @param idConductor Identificador de la conductor que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param conductor
     * @return JSON {@link ConductorDTO} - La Tarjeta de Credito
     * Actualizada
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @PUT
    @Path("{idConductor: \\d+}")
    public ConductorDTO cambiarConductor(@PathParam("login") String loginProveedor, @PathParam("idConductor") Long idConductor, ConductorDTO conductor) throws WebApplicationException, BusinessLogicException {
        conductor.setId(idConductor);
        if (conductorLogic.getConductorProveedor(loginProveedor, idConductor) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + loginProveedor + "/conductores/" + idConductor + " no existe.", 404);
        }
        ConductorDTO dto = new ConductorDTO(conductorLogic.updateConductor(conductor.toEntity()));
        return dto;
    }
    
    @DELETE
    @Path("{idConductor: \\d+}")
    public void borrarConductor(@PathParam("login") String loginProveedor, @PathParam("idConductor") Long idConductor) throws BusinessLogicException, WebApplicationException {
        ConductorEntity tarj = conductorLogic.getConductorProveedor(loginProveedor, idConductor);
        if(tarj == null) {
            throw new WebApplicationException("El recurso /proveedores/" + loginProveedor + "/conductores/" + idConductor + " no existe.", 404);
        }
        conductorLogic.deleteConductorProveedor(loginProveedor, idConductor);
    }

    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param conductoresList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<ConductorDetailDTO> listEntity2DTO(List<ConductorEntity> conductoresList) {
        List<ConductorDetailDTO> lista = new ArrayList<>();
        for (ConductorEntity entidad : conductoresList) {
            lista.add(new ConductorDetailDTO(entidad));
        }
        return lista;
    }
}
