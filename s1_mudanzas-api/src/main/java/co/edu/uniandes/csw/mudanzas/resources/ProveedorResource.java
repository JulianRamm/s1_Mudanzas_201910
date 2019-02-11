/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.ProveedorDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Daniel Machado
 */
@Path("proveedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProveedorResource 
{
    
    @Path("{login}/conductores")
    public Class<ProveedorConductorResource> getProveedorConductorResource(@PathParam("login") String login)
    {
        return ProveedorConductorResource.class;
    }
    
    private static final Logger LOGGER = Logger.getLogger(ProveedorResource.class.getName());
    
        /**
     * Crea un nuevo Proveedor y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param proveedor {@link proveedorDTO} - El proveedor a
     * guardar.
     * @return JSON {@link ProveedorDTO} - El proveedor guradado con el atributo
     */
    @POST
    public ProveedorDTO crearProveedor(ProveedorDTO proveedor){
        return proveedor;
    }
    
    
    /**
     * Busca y devuelve todos los proveedores que existen en la aplicacion.
     *
     * @return JSONArray {@link ProveedoreDTO} - Los proveedores encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET 
    public List<ProveedorDTO> getProveedores(){
        return null;
    }
    
        /**
     * Busca el proveedor asociado recibido en la URL y lo devuelve.
     *
     * @param login del proveedor que se esta buscando.
     * @return JSON {@link ProveedorDTO} - El usuario buscado.
     */
    @GET
    @Path("{login}")
    public ProveedorDTO getProveedor(@PathParam("login") String login){
        return null;
    }
    
        /**
     * Borra el proveedor asociado recibido en la URL.
     *
     * @param login del proveedor que se desea borrar.
     */
    @DELETE
    @Path("{login}")
    public void deleteProveedor(@PathParam("login") String login) {
        
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
    public Class<SubastasProveedorResource> getSubastasUsuarioResource(@PathParam("login") String login)
    {
        return SubastasProveedorResource.class;
    }
    
    
}
