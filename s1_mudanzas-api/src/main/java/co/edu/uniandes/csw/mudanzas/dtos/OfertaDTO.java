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

    private SubastaDTO subasta;

    private ProveedorDTO proveedor;

    private Long id;

    private String comentario;

    public OfertaDTO() {

    }

    public OfertaDTO(OfertaEntity oferEntity) {
        if (oferEntity != null) {
            setComentario(oferEntity.getComentario());
            setValor(oferEntity.getValor());
            setId(oferEntity.getId());
            SubastaDTO subastaDto = new SubastaDTO(oferEntity.getSubasta());
            setSubasta(subastaDto);
            setProveedor(new ProveedorDTO(oferEntity.getProveedor()));
        }

    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public SubastaDTO getSubasta() {
        return subasta;
    }

    public void setSubasta(SubastaDTO subasta) {
        this.subasta = subasta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long ofertaId) {
        this.id = ofertaId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public OfertaEntity toEntity() {
        OfertaEntity oferEntity = new OfertaEntity();
        oferEntity.setId(id);
        oferEntity.setComentario(comentario);
        oferEntity.setValor(valor);
        if (subasta != null) {
            oferEntity.setSubasta(subasta.toEntity());
        }
        if (proveedor != null) {
            oferEntity.setProveedor(proveedor.toEntity());
        }

        return oferEntity;
    }

}
