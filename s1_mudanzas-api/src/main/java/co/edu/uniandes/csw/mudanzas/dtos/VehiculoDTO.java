/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author estudiante
 */
public class VehiculoDTO 
{
    private String imagen;
    
    private Long idConductorActual;
    
    private int numeroConductores;
    
    private String marca;
    
    private String placa;
    
    private String ubicacion;
    
    private String dimensiones;
    
    public VehiculoDTO()
    {
        
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
    /**
     * @return the idConductorActual
     */
    public Long getIdConductorActual() {
        return idConductorActual;
    }

    /**
     * @param idConductorActual the idConductorActual to set
     */
    public void setIdConductorActual(Long idConductorActual) {
        this.idConductorActual = idConductorActual;
    }

    /**
     * @return the numeroConductores
     */
    public int getNumeroConductores() {
        return numeroConductores;
    }

    /**
     * @param numeroConductores the numeroConductores to set
     */
    public void setNumeroConductores(int numeroConductores) {
        this.numeroConductores = numeroConductores;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the dimensiones
     */
    public String getDimensiones() {
        return dimensiones;
    }

    /**
     * @param dimensiones the dimensiones to set
     */
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
    
}
