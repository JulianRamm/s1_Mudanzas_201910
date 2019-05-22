/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class VehiculoDetailDTO extends VehiculoDTO implements Serializable
{
    
    private List<ConductorDTO> conductores;
    
    
    public VehiculoDetailDTO(VehiculoEntity entity)
    {
        super(entity);
        if(entity != null){
            
            if(entity.getConductores() != null){
                conductores = new ArrayList<>();
                for(ConductorEntity con: entity.getConductores()){
                    conductores.add(new ConductorDTO(con) );
                }
                System.out.println(conductores.size());
            }
        }
    }

    /**
     * @return the conductores
     */
    public List<ConductorDTO> getConductores() {
        return conductores;
    }

    /**
     * @param conductores the conductores to set
     */
    public void setConductores(List<ConductorDTO> conductores) {
        this.conductores = conductores;
    }
    
}
