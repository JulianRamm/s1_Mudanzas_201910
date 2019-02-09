/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author je.osorio
 */
public class ViajeDetailDTO extends ViajeDTO implements Serializable{

    /**
     * Lista encadenada de CargaDTO que corresponde a que un viaje pude tener 1 o màs cargas asignadas
     */
	private LinkedList<CargaDTO> cargas;
	

    /**
     * @return the cargas
     */
    public LinkedList<CargaDTO> getCargas() {
        return cargas;
    }

    /**
     * @param cargas the cargas to set
     */
    public void setCargas(LinkedList<CargaDTO> cargas) {
        this.cargas = cargas;
    }
    /**
     * constructor de un objeto de tipo ViajeDetailDTO
     */
    public ViajeDetailDTO(){
		
	}
} 
        
    

