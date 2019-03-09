
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.UsuarioDTO;
import co.edu.uniandes.csw.mudanzas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
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

    /**
     * Atributo que inyecta la logica del usuario en el recurso.
     */
    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Crea un nuevo Usuario y se regresa un objeto identico con un id
     * auto-generado por la base de datos.
     *
     * @param usuario {@link UsuarioDTO} - El usuario a guardar.
     * @return JSON {@link UsuarioDTO} - El usuario guradado con el atributo
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public UsuarioDTO crearUsuario(UsuarioDTO usuario) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.crearUsuario(usuarioEntity);
        UsuarioDTO nuevoDTO = new UsuarioDetailDTO(nuevoUsuarioEntity);
        return nuevoDTO;
    }

    /**
     * Busca y devuelve todos los usuarios que existen en la aplicacion.
     *
     * @return JSONArray {@link UsuarioDTO} - Los usuarios encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        List<UsuarioDetailDTO> listaUsuarios = listEntity2DetailDTO(usuarioLogic.getUsuarios());
        return listaUsuarios;
    }

    /**
     * Busca el usuario asociado recibido en la URL y lo devuelve.
     *
     * @param login del usuario que se esta buscando.
     * @return JSON {@link UsuarioDTO} - El usuario buscado.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{login}")
    public UsuarioDetailDTO getUsuario(@PathParam("login") String login) throws WebApplicationException, BusinessLogicException {
        UsuarioEntity usuarioEntity = usuarioLogic.getUsuario(login);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioEntity);
        return detailDTO;
    }

    /**
     * Actualiza el usuario asociado recibido en la URL y lo cambia...
     *
     * @param login del usuario que se quiere actualizar
     * @param usuario el usuario por el que se quiere cambiar
     * @return un detailDTO de ese usuario que se acaba de cambiar
     * @throws BusinessLogicException
     */
    @PUT
    @Path("{login}")
    public UsuarioDetailDTO updateUsuario(@PathParam("login") String login, UsuarioDetailDTO usuario) throws WebApplicationException, BusinessLogicException {
        usuario.setLogin(login);
        if (usuarioLogic.getUsuario(login) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuario.toEntity()));
        return detailDTO;
    }

    /**
     * Borra el usuario asociado recibido en la URL.
     *
     * @param login del usuario que se desea borrar.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{login}")
    public void deleteUsuario(@PathParam("login") String login) throws WebApplicationException, BusinessLogicException {
        UsuarioEntity usr = usuarioLogic.getUsuario(login);
        if (usr == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + " no existe.", 404);
        }
        usuarioLogic.deleteUsuario(usr.getId());
    }

    /**
     * Conexión con el servicio de tarjetas de credito para un usuario.
     * {@link TarjetasUsuarioResource}
     *
     * Este método conecta la ruta de /usuarios con las rutas de /tarjetas que
     * dependen del usuario, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las tarjetas de un usuario.
     *
     * @param login El login del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de tarjetas para este usuario en particular.
     */
    @Path("{login}/tarjetas")
    public Class<TarjetasUsuarioResource> getTarjetasUsuarioResource(@PathParam("login") String login) {
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
     * @param login El login del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de subastas para este usuario en particular.
     */
    @Path("{login}/subastas")
    public Class<SubastasUsuarioResource> getSubastasUsuarioResource(@PathParam("login") String login) {
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
     * @param login El login del usuario con respecto al cual se accede al
     * servicio.
     * @return El servicio de cargas para este usuario en particular.
     */
    @Path("{login}/cargas")
    public Class<CargasUsuarioResource> getCargasUsuarioResource(@PathParam("login") String login) {
        return CargasUsuarioResource.class;
    }

    public List<UsuarioDetailDTO> listEntity2DetailDTO(List<UsuarioEntity> usuariosList) {
        List<UsuarioDetailDTO> lista = new ArrayList<>();
        for (UsuarioEntity entidad : usuariosList) {
            lista.add(new UsuarioDetailDTO(entidad));
        }
        return lista;
    }
}
