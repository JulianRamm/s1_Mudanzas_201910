/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Samuel Bernal Neira 
 */
public class DiaDTO implements Serializable
{
    
    private boolean disponibilidad;
    
    private Time horaInicio;
    
    private Time horaFin;
    
    private Date diaActual;
    
    public DiaDTO(DiaEntity entity)
    {
        if(entity != null)
        {
            this.disponibilidad = entity.getIsDisponibilidad();
            this.horaInicio = entity.getHoraInicio();
            this.horaFin = entity.getHoraFin();
            this.diaActual = entity.getDiaActual();
        }
    }
    
    public DiaEntity toEntity()
    {
        DiaEntity rta = new DiaEntity();
        rta.setDiaActual(this.diaActual);
        rta.setIsDisponibilidad(this.disponibilidad);
        rta.setHoraInicio(this.horaInicio);
        rta.setHoraFin(this.horaFin);
        return rta;
    }
    
    /**
     * @return the disponibilidad
     */
    
    public boolean getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * @return the horaPartida
     */
    public Time getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaPartida the horaPartida to set
     */
    public void setHoraInicio(Time horaPartida) {
        this.horaInicio = horaPartida;
    }

    /**
     * @return the horaLlegada
     */
    public Time getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraFin(Time horaLlegada) {
        this.horaFin = horaLlegada;
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
