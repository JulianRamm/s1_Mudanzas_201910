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
public class CargaDetailDTO extends CargaDTO implements Serializable{
    private List<DireccionDTO> direcciones;

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
