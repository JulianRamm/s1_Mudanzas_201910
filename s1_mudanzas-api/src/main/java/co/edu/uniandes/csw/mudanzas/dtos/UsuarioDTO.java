/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.io.Serializable;

/**
 *
 * @author Luis Miguel
 */
public class UsuarioDTO implements Serializable{
    
    /**
     * Atributo que representa el nombre de usuario de un Usuario
     */
    private String login;
    
    /**
     * Atributo que representa la contraseña de un Usuario
     */
    private String password;
    
    /**
     * Atributo que representa el nombre del usuario
     */    
    private String nombre;
    
    /**
     * Atributo que representa el apellido del usuario.
     */
    private String apellido;
    
    /**
     * Atributo que representa el correo electronico del usuario
     */
    private String correoElectronico;
    
    /**
     * Atributo que representa la ciudad de origen del usuario.
     */
    private String ciudadDeOrigen;
    
    /**
     * Constructor por defecto
     */    
    public UsuarioDTO(){
        //No se hace nada, se crea mediante 
    }
    
    /**
     * @return the login
     */
    public String getDTOLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setDTOLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getDTOPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setDTOPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nombre
     */
    public String getDTONombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setDTONombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getDTOApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setDTOApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correoElectronico
     */
    public String getDTOCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setDTOCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the ciudadDeOrigen
     */
    public String getDTOCiudadDeOrigen() {
        return ciudadDeOrigen;
    }

    /**
     * @param ciudadDeOrigen the ciudadDeOrigen to set
     */
    public void setDTOCiudadDeOrigen(String ciudadDeOrigen) {
        this.ciudadDeOrigen = ciudadDeOrigen;
    }
    
    
}
