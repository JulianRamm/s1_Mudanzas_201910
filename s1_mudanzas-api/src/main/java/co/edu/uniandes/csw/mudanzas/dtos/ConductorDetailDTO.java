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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Daniel Machado
 */
public class ConductorDetailDTO extends ConductorDTO implements Serializable
{
    private List<VehiculoDTO> vehiculos;
    
    /**
     *Constructor vacio
     */
    public ConductorDetailDTO(){
        
    }
    
    public ConductorDetailDTO(ConductorEntity entity) 
    {
        super(entity);
        if(entity != null){
            if(entity.getVehiculos() != null){
                vehiculos = new ArrayList<>();
                for(VehiculoEntity carro : entity.getVehiculos()){
                    vehiculos.add(new VehiculoDTO(carro));
                    System.out.print("ld,fld,lg,f,bgsd");
                }
            }
            
        }
    }
    public List<VehiculoDTO> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }
    
    @Override
    public ConductorEntity toEntity(){
        ConductorEntity conductorEntity =super.toEntity();
        if(vehiculos!=null){
            List<VehiculoEntity> vehiculosEntity= new ArrayList<>();
            for (VehiculoDTO carroDTO : vehiculos) {
                vehiculosEntity.add(carroDTO.toEntity());
            }
            conductorEntity.setVehiculos(vehiculosEntity);
        }
        return conductorEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    
    
    
}
