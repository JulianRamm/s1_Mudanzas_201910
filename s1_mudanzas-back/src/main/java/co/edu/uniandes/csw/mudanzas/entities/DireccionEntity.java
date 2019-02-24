/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author je.osorio
 */
@Entity
public class DireccionEntity extends BaseEntity implements Serializable {
    /**
     * Carga que contiene una lista de direcciones
     */
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

    private boolean deSalida;
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
    public boolean isDeSalida() {
        return deSalida;
    }

    /**
     * @param deSalida the deSalida to set
     */
    public void setDeSalida(boolean deSalida) {
        this.deSalida = deSalida;
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
}
