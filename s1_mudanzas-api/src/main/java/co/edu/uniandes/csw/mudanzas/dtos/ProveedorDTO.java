/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Daniel Machado
 */
public class ProveedorDTO implements Serializable{


   
    private Long id;
    
    private String login;
    
    private String password;
    
    private String nombre;
    
    private String numeroTelefono;
    
    private String correoElectronico;
    
    private String ciudadOrigen;
    
    private Integer dineroDisponible;
    
    private Integer numeroVehiculos;
    
    private Double clasificacion;
    
    private String logotipo;

    
    public ProveedorDTO(){
        
    }
    
    /**
     * Convertir Entity a DTO
     *
     * @param entidad ProveedorEntity que se va a convertir en DTO
     */
    public ProveedorDTO(ProveedorEntity entidad){
        
        if(entidad != null){
            this.id = entidad.getId(); 
            this.login = entidad.getLogin();
            this.nombre = entidad.getNombre();
            this.password = entidad.getPassword();
            this.correoElectronico = entidad.getCorreoElectronico();
            this.numeroTelefono = entidad.getTelefono();
            this.ciudadOrigen = entidad.getCiudadOrigen();
            this.dineroDisponible = entidad.getDineroDisponible();
            this.numeroVehiculos = entidad.getNumeroVehiculos();
            this.logotipo = entidad.getLogotipo();
            this.clasificacion = entidad.getCalificacion();
        }
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

    public Double getClasificacion() {
        return clasificacion;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumeroVehiculos() {
        return numeroVehiculos;
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

    public void setClasificacion(Double clasificacion) {
        this.clasificacion = clasificacion;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroVehiculos(Integer numeroVehiculos) {
        this.numeroVehiculos = numeroVehiculos;
    }
    
    /**
     * Metodo que convierte el ProveedorDTO a un ProveedorEnity
     *
     * @return ProveedorEntity el proveedor DTO ya convertido.
     */
    public ProveedorEntity  toEntity(){
        
        ProveedorEntity entidad = new ProveedorEntity();
        entidad.setId(this.id);
        entidad.setLogin(this.login);
        entidad.setNombre(this.nombre);
        entidad.setPassword(this.password);
        entidad.setCorreoElectronico(this.correoElectronico);
        entidad.setCiudadOrigen(this.ciudadOrigen);
        entidad.setCalificacion(this.clasificacion);
        entidad.setNumeroVehiculos(this.numeroVehiculos);
        entidad.setTelefono(this.numeroTelefono);
        entidad.setLogotipo(this.logotipo);
        entidad.setDineroDisponible(this.dineroDisponible);
        return entidad;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
}
