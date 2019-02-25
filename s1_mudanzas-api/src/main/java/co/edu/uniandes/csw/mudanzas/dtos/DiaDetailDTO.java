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
}
