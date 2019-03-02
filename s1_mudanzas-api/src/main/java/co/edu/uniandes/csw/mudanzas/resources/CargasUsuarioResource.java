/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.CargaDTO;
import co.edu.uniandes.csw.mudanzas.ejb.CargaLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "usuarios/{login}/cargas"
 * @author Luis Miguel
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CargasUsuarioResource {
    
    private static final Logger LOGGER = Logger.getLogger(CargasUsuarioResource.class.getName());
        
    /**
     * Atributo que inyecta la logica de la carga en el recurso.
     */
    @Inject
    private CargaLogic cargaLogic;
    
    /**
     * Atributo que inyecta la logica del usuario en el recurso.
     */
    @Inject
    private UsuarioLogic usuarioLogic;
    
    /**
     * Busca y devuelve todas las cargas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link CargaDTO} - Las cargas encontradas en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<CargaDTO> getCargas(@PathParam("login") String login)
    {
       List<CargaDTO> listaCargas = listEntity2DTO(cargaLogic.getCargas());
       return null;
    }
    
    /**
     * Busca la carga con el idCarga asociado dentro del usuario con el login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @param idCarga Identificador de la carga que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CargaDTO} - La Carga buscada
     */
    @GET
    @Path("{idCarga: \\d+}")
    public CargaDTO getCarga(@PathParam("login") String login, @PathParam("idCarga") Long idCarga)
    {
        return null;
    }
    
    /**
     * Guarda una carga dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la carga que se guarda en el usuario.
     *
     * @param login del usuario que se esta
     * actualizando.
     * @param idCarga Identificador de la carga que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CargaDTO} - La carga guardada en el usuario.
     */
    @POST
    @Path("{idCarga: \\d+}")
    public CargaDTO crearCarga(@PathParam("login") String login,@PathParam("idCarga") Long idCarga)
    {
        return null;
    }
    
    /**
     * Remplaza una instancia de Carga asociada a una instancia del Usuario
     *
     * @param login del usuario que se esta
     * remplazando.
     * @param idCarga Identificador de la carga que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CargaDTO} - La Carga Actualizada
     */
    @PUT
    @Path("{idCarga: \\d+}")
    public CargaDTO cambiarCarga(@PathParam("login") String login, @PathParam("idCarga") Long idCarga){
        return null;
    }
    
    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param tarjetasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<CargaDTO> listEntity2DTO(List<CargaEntity> cargasList) {
        List<CargaDTO> lista = new ArrayList<>();
        for (CargaEntity entidad : cargasList) {
            //lista.add(new CargaDTO(entidad));
        }
        return lista;
    }
}
