/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

/**
 *
 * @author je.osorio
 */
public class DireccionDTO {

    /**
     * latitud de la dirección
     */

    private int latitud;
    /**
     * longitud de la dirección
     */

    private int longitud;
    /**
     *  indica true si la dirección es de salida del origen o false si es de llegada al destino
     */
    
    private boolean deSalida;
    /**
     * id del par latitudf,longitudf,latitudi,longitudi
     */
    private Long idPar;
    /**
     * @return the latitud
     */
    public int getLatitud() {
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
    public int getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public DireccionDTO() {

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
    public Long getIdPar() {
        return idPar;
    }

    /**
     * @param idPar the idPar to set
     */
    public void setIdPar(Long idPar) {
        this.idPar = idPar;
    }
}
