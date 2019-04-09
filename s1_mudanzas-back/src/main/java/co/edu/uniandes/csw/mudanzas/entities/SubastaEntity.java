/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres Gonzalez
 */
@Entity
public class SubastaEntity extends BaseEntity implements Serializable {

   private static long serialVersionUID = 1L;

    
    /**
     * Atributo que representa el valor inicial de la subasta dado por el
     * usuario.
     */
    private double valorInicial;

    /**
     * Atributo que representa el valor final(actual) de la subasta.
     */
    private double valorFinal;

    @PodamExclude
    @ManyToOne
    (  
            fetch = FetchType.LAZY
    )
    private ProveedorEntity proveedor;

    @PodamExclude
    @ManyToOne
    (  
            fetch = FetchType.LAZY
    )
    private UsuarioEntity usuario;

    @PodamExclude
    @OneToMany(
            mappedBy = "subasta",
            fetch = FetchType.LAZY
    )
    private List<OfertaEntity> ofertas = new ArrayList<>();

    public SubastaEntity() {

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

    /**
     * @return the usuario
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the ofertas
     */
    public List<OfertaEntity> getOfertas() {
        return ofertas;
    }

    /**
     * @param ofertas the ofertas to set
     */
    public void setOfertas(List<OfertaEntity> ofertas) {
        this.ofertas = ofertas;
    }
    
}
