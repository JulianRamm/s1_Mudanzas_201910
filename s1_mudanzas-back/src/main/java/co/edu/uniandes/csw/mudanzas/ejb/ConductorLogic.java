/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;

import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.VehiculoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Machado
 */
@Stateless
public class ConductorLogic {

    @Inject
    private ConductorPersistence conductorPersistence;

    @Inject
    private ProveedorPersistence proveedorPersistence;

    @Inject
    private VehiculoPersistence vehiculoPersistence;

    public ConductorEntity crearConductor(ConductorEntity conductor, String loginProveedor) throws BusinessLogicException {
        ProveedorEntity proveedorEntity = proveedorPersistence.findProveedorPorLogin(loginProveedor);

        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe ningun proveedor \"" + loginProveedor + "\"");
        }
        //Verificacion de existencia
        for (ConductorEntity conductorE : proveedorEntity.getConductores()) {
            if (conductor.getId() == conductorE.getId()) {
                throw new BusinessLogicException("Ya existe un conductor con el id \"" + conductor.getId() + "\"");
            }
        }
        conductor.setProveedor(proveedorEntity);
        if (conductorPersistence.find(conductor.getId()) != null) {
            throw new BusinessLogicException("Ya existe un conductor con el id: \"" + conductor.getId() + "\"");
        }
        if (conductorPersistence.findByName(conductor.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un conductor con el nombre: \"" + conductor.getNombre() + "\"");
        }
        if (!conductor.getNombre().matches("([a-zA-Z ]+){2,}")) {
            throw new BusinessLogicException("el nombre ingresado no es valido");
        }
        if (conductor.getTelefono().matches("[0-9]{7,10}+")) {
            throw new BusinessLogicException("el numero telefonico ingresado no es valido");
        }

        proveedorEntity.getConductores().add(conductor);
        conductorPersistence.create(conductor);
        proveedorPersistence.update(proveedorEntity);
        return conductor;
    }

    /**
     * Obtener todas los conductores existentes en la base de datos.
     *
     * @return una lista de conductores.
     */
    public List<ConductorEntity> getConductores() {
        List<ConductorEntity> conductores = conductorPersistence.findAll();
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
        List<ConductorEntity> conductores = proveedorPersistence.findProveedorPorLogin(login).getConductores();
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
        ConductorEntity conductorEntity = conductorPersistence.find(conductorId);
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
    public ConductorEntity getConductorProveedor(String loginProveedor, Long idConductor) throws BusinessLogicException {
        List<ConductorEntity> conductores = proveedorPersistence.findProveedorPorLogin(loginProveedor).getConductores();
        ConductorEntity conductor = conductorPersistence.find(idConductor);
        int index = conductores.indexOf(conductor);
        if (index >= 0) {
            return conductores.get(index);
        }
        throw new BusinessLogicException("No existe tal conductor con proveedor de login: " + loginProveedor);

    }

    /**
     * Obtener un tarjeta por medio de su login.
     *
     * @param placaVehiculo: nombre del propietario de la tarjeta para ser
     * buscado.
     * @param idConductor que estamos buscando
     * @return la tarjeta solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ConductorEntity getConductorVehiculo(String placaVehiculo, Long idConductor) throws BusinessLogicException {
        List<ConductorEntity> conductores = vehiculoPersistence.findByPlaca(placaVehiculo).getConductor();
        ConductorEntity conductor = conductorPersistence.find(idConductor);
        int index = conductores.indexOf(conductor);
        if (index >= 0) {
            return conductores.get(index);
        }
        throw new BusinessLogicException("No existe tal conductor de un vehiculo con placas: " + placaVehiculo);

    }

    /**
     * Actualizar un conductor.
     *
     * @param nuevoConductor: conductor con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return el conductor con los cambios actualizados en la base de datos.
     */
    public ConductorEntity updateConductor(ConductorEntity nuevoConductor) throws BusinessLogicException {
        
        ConductorEntity conductor = getConductor(nuevoConductor.getId());
        ProveedorEntity pertenece = conductor.getProveedor();
        pertenece.getConductores().remove(conductor);
        pertenece.getConductores().add(nuevoConductor);
        
        proveedorPersistence.update(pertenece);
        
        for (VehiculoEntity veh : conductor.getVehiculos()) {
            for (ConductorEntity c : veh.getConductor()) {
                if (c.getId() == conductor.getId()) {
                    veh.getConductor().remove(c);
                    veh.getConductor().add(nuevoConductor);
                    break;
                }
            }
            vehiculoPersistence.update(veh);
        }
        
        ConductorEntity actualizado = conductorPersistence.update(nuevoConductor);
        
        return actualizado;
    }

    /**
     * Borrar un conductor
     *
     * @param conductorId: id del conductor a borrar
     */
    public void deleteConductor(Long conductorId) {
        conductorPersistence.delete(conductorId);
    }

    public void deleteConductorProveedor(String loginProveedor, Long idConductor) throws BusinessLogicException {
        ConductorEntity conductor = getConductorProveedor(loginProveedor, idConductor);
        ProveedorEntity pertenece = conductor.getProveedor();
        pertenece.getConductores().remove(conductor);
        conductorPersistence.delete(idConductor);
        proveedorPersistence.update(pertenece);
    }

    public void deleteConductorTodosLosVehiculo(Long idConductor) throws BusinessLogicException {
        ConductorEntity conductor = conductorPersistence.find(idConductor);
        for (VehiculoEntity veh : conductor.getVehiculos()) {
            for (ConductorEntity c : veh.getConductor()) {
                if (c.getId() == conductor.getId()) {
                    veh.getConductor().remove(c);
                    break;
                }
            }
            vehiculoPersistence.update(veh);
        }
        conductorPersistence.delete(idConductor);
    }

    public void deleteConductorVehiculo(String placa, Long idConductor) throws BusinessLogicException {
        List<VehiculoEntity> vehiculos = getConductorVehiculo(placa, idConductor).getVehiculos();
        VehiculoEntity vehiculo = vehiculoPersistence.findByPlaca(placa);
        ConductorEntity conductor = getConductorProveedor(placa, idConductor);
        int index = vehiculos.indexOf(vehiculo);
        if (index >= 0) {
            vehiculos.get(index).getConductor().remove(conductor);
        }
        conductorPersistence.delete(idConductor);
        vehiculoPersistence.update(vehiculo);
    }
}
