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
     
    private byte[] imagen;
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
            this.login = usuarioEntity.getLogin();
            this.nombre = usuarioEntity.getNombre();
            this.apellido = usuarioEntity.getApellido();
            this.password = usuarioEntity.getPassword();
            this.ciudadDeOrigen = usuarioEntity.getCiudadDeOrigen();
            this.correoElectronico = usuarioEntity.getCorreoElectronico();
            this.imagen = usuarioEntity.getImagen();
        }
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the ciudadDeOrigen
     */
    public String getCiudadDeOrigen() {
        return ciudadDeOrigen;
    }

    /**
     * @param ciudadDeOrigen the ciudadDeOrigen to set
     */
    public void setCiudadDeOrigen(String ciudadDeOrigen) {
        this.ciudadDeOrigen = ciudadDeOrigen;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que convierte el UsuarioDTO a un UsuarioEnity
     *
     * @return UsuarioEntity el usuario DTO ya convertido.
     */
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.id);
        usuarioEntity.setLogin(this.login);
        usuarioEntity.setPassword(this.password);
        usuarioEntity.setNombre(this.nombre);
        usuarioEntity.setApellido(this.apellido);
        usuarioEntity.setCorreoElectronico(this.correoElectronico);
        usuarioEntity.setCiudadDeOrigen(this.ciudadDeOrigen);
        usuarioEntity.setImagen(this.imagen);
        return usuarioEntity;
    }

    /**
     * @return the imagen
     */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}
