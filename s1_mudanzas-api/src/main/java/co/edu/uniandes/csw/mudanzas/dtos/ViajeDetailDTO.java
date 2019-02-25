/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author je.osorio
 */
public class ViajeDetailDTO extends ViajeDTO implements Serializable {

    /**
     * Lista encadenada de CargaDTO que corresponde a que un viaje pude tener 1
     * o màs cargas asignadas
     */
    private List<CargaDTO> cargas;
    private List<DireccionDTO> direcciones;
    /**
     * @return the cargas
     */
    public List<CargaDTO> getCargas() {
        return cargas;
    }

    /**
     * @param cargas the cargas to set
     */
    public void setCargas(List<CargaDTO> cargas) {
        this.cargas = cargas;
    }

    /**
     * constructor de un objeto de tipo ViajeDetailDTO
     */
    public ViajeDetailDTO() {

    }

    /**
     * @return the direcciones
     */
    public List<DireccionDTO> getDirecciones() {
        return direcciones;
    }

    /**
     * @param direcciones the direcciones to set
     */
    public void setDirecciones(List<DireccionDTO> direcciones) {
        this.direcciones = direcciones;
    }
}
