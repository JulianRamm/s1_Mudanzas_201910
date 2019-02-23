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
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author estudiante
 */
@Entity
public class AgendaEntity extends BaseEntity implements Serializable  
{
 private static final long serialVersionUID = 1L;
 @PodamExclude
 private List<String> diasDisponible;
 
 @Temporal (TemporalType.TIME)
 private Date horaSalida;
 
 @Temporal (TemporalType.TIME)
 private Date horaLlegada;
 private boolean disponibilidad;
 
 @PodamExclude
 @OneToOne
 private VehiculoEntity vehiculo;

 public AgendaEntity()
 {
     
 }

    /**
     * @return the diasDisponible
     */
    public List<String> getDiasDisponible() {
        return diasDisponible;
    }

    /**
     * @param diasDisponible the diasDisponible to set
     */
    public void setDiasDisponible(List<String> diasDisponible) {
        this.diasDisponible = diasDisponible;
    }

    /**
     * @return the horaSalida
     */
    public Date getHoraSalida() {
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
    public Date getHoraLlegada() {
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
