/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Andres Gonzalez
 */
public class SubastaDetailDTO extends SubastaDTO implements Serializable{

    private List<OfertaDTO> ofertas= new ArrayList<>();

    public SubastaDetailDTO() {

    }

    public SubastaDetailDTO(SubastaEntity subastaEntity) {
        super(subastaEntity);
        if (subastaEntity != null) {
            if (subastaEntity.getOfertas() != null) {
                for (OfertaEntity ofertaActual : subastaEntity.getOfertas()) {
                       ofertas.add(new OfertaDTO(ofertaActual));
                }
            }
        }
    }

    public List<OfertaDTO> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<OfertaDTO> ofertas) {
        this.ofertas = ofertas;
    }
    
    @Override
    public SubastaEntity toEntity()
    {
        SubastaEntity subEntity = super.toEntity();
       if(ofertas!= null)
       {
           List<OfertaEntity> ofertasEntitys = new LinkedList<>();
           for (OfertaDTO ofertaActual : ofertas) {
               ofertasEntitys.add(ofertaActual.toEntity());
           }
           subEntity.setOfertas(ofertasEntitys);
       }
        
        
        return subEntity;
    }

   

}
