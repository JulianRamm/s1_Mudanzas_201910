/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.util.List;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Machado
 */
@Stateless
public class ProveedorLogic {
    
    
     /**
     * Variable para acceder a la persistencia del proveedor.
     */
    @Inject
    private ProveedorPersistence pP;
    
    /**
     * Crea un proveedor en la persistencia.
     *
     * @param proveedor La entidad que representa el proveedor a persistir.
     * @return La entiddad del proveedor luego de persistirla.
     * @throws BusinessLogicException Si el proveedor a persistir no cumple las reglas de negocio.
     */
    public ProveedorEntity createProveedor(ProveedorEntity proveedor) throws BusinessLogicException{
        
        if (pP.findProveedorPorLogin(proveedor.getLogin()) != null) 
        {
            throw new BusinessLogicException("Ya existe un proveedor con el login \"" + proveedor.getLogin() + "\"");
        }
        else if(pP.findProveedorPorNombre(proveedor.getNombre())!=null)
        {
            throw new BusinessLogicException("Ya existe un proveedor con el nombre \"" + proveedor.getNombre() + "\"");
        }
        else if(proveedor.getNombre() == null ||
                proveedor.getPassword() == null ||
                proveedor.getCiudadOrigen() == null ||                
                proveedor.getTelefono() == null ||
                proveedor.getCorreoElectronico() == null)
        {
            throw new BusinessLogicException("Ninguno de los campos puede ser nulo" +"fsasfa" );
        }
        else if (!proveedor.getLogin().matches("([0-9a-zA-Z_.-][0-9a-zA-Z_.-]*){8,}$")) {
            throw new BusinessLogicException("El login solamente puede contener Letras, numeros, (-), (.), (_), minimo 8 caracteres.");
        }
        else if(!proveedor.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,128}$"))
        {
            throw new BusinessLogicException("La contraseña ingresada no es valida");
        }
        else if(!proveedor.getCorreoElectronico().matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")){
            throw new BusinessLogicException("El correo ingresado no es valido");
        }
        else if(!proveedor.getTelefono().matches("[0-9]{7,10}+")){
            throw new BusinessLogicException("El telefono ingresado no es valido");
        }
        if(proveedor.getNumeroVehiculos()< 0){
            throw new BusinessLogicException("El numero de vehiculos ingresado no es valido");
        }
        
        return pP.create(proveedor);
    }
    
    /**
     * Obtener todos los proveedores existentes en la base de datos.
     *
     * @return una lista de proveedores.
     */
    public List<ProveedorEntity> getProveedores() {
        List<ProveedorEntity> proveedores = pP.findAll();
        return proveedores;
    }
    
    /**
     * Obtener un proveedor por medio de su id.
     *
     * @param proveedorId: id del usuario para ser buscado.
     * @return el proveedor solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ProveedorEntity getProveedor(Long proveedorId) throws BusinessLogicException {
        ProveedorEntity proveedorEntity = pP.find(proveedorId);
        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe tal proveedor con id: " + proveedorId);
        }
        return proveedorEntity;
    }
    
    /**
     * Obtener un proveedor por medio de su login.
     *
     * @param proveedorLogin: login del proveedor para ser buscado.
     * @return el proveedor solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public ProveedorEntity getProveedor(String proveedorLogin) throws BusinessLogicException {
        ProveedorEntity proveedorEntity = pP.findProveedorPorLogin(proveedorLogin);
        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe tal proveedor con login: " + proveedorLogin);
        }
        return proveedorEntity;
    }
    
    /**
     * Actualizar un proveedor.
     *
     * @param nuevoProveedor: proveedor con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el proveedor con los cambios actualizados en la base de datos.
     */
    public ProveedorEntity updateProveedor(ProveedorEntity nuevoProveedor) {
        ProveedorEntity proveedorEntity = pP.update(nuevoProveedor);
        return proveedorEntity;
    }
    
    /**
     * Borrar un proveedor
     *
     * @param proveedorId: id del proveedor a borrar
     */
    public void deleteUsuario(Long proveedorId)  {
        
        pP.delete(proveedorId);
    }
}
