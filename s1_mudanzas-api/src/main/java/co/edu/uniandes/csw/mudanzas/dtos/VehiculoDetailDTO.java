/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class VehiculoDetailDTO extends VehiculoDTO
{
    private List<DiaDTO> agendas;
    
    private List<ConductorDTO> conductores;
    
    public VehiculoDetailDTO(VehiculoEntity entity)
    {
        super(entity);
    }
    
}
