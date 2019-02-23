/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    public ProveedorEntity createProveedor(ProveedorEntity proveedor) throws BusinessLogicException{
        
        if (pP.findProveedorPorLogin(proveedor.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un proveedor con el login \"" + proveedor.getLogin() + "\"");
        }
        if(!comprobarContraseña(proveedor)){
            throw new BusinessLogicException("La contraseña ingresada no es valida");
        }
        if(!comprobarCorreo(proveedor)){
            throw new BusinessLogicException("El correo ingresado no es valido");
        }
        if(!comprobarTelefono(proveedor)){
            throw new BusinessLogicException("El telefono ingresado no es valido");
        }
        if(!comprobarNumeroVehiculos(proveedor)){
            throw new BusinessLogicException("El numero de vehiculos ingresado no es valido");
        }
        
        return pP.create(proveedor);
    }
    
    /**
    *  Metodo que verifica la contraseña comparandola con una expresion regular predeterminada
    * La contraseña puede tener minimo 8 caracteres 
     * @param proveedor al cual se le quiere verificar la contraseña
     * @return un boolean que indica si la contraseña está correcta
    */
    public Boolean comprobarContraseña(ProveedorEntity proveedor){
        String contrasena = proveedor.getPassword();
        //Patron con la expresion regular 
        Pattern patron = Pattern.compile("/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,15}$/");
        // Matcher que verifica si la contraseña coincide con la expresion regular
        Matcher mat = patron.matcher(contrasena);
        
        return mat.matches();
    }
    
    /**
    *  Metodo que verifica el correo electronico comparandolo  con una expresion regular predeterminada
    * La contraseña puede tener minimo 8 caracteres 
     * @param proveedor al cual se le quiere verificar la contraseña
     * @return un boolean que indica si la contraseña está correcta
    */
    public Boolean comprobarCorreo(ProveedorEntity proveedor){
        String contrasena = proveedor.getCorreoElectronico();
        //Patron con la expresion regular 
        Pattern patron = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        // Matcher que verifica si el correo dado coincide con la expresion regular
        Matcher mat = patron.matcher(contrasena);
        
        return mat.matches();
    }
    
    /**
    *  Metodo que verifica el telefono  con una expresion regular predeterminada
     * @param proveedor al cual se le quiere verificar el telefono
     * @return un boolean que indica el telefono está correcto
    */
    public Boolean comprobarTelefono(ProveedorEntity proveedor){
        String contrasena = proveedor.getTelefono();
        //Patron con la expresion regular 
        Pattern patron = Pattern.compile("^[0-9]{7,10}$");
        // Matcher que verifica si el correo dado coincide con la expresion regular
        Matcher mat = patron.matcher(contrasena);
        
        return mat.matches();
    }
    
    /**
    *  Metodo que verifica el telefono  con una expresion regular predeterminada
     * @param proveedor al cual se le quiere verificar el telefono
     * @return un boolean que indica el telefono está correcto
    */
    public Boolean comprobarNumeroVehiculos(ProveedorEntity proveedor){       
        return proveedor.getNumeroVehiculos() > 0;
    }
    
    
}
