/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Machado
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;

    private String password;

    private String nombre;

    private String logotipo;

    private String ciudadOrigen;

    private String correoElectronico;

    private String telefono;

    private Integer numeroVehiculos;

    private Integer dineroDisponible;

    private Double calificacion;

    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    private List<ConductorEntity> conductores;

    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    private List<OfertaEntity> ofertas;

    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    private List<SubastaEntity> subastas;

    @PodamExclude
    @OneToMany(
            mappedBy = "proveedor",
            fetch = FetchType.LAZY
    )
    private List<VehiculoEntity> vehiculos;

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

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public void setConductores(List<ConductorEntity> conductores) {
        this.conductores = conductores;
    }

    public void setOfertas(List<OfertaEntity> ofertas) {
        this.ofertas = ofertas;
    }

    public void setSubastas(List<SubastaEntity> subastas) {
        this.subastas = subastas;
    }

    public void setVehiculos(List<VehiculoEntity> vehiculos) {
        this.vehiculos = vehiculos;
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

    public Double getCalificacion() {
        return calificacion;
    }

    public List<ConductorEntity> getConductores() {
        return conductores;
    }

    public List<OfertaEntity> getOfertas() {
        return ofertas;
    }

    public List<SubastaEntity> getSubastas() {
        return subastas;
    }

    public List<VehiculoEntity> getVehiculos() {
        return vehiculos;
    }

}
