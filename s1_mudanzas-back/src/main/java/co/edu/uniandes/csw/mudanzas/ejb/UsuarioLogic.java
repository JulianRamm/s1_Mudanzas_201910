/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Usuario.
 * @author Luis Miguel
 */
@Stateless
public class UsuarioLogic {

    /**
     * Variable para acceder a la persistencia del usuario.
     */
    @Inject
    private UsuarioPersistence persistence;

    /**
     * Crea una usuario en la persistencia.
     *
     * @param usuario La entidad que representa el usuario a
     * persistir.
     * @return La entiddad del usuario luego de persistirla.
     * @throws BusinessLogicException Si el usuario a persistir ya existe.
     */
    public UsuarioEntity crearUsuario(UsuarioEntity usuario) throws BusinessLogicException {

        if (persistence.buscarUsuarioPorLogin(usuario.getLogin()) != null) {
            throw new BusinessLogicException("Ya existe un usuario con el login \"" + usuario.getLogin() + "\"");
        }
        usuario = persistence.create(usuario);
        return usuario;
    }
    
    /**
     * Obtener todos los usuarios existentes en la base de datos.
     *
     * @return una lista de usuarios.
     */
    public List<UsuarioEntity> getUsuarios()
    {
        List<UsuarioEntity> usuarios = persistence.findAll();
        return usuarios;
    }
    
    /**
     * Obtener un usuario por medio de su id.
     *
     * @param usuarioId: id del usuario para ser buscado.
     * @return el usuario solicitado por medio de su id.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public UsuarioEntity getUsuario(Long usuarioId) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = persistence.find(usuarioId);
        if (usuarioEntity == null)
            throw new BusinessLogicException("No existe tal usuario con id: " + usuarioId);
        return usuarioEntity;
    }
    
    /**
     * Obtener un usuario por medio de su login.
     *
     * @param usuarioLogin: login del usuario para ser buscado.
     * @return el usuario solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public UsuarioEntity getUsuario(String usuarioLogin) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = persistence.buscarUsuarioPorLogin(usuarioLogin);
        if (usuarioEntity == null)
            throw new BusinessLogicException("No existe tal usuario con login: " + usuarioLogin);
        return usuarioEntity;
    }
    
    /**
     * Actualizar un usuario.
     * @param nuevoUsuario: usuario con los cambios para ser actualizado,
     * por ejemplo el nombre.
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public UsuarioEntity updateUsuario(UsuarioEntity nuevoUsuario) {
        UsuarioEntity usuarioEntity = persistence.update(nuevoUsuario);
        return usuarioEntity;
    }
    
    /**
     * Borrar un usuario
     *
     * @param usuarioId: id del usuario a borrar
     * @throws BusinessLogicException Si el usuario a eliminar tiene tarjetas de credito.
     */
    public void deleteUsuario(Long usuarioId) throws BusinessLogicException {
        List<TarjetaDeCreditoEntity> tarjetas = getUsuario(usuarioId).getTarjetas();
        if (tarjetas != null && !tarjetas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el usuario con id: " + usuarioId + " porque tiene tarjetas de credito asociadas");
        }
        persistence.delete(usuarioId);
    }

}
