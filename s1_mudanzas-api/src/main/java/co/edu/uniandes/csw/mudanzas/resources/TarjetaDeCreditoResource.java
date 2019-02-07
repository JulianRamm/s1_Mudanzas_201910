/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.resources;

import co.edu.uniandes.csw.mudanzas.dtos.TarjetaDeCreditoDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Luis Miguel
 */

@Path("usuarios/login/tarjetas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TarjetaDeCreditoResource {
    
    private static final Logger LOGGER = Logger.getLogger(TarjetaDeCreditoResource.class.getName());
    
    @POST
    public TarjetaDeCreditoDTO crearTarjeta(TarjetaDeCreditoDTO tarjeta) {
        return tarjeta;
    }
    
    @GET
    public List<TarjetaDeCreditoDTO> getTarjetas() {
        return null;
    }
    
    
    
}
