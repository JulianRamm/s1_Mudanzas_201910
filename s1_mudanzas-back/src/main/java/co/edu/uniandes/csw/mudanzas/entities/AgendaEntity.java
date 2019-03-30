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
public class AgendaEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIME)
    private Date horaSalida;

    @Temporal(TemporalType.TIME)
    private Date horaLlegada;
    private boolean isDisponibilidad;

    public AgendaEntity() {

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
     * @return the isDisponibilidad
     */
    public boolean getIsDisponibilidad() {
        return isDisponibilidad;
    }

    /**
     * @param isDisponibilidad the isDisponibilidad to set
     */
    public void setIsDisponibilidad(boolean isDisponibilidad) {
        this.isDisponibilidad = isDisponibilidad;
    }

}
