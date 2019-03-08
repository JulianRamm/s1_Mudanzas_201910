/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import java.io.Serializable;

/**
 *
 * @author aj.gonzalezt
 */
public class OfertaDTO implements Serializable {
 
    private double valor;
     
    private Long idOferta;
    
    private String comentario;
    
    public OfertaDTO()
    {
        
    }

    
    public OfertaDTO(OfertaEntity oferEntity)
    {
        setComentario(oferEntity.getComentario());
        setValor(oferEntity.getValor());
        setidOferta(oferEntity.getId());
       
    }
 
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getidOferta() {
        return idOferta;
    }

    public void setidOferta(Long ofertaId) {
        this.idOferta = ofertaId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public OfertaEntity toEntity() {
        OfertaEntity oferEntity = new OfertaEntity();
       oferEntity.setId(idOferta);
       oferEntity.setComentario(comentario);
       oferEntity.setValor(valor);
        
        return oferEntity;
    }
    
    
    
}
