/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import java.io.Serializable;

/**
 *
 * @author Luis Miguel
 */
public class TarjetaDeCreditoDTO implements Serializable{
    
    /**
     * Atributo que representa el id de la tarjeta.
     */
    private Long idTarjeta;
    
    /**
     * Atributo que representa el nombre personalizado que se le quiere dar a la tarjeta.
     */
    private String nombreTarjeta;
    
    /**
     * Atributo que representa el numero serial de la tarjeta
     */
    private int numeroSerial;
    
    /**
     * Atributo que representa el codigo de seguridad en la parte posterior de la tarjeta.
     */
    private int codigoSeguridad;
    
    /**
     * Atributo que representa el nombre del usuario titular de la cuenta. 
     */
    private UsuarioDTO titularCuenta;
    
    /**
     * Atributo que representa la fecha de vencimiento de la tarjeta de credito.
     */
    private String fechaVencimiento;

    /**
     * Constructor por defecto.
     */
    public TarjetaDeCreditoDTO(TarjetaDeCreditoEntity tarjetaEntity){
        
    }
    
    /**
     * @return the idTarjeta
     */
    public Long getIdTarjeta() {
        return idTarjeta;
    }

    /**
     * @param id the id to set
     */
    public void setIdTarjeta(Long id) {
        this.idTarjeta = id;
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
    public int getNumeroSerial() {
        return numeroSerial;
    }

    /**
     * @param numeroSerial the numeroSerial to set
     */
    public void setNumeroSerial(int numeroSerial) {
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
    public UsuarioDTO getTitularCuenta() {
        return titularCuenta;
    }

    /**
     * @param titularCuenta the titularCuenta to set
     */
    public void setTitularCuenta(UsuarioDTO titularCuenta) {
        this.titularCuenta = titularCuenta;
    }

    /**
     * @return the fechaVencimiento
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
       
}
