/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;

/**
 *
 * @author estudiante
 */
public class ConductorDTO {

    private String nombre;

    private Long id;

    private double calificacion;

    private String telefono;

    public ConductorDTO(ConductorEntity entity) {
        if (entity != null) {
            this.nombre = entity.getNombre();
            this.id = entity.getId();
            this.calificacion = entity.getCalificacion();
            this.telefono = entity.getTelefono();
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the calificacion
     */
    public double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public ConductorEntity toEntity() {
        ConductorEntity conductor = new ConductorEntity();
        conductor.setId(this.getId());
        conductor.setNombre(this.getNombre());
        conductor.setTelefono(this.getTelefono());
        conductor.setCalificacion(this.getCalificacion());
        return conductor;
    }
}
