/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.OfertaPersistence;
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
    private OfertaPersistence persistence;

    public OfertaEntity createOferta(OfertaEntity oferEntity) throws Exception {

        if (persistence.find(oferEntity.getId()) != null) {
            throw new BusinessLogicException("la oferta con id: " + oferEntity.getId() + "ya existe");

        }

        if (oferEntity.getValor() < 0) {
            throw new BusinessLogicException("la oferta debe tener un valor mayor a cero");

        }

        return persistence.create(oferEntity);
    }
    
    public List<OfertaEntity> getOfertas()
    {
        return persistence.findAll();
    }
    public List<OfertaEntity>getOfertasSubasta(String idSubasta)
    {
        return persistence.findBySubasta(idSubasta);
    }
    public List<OfertaEntity>getOfertasProveedor(String idProveedor)
    {
        return persistence.findByProveedor(idProveedor);
    }
    
    
    public OfertaEntity getOfertaSubasta(Long idOferta, String idSubasta) throws BusinessLogicException {
        OfertaEntity retornable = persistence.findOneBySubasta(idSubasta, idOferta);
        if (retornable == null) {
            throw new BusinessLogicException("la oferta buscada no existe");

        }
        return retornable;
    }
    
    
    public OfertaEntity getOfertaProveedor(Long idOferta, String idProveedor) throws BusinessLogicException {
        OfertaEntity retornable = persistence.findOneByProveedor(idProveedor, idOferta);
        if (retornable == null) {
            throw new BusinessLogicException("la oferta buscada no existe");

        }
        return retornable;
    }
    
    
    public void delete(Long idOferta)
    {
        persistence.delete(idOferta);
    }
    public OfertaEntity updateOferta(OfertaEntity oferta)
    {
        return persistence.update(oferta);
    }

}
