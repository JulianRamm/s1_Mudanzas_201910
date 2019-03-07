/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import co.edu.uniandes.csw.mudanzas.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Samuel Bernal Neira
 */
@Entity
public class DiaEntity extends BaseEntity implements Serializable {

  //  private static final long serialVersionUID = 1L;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date horaInicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date horaFin;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date diaActual;

    private boolean isDisponibilidad;

    @PodamExclude
    @OneToOne
    private VehiculoEntity vehiculo;

    public DiaEntity() {

    }

    /**
     * @return the horaSalida
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaSalida the horaSalida to set
     */
    public void setHoraInicio(Date horaSalida) {
        this.horaInicio = horaSalida;
    }

    /**
     * @return the horaLlegada
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraFin(Date horaLlegada) {
        this.horaFin = horaLlegada;
    }

    /**
     * @return the disponibilidad
     */
    public boolean getIsDisponibilidad() {
        return isDisponibilidad;
    }

    /**
     * @param isDisponibilidad
     */
    public void setIsDisponibilidad(boolean isDisponibilidad) {
        this.isDisponibilidad = isDisponibilidad;
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
