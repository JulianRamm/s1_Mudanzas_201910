/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
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
    

    /**
     * Atributo que inyecta la persistencia del proveedor en la logica.
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
    /**
     * Atributo que inyecta la persistencia del día en la logica.
     */
    @Inject
    private DiaPersistence diaPersistence;
/**
 * 
 * @param entity la entidad con la que se van a probar las reglas de negocio.
 * @param login el login del proveedor
 * @return
 * @throws BusinessLogicException 
 */
    public VehiculoEntity crearVehiculo(VehiculoEntity entity, String login) throws BusinessLogicException {
        //Verificación de nulidad del proveedor
        ProveedorEntity prov = proveedorPersistence.findProveedorPorLogin(login);        
        if(prov == null)
        {
            throw new BusinessLogicException("No existe ningun proveedor de login: " + login);
        }
        entity.setProveedor(prov);
        //Verificación para que la placa sea única.
        if (vehiculoPersistence.findByPlaca(entity.getPlaca()) != null) {
            throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getPlaca() + "\"");
        }
        //Verificacion para que el numero de conductores adscritos a un vehiculo sean menores a 8.
        if(entity.getNumeroConductores()>8)
        {
            throw new BusinessLogicException("El Vehiculo tiene mas conductores que el limite");
        }
        // Verificación del formato del color
         if (!entity.getColor().matches("([a-zA-Z ]+){2,}")&& !entity.getMarca().matches("([a-zA-Z ]+){2,}"))
         {
            throw new BusinessLogicException("El color solo puede contener letras minusculas o mayusculas");
         }
         //Verificación del formato de la placa por medio de un recorrido simple a un arreglo de chars que viene del string de la placa.
         char[] cadena = entity.getPlaca().toCharArray();
         for(int i = 0; i < cadena.length; i++)
         {
             if(i<=2)
             {
                 if(!Character.isUpperCase(cadena[i]))
                 {
                   throw new BusinessLogicException("Los 3 primeros caracteres de la placa tienen que estar en mayuscula");

                 }
             }
             if(i>2)
             {
                 if(!Character.isDigit(cadena[i]))
                 {
                   throw new BusinessLogicException("Los 3 ultimos caracteres de la placa tienen que ser números");
                 }
             }
         }
         //Verificacion para que el rendimiento no sea negativo
         if(entity.getRendimiento()<=0)
         {
             throw new BusinessLogicException("El rendimiento no puede ser menor a 0");
         }
         
         //Verificación del formato correcto de la marca
         if(entity.getMarca() == null || entity.getColor()==null|| entity.getPlaca()==null)
         {
             throw new BusinessLogicException("Ninguno de los campos puede ser nulo");
         }
         
        //Se persiste y se crea el vehiculo despues de haber evaluado todas las reglas de negocio.
    
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
     * @param idC id de la agenda 
     * @param placa: id del vehiculo para ser buscado.
     * @return el vehiculo solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculoIdAgenda(Long idD, String placa) throws BusinessLogicException 
    {
        VehiculoEntity rta= null;
        DiaEntity agenda = diaPersistence.find(idD);
        VehiculoEntity vehiculoEntity = vehiculoPersistence.findByPlaca(placa);
        if(vehiculoEntity.getAgenda().getId() == agenda.getId())
        {
            rta = vehiculoEntity;
        }
        else
        {
        throw new BusinessLogicException("No existe tal vehiculo con una agenda con id: " + idD);
        }
        return rta;
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
        ProveedorEntity prov = proveedorPersistence.findProveedorPorLogin(nuevoVehiculo.getProveedor().getLogin());
        for(VehiculoEntity vehiculoEntity: prov.getVehiculos())
        {
            if(vehiculoEntity.getPlaca().equals(nuevoVehiculo.getPlaca()))
            {
                prov.getVehiculos().remove(vehiculoEntity);
                prov.getVehiculos().add(nuevoVehiculo);
                break;
            }
        }
        proveedorPersistence.update(prov);
        vehiculoPersistence.update(nuevoVehiculo);
        return nuevoVehiculo;
    }
    
    public void deleteVehiculo(String login, String placa)throws BusinessLogicException
    {
        VehiculoEntity vec = getVehiculoPlacaProveedor(login, placa);
        ProveedorEntity pertenece = vec.getProveedor();
        pertenece.getVehiculos().remove(vec);
        vehiculoPersistence.delete(vec.getId());
        proveedorPersistence.update(pertenece);
    }

}
