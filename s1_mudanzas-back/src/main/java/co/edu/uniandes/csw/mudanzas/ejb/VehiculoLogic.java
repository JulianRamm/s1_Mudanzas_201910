/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
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
     * Atributo que inyecta la persistencia del prioveedor en la logica.
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;

    public VehiculoEntity crearVehiculo(VehiculoEntity entity, String login) throws BusinessLogicException {
        //Verificacion de un vehiculo con id unico.
        if (vehiculoPersistence.find(entity.getId()) != null) {
            throw new BusinessLogicException("Ya existe un vehiculo con el id: \"" + entity.getId() + "\"");
        }
        //Verificacion de un vehiculo con id unico.
        if (vehiculoPersistence.findByPlaca(entity.getPlaca()) != null) {
            throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getPlaca() + "\"");
        }
        //Verificacion de un vehiculo con id unico.
        if (vehiculoPersistence.findByUbicacionActual(entity.getUbicacionActual().getId()) != null) {
            throw new BusinessLogicException("Ya existe un vehiculo con el id de ubicación actual: \"" + entity.getUbicacionActual().getIdPar() + "\"");
        }
        //Verificación de un vehiculo con una agenda única.
        if (vehiculoPersistence.findByDia(entity.getAgenda().getId(), entity.getPlaca()) != null) {
            throw new BusinessLogicException("Ya existe un vehiculo con la agenda: \"" + entity.getAgenda().getId() + "\"");

        }
        //Verificación que marca no tenga carácteres especiales, no sea vacia y su longitud sea menor a 25 carácteres
        boolean marcaCorrectFormat = true;
        char[] caracteresEspeciales = "!#$%&/()=?¡¿_{}´+¨*~[]".toCharArray();
        if (entity.getMarca().equals("")) {
            marcaCorrectFormat = false;
        } else {
            marcaCorrectFormat = true;
        }
        for (int i = 0; i < caracteresEspeciales.length && marcaCorrectFormat; i++) {
            if (entity.getMarca().indexOf(caracteresEspeciales[i]) > 0) {
                marcaCorrectFormat = false;
            } else {
                marcaCorrectFormat = true;
            }
        }
        if (!marcaCorrectFormat || entity.getMarca().length() > 25) {
            throw new BusinessLogicException("La marca: \"" + entity.getMarca() + "no tiene un formato valido\"");
        }

        //Verificación que el color tenga carácteres especiales, no sea vacia y su longitud sea menor a 25 carácteres
        boolean colorCorrectFormat = true;
        if (entity.getMarca().equals("")) {
            colorCorrectFormat = false;
        }
        for (int i = 0; i < caracteresEspeciales.length && colorCorrectFormat; i++) {
            if (entity.getColor().indexOf(caracteresEspeciales[i]) > 0) {
                colorCorrectFormat = false;
            } else {
                colorCorrectFormat = true;
            }
        }
        if (!colorCorrectFormat || entity.getColor().length() > 25) {
            throw new BusinessLogicException("El color: \"" + entity.getColor() + "no es valida\"");
        }

        //Verificación de nulidad para marca, color, agenda, placa y ubicaciónActual
        if (entity.getMarca() == null || entity.getColor() == null || entity.getAgenda() == null || entity.getPlaca() == null || entity.getUbicacionActual() == null ) {
            throw new BusinessLogicException("Ninguno de los campos puede ser nulo");
        }
        
        //Verificación de manejo correcto de rendimiento
        
        if(entity.getRendimiento()<=0)
        {
            throw new BusinessLogicException("El rendimiento del vehiculo no puede ser negativo ni tampoco 0.");
        }

        //Verificación del numero de conductores posibles a los que un vehículo puede estar adscrito.
        if (entity.getNumeroConductores() < 0 || entity.getNumeroConductores() > 8) {
            throw new BusinessLogicException("El número de conductores: \"" + entity.getNumeroConductores() + "No es valido \"");
        }

        //Verificación del formato de la placa
        char[] cadenaPlaca = entity.getPlaca().toCharArray();
        boolean placaCorrectFormat = false;
        for (int i = 0; i < cadenaPlaca.length; i++) {
            if (i <= 2) {
                if (isUpperCase(cadenaPlaca[i])) {
                    placaCorrectFormat = true;
                } else {
                    placaCorrectFormat = false;
                }
            }
            if (i > 2) {
                if (isDigit(cadenaPlaca[i])) {
                    placaCorrectFormat = true;
                } else {
                    placaCorrectFormat = false;
                }
            }
        }
        if (!placaCorrectFormat || entity.getPlaca().length() > 6) {
            throw new BusinessLogicException("La placa: \"" + entity.getPlaca() + "no tiene un formato valido\"");
        }

        entity = vehiculoPersistence.create(entity);
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
     * @param placa: id del vehiculo para ser buscado.
     * @return el vehiculo solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public VehiculoEntity getVehiculoPlaca(String placa) throws BusinessLogicException {
        VehiculoEntity vehiculoEntity = vehiculoPersistence.findByPlaca(placa);
        if (vehiculoEntity == null) {
            throw new BusinessLogicException("No existe tal vehiculo con placa: " + placa);
        }
        return vehiculoEntity;
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
