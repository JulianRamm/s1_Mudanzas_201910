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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Daniel Machado
 */
public class ProveedorDetailDTO extends ProveedorDTO implements Serializable{
    

    
    
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
                System.out.println(subastas.size());
            }
            if(entidad.getOfertas() != null){
                ofertas = new ArrayList<>();
                for(OfertaEntity oferta: entidad.getOfertas()){
                    ofertas.add(new OfertaDTO(oferta) );
                }
                System.out.println(ofertas.size());
            }
            if(entidad.getConductores() != null){
                conductores = new ArrayList<>();
                for(ConductorEntity conductor: entidad.getConductores()){
                    conductores.add(new ConductorDTO(conductor) );
                }
                System.out.println(conductores.size());
            }
            if(entidad.getVehiculos() != null){
                vehiculos = new ArrayList<>();
                for(VehiculoEntity conductor: entidad.getVehiculos()){
                    vehiculos.add(new VehiculoDTO(conductor) );
                }
                System.out.println(conductores.size());

            }
        }
    }
    
    /**
     * @return the subastas
     */
    public List<SubastaDTO> getSubastas() {
        return subastas;
    }

    /**
     * @param subastas the subastas to set
     */
    public void setSubastas(List<SubastaDTO> subastas) {
        this.subastas = subastas;
    }

    /**
     * @return the ofertas
     */
    public List<OfertaDTO> getOfertas() {
        return ofertas;
    }

    /**
     * @param ofertas the ofertas to set
     */
    public void setOfertas(List<OfertaDTO> ofertas) {
        this.ofertas = ofertas;
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
    
    
    
    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del usuario para transformar a Entity
     */
    @Override
    public ProveedorEntity toEntity(){
        ProveedorEntity entidad = super.toEntity();
        if(getSubastas() != null){
            List<SubastaEntity> subastasEntity = new ArrayList<>();
            for(SubastaDTO subastaDTO : getSubastas()){
                subastasEntity.add(subastaDTO.toEntity());
            }
            entidad.setSubastas(subastasEntity);
        }
        if(getOfertas() != null){
            List<OfertaEntity> ofertasEntity = new ArrayList<>();
            for(OfertaDTO ofertaDTO : getOfertas()){
                ofertasEntity.add(ofertaDTO.toEntity());
            }
            entidad.setOfertas(ofertasEntity);
        }
        if(getConductores() != null){
            List<ConductorEntity> conductoresEntity = new ArrayList<>();
            for(ConductorDTO conductorDTO : getConductores()){
                conductoresEntity.add(conductorDTO.toEntity());
            }
            entidad.setConductores(conductoresEntity);
        }
        if(getVehiculos() != null){
            List<VehiculoEntity> vehucyulosEntity = new ArrayList<>();
            for(VehiculoDTO vehiculoDTO : getVehiculos()){
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
