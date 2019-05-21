/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class DiaDetailDTO extends DiaDTO
{
    private List<VehiculoDTO> vehiculos;
    
    public DiaDetailDTO(DiaEntity entity)
    {
        super(entity);
    }

    /**
     * @return the vehiculos
     */
    public List<VehiculoDTO> getVehiculos() {
        return vehiculos;
    }

    /**
     * @param vehiculos the vehiculos to set
     */
    public void setVehiculos(List<VehiculoDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }
}
