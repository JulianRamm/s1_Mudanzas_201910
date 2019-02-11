/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.util.List;

/**
 *
 * @author estudiante
 */
public class ProveedorDetailDTO extends ProveedorDTO{
    
        /*
    * Esta lista de tipo SubastaDTO contiene las subastas que estan asociadas a un Proveedor.
     */
    private List<SubastaDTO> subastas;
    
    public ProveedorDetailDTO(){
        
    }
}
