/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author estudiante
 */
public class AgendaDTO 
{
    
    private boolean disponibilidad;
    
    private LocalDateTime horaPartida;
    
    private LocalDateTime horaLlegada;
    
    public AgendaDTO()
    {
        
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
    public LocalDateTime getHoraPartida() {
        return horaPartida;
    }

    /**
     * @param horaPartida the horaPartida to set
     */
    public void setHoraPartida(LocalDateTime horaPartida) {
        this.horaPartida = horaPartida;
    }

    /**
     * @return the horaLlegada
     */
    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * @param horaLlegada the horaLlegada to set
     */
    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    
}
