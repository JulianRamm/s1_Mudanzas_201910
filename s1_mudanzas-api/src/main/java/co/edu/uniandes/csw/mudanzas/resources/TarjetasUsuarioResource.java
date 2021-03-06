/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.mudanzas.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "usuarios/{login}/tarjetas"
 *
 * @author Luis Miguel
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TarjetasUsuarioResource {

    /**
     * Atributo que inyecta la logica de la tarjeta en el recurso.
     */
    @Inject
    private TarjetaDeCreditoLogic tarjetaLogic;

    /**
     * Atributo que inyecta la logica del usuario en el recurso.
     */
    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Busca y devuelve todas las tarjetas que existen en el usuario.
     *
     * @param login del usuario que se esta buscando.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas
     * en el usuario. Si no hay ninguno retorna una lista vacía.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @GET
    public List<TarjetaDeCreditoDTO> getTarjetas(@PathParam("login") String login) throws BusinessLogicException {
        List<TarjetaDeCreditoDTO> listaTarjetas = listEntity2DTO(tarjetaLogic.getTarjetasUsuario(login));
        return listaTarjetas;
    }

    /**
     * Busca la tarjeta con el idTarjeta asociado dentro del usuario con el
     * login asociado.
     *
     * @param login del usuario que se esta buscando.
     * @param idTarjeta Identificador de la tarjeta que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La Tarjeta buscada
     */
    @GET
    @Path("{idTarjeta: \\d+}")
    public TarjetaDeCreditoDTO getTarjeta(@PathParam("login") String login, @PathParam("idTarjeta") Long idTarjeta) throws WebApplicationException {
        try {
            TarjetaDeCreditoDTO tarjetaDTO = new TarjetaDeCreditoDTO(tarjetaLogic.getTarjeta(login, idTarjeta));
            return tarjetaDTO;
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + idTarjeta + " no existe.", 404);
        }
    }

    /**
     * Guarda una tarjeta dentro de un usuario con la informacion que recibe el
     * la URL. Se devuelve la tarjeta que se guarda en el usuario.
     *
     * @param login del usuario que se esta actualizando.
     * @param tarjeta {@link TarjetaDeCreditoDTO}la tarjeta que se desea
     * guardar. Este debe ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La tarjeta guardada en el
     * usuario.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @POST
    public TarjetaDeCreditoDTO crearTarjeta(@PathParam("login") String login, TarjetaDeCreditoDTO tarjeta) throws WebApplicationException {
        try {
            TarjetaDeCreditoDTO tarjetaDTO = new TarjetaDeCreditoDTO(tarjetaLogic.crearTarjeta(tarjeta.toEntity(), login));
            return tarjetaDTO;
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + tarjeta.getId() + " ya existe.", 412);
        }
    }

    /**
     * Remplaza una instancia de Tarjeta de Credito asociada a una instancia del
     * Usuario
     *
     * @param login del usuario que se esta remplazando.
     * @param idTarjeta Identificador de la tarjeta que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La Tarjeta de Credito
     * Actualizada
     */
    @PUT
    @Path("{idTarjeta: \\d+}")
    public TarjetaDeCreditoDTO cambiarTarjeta(@PathParam("login") String login, @PathParam("idTarjeta") Long idTarjeta, TarjetaDeCreditoDTO tarjeta) throws WebApplicationException, BusinessLogicException {
        tarjeta.setId(idTarjeta);
        if (tarjetaLogic.getTarjeta(login, idTarjeta) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + idTarjeta + " no existe.", 404);
        }
        TarjetaDeCreditoDTO dto = new TarjetaDeCreditoDTO(tarjetaLogic.updateTarjeta(tarjeta.toEntity()));
        return dto;
    }
    
    @DELETE
    @Path("{idTarjeta: \\d+}")
    public void borrarTarjeta(@PathParam("login") String login, @PathParam("idTarjeta") Long idTarjeta) throws BusinessLogicException, WebApplicationException {
        TarjetaDeCreditoEntity tarj = tarjetaLogic.getTarjeta(login, idTarjeta);
        if(tarj == null) {
            throw new WebApplicationException("El recurso /usuarios/" + login + "/tarjetas/" + idTarjeta + " no existe.", 404);
        }
        tarjetaLogic.deleteTarjeta(login, idTarjeta);
    }

    /**
     * Convierte una lista de entidades en lista de DTOs
     *
     * @param tarjetasList la lista de entidades a convertir
     * @return una lista de dtos.
     */
    public List<TarjetaDeCreditoDTO> listEntity2DTO(List<TarjetaDeCreditoEntity> tarjetasList) {
        List<TarjetaDeCreditoDTO> lista = new ArrayList<>();
        for (TarjetaDeCreditoEntity entidad : tarjetasList) {
            lista.add(new TarjetaDeCreditoDTO(entidad));
        }
        return lista;
    }

}
