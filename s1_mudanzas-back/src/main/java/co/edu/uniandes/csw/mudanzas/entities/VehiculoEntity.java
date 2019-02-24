/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.Collection;
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

    private String placa;

    private double rendimiento;
    
    private Long idConductorActual;
    
    private String marca;
    
   // private String UbicacionActual;

    private int numeroConductores;

    private String color;

    private String dimensiones;


    /**
     * Atributo que modela la lista de vehiculos de un conductor
     */
    @PodamExclude
    @ManyToOne
    private ConductorEntity conductor;

    @PodamExclude
    @OneToOne
    private DiaEntity agenda;

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
    public ConductorEntity getConductor() {
        return conductor;
    }

    /**
     * @param conductor the conductores to set
     */
    public void setConductores(ConductorEntity conductor) {
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
    public Long getIdConductorActual() {
        return idConductorActual;
    }

    /**
     * @param idConductorActual the idConductorActual to set
     */
    public void setIdConductorActual(Long idConductorActual) {
        this.idConductorActual = idConductorActual;
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

}
