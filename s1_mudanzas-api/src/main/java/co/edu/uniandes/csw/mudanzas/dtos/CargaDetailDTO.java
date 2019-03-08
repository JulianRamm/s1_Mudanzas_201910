/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author je.osorio
 */
public class CargaDetailDTO extends CargaDTO implements Serializable{
    
    private List<DireccionDTO> direcciones;
    
    public CargaDetailDTO() {
        
    }
    
    public CargaDetailDTO(CargaEntity entidad) {
        super(entidad);
        if(entidad != null) {
            if(entidad.getDirecciones() != null) {
                direcciones = new ArrayList<>();
                for(DireccionEntity d : entidad.getDirecciones()) {
                    direcciones.add(new DireccionDTO(d));
                }
            }
        }
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
