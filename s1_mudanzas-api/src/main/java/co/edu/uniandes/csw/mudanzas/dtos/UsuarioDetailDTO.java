/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.List;

/**
 *
 * @author Luis Miguel
 */
public class UsuarioDetailDTO extends UsuarioDTO {
    
    /*
    * Esta lista de tipo TarjetaDeCreditoDTO contiene las Tarjetas De Credito que estan asociados a un Usuario.
     */
    private List<TarjetaDeCreditoDTO> tarjetas;
    
    /*
    * Esta lista de tipo SubastaDTO contiene las subastas que estan asociadas a un Usuario.
     */
    private List<SubastaDTO> subastas;
    
    /*
    * Esta lista de tipo CargaDTO contiene las cargas que estan asociadas a un Usuario.
     */
    private List<CargaDTO> cargas;
    
    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO(){
    }

    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
