/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author je.osorio
 */
@Entity
public class ViajesEntity extends BaseEntity implements Serializable {
    /**
     * Lista de objetos CargaEntity ya que la relación para estas dos entidades está definida
     * como Viaje tiene 1 o muchas cargas debido a esto se utiliza una lista, además, se utiliza fetch lazy
     * para que no se carguen todos los objetos CargaEntity cuando se necesite un objeto de este tipo
     */
    @OneToMany(
            mappedBy="viajes",
            fetch=FetchType.LAZY
    )
    LinkedList<CargaEntity> cargas;
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
    public ViajesEntity() {
    }
}
