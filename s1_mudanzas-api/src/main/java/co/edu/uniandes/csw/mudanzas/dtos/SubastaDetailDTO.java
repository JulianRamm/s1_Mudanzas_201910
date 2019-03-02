/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.util.List;

/**
 *
 * @author Andres Gonzalez
 */
public class SubastaDetailDTO extends SubastaDTO
{
       private List<OfertaDTO> Ofertas;
       
       public SubastaDetailDTO()
       {
           
       }

    public List<OfertaDTO> getOfertas() {
        return Ofertas;
    }

    public void setOfertas(List<OfertaDTO> Ofertas) {
        this.Ofertas = Ofertas;
    }
       
       
}
