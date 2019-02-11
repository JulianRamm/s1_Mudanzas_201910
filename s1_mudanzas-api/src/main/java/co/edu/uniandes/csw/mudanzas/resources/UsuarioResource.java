/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.CargaDTO;
import co.edu.uniandes.csw.mudanzas.dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Luis Miguel
 */

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UsuarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    
    /**
     * Crea un nuevo Usuario y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param usuario {@link UsuarioDTO} - El usuario a
     * guardar.
     * @return JSON {@link UsuarioDTO} - El usuario guradado con el atributo
     */
    @POST
    public UsuarioDTO crearUsuario(UsuarioDTO usuario) {
        return usuario;
    }
    
    /**
     * Busca y devuelve todos los usuarios que existen en la aplicacion.
     *
     * @return JSONArray {@link UsuarioDTO} - Los usuarios encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET 
    public List<UsuarioDTO> getUsuarios(){
        List<UsuarioDTO> users = new ArrayList<>();
        return users;
    }
    
    /**
     * Busca el usuario asociado recibido en la URL y lo devuelve.
     *
     * @param login del usuario que se esta buscando.
     * @return JSON {@link UsuarioDTO} - El usuario buscado.
     */
    @GET
    @Path("{login}")
    public UsuarioDTO getUsuario(@PathParam("login") String login){
        return null;
    }
    
    /**
     * Borra el usuario asociado recibido en la URL.
     *
     * @param login del usuario que se desea borrar.
     */
    @DELETE
    @Path("{login}")
    public void deleteUsuario(@PathParam("login") String login) {
        
    }
    
    /**
     * Conexión con el servicio de tarjetas de credito para un usuario.
     * {@link TarjetasUsuarioResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /tarjetas que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las tarjetas de un usuario.
     *
     * @param login El login del usuario con respecto al cual se
     * accede al servicio.
     * @return El servicio de tarjetas para este usuario en particular.
     */
    @Path("{login}/tarjetas")
    public Class<TarjetasUsuarioResource> getTarjetasUsuarioResource(@PathParam("login") String login)
    {
        return TarjetasUsuarioResource.class;
    }

    /**
     * Conexión con el servicio de subastas para un usuario.
     * {@link SubastasUsuarioResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /subastas que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las subastas de un usuario.
     *
     * @param login El login del usuario con respecto al cual se
     * accede al servicio.
     * @return El servicio de subastas para este usuario en particular.
     */
    @Path("{login}/subastas")
    public Class<SubastasUsuarioResource> getSubastasUsuarioResource(@PathParam("login") String login)
    {
        return SubastasUsuarioResource.class;
    }
    
    /**
     * Conexión con el servicio de cargas para un usuario.
     * {@link CargasUsuarioResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /cargas que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las cargas de un usuario.
     *
     * @param login El login del usuario con respecto al cual se
     * accede al servicio.
     * @return El servicio de cargas para este usuario en particular.
     */
    @Path("{login}/cargas")
    public Class<CargasUsuarioResource> getCargasUsuarioResource(@PathParam("login") String login)
    {
        return CargasUsuarioResource.class;
    }
    
}
