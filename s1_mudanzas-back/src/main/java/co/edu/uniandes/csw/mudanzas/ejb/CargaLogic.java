/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.CargaPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author je.osorio
 */
@Stateless
public class CargaLogic {

    @Inject
    private CargaPersistence persistence;
    @Inject
    private UsuarioPersistence usuarioPer;

    /**
     * método que crea una carga y verifica que se cumplan las reglas de negocio
     *
     * @param carga
     * @param login
     * @return
     * @throws BusinessLogicException
     */
    public CargaEntity createCarga(CargaEntity carga, String login) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = usuarioPer.findUsuarioPorLogin(login);
        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe ningun usuario \"" + login + "\"");
        }
        //Verificacion de existencia
        for (CargaEntity cargaE : usuarioEntity.getCargas()) {
            if (carga.getId() == cargaE.getId()) {
                throw new BusinessLogicException("Ya existe un tarjeta con el id \"" + carga.getId() + "\"");
            }
        }
        carga.setUsuario(usuarioEntity);
        if (carga.getVolumen() <= 0) {
            throw new BusinessLogicException("El volumen no puede ser 0 o menor a cero");
        }
        if (carga.getVolumen() == 1) {
            throw new BusinessLogicException("El volumen no puede ser 1");
        }
        if (carga.getImagenes().isEmpty()) {
            throw new BusinessLogicException("Las imagenes no puyeden ser vacias");
        }
        if (carga.getLugarLlegada() == null || carga.getLugarLlegada().equals("")) {
            throw new BusinessLogicException("El lugar de llegada no puede ser null");
        }
        if (carga.getLugarSalida() == null || carga.getLugarSalida().equals("")) {
            throw new BusinessLogicException("El lugar de salida no puede ser null");
        }
        Date a= carga.getFechaEstimadaLlegada();
        Date b=new Date(carga.getFechaEstimadaLlegada().getYear(),carga.getFechaEstimadaLlegada().getMonth(),carga.getFechaEstimadaLlegada().getDate(),carga.getViaje().getTiempo(),carga.getFechaEstimadaLlegada().getSeconds());
        Date c=carga.getFechaEnvio();
        double tiempo=(Math.abs(a.getTime()-b.getTime()))/3.6E6;
        double tiempo2=(a.getTime()-c.getTime())/3.6E6;
        if ((tiempo2 >= tiempo - 8 && tiempo2 <= tiempo2 + 8)==false) {
            throw new BusinessLogicException("La fecha estimada no es acorde al tiempo del envío");
        }
        if (carga.getFechaEnvio() == null) {
            throw new BusinessLogicException("la fecha de envío no pued e ser null");
        }
        if (carga.getDatosEnvio() == null || carga.getDatosEnvio().equals("")) {
            throw new BusinessLogicException("los datos de envío no puede ser null o vacío");
        }
        usuarioEntity.getCargas().add(carga);
        persistence.create(carga);
        usuarioPer.update(usuarioEntity);
        return carga;
    }

    /**
     * método que devuelve todas las cargas encontradas
     *
     * @return
     */
    public List<CargaEntity> getCargas() {
        List<CargaEntity> cargas = persistence.findAll();
        return cargas;
    }
    
    /**
     * Obtener todas las tarjetas existentes en la base de datos que le
     * pertencen a un usuario en especifico.
     *
     * @return una lista de tarjetas de ese usuario.
     */
    public List<CargaEntity> getCargasUsuario(String login) {
        List<CargaEntity> cargas = usuarioPer.findUsuarioPorLogin(login).getCargas();
        return cargas;
    }

    /**
     * método que retorna una carga dado su id
     *
     * @param id
     * @return
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public CargaEntity getCarga(long id) throws BusinessLogicException {
        CargaEntity carga = persistence.find(id);
        if (carga == null) {
            throw new BusinessLogicException("No existe una carga con id: " + id);
        }
        return carga;
    }

    /**
     * métodoq que actualiza una carga
     *
     * @param cargaEntity
     * @return
     */
    public CargaEntity updateCarga(CargaEntity cargaEntity) {
        CargaEntity carga = persistence.update(cargaEntity);
        return carga;
    }

    /**
     * método que elimina una carga
     *
     * @param id
     */
    public void deleteCarga(String login, Long id) throws BusinessLogicException {
        CargaEntity crg = getCarga(login, id);
        UsuarioEntity pertenece = crg.getUsuario();
        pertenece.getCargas().remove(crg);
        persistence.delete(id);
        usuarioPer.update(pertenece);
    }
    
    public void deleteCarga(Long id) {
        persistence.delete(id);
    }

    /**
     * retorna las cargas de un usuario
     *
     * @param login
     * @param idCarga
     * @return
     * @throws BusinessLogicException
     */
    public CargaEntity getCarga(String login, Long idCarga) throws BusinessLogicException {
        List<CargaEntity> cargas = usuarioPer.findUsuarioPorLogin(login).getCargas();
        CargaEntity carga = persistence.find(idCarga);
        int index = cargas.indexOf(carga);
        if (index >= 0) {
            return cargas.get(index);
        }
        throw new BusinessLogicException("No existe tal carga con propietario de login: " + login);
    }

    /**
     * devuelve todas las cargas para un usuario en específico
     *
     * @param login
     * @return
     * @throws BusinessLogicException
     */
    public List<CargaEntity> getCargas(String login) throws BusinessLogicException {
        List<CargaEntity> cargas = usuarioPer.findUsuarioPorLogin(login).getCargas();
        if (cargas == null) {
            throw new BusinessLogicException("No hay cargas para este usuario con login: " + login);
        }
        return cargas;
    }
}
