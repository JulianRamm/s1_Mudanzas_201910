/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author je.osorio
 */
public class CargaDTO implements Serializable{
    /**
	 * Representa los datos de env�o de la carga que se lleva de un lugar a otro
	 */
	
	private String datosEnvio;

	/**
	 * volumen de la carga en metros al cubo
	 */
	
	private int volumen;

	/**
	 * lista encadenada de im�genes de la carga del env�o
	 */
	
	private LinkedList<String> imagenes;

	/**
	 * direcci�n del lugar de salida de la carga
	 */
	
	private String lugarSalida;

	/**
	 * direcci�n del lugar de llegada de la carga
	 */
	
	private String lugarLlegada;

	/**
	 * fecha estimada de llegada definida por el proveedor
	 */
	
	private String fechaEstimadaLlegada;

	/**
	 * fecha en la que la carga va a ser trasladada
	 */
	
	private String fechaEnvio;

	/**
	 * observaciones sadicionales definidas por el cliente
	 */
	
	private String observaciones;

	/**
	 * 
	 */
	
	private int valorInicialS;

	/**
	 * id de la carga 
	 */
	
	private long id;
        
        /**
         * Constructor por defecto
         */
        public CargaDTO() {
            
        }

    CargaDTO(CargaEntity carga) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the datosEnvio
     */
    public String getDatosEnvio() {
        return datosEnvio;
    }

    /**
     * @param datosEnvio the datosEnvio to set
     */
    public void setDatosEnvio(String datosEnvio) {
        this.datosEnvio = datosEnvio;
    }

    /**
     * @return the volumen
     */
    public int getVolumen() {
        return volumen;
    }

    /**
     * @param volumen the volumen to set
     */
    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    /**
     * @return the imagenes
     */
    public LinkedList<String> getImagenes() {
        return imagenes;
    }

    /**
     * @param imagenes the imagenes to set
     */
    public void setImagenes(LinkedList<String> imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * @return the lugarSalida
     */
    public String getLugarSalida() {
        return lugarSalida;
    }

    /**
     * @param lugarSalida the lugarSalida to set
     */
    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    /**
     * @return the lugarLlegada
     */
    public String getLugarLlegada() {
        return lugarLlegada;
    }

    /**
     * @param lugarLlegada the lugarLlegada to set
     */
    public void setLugarLlegada(String lugarLlegada) {
        this.lugarLlegada = lugarLlegada;
    }

    /**
     * @return the fechaEstimadaLlegada
     */
    public String getFechaEstimadaLlegada() {
        return fechaEstimadaLlegada;
    }

    /**
     * @param fechaEstimadaLlegada the fechaEstimadaLlegada to set
     */
    public void setFechaEstimadaLlegada(String fechaEstimadaLlegada) {
        this.fechaEstimadaLlegada = fechaEstimadaLlegada;
    }

    /**
     * @return the fechaEnvio
     */
    public String getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * @param fechaEnvio the fechaEnvio to set
     */
    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the valorInicialS
     */
    public int getValorInicialS() {
        return valorInicialS;
    }

    /**
     * @param valorInicialS the valorInicialS to set
     */
    public void setValorInicialS(int valorInicialS) {
        this.valorInicialS = valorInicialS;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    CargaEntity toEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
