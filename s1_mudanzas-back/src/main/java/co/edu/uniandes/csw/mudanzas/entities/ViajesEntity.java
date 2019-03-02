/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author je.osorio
 */
@Entity
public class ViajesEntity extends BaseEntity implements Serializable {

    /**
     * Lista de objetos CargaEntity ya que la relación para estas dos entidades
     * está definida como Viaje tiene 1 o muchas cargas debido a esto se utiliza
     * una lista, además, se utiliza fetch lazy para que no se carguen todos los
     * objetos CargaEntity cuando se necesite un objeto de este tipo
     */
    @PodamExclude
    @OneToMany(
            mappedBy = "viaje",
            fetch = FetchType.LAZY
    )
    List<CargaEntity> cargas;
    /**
     * conducto asignado en el viaje
     */
    @PodamExclude
    @OneToOne
    ConductorEntity conductorEntity;
    
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
    @Temporal(TemporalType.DATE)
    private Date horaPartida;
    /**
     * hora de llegada del viaje
     */
    @Temporal(TemporalType.DATE)
    private Date horaLlegada;

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

    /**
     * @return the cargas
     */
    public List<CargaEntity> getCargas() {
        return cargas;
    }

    /**
     * @param cargas the cargas to set
     */
    public void setCargas(List<CargaEntity> cargas) {
        this.cargas = cargas;
    }

    /**
     * @return the conductor
     */
    public ConductorEntity getConductorEntity() {
        return conductorEntity;
    }

    /**
     * @param conductor the conductor to set
     */
    public void setConductorEntity(ConductorEntity conductor) {
        this.conductorEntity = conductor;
    }

    public VehiculoEntity getVehiculoDelViaje() {
        VehiculoEntity res = new VehiculoEntity();
        for (VehiculoEntity ve : conductorEntity.getVehiculos()) {
            if (Objects.equals(ve.getId(), conductorEntity.getId())) {
              res=ve;
            }
        }
        return res;
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
    /**
     * método que verifica que el tiempo es acorde a la distancia
     * @param tiempo
     * @throws BusinessLogicException 
     */
    public void verificarTiempo(double tiempo)throws BusinessLogicException{
        if (!(this.getTiempo() >= tiempo - 8.0 && this.getTiempo() <= tiempo + 8.0)) {
                throw new BusinessLogicException("El tiempo no es acorde a la distancia");
            } else if (this.getTiempo() < 0) {
                throw new BusinessLogicException("El tiempo es negativo");
            } else if (this.getTiempo() == 1 && this.getTiempo() == 0) {
                throw new BusinessLogicException("El tiempo 1 o 0");
            } //24*7=168
            else if (this.getTiempo() > 168) {
                throw new BusinessLogicException("El tiempo no es acorde a la distancia de colombia");
            }
    }

}
