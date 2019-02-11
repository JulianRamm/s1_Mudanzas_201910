/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

/**
 *
 * @author aj.gonzalezt
 */
public class OfertaDTO {
 
    private int valor;
     
    private Long idOferta;
    
    private String comentario;
    
    public OfertaDTO()
    {
        
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
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
    
    
    
}
