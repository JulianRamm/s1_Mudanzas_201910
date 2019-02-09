/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.io.Serializable;

/**
 *
 * @author Daniel Machado
 */
public class ProveedorDTO implements Serializable{


   
    private String login;
    
    private String password;
    
    private String nombre;
    
    private String numeroTelefono;
    
    private String correoElectronico;
    
    private String ciudadOrigen;
    
    private Integer dineroDisponible;
    
    private Integer clasificacion;
    
    private String logotipo;

    
    
    
    
    
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public Integer getDineroDisponible() {
        return dineroDisponible;
    }

    public Integer getClasificacion() {
        return clasificacion;
    }

    public String getLogotipo() {
        return logotipo;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public void setDineroDisponible(Integer dineroDisponible) {
        this.dineroDisponible = dineroDisponible;
    }

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }
    
}
