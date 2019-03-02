/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.UserTransaction;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Luis Miguel
 */
@Entity
public class TarjetaDeCreditoEntity extends BaseEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * Atributo que representa el nombre personalizado que se le quiere dar a la
     * tarjeta.
     */
    private String nombreTarjeta;

    /**
     * Atributo que representa el numero serial de la tarjeta
     */
    private String numeroSerial;

    /**
     * Atributo que representa el codigo de seguridad en la parte posterior de
     * la tarjeta.
     */
    private int codigoSeguridad;

    /**
     * Atributo que representa el nombre del usuario titular de la cuenta.
     */
    private String titularCuenta;

    /**
     * Atributo que representa la fecha de vencimiento de la tarjeta de credito.
     */
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    /**
     * Atributo que representa al usuario dueno de la tarjeta.
     */
    @PodamExclude
    @ManyToOne()
    private UsuarioEntity usuario;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;

    /**
     * Constructor por defecto.
     */
    public TarjetaDeCreditoEntity() {

    }

    /**
     * @return the nombreTarjeta
     */
    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    /**
     * @param nombreTarjeta the nombreTarjeta to set
     */
    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    /**
     * @return the numeroSerial
     */
    public String getNumeroSerial() {
        return numeroSerial;
    }

    /**
     * @param numeroSerial the numeroSerial to set
     */
    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    /**
     * @return the codigoSeguridad
     */
    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * @param codigoSeguridad the codigoSeguridad to set
     */
    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    /**
     * @return the titularCuenta
     */
    public String getTitularCuenta() {
        return titularCuenta;
    }

    /**
     * @param titularCuenta the titularCuenta to set
     */
    public void setTitularCuenta(String titularCuenta) {
        this.titularCuenta = titularCuenta;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return el usuario al cual le pertenece esta tarjeta.
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * @param user que se establecera como el usuario propietario.
     */
    public void setUsuario(UsuarioEntity user) {
        usuario = user;
    }

}
