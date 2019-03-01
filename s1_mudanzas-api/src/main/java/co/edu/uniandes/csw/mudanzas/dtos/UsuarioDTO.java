/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.io.Serializable;

/**
 * UsuarioDTO Objeto de transferencia de datos de Usuarios. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *  "id": number,
 *  "login": string,
 *  "password": string,
 *  "nombre": string,
 *  "apellido": string,
 *  "correo": string,
 *  "ciudad": string
 * }
 * </pre> Por ejemplo un usuario se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *  "id": 1234,
 *  "login": "p.navas",
 *  "password": "123",
 *  "nombre": "Luis Miguel",
 *  "apellido": "Gomez",
 *  "correo": "lm.gomezl@uniandes.edu.co",
 *  "ciudad": "Manizales"
 * }
 *
 * </pre>
 *
 * @author Luis Miguel
 */
public class UsuarioDTO implements Serializable {

    /**
     * Atributo que representa e identifica a un usuario
     */
    private Long id;

    /**
     * Atributo que representa el nombre de usuario de un Usuario
     */
    private String login;

    /**
     * Atributo que representa la contraseña de un Usuario
     */
    private String password;

    /**
     * Atributo que representa el nombre del usuario
     */
    private String nombre;

    /**
     * Atributo que representa el apellido del usuario.
     */
    private String apellido;

    /**
     * Atributo que representa el correo electronico del usuario
     */
    private String correoElectronico;

    /**
     * Atributo que representa la ciudad de origen del usuario.
     */
    private String ciudadDeOrigen;

    /**
     * Constructor por defecto
     */
    public UsuarioDTO() {
        //No se hace nada, se crea mediante 
    }

    /**
     * Convertir Entity a DTO
     *
     * @param usuarioEntity la entidad que se va a convertir a dto
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.login = usuarioEntity.getELogin();
            this.nombre = usuarioEntity.getENombre();
            this.apellido = usuarioEntity.getEApellido();
            this.password = usuarioEntity.getEPassword();
            this.ciudadDeOrigen = usuarioEntity.getECiudadDeOrigen();
            this.correoElectronico = usuarioEntity.getECorreoElectronico();
        }
    }

    /**
     * @return the login
     */
    public String getDTOLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setDTOLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getDTOPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setDTOPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nombre
     */
    public String getDTONombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setDTONombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getDTOApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setDTOApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correoElectronico
     */
    public String getDTOCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setDTOCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the ciudadDeOrigen
     */
    public String getDTOCiudadDeOrigen() {
        return ciudadDeOrigen;
    }

    /**
     * @param ciudadDeOrigen the ciudadDeOrigen to set
     */
    public void setDTOCiudadDeOrigen(String ciudadDeOrigen) {
        this.ciudadDeOrigen = ciudadDeOrigen;
    }

    /**
     * @return the id
     */
    public Long getDTOId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setDTOId(Long id) {
        this.id = id;
    }

    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.id);
        usuarioEntity.setELogin(this.login);
        usuarioEntity.setEPassword(this.password);
        usuarioEntity.setENombre(this.nombre);
        usuarioEntity.setEApellido(this.apellido);
        usuarioEntity.setECorreoElectronico(this.correoElectronico);
        usuarioEntity.setCiudadDeOrigen(this.ciudadDeOrigen);
        return usuarioEntity;
    }

}
