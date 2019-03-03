/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.transaction.UserTransaction;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Andres Gonzalez
 */
@Entity
public class SubastaEntity extends BaseEntity implements Serializable {

    private String subastaId;
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
    private ProveedorEntity proveedor;

    @PodamExclude
    @ManyToOne()
    private UsuarioEntity usuario;

    @PodamExclude
    @OneToMany(
            mappedBy = "subasta",
            fetch = FetchType.LAZY
    )
    private List<OfertaEntity> ofertas;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;

    public SubastaEntity() {

    }

    public String getSubastaId() {
        return subastaId;
    }

    public void setSubastaId(String subastaId) {
        this.subastaId = subastaId;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public List<OfertaEntity> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<OfertaEntity> ofertas) {
        this.ofertas = ofertas;
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
