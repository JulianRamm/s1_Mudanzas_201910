/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres Gonzalez
 */

@Entity
public class OfertaEntity extends BaseEntity implements Serializable{
    
    private static long serialVersionUID = 1L;
    
     /**
     * Atributo que representa comentario dado por el proveedor. 
     */
    private String comentario;
    
     /**
     * Atributo que representa el valor de la oferta de un proveedor. 
     */
    private double valor;
    
    @PodamExclude
    @ManyToOne
    (  
            fetch = FetchType.LAZY
    )
    private SubastaEntity subasta;
    
    @PodamExclude
    @ManyToOne
    (  
        fetch = FetchType.LAZY
    )
    private ProveedorEntity proveedor;
    
    public OfertaEntity()
    {
        
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the subasta
     */
    public SubastaEntity getSubasta() {
        return subasta;
    }

    /**
     * @param subasta the subasta to set
     */
    public void setSubasta(SubastaEntity subasta) {
        this.subasta = subasta;
    }
    
}
