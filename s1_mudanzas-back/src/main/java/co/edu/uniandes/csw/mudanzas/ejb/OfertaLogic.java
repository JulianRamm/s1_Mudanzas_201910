/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.OfertaPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Gonzalez
 */
@Stateless
public class OfertaLogic {

    @Inject
    private OfertaPersistence ofertaPersistence;
    
    @Inject
    private ProveedorPersistence proveedorPersistence;

    public OfertaEntity createOfertaProveedor(OfertaEntity oferta, String loginProveedor) throws BusinessLogicException {

        
        ProveedorEntity proveedorEntity = proveedorPersistence.findProveedorPorLogin(loginProveedor);
        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe ningun proveedor \"" + loginProveedor + "\"");
        }
        //Verificacion de existencia en el proveedor
        for (OfertaEntity subastaE : proveedorEntity.getOfertas()) {
            if (oferta.getId() == subastaE.getId()) {
                throw new BusinessLogicException("Ya existe un oferta con el id \"" + oferta.getId() + "\"");
            }
        }
        
        if (ofertaPersistence.find(oferta.getId()) != null) {
            throw new BusinessLogicException("la oferta con id: " + oferta.getId() + "ya existe");

        }

        if (oferta.getValor() < 0) {
            throw new BusinessLogicException("la oferta debe tener un valor mayor a cero");

        }
        
        proveedorEntity.getOfertas().add(oferta);
        ofertaPersistence.create(oferta);
        proveedorPersistence.update(proveedorEntity);

        return oferta;
    }
    
    public List<OfertaEntity> getOfertas()
    {
        return ofertaPersistence.findAll();
    }
    public List<OfertaEntity>getOfertasSubasta(String idSubasta)
    {
        return ofertaPersistence.findBySubasta(idSubasta);
    }
    public List<OfertaEntity>getOfertasProveedor(String idProveedor)
    {
        return ofertaPersistence.findByProveedor(idProveedor);
    }
    
    
    public OfertaEntity getOfertaSubasta(Long idOferta, String idSubasta) throws BusinessLogicException {
        OfertaEntity retornable = ofertaPersistence.findOneBySubasta(idSubasta, idOferta);
        if (retornable == null) {
            throw new BusinessLogicException("la oferta buscada no existe");

        }
        return retornable;
    }
    
    
    public OfertaEntity getOfertaProveedor(Long idOferta, String idProveedor) throws BusinessLogicException {
        OfertaEntity retornable = ofertaPersistence.findOneByProveedor(idProveedor, idOferta);
        if (retornable == null) {
            throw new BusinessLogicException("la oferta buscada no existe");

        }
        return retornable;
    }
    
    
    public void delete(Long idOferta)
    {
        ofertaPersistence.delete(idOferta);
    }
    public OfertaEntity updateOferta(OfertaEntity oferta)
    {
        return ofertaPersistence.update(oferta);
    }

}
