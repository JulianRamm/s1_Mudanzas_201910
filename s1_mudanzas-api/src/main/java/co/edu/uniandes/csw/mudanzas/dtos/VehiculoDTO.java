/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class VehiculoDTO implements Serializable
{
    //Falta el atributo de la ubicaciòn actual con la clase de direccion
    private Long id;
    
    private Long idConductorActual;
    
    private int numeroConductores;
    
    private String marca;
    
    private String placa;
    
    private String color;
    
    private String imagen;
    
    private String dimensiones;
    
    private double rendimiento;
    
    public VehiculoDTO()
    {
        
    }
    
    public VehiculoDTO(VehiculoEntity entity)
    {
        if(entity != null)
        {
            this.idConductorActual = entity.getIdConductorActual();
            this.numeroConductores = entity.getNumeroConductores();
            this.marca = entity.getMarca();
            this.placa = entity.getPlaca();
            this.color = entity.getColor();
            this.imagen = entity.getImagen();
            this.dimensiones = entity.getDimensiones();
            this.rendimiento = entity.getRendimiento();
        }
    }

    
    public VehiculoEntity toEntity()
    {
        VehiculoEntity rta = new VehiculoEntity();
        rta.setIdConductorActual(this.idConductorActual);
        rta.setNumeroConductores(this.numeroConductores);
        rta.setMarca(this.marca);
        rta.setPlaca(this.placa);
        rta.setColor(this.color);
        rta.setImagen(this.imagen);
        rta.setRendimiento(this.rendimiento);
        rta.setDimensiones(this.dimensiones);
        return rta;
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

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the rendimiento
     */
    public double getRendimiento() {
        return rendimiento;
    }

    /**
     * @param rendimiento the rendimiento to set
     */
    public void setRendimiento(double rendimiento) {
        this.rendimiento = rendimiento;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
    
}
