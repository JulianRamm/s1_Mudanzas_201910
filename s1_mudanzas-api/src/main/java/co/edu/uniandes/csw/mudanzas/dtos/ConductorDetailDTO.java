/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class ConductorDetailDTO extends ConductorDTO
{
    private List<VehiculoDTO> vehiculos;

    public ConductorDetailDTO(ConductorEntity entity) 
    {
        super(entity);
    }
    
    
}
