/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class VehiculoDetailDTO extends VehiculoDTO implements Serializable
{
    private DiaDTO agenda;
    
    private List<ConductorDTO> conductores;
    
    public VehiculoDetailDTO(VehiculoEntity entity)
    {
        super(entity);
    }
    
}
