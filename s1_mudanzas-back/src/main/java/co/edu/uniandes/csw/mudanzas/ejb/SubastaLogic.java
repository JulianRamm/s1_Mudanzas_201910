/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Gonzalez
 */
@Stateless
public class SubastaLogic {

    @Inject
    private SubastaPersistence persistence;

    public SubastaEntity createSubasta(SubastaEntity subEntity) throws BusinessLogicException {
        if (persistence.find(subEntity.getId()) != null) {
            throw new BusinessLogicException("la subasta con id: " + subEntity.getId() + "ya existe");
        }
        if (subEntity.getValorInicial() != subEntity.getValorFinal()) {
            throw new BusinessLogicException("la subasta debe tener el mismo valor inicial y final al crear");

        }
        return persistence.create(subEntity);
    }
    
    public List<SubastaEntity> getOfertas()
    {
        return persistence.findAll();
    }
    
    public List<SubastaEntity> getSubastassUsuario(String loginUsuario)
    {
        return persistence.findByUser(loginUsuario);
    }
    
    public List<SubastaEntity> getSubastasProveedor(String loginProveedor)
    {
        return persistence.findByProveedor(loginProveedor);
    }
    
    
    public SubastaEntity getSubastaUsuario(Long idSubasta, String loginUsuario) throws BusinessLogicException
    {
        SubastaEntity retornable = persistence.findOneByUser(loginUsuario, idSubasta);
        if(retornable == null)
        {
            throw new BusinessLogicException("La subasta buscada no existe");
        }
        return retornable;
    }
    
    public SubastaEntity getSubastaProveedor(Long idSubasta, String loginProveedor) throws BusinessLogicException
    {
        SubastaEntity retornable = persistence.findOneByProveedor(loginProveedor, idSubasta);
        if(retornable == null)
        {
            throw new BusinessLogicException("La subasta buscada no existe");
        }
        return retornable;
    }
    
    public void delete(Long idSubasta)
    {
        persistence.delete(idSubasta);
    }
    
    public SubastaEntity update(SubastaEntity cambio)
    {
        return persistence.update(cambio);
    }
}
