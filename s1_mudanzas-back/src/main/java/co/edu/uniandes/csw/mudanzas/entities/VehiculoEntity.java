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
import javax.persistence.OneToOne;

/**
 *
 * @author estudiante
 */
@Entity
public class VehiculoEntity extends BaseEntity implements Serializable        
{
    private String placa;
    
    private boolean disponibilidad;
    
    private String UbicacionActual;
    
    private int numeroConductores;
    
    private String color;
    
    private String dimensiones;
    
    private int capacidad;
    
      /**
 * Atributo que modela la lista de vehiculos de un conductor
 */
    @ManyToMany(mappedBy = "conductores", fetch = FetchType.LAZY)
    private Collection <ConductorEntity> conductores;
    
    @OneToOne
    private AgendaEntity agenda;
    
    public VehiculoEntity()
    {
        
    }

    /**
     * @return the placa
     */
    public String getPlaca() 
    {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the disponibilidad
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * @return the UbicacionActual
     */
    public String getUbicacionActual() {
        return UbicacionActual;
    }

    /**
     * @param UbicacionActual the UbicacionActual to set
     */
    public void setUbicacionActual(String UbicacionActual) {
        this.UbicacionActual = UbicacionActual;
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
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the conductores
     */
    public Collection <ConductorEntity> getConductores() {
        return conductores;
    }

    /**
     * @param conductores the conductores to set
     */
    public void setConductores(Collection <ConductorEntity> conductores) {
        this.conductores = conductores;
    }

    /**
     * @return the agenda
     */
    public AgendaEntity getAgenda() {
        return agenda;
    }

    /**
     * @param agenda the agenda to set
     */
    public void setAgenda(AgendaEntity agenda) {
        this.agenda = agenda;
    }
    
    
}
