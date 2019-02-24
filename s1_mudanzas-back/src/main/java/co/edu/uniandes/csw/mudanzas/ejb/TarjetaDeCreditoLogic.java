/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Luis Miguel
 */
@Stateless
public class TarjetaDeCreditoLogic {

    /**
     * Variable para acceder a la persistencia de la tarjeta.
     */
    @Inject
    private TarjetaDeCreditoPersistence persistence;

    /**
     * Crea una tarjeta en la persistencia.
     *
     * @param tarjeta La entidad que representa la tarjeta a persistir.
     * @return La entiddad de la tarjeta luego de persistirla.
     * @throws BusinessLogicException Si la tarjeta a persistir ya existe.
     */
    public TarjetaDeCreditoEntity crearTarjeta(TarjetaDeCreditoEntity tarjeta) throws BusinessLogicException {

        if (persistence.find(tarjeta.getId()) != null) {
            throw new BusinessLogicException("Ya existe un tarjeta con el id \"" + tarjeta.getId() + "\"");
        }
        tarjeta = persistence.create(tarjeta);
        return tarjeta;
    }

    /**
     * Obtener todas las tarjetas existentes en la base de datos.
     *
     * @return una lista de tarjetas.
     */
    public List<TarjetaDeCreditoEntity> getTarjetas() {
        List<TarjetaDeCreditoEntity> tarjetas = persistence.findAll();
        return tarjetas;
    }

    /**
     * Obtener un tarjeta por medio de su id.
     *
     * @param tarjetaId: id de la tarjeta para ser buscado.
     * @return la tarjeta solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public TarjetaDeCreditoEntity getTarjeta(Long tarjetaId) throws BusinessLogicException {
        TarjetaDeCreditoEntity tarjetaEntity = persistence.find(tarjetaId);
        if (tarjetaEntity == null) {
            throw new BusinessLogicException("No existe tal tarjeta con id: " + tarjetaId);
        }
        return tarjetaEntity;
    }

    /**
     * Obtener un tarjeta por medio de su login.
     *
     * @param usuarioTitular: nombre del propietario de la tarjeta para ser
     * buscado.
     * @return la tarjeta solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public TarjetaDeCreditoEntity getTarjeta(String usuarioTitular) throws BusinessLogicException {
        TarjetaDeCreditoEntity usuarioEntity = persistence.findTarjetaPorPropietario(usuarioTitular);
        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe tal tarjeta con propietario de nombre: " + usuarioTitular);
        }
        return usuarioEntity;
    }

    /**
     * Actualizar un tarjeta.
     *
     * @param nuevaTarjeta: tarjeta con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return la tarjeta con los cambios actualizados en la base de datos.
     */
    public TarjetaDeCreditoEntity updateTarjeta(TarjetaDeCreditoEntity nuevaTarjeta) {
        TarjetaDeCreditoEntity tarjetaEntity = persistence.update(nuevaTarjeta);
        return tarjetaEntity;
    }

    /**
     * Borrar una tarjeta
     *
     * @param tarjetaId: id del tarjeta a borrar
     * @throws BusinessLogicException Si el tarjeta a eliminar tiene tarjetas de
     * credito.
     */
    public void deleteTarjeta(Long tarjetaId) throws BusinessLogicException {
        persistence.delete(tarjetaId);
    }
}
