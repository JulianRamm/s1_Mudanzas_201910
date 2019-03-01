/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author je.osorio
 */
public class ViajeDTO implements Serializable {

    /**
     * id del viaje
     */

    private long id;

    /**
     * direcciòn del lugar de salida del viaje
     */
    private String lugarSalida;

    /**
     * Direcciòn del lugar de llegada del viaje
     */
    private String lugarLlegada;

    /**
     * tiempo que se va a demorar el viaje acorde con la distancia
     */
    private int tiempo;

    /**
     * gasolina que se va a necesitar para completar el viaje
     */
    private int gastoGasolina;

    /**
     * clima actual de la posiciòn en la que se encuentra el conductor
     */
    private String clima;
    /**
     * hora de salida del viaje
     */
    private Date horaPartida;
    /**
     * hora de llegada del viaje
     */
    private Date horaLlegada;
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the lugarSalida
     */
    public String getLugarSalida() {
        return lugarSalida;
    }

    /**
     * @param lugarSalida the lugarSalida to set
     */
    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    /**
     * @return the lugarLlegada
     */
    public String getLugarLlegada() {
        return lugarLlegada;
    }

    /**
     * @param lugarLlegada the lugarLlegada to set
     */
    public void setLugarLlegada(String lugarLlegada) {
        this.lugarLlegada = lugarLlegada;
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the gastoGasolina
     */
    public int getGastoGasolina() {
        return gastoGasolina;
    }

    /**
     * @param gastoGasolina the gastoGasolina to set
     */
    public void setGastoGasolina(int gastoGasolina) {
        this.gastoGasolina = gastoGasolina;
    }

    /**
     * @return the clima
     */
    public String getClima() {
        return clima;
    }

    /**
     * @param clima the clima to set
     */
    public void setClima(String clima) {
        this.clima = clima;
    }

    /**
     * constructor de un objeto que toma la definiciòn de la clase viaje
     */
    public ViajeDTO() {

    }

    /**
     * constructor de un DTO dado una entidad
     *
     * @param viajesEntity
     */
    public ViajeDTO(ViajesEntity viajesEntity) {
        if (viajesEntity != null) {
            this.id=viajesEntity.getId();
            this.clima = viajesEntity.getClima();
            this.gastoGasolina = viajesEntity.getGastoGasolina();
            this.lugarLlegada = viajesEntity.getLugarLlegada();
            this.lugarSalida = viajesEntity.getLugarSalida();
            this.tiempo = viajesEntity.getTiempo();
            this.horaLlegada=viajesEntity.getHoraLlegada();
            this.horaPartida=viajesEntity.getHoraPartida();
        }
    }

    public ViajesEntity toEntity() {
        ViajesEntity viajesEntity = new ViajesEntity();
        viajesEntity.setClima(this.clima);
        viajesEntity.setGastoGasolina(this.gastoGasolina);
        viajesEntity.setId(this.id);
        viajesEntity.setLugarLlegada(this.lugarLlegada);
        viajesEntity.setLugarSalida(this.lugarSalida);
        viajesEntity.setTiempo(this.tiempo);
        viajesEntity.setHoraLlegada(this.horaLlegada);
        viajesEntity.setHoraPartida(this.horaPartida);
        return viajesEntity;
    }

    /**
     * @return the horaPartida
     */
    public Date getHoraPartida() {
        return horaPartida;
    }

    /**
     * @param horaPartida the horaPartida to set
     */
    public void setHoraPartida(Date horaPartida) {
        this.horaPartida = horaPartida;
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
    public void setHoraLlegada(Date horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
