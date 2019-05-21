/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ProveedorDTO;
import co.edu.uniandes.csw.mudanzas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.mudanzas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 * @author Daniel Machado
 */
@Path("proveedores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProveedorResource 
{
    
    @Inject
    private ProveedorLogic proveedorLogic;
        
        /**
     * Crea un nuevo Proveedor y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param proveedor {@link proveedorDTO} - El proveedor a
     * guardar.
     * @return JSON {@link ProveedorDTO} - El proveedor guradado con el atributo
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public ProveedorDTO crearProveedor(ProveedorDTO proveedor) throws BusinessLogicException{
        ProveedorEntity entidad = proveedor.toEntity();
        ProveedorEntity nuevaEntidad = proveedorLogic.createProveedor(entidad);
        ProveedorDTO nuevoDTO = new ProveedorDetailDTO(nuevaEntidad);
        return nuevoDTO;
    }
    
    
    /**
     * Busca y devuelve todos los proveedores que existen en la aplicacion.
     *
     * @return JSONArray {@link ProveedoreDTO} - Los proveedores encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET 

    public List<ProveedorDTO> getProveedores(){
        List<ProveedorDTO> listaUsuarios = listEntity2DetailDTO(proveedorLogic.getProveedores());
        return listaUsuarios;
    }
    
     /**
     * Busca el proveedor asociado recibido en la URL y lo devuelve.
     *
     * @param login del proveedor que se esta buscando.
     * @return JSON {@link ProveedorDTO} - El proveedor buscado.
     */
    @GET
    @Path("{login}")
    public ProveedorDTO getProveedor(@PathParam("login") String login)throws WebApplicationException{
        
        ProveedorDTO detailDTO = null;
        try {
            ProveedorEntity entidad = proveedorLogic.getProveedor(login);
            detailDTO = new ProveedorDetailDTO(entidad);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /usuarios/" + login + " no existe.", 404);
        }
        
        return detailDTO;
    }
    
    /**
     * Actualiza el usuario asociado recibido en la URL y lo cambia...
     *
     * @param login del usuario que se quiere actualizar
     * @param proveedor el proveedor por el que se quiere cambiar
     * @return un detailDTO de ese usuario que se acaba de cambiar
     * @throws BusinessLogicException
     */
    @PUT
    @Path("{login}")
    public ProveedorDTO updateProveedor(@PathParam("login") String login, ProveedorDTO proveedor) throws WebApplicationException, BusinessLogicException {
        proveedor.setLogin(login);
        if (proveedorLogic.getProveedor(login) == null) {
            throw new WebApplicationException("El recurso /proveedores/" + login + " no existe.", 404);
        }
        ProveedorDetailDTO detailDTO = new ProveedorDetailDTO(proveedorLogic.updateProveedor(proveedor.toEntity()));
        return detailDTO;
    }
    
     /**
     * Borra el proveedor asociado recibido en la URL.
     *
     * @param login del proveedor que se desea borrar.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{login}")
    public void deleteProveedor(@PathParam("login") String login) throws BusinessLogicException {
        
        ProveedorEntity proveedor = proveedorLogic.getProveedor(login);
        if (proveedor == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + " no existe.", 404);
        }
        proveedorLogic.deleteUsuario(proveedor.getId());
    }
    
     /**
     * Conexión con el servicio de subastas para un proveedor.
     * {@link SubastasProveedorResource}
     *
     * Este método conecta la ruta de /proveedores con las rutas de /subastas que
     * dependen del proveedor, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las subastas de un proveedor.
     *
     * @param login El login del proveedor con respecto al cual se
     * accede al servicio.
     * @return El servicio de subastas para este proveedor en particular.
     */
    @Path("{login}/subastas")
    public Class<SubastasProveedorResource> getSubastasProveedorResource(@PathParam("login") String login)
    {
        return SubastasProveedorResource.class;
    }
    @Path("{login}/ofertas")
    public Class<OfertaProveedorResource> getOfertaProveedorResource(@PathParam("login") String login)
    {
        return OfertaProveedorResource.class;
    }
    
    @Path("{login}/conductores")
    public Class<ConductorProveedorResource> getProveedorConductorResource(@PathParam("login") String login)
    {
        return ConductorProveedorResource.class;
    }
    
    /**
     * Conexión con el servicio de vehiculos para un proveedor.
     * {@link VehiculosProveedorResource}
     *
     * Este método conecta la ruta de /proveedores con las rutas de /vehiculos que
     * dependen del proveedor, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las subastas de un proveedor.
     *
     * @param login El login del proveedor con respecto al cual se
     * accede al servicio.
     * @return El servicio de subastas para este proveedor en particular.
     */
    @Path("{login}/vehiculos")
    public Class<VehiculosProveedorResource> getVehiculosProveedorResource(@PathParam("login") String login)
    {
        return VehiculosProveedorResource.class;
    }
    
    public List<ProveedorDTO> listEntity2DetailDTO(List<ProveedorEntity> proveedorList) {
        List<ProveedorDTO> lista = new ArrayList<>();
        for (ProveedorEntity entidad : proveedorList) {
            lista.add(new ProveedorDetailDTO(entidad));
        }
        return lista;
    }
    
}
