/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Daniel Machado
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable{
    
    
    private String login;
    
    private String password;
    
    private String nombre;
    
    private String logotipo;
    
    private String ciudadOrigen;
    
    private String correoElectronico;
    
    private String telefono;
    
    private Integer numeroVehiculos;
    
    private Integer dineroDisponible;
    
    private Integer calificacion;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNumeroVehiculos(Integer numeroVehiculos) {
        this.numeroVehiculos = numeroVehiculos;
    }

    public void setDineroDisponible(Integer dineroDisponible) {
        this.dineroDisponible = dineroDisponible;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getNumeroVehiculos() {
        return numeroVehiculos;
    }

    public Integer getDineroDisponible() {
        return dineroDisponible;
    }

    public Integer getCalificacion() {
        return calificacion;
    }
    
    
}
