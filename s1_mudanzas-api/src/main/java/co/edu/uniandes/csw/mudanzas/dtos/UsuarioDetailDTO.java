/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import java.util.List;

/**
 *
 * @author Luis Miguel
 */
public class UsuarioDetailDTO extends UsuarioDTO{
    
    private List<TarjetaDeCreditoDTO> tarjetas;
    
    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO(){
    }
    
}
