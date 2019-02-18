/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author estudiante
 */
public class AgendaEntity extends BaseEntity implements Serializable  
{
 @Temporal (TemporalType.DATE)
 private Collection<Date> diasDisponible;
 
 @Temporal (TemporalType.TIME)
 private Time horaSalida;
 
 @Temporal (TemporalType.TIME)
 private Time horaLlegada;
 private boolean disponibilidad;
 
 @OneToOne
 private VehiculoEntity vehiculo;

 public AgendaEntity()
 {
     
 }

    /**
     * @return the diasDisponible
     */
    public Collection<Date> getDiasDisponible() {
        return diasDisponible;
    }

    /**
     * @param diasDisponible the diasDisponible to set
     */
    public void setDiasDisponible(Collection<Date> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }

    /**
     * @return the horaSalida
     */
    public Time getHoraSalida() {
        return horaSalida;
    }

    /**
     * @param horaSalida the horaSalida to set
     */
    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    /**
     * @return the horaLlegada
     */
    public Time getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraLlegada(Time horaLlegada) {
        this.horaLlegada = horaLlegada;
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
     * @return the vehiculo
     */
    public VehiculoEntity getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(VehiculoEntity vehiculo) {
        this.vehiculo = vehiculo;
    }
 
 

}
