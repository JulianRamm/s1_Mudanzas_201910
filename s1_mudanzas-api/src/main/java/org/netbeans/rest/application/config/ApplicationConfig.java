
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author estudiante
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.edu.uniandes.csw.mudanzas.filters.CORSFilter.class);
        resources.add(co.edu.uniandes.csw.mudanzas.mappers.BusinessLogicExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.mudanzas.mappers.ExceptionMapperA.class);
        resources.add(co.edu.uniandes.csw.mudanzas.mappers.WebApplicationExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.CargasUsuarioResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.ConductorVehiculoResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.OfertaProveedorResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.OfertasSubastaResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.ProveedorResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.SubastaResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.SubastasProveedorResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.TarjetasUsuarioResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.UsuarioResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.VehiculoDiaResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.VehiculosProveedorResource.class);
        resources.add(co.edu.uniandes.csw.mudanzas.resources.ViajesCargaResource.class);
    }
}

