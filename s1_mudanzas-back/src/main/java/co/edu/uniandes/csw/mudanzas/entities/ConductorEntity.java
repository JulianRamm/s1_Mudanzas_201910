/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class ConductorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributo que modela el nombre de un conductor
     */
    private String nombre;

    /**
     * Atributo que modela la calificacion de un conductor
     */
    private double calificacion;
    /**
     * Atributo que modela el numero de telefono de un conductor
     */

    private String telefono;

    /**
     * Atributo que modela la lista de vehiculos de un conductor
     */
    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<VehiculoEntity> vehiculos;
    /**
     * Atributo que modela el proveeedor al cual el conductor está adscrito.
     */
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    @PodamExclude
    @OneToMany(mappedBy = "conductor", fetch = FetchType.LAZY)
    private ViajesEntity viajes;

    
    /**
     * Metdo constructor de la entidad de un conductor
     */
    public ConductorEntity() {

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the calificacion
     */
    public double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the vehiculos
     */
    public Collection<VehiculoEntity> getVehiculos() {
        return vehiculos;
    }

    /**
     * @param vehiculos the vehiculos to set
     */
    public void setVehiculos(List<VehiculoEntity> vehiculos) {
        this.vehiculos = vehiculos;
    }

    /**
     * @return the proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

}
