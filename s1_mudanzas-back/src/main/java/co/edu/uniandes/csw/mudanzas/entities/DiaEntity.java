/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Samuel Bernal Neira
 */
@Entity
public class DiaEntity extends BaseEntity implements Serializable  
{
 private static final long serialVersionUID = 1L;
 
 
 @Temporal (TemporalType.TIME)
 private LocalDateTime horaInicio;
 
 @Temporal (TemporalType.TIME)
 private LocalDateTime horaFin;
 
 @Temporal (TemporalType.DATE)
 private Date diaActual;
 
 private boolean disponibilidad;
 
 @PodamExclude
 @OneToOne
 private VehiculoEntity vehiculo;
 

 public DiaEntity()
 {
     
 }

   

    

    /**
     * @return the horaSalida
     */
    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaSalida the horaSalida to set
     */
    public void setHoraInicio(LocalDateTime horaSalida) {
        this.horaInicio = horaSalida;
    }

    /**
     * @return the horaLlegada
     */
    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraFin(LocalDateTime horaLlegada) {
        this.horaFin = horaLlegada;
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

    /**
     * @return the diaActual
     */
    public Date getDiaActual() {
        return diaActual;
    }

    /**
     * @param diaActual the diaActual to set
     */
    public void setDiaActual(Date diaActual) {
        this.diaActual = diaActual;
    }
 
 

}
