/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
    private TarjetaDeCreditoPersistence tarjetaPersistence;

    /**
     * Atributo que inyecta la persistencia del usuario en la logica.
     */
    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     * Crea una tarjeta en la persistencia.
     *
     * @param tarjeta La entidad que representa la tarjeta a persistir.
     * @param username
     * @return La entiddad de la tarjeta luego de persistirla.
     * @throws BusinessLogicException Si la tarjeta a persistir ya existe.
     */public TarjetaDeCreditoEntity crearTarjeta(TarjetaDeCreditoEntity tarjeta, String username) throws BusinessLogicException {

        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuarioPorLogin(username);

        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe ningun usuario \"" + username + "\"");
        }

        //Verificacion de existencia
        for (TarjetaDeCreditoEntity tarjetaE : usuarioEntity.getTarjetas()) {
            if (tarjeta.getId() == tarjetaE.getId()) {
                throw new BusinessLogicException("Ya existe un tarjeta con el id \"" + tarjeta.getId() + "\"");
            }
        }
        tarjeta.setUsuario(usuarioEntity);
        //Verificacion de "nulidad"
        if (tarjeta.getNombreTarjeta() == null
                || tarjeta.getNumeroSerial() == null) {
            throw new BusinessLogicException("Los campos no pueden ser nulos");
        }

        //Verificacion de formato para el nombre de la tarjeta y del propietario
        if (!tarjeta.getNombreTarjeta().matches("([a-zA-Z ]+){2,}")) {
            throw new BusinessLogicException("El nombre de la tarjeta o del propietario solo puede contener letras");
        }
        String codigoS = tarjeta.getCodigoSeguridad() + "";
        String serial = tarjeta.getNumeroSerial();
        if (!codigoS.matches("[0-9]{1,3}+")
                || !serial.matches("[0-9]{12,19}+")) {
            throw new BusinessLogicException("Los digitos de la tarjeta o cs no son validos");
        }
        //verificacion de fecha de expedicion
        Date fechaV = tarjeta.getFechaVencimiento();
        LocalDate vencimiento = fechaV.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthV = vencimiento.getMonthValue();
        int yearV = vencimiento.getYear();

        Date hoy = new Date();
        LocalDate localDate = hoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int esteMes = localDate.getMonthValue();
        int esteAnio = localDate.getYear();
        if (esteMes > monthV && esteAnio > yearV) {
            throw new BusinessLogicException("Esta tarjeta de credito ha expedido: Mes de ven: " + monthV + " Año de ven: " + yearV + " este mes: " + esteMes + " este año: " + esteAnio);
        }
        usuarioEntity.getTarjetas().add(tarjeta);
        tarjetaPersistence.create(tarjeta);
        usuarioPersistence.update(usuarioEntity);
        return tarjeta;
    }
    

    /**
     * Obtener todas las tarjetas existentes en la base de datos.
     *
     * @return una lista de tarjetas.
     */
    public List<TarjetaDeCreditoEntity> getTarjetas() {
        List<TarjetaDeCreditoEntity> tarjetas = tarjetaPersistence.findAll();
        return tarjetas;
    }

    /**
     * Obtener todas las tarjetas existentes en la base de datos que le
     * pertencen a un usuario en especifico.
     *
     * @return una lista de tarjetas de ese usuario.
     */
    public List<TarjetaDeCreditoEntity> getTarjetasUsuario(String login) {
        List<TarjetaDeCreditoEntity> tarjetas = usuarioPersistence.findUsuarioPorLogin(login).getTarjetas();
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
        TarjetaDeCreditoEntity tarjetaEntity = tarjetaPersistence.find(tarjetaId);
        if (tarjetaEntity == null) {
            throw new BusinessLogicException("No existe tal tarjeta con id: " + tarjetaId);
        }
        return tarjetaEntity;
    }

    /**
     * Obtener un tarjeta por medio de su login.
     *
     * @param login: nombre del propietario de la tarjeta para ser buscado.
     * @return la tarjeta solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public TarjetaDeCreditoEntity getTarjeta(String login, Long idTarjeta) throws BusinessLogicException {
        List<TarjetaDeCreditoEntity> tarjetas = usuarioPersistence.findUsuarioPorLogin(login).getTarjetas();
        TarjetaDeCreditoEntity tarjeta = tarjetaPersistence.find(idTarjeta);
        int index = tarjetas.indexOf(tarjeta);
        if (index >= 0) {
            return tarjetas.get(index);
        }
        throw new BusinessLogicException("No existe tal tarjeta con propietario de login: " + login);
    }

    /**
     * Actualizar un tarjeta.
     *
     * @param nuevaTarjeta: tarjeta con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return la tarjeta con los cambios actualizados en la base de datos.
     */
    public TarjetaDeCreditoEntity updateTarjeta(TarjetaDeCreditoEntity nuevaTarjeta) {
        UsuarioEntity usuario = usuarioPersistence.findUsuarioPorLogin(nuevaTarjeta.getUsuario().getLogin());
        for(TarjetaDeCreditoEntity tc : usuario.getTarjetas())
        {
            if(tc.getId() == nuevaTarjeta.getId())
            {
                usuario.getTarjetas().remove(tc);
                usuario.getTarjetas().add(nuevaTarjeta);
                break;
            }
        }
        usuarioPersistence.update(usuario);
        tarjetaPersistence.update(nuevaTarjeta);
        return nuevaTarjeta;
    }

    /**
     * Borrar una tarjeta
     *
     * @param login
     * @param tarjetaId: id del tarjeta a borrar
     * @throws BusinessLogicException Si el tarjeta a eliminar tiene tarjetas de
     * credito.
     */
    public void deleteTarjeta(String login, Long tarjetaId) throws BusinessLogicException {
        TarjetaDeCreditoEntity trj = getTarjeta(login, tarjetaId);
        UsuarioEntity pertenece = trj.getUsuario();
        pertenece.getTarjetas().remove(trj);
        tarjetaPersistence.delete(tarjetaId);
        usuarioPersistence.update(pertenece);
    }
}
