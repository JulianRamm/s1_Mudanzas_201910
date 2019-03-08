/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;

/**
 *
 * @author je.osorio
 */
public class DireccionDTO {

    /**
     * id del par latitudf,longitudf,latitudi,longitudi
     */
    private Long id;
    
    /**
     * latitud de la dirección
     */

    private Double latitud;
    /**
     * longitud de la dirección
     */

    private Double longitud;
    /**
     *  indica true si la dirección es de salida del origen o false si es de llegada al destino
     */
    
    private boolean deSalida;

    public DireccionDTO() {
        
    }
    
    public DireccionDTO(DireccionEntity d) {
        if(d != null) {
            this.id = d.getId();
            this.longitud = d.getLongitud();
            this.latitud = d.getLatitud();
            this.deSalida = d.getIsDeSalida();
        }
    }
    
    /**
     * @return the latitud
     */
    public Double getLatitud() {
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
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Double longitud) {
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
