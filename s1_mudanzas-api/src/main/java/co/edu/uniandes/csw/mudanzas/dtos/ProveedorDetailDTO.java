/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Daniel Machado
 */
public class ProveedorDetailDTO extends ProveedorDTO{
    
    /*
    * Esta lista de tipo SubastaDTO contiene las subastas que estan asociadas a un Proveedor.
    */
    private List<SubastaDTO> subastas;
    
    private List<OfertaDTO> ofertas;
    
    private List<ConductorDTO> conductores;
    
    private List<VehiculoDTO> vehiculos;
    
    public ProveedorDetailDTO(){
        
    }
    
    public ProveedorDetailDTO(ProveedorEntity entidad){
        
        super(entidad);
        if(entidad != null){
            
            if(entidad.getSubastas() != null){
                subastas = new ArrayList<>();
                for(SubastaEntity subasta: entidad.getSubastas()){
                    subastas.add(new SubastaDTO(subasta) );
                }
            }
            if(entidad.getOfertas() != null){
                ofertas = new ArrayList<>();
                for(OfertaEntity oferta: entidad.getOfertas()){
                    ofertas.add(new OfertaDTO(oferta) );
                }
            }
            if(entidad.getConductores() != null){
                conductores = new ArrayList<>();
                for(ConductorEntity conductor: entidad.getConductores()){
                    conductores.add(new ConductorDTO(conductor) );
                }
            }
            if(entidad.getVehiculos() != null){
                vehiculos = new ArrayList<>();
                for(VehiculoEntity conductor: entidad.getVehiculos()){
                    vehiculos.add(new VehiculoDTO(conductor) );
                }
            }
        }
    }
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del usuario para transformar a Entity
     */
    @Override
    public ProveedorEntity toEntity(){
        ProveedorEntity entidad = super.toEntity();
        if(subastas != null){
            List<SubastaEntity> subastasEntity = new ArrayList<>();
            for(SubastaDTO subastaDTO : subastas){
                subastasEntity.add(subastaDTO.toEntity());
            }
            entidad.setSubastas(subastasEntity);
        }
        if(ofertas != null){
            List<OfertaEntity> ofertasEntity = new ArrayList<>();
            for(OfertaDTO ofertaDTO : ofertas){
                ofertasEntity.add(ofertaDTO.toEntity());
            }
            entidad.setOfertas(ofertasEntity);
        }
        if(conductores != null){
            List<ConductorEntity> conductoresEntity = new ArrayList<>();
            for(ConductorDTO conductorDTO : conductores){
                conductoresEntity.add(conductorDTO.toEntity());
            }
            entidad.setConductores(conductoresEntity);
        }
        if(vehiculos != null){
            List<VehiculoEntity> vehucyulosEntity = new ArrayList<>();
            for(VehiculoDTO vehiculoDTO : vehiculos){
                vehucyulosEntity.add(vehiculoDTO.toEntity());
            }
            entidad.setVehiculos(vehucyulosEntity);
        }
        return entidad;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
