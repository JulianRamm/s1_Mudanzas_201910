/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres Gonzalez
 */
@Entity
public class SubastaEntity extends BaseEntity implements Serializable{
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
     /**
     * Atributo que representa el valor inicial de la subasta dado por el usuario. 
     */
    private double valorInicial;
    
     /**
     * Atributo que representa el valor final(actual) de la subasta. 
     */
    private double valorFinal;

    
    public SubastaEntity()
    {
                
    }
    /**
     * @return the valorInicial
     */
    public double getValorInicial() {
        return valorInicial;
    }

    /**
     * @param valorInicial the valorInicial to set
     */
    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    /**
     * @return the valorFinal
     */
    public double getValorFinal() {
        return valorFinal;
    }

    /**
     * @param valorFinal the valorFinal to set
     */
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
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
    
    
    
  
    
}
