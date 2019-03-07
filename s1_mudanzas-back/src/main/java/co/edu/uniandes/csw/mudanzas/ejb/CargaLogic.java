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
     * @param cargaEntity
     * @param login
     * @return
     * @throws BusinessLogicException 
     */
    public CargaEntity createCarga(CargaEntity cargaEntity, String login) throws BusinessLogicException{
        UsuarioEntity usuarioEntity = usuarioPer.findUsuarioPorLogin(login);
        
        if(cargaEntity.getVolumen()<=0){
            throw new BusinessLogicException("El volumen no puede ser 0 o menor a cero");
        }
        if(cargaEntity.getVolumen()==1){
            throw new BusinessLogicException("El volumen no puede ser 1");
        }
        if(cargaEntity.getImagenes().isEmpty()){
             throw new BusinessLogicException("Las imagenes no puyeden ser vacias");
        }
        if(cargaEntity.getLugarLlegada()==null||cargaEntity.getLugarLlegada().equals("")){
            throw new BusinessLogicException("El lugar de llegada no puede ser null");
        }
        if(cargaEntity.getLugarSalida()==null||cargaEntity.getLugarSalida().equals("")){
            throw new BusinessLogicException("El lugar de salida no puede ser null");
        }
        /**
        Date tiempo= cargaEntity.getFechaEnvio().plusHours(cargaEntity.getViaje().getTiempo());
        if(!(cargaEntity.getFechaEstimadaLlegada().isAfter(tiempo.minusHours(8))&&cargaEntity.getFechaEstimadaLlegada().isBefore(tiempo.plusHours(8)))){
            throw new BusinessLogicException("La fecha estimada no es acorde al tiempo del envío");
        }
        */
        if(cargaEntity.getFechaEnvio()==null){
            throw new BusinessLogicException("la fecha de envío no pued e ser null");
        }
        if(cargaEntity.getDatosEnvio()==null||cargaEntity.getDatosEnvio().equals("")){
            throw new BusinessLogicException("los datos de envío no puede ser null o vacío");
        }
        cargaEntity.setUsuario(usuarioEntity);
        usuarioEntity.getCargas().add(cargaEntity);
        persistence.create(cargaEntity);
        return cargaEntity;
    }
    /**
     * método que devuelve todas las cargas encontradas
     * @return 
     */
    public List<CargaEntity> getCargas(){
        List<CargaEntity> cargas=persistence.findAll();
        return cargas;
    }
    /**
     * método que retorna una carga dado su id
     * @param id
     * @return 
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public CargaEntity getCarga(long id) throws BusinessLogicException{
        CargaEntity carga= persistence.find(id);
        if(carga==null){
            throw new BusinessLogicException("No existe una carga con id: " + id);
        }
        return carga;
    }
    
    /**
     * métodoq que actualiza una carga
     * @param cargaEntity
     * @return 
     */
    public CargaEntity updateCarga(CargaEntity cargaEntity){
        CargaEntity carga= persistence.update(cargaEntity);
        return carga;
    }
    /**
     * método que elimina una carga
     * @param id
     */
    public void deleteCarga(Long id){
        persistence.delete(id);
    }
    /**
     * retorna las cargas de un usuario
     * @param login
     * @param id
     * @return
     * @throws BusinessLogicException 
     */
    public CargaEntity getCargaUsuario(String login, Long id) throws BusinessLogicException{
        CargaEntity carga=persistence.findCargaPorLoginPropietario(login, id);
        if(carga==null){
            throw new BusinessLogicException("No existe una carga con ese login");
        }
        return carga;
    }
    /**
     * devuelve todas las cargas para un usuario en específico
     * @param login
     * @return
     * @throws BusinessLogicException 
     */
    public List<CargaEntity> getCargas(String login) throws BusinessLogicException{
        List<CargaEntity> cargas=usuarioPer.findUsuarioPorLogin(login).getCargas();
        if(cargas==null){
            throw new BusinessLogicException("No hay cargas para este usuario con login: "+ login);
        }
        return cargas;
    }
}