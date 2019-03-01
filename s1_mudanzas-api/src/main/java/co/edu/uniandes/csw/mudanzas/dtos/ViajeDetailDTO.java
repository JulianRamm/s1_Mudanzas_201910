/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
     * constructor vacío
     */
    public ViajeDetailDTO(){
        
    }
    /**
     * constructor de un objeto de tipo ViajeDetailDTO con una entity
     *
     * @param viajesEntity
     */
    public ViajeDetailDTO(ViajesEntity viajesEntity) {
        super(viajesEntity);
        if (viajesEntity != null) {
            if (viajesEntity.getCargas() != null) {
                cargas = new ArrayList<>();
                for (CargaEntity carga : viajesEntity.getCargas()) {
                    cargas.add(new CargaDTO(carga));
                }
            }
        }
    }
    @Override
    public ViajesEntity toEntity(){
        ViajesEntity viajesEntity =super.toEntity();
        if(cargas!=null){
            List<CargaEntity> cargasEntity= new ArrayList<>();
            for (CargaDTO cargaDTO : cargas) {
                cargasEntity.add(cargaDTO.toEntity());
            }
            viajesEntity.setCargas(cargasEntity);
        }
        return viajesEntity;
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
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
