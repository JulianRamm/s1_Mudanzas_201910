/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author je.osorio
 */
@Entity
public class DireccionEntity extends BaseEntity implements Serializable {
    /**
     * Carga que contiene una lista de direcciones
     */
    @PodamExclude
    @ManyToOne
    private CargaEntity carga;
    
    /**
     * latitud de la dirección
    */

    private double latitud;
    /**
     * longitud de la dirección
     */

    private double longitud;
    /**
     * indica true si la dirección es de salida del origen o false si es de
     * llegada al destino
     */

    private boolean isDeSalida;
    /**
     * id del par latitudf,longitudf,latitudi,longitudi
     */
    private long idPar; 
    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the deSalida
     */
    public boolean getIsDeSalida() {
        return isDeSalida;
    }

    /**
     * @param isDeSalida
     */
    public void setIsDeSalida(boolean isDeSalida) {
        this.isDeSalida = isDeSalida;
    }

    /**
     * @return the idPar
     */
    public long getIdPar() {
        return idPar;
    }

    /**
     * @param idPar the idPar to set
     */
    public void setIdPar(long idPar) {
        this.idPar = idPar;
    }

    /**
     * @return the carga
     */
    public CargaEntity getCarga() {
        return carga;
    }

    /**
     * @param carga the carga to set
     */
    public void setCarga(CargaEntity carga) {
        this.carga = carga;
    }
}
