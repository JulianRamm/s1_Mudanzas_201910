/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;

import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira 
 */
@Stateless
public class ConductorLogic 
{
    
    
    @Inject
    private ConductorPersistence conductorPer;
    
    @Inject
    private ProveedorPersistence proveedorPer;
    
    public ConductorEntity crearConductor(ConductorEntity conductor, String login)throws BusinessLogicException
    {
        if(conductorPer.find(conductor.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe un conductor con el id: \"" + conductor.getId() + "\"");
        }
        
        if(conductorPer.findByName(conductor.getNombre())!= null)
        {
            throw new BusinessLogicException("Ya existe un conductor con el nombre: \"" + conductor.getNombre() + "\"");
        }
        conductor = conductorPer.create(conductor);
        
        return conductor;
    }
  
    /**
     * Obtener todas los conductores existentes en la base de datos.
     *
     * @return una lista de conductores.
     */
    public List<ConductorEntity> getConductores() {
        List<ConductorEntity> conductores = conductorPer.findAll();
        return conductores;
    }
    
    /**
     * Obtener todas los conductores existentes en la base de datos que le
     * pertencen a un usuario en especifico.
     *
     * @param login del proveedor
     * @return una lista de conductores de ese proveedor.
     */
    public List<ConductorEntity> getConductoresProveedor(String login) {
        List<ConductorEntity> conductores = proveedorPer.findProveedorPorLogin(login).getConductores();
        return conductores;
    }
    
    /**
     * Obtener un Conductor por medio de su id.
     *
     * @param conductorId: id del conductor para ser buscado.
     * @return el conductor solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ConductorEntity getConductor(Long conductorId) throws BusinessLogicException {
        ConductorEntity conductorEntity = conductorPer.find(conductorId);
        if (conductorEntity == null) {
            throw new BusinessLogicException("No existe tal conductor con id: " + conductorId);
        }
        return conductorEntity;
    }
    
    /**
     * Obtener un tarjeta por medio de su login.
     *
     * @param loginProveedor: nombre del propietario de la tarjeta para ser
     * buscado.
     * @param idConductor que estamos buscando
     * @return la tarjeta solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ConductorEntity getConductor(String loginProveedor, Long idConductor) throws BusinessLogicException {
        ConductorEntity usuarioEntity = conductorPer.findConductorPorLoginProveedor(loginProveedor, idConductor);
        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe tal conductor con proveedor de login: " + loginProveedor);
        }
        return usuarioEntity;
    }
    
    /**
     * Actualizar un conductor.
     *
     * @param nuevoConductor: conductor con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el conductor con los cambios actualizados en la base de datos.
     */
    public ConductorEntity updateConductor(ConductorEntity nuevoConductor) {
        ConductorEntity tarjetaEntity = conductorPer.update(nuevoConductor);
        return tarjetaEntity;
    }
    
    /**
     * Borrar un conductor
     *
     * @param conductorId: id del conductor a borrar
     * @throws BusinessLogicException Si el tarjeta a eliminar tiene tarjetas de
     * credito.
     */
    public void deleteConductor(Long conductorId) throws BusinessLogicException {
        conductorPer.delete(conductorId);
    }
       
}
