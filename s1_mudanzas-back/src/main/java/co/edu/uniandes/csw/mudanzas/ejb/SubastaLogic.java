/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
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
    private SubastaPersistence subastaPersistence;
    @Inject
    private UsuarioPersistence usuarioPersistence;
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
    public SubastaEntity createSubastaUsuario(SubastaEntity subasta, String loginUsuario) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = usuarioPersistence.findUsuarioPorLogin(loginUsuario);
        if (usuarioEntity == null) {
            throw new BusinessLogicException("No existe ningun usuario \"" + loginUsuario + "\"");
        }
        //Verificacion de existencia en el usuario
        for (SubastaEntity subastaE : usuarioEntity.getSubastas()) {
            if (subasta.getId() == subastaE.getId()) {
                throw new BusinessLogicException("Ya existe un subasta con el id \"" + subasta.getId() + "\"");
            }
        }
        subasta.setUsuario(usuarioEntity);
        if (subastaPersistence.findOneByUser(loginUsuario, subasta.getId()) != null) {
            throw new BusinessLogicException("la subasta con id: " + subasta.getId() + "ya existe para el usuario: " + loginUsuario);
        }
        if (subasta.getValorInicial() != subasta.getValorFinal()) {
            throw new BusinessLogicException("la subasta debe tener el mismo valor inicial y final al crear");
        }
        usuarioEntity.getSubastas().add(subasta);
        subastaPersistence.create(subasta);
        usuarioPersistence.update(usuarioEntity);
        return subasta;
    }
    
    public SubastaEntity createSubastaProveedor(SubastaEntity subasta, String loginProveedor) throws BusinessLogicException {
        ProveedorEntity proveedorEntity = proveedorPersistence.findProveedorPorLogin(loginProveedor);
        if (proveedorEntity == null) {
            throw new BusinessLogicException("No existe ningun proveedor \"" + loginProveedor + "\"");
        }
        //Verificacion de existencia en el proveedor
        for (SubastaEntity subastaE : proveedorEntity.getSubastas()) {
            if (subasta.getId() == subastaE.getId()) {
                throw new BusinessLogicException("Ya existe un subasta con el id \"" + subasta.getId() + "\"");
            }
        }
        subasta.setProveedor(proveedorEntity);
        if (subastaPersistence.findOneByProveedor(loginProveedor, subasta.getId()) != null) {
            throw new BusinessLogicException("la subasta con id: " + subasta.getId() + "ya existe para un proveedor con login: "+loginProveedor);
        }
        if (subasta.getValorInicial() != subasta.getValorFinal()) {
            throw new BusinessLogicException("la subasta debe tener el mismo valor inicial y final al crear");
        }
        proveedorEntity.getSubastas().add(subasta);
        subastaPersistence.create(subasta);
        proveedorPersistence.update(proveedorEntity);
        return subasta;
    }
    
    public List<SubastaEntity> getSubastas()
    {
        return subastaPersistence.findAll();
    }
    
    /**
     * método que retorna una subasta dada su id
     *
     * @param id
     * @return
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public SubastaEntity getSubasta(long id) throws BusinessLogicException {
        SubastaEntity subasta = subastaPersistence.find(id);
        if (subasta == null) {
            throw new BusinessLogicException("No existe una subasta con id: " + id);
        }
        return subasta;
    }
    
    /**
     * Obtener todas las subastas existentes en la base de datos que le
     * pertencen a un usuario en especifico.
     *
     * @param login
     * @return una lista de subastas de ese usuario.
     */
    public List<SubastaEntity> getSubastasUsuario(String login) {
        List<SubastaEntity> subastas = usuarioPersistence.findUsuarioPorLogin(login).getSubastas();
        return subastas;
    }
    
    /**
     * Obtener todas las subastas existentes en la base de datos que le
     * pertencen a un usuario en especifico.
     *
     * @return una lista de subastas de ese usuario.
     */
    public List<SubastaEntity> getSubastasProveedor(String login) {
        List<SubastaEntity> subastas = proveedorPersistence.findProveedorPorLogin(login).getSubastas();
        return subastas;
    }
    
    
    /**
     * retorna las subastas de un usuario
     *
     * @param loginUsuario
     * @param idSubasta
     * @return
     * @throws BusinessLogicException
     */
    public SubastaEntity getSubastaUsuario(String loginUsuario, Long idSubasta) throws BusinessLogicException {
        List<SubastaEntity> subastas = usuarioPersistence.findUsuarioPorLogin(loginUsuario).getSubastas();
        SubastaEntity subasta = subastaPersistence.find(idSubasta);
        int index = subastas.indexOf(subasta);
        if (index >= 0) {
            return subastas.get(index);
        }
        throw new BusinessLogicException("No existe tal subasta con propietario de login: " + loginUsuario);
    }
    
    /**
     * retorna las subastas de un usuario
     *
     * @param loginProveedor
     * @param idSubasta
     * @return
     * @throws BusinessLogicException
     */
    public SubastaEntity getSubastaProveedor(String loginProveedor, Long idSubasta) throws BusinessLogicException {
        List<SubastaEntity> subastas = proveedorPersistence.findProveedorPorLogin(loginProveedor).getSubastas();
        SubastaEntity subasta = subastaPersistence.find(idSubasta);
        int index = subastas.indexOf(subasta);
        if (index >= 0) {
            return subastas.get(index);
        }
        throw new BusinessLogicException("No existe tal subasta con proveedor de login: " + loginProveedor);
    }
    
    /**
     * método que elimina una subasta
     *
     * @param id
     */
    public void deleteSubastaUsuario(String loginUsuario, Long id) throws BusinessLogicException {
        SubastaEntity sub = getSubastaUsuario(loginUsuario, id);
        UsuarioEntity pertenece = sub.getUsuario();
        pertenece.getSubastas().remove(sub);
        subastaPersistence.delete(id);
        usuarioPersistence.update(pertenece);
    }
    
    /**
     * método que elimina una subasta
     *
     * @param id
     */
    public void deleteSubastaProveedor(String loginProveedor, Long id) throws BusinessLogicException {
        SubastaEntity sub = getSubastaUsuario(loginProveedor, id);
        ProveedorEntity pertenece = sub.getProveedor();
        pertenece.getSubastas().remove(sub);
        subastaPersistence.delete(id);
        proveedorPersistence.update(pertenece);
    }
    
//    public void delete(Long idSubasta)
//    {
//        subastaPersistence.delete(idSubasta);
//    }
    
    public SubastaEntity update(SubastaEntity cambio)
    {
        return subastaPersistence.update(cambio);
    }
}
