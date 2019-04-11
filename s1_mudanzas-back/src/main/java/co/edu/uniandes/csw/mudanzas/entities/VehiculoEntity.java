/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Samuel Bernal Neira
 */
@Entity
public class VehiculoEntity extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Atributo que modela la placa de un vehiculo.
     */
    private String placa;

    /**
     * Atributo que modela el rendimiento de un vehiculo.
     */
    private double rendimiento;
    
    /**
     * Atributo que modela el id del conductor que está manejando el vehiculo.
     */
    private long idConductorActual;
    
    /**
     * Atributo que modela la marca de un vehiculo.
     */
    private String marca;
   
    /**
     * Atributo que modela el numero de conductores que posee un vehiculo.
     */
    private int numeroConductores;

    /**
     * Atributo que modela el color de un vehiculo.
     */
    private String color;

    
    /**
     * Atributo que modela las dimensiones de un vehiculo.
     */
    private String dimensiones;

    /**
     * Atributo que modela la lista de vehiculos de un conductor
     */
    @PodamExclude
    @ManyToOne
    (  
            fetch = FetchType.LAZY
    )
    private ProveedorEntity proveedor;
    /**
     * Atributo que modela la lista de vehiculos de un conductor
     */
    @PodamExclude
    @ManyToMany
    private List<ConductorEntity> conductor ;

    @PodamExclude
    @OneToOne
    private DiaEntity agenda;
    
    @PodamExclude
    @OneToOne
    private DireccionEntity ubicacionActual;

    public VehiculoEntity() {

    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the numeroConductores
     */
    public int getNumeroConductores() {
        return numeroConductores;
    }

    /**
     * @param numeroConductores the numeroConductores to set
     */
    public void setNumeroConductores(int numeroConductores) {
        this.numeroConductores = numeroConductores;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the dimensiones
     */
    public String getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }


    /**
     * @return the conductores
     */
    public List<ConductorEntity> getConductor() {
        return conductor;
    }

    /**
     * @param conductor the conductores to set
     */
    public void setConductores(List<ConductorEntity> conductor) {
        this.conductor = conductor;
    }

    /**
     * @return the agenda
     */
    public DiaEntity getAgenda() {
        return agenda;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgenda(DiaEntity agenda) {
        this.agenda = agenda;
    }

    /**
     * @return the rendimiento
     */
    public double getRendimiento() {
        return rendimiento;
    }

    /**
     * @param rendimiento the rendimiento to set
     */
    public void setRendimiento(double rendimiento) {
        this.rendimiento = rendimiento;
    }

    /**
     * @return the idConductorActual
     */
    public long getIdConductorActual() {
        return idConductorActual;
    }

    /**
     * @param conductorActual
     * 
     */
    public void setIdConductorActual(long conductorActual) {
        this.idConductorActual = conductorActual;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
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

    /**
     * @return the ubicacionActual
     */
    public DireccionEntity getUbicacionActual() {
        return ubicacionActual;
    }

    /**
     * @param ubicacionActual the ubicacionActual to set
     */
    public void setUbicacionActual(DireccionEntity ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

}
