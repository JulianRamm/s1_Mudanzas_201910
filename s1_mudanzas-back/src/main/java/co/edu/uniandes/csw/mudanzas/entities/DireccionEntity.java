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
     * latitud de la dirección
    */

    private Double latitud;
    /**
     * longitud de la dirección
     */

    private Double longitud;
    /**
     * indica true si la dirección es de salida del origen o false si es de
     * llegada al destino
     */
    private boolean isDeSalida;
    
    /**
     * Carga que contiene una lista de direcciones
     */
    @PodamExclude
    @ManyToOne
    private CargaEntity carga;

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Double latitud) {
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
