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
import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */
@Stateless
public class VehiculoLogic {

    /**
     * Variable para acceder a la persistencia del vehiculo.
     */
    @Inject
    private VehiculoPersistence vehiculoPersistence;
    
    @Inject
    private ConductorPersistence conductorPersistence;

    /**
     * Atributo que inyecta la persistencia del prioveedor en la logica.
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;

    public VehiculoEntity crearVehiculo(VehiculoEntity entity, String login) throws BusinessLogicException {
        ProveedorEntity prov = proveedorPersistence.findProveedorPorLogin(login);        
        if(prov == null)
        {
            throw new BusinessLogicException("No existe ningun proveedor de login: " + login);
        }
        entity.setProveedor(prov);
        
        if(entity.getNumeroConductores()>8)
        {
            throw new BusinessLogicException("El Vehiculo tiene mas conductores que el limite");
        }
        
         if (!entity.getColor().matches("([a-zA-Z ]+){2,}"))
         {
            throw new BusinessLogicException("El color solo puede contener letras minusculas o mayusculas");
         }
         
         
     //   if(entity.getMarca().isEmpty() || entity.getMarca().toCharArray().length > 25 || entity.getMarca().contains("!"))
     //   {
     //       throw new BusinessLogicException("La marca: \"" + entity.getMarca() + "no tiene un formato valido\"");
     //   }
        prov.getVehiculos().add(entity);
        vehiculoPersistence.create(entity);
        proveedorPersistence.update(prov);
        return entity;
    }

    /**
     * Obtener todos los vehiculos existentes en la base de datos que le
     * pertencen a un proveedor en especifico.
     *
     * @return una lista de vehiculos de ese proveedor.
     */
    public List<VehiculoEntity> getVehiculosProveedor(String login) {
        List<VehiculoEntity> vehiculos = proveedorPersistence.findProveedorPorLogin(login).getVehiculos();
        return vehiculos;
    }

    /**
     * Obtener un vehiculo por medio de su id.
     *
     * @param vehiculoId: id del vehiculo para ser buscado.
     * @return el vehiculo solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculo(Long vehiculoId) throws BusinessLogicException {
        VehiculoEntity vehiculoEntity = vehiculoPersistence.find(vehiculoId);
        if (vehiculoEntity == null) {
            throw new BusinessLogicException("No existe tal vehiculo con id: " + vehiculoId);
        }
        return vehiculoEntity;
    }

    /**
     * Obtener un vehiculo por medio de su placa.
     *
     * @param loginP login del proveedor. 
     * @param placa: id del vehiculo para ser buscado.
     * @return el vehiculo solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculoPlacaProveedor(String loginP, String placa) throws BusinessLogicException {
        List<VehiculoEntity> vehiculos = proveedorPersistence.findProveedorPorLogin(loginP).getVehiculos();
        VehiculoEntity vehiculoEntity = vehiculoPersistence.findByPlaca(placa);
        int index = vehiculos.indexOf(vehiculoEntity);
        if (index >= 0) {
            return vehiculos.get(index);
        }
        throw new BusinessLogicException("No existe tal vehiculo con un proveedor de login: " + loginP);
    }

    /**
     * Obtener un vehiculo por medio de su placa.
     *
     * @param idC id del conductor
     * @param placa: id del vehiculo para ser buscado.
     * @return el vehiculo solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculoPlacaConductor(Long idC, String placa) throws BusinessLogicException {
        List<VehiculoEntity> vehiculos = conductorPersistence.find(idC).getVehiculos();
        VehiculoEntity vehiculoEntity = vehiculoPersistence.findByPlaca(placa);
        int index = vehiculos.indexOf(vehiculoEntity);
        if (index >= 0) {
            return vehiculos.get(index);
        }
        throw new BusinessLogicException("No existe tal vehiculo con un conductor de id: " + idC);
    }
    
    /**
     * Obtener un vehiculo por medio de su login.
     *
     * @param login: nombre del propietario de la vehiculo para ser buscado.
     * @param placa
     * @return la vehiculo solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculoProveedor(String login, String placa) throws BusinessLogicException {
        VehiculoEntity proveedorEntity = vehiculoPersistence.findVehiculoPorLoginProveedor(login, placa);
        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe tal vehiculo con propietario de login: " + login);
        }
        return proveedorEntity;
    }

    /**
     * Actualizar un vehiculo.
     *
     * @param nuevoVehiculo: vehiculo con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return la vehiculo con los cambios actualizados en la base de datos.
     */
    public VehiculoEntity updateVehiculo(VehiculoEntity nuevoVehiculo) {
        VehiculoEntity vehiculoEntity = vehiculoPersistence.update(nuevoVehiculo);
        return vehiculoEntity;
    }

}
