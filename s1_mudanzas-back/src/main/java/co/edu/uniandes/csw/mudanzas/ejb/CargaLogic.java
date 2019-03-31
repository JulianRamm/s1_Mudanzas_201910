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
    private CargaPersistence cargaPersistence;
    @Inject
    private UsuarioPersistence usuarioPersistence;

    /**
     * método que crea una carga y verifica que se cumplan las reglas de negocio
     *
     * @param carga
     * @param login
     * @return
     * @throws BusinessLogicException
     */
    public CargaEntity createCarga(CargaEntity carga, String login) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuarioPorLogin(login);
        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe ningun usuario \"" + login + "\"");
        }
        //Verificacion de existencia en el usuario
        for (CargaEntity cargaE : usuarioEntity.getCargas()) {
            if (carga.getId() == cargaE.getId()) {
                throw new BusinessLogicException("Ya existe una carga con el id \"" + carga.getId() + "\"");
            }
        }
        carga.setUsuario(usuarioEntity);
        if (carga.getVolumen() <= 0) {
            throw new BusinessLogicException("El volumen no puede ser 0 o menor a cero");
        }
        if (carga.getVolumen() == 1) {
            throw new BusinessLogicException("El volumen no puede ser 1");
        }
        if (carga.getImagenes().equals("") ){
            throw new BusinessLogicException("Las imagenes no puyeden ser vacias");
        }
        if (carga.getLugarLlegada() == null || carga.getLugarLlegada().equals("")) {
            throw new BusinessLogicException("El lugar de llegada no puede ser null");
        }
        if (carga.getLugarSalida() == null || carga.getLugarSalida().equals("")) {
            throw new BusinessLogicException("El lugar de salida no puede ser null");
        }
//        Date a= carga.getFechaEstimadaLlegada();
//        Date b=new Date(carga.getFechaEstimadaLlegada().getYear(),carga.getFechaEstimadaLlegada().getMonth(),carga.getFechaEstimadaLlegada().getDate(),carga.getViaje().getTiempo(),carga.getFechaEstimadaLlegada().getSeconds());
//        Date c=carga.getFechaEnvio();
//        double tiempo=(Math.abs(a.getTime()-b.getTime()))/3.6E6;
//        double tiempo2=(a.getTime()-c.getTime())/3.6E6;
//        if ((tiempo2 >= tiempo - 8 && tiempo2 <= tiempo2 + 8)==false) {
//            throw new BusinessLogicException("La fecha estimada no es acorde al tiempo del envío");
//        }
        if (carga.getFechaEnvio() == null) {
            throw new BusinessLogicException("la fecha de envío no pued e ser null");
        }
        if (carga.getDatosEnvio() == null || carga.getDatosEnvio().equals("")) {
            throw new BusinessLogicException("los datos de envío no puede ser null o vacío");
        }
        usuarioEntity.getCargas().add(carga);
        cargaPersistence.create(carga);
        usuarioPersistence.update(usuarioEntity);
        return carga;
    }

    /**
     * método que devuelve todas las cargas encontradas
     *
     * @return
     */
    public List<CargaEntity> getCargas() {
        List<CargaEntity> cargas = cargaPersistence.findAll();
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
        CargaEntity carga = cargaPersistence.find(id);
        if (carga == null) {
            throw new BusinessLogicException("No existe una carga con id: " + id);
        }
        return carga;
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
        List<CargaEntity> cargas = usuarioPersistence.findUsuarioPorLogin(login).getCargas();
        CargaEntity carga = cargaPersistence.find(idCarga);
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
    public List<CargaEntity> getCargasUsuario(String login) throws BusinessLogicException {
        List<CargaEntity> cargas = cargaPersistence.getCargasUsuario(login);
        if (cargas == null) {
            throw new BusinessLogicException("No hay cargas para este usuario con login: " + login);
        }
        return cargas;
    }

    /**
     * métodoq que actualiza una carga
     *
     * @param cargaEntity
     * @return
     */
    public CargaEntity updateCarga(CargaEntity cargaEntity) {
        CargaEntity carga = cargaPersistence.update(cargaEntity);
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
        cargaPersistence.delete(id);
        usuarioPersistence.update(pertenece);
    }
    
    public void deleteCarga(Long id) {
        cargaPersistence.delete(id);
    }

}
