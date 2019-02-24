/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.UserTransaction;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Luis Miguel
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
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
     * Lista, coleccion que contiene todas las tarjetas de ese usuario. bla bla
     */
    @PodamExclude
    @OneToMany(
            mappedBy = "usuario", 
            fetch = FetchType.LAZY
    )
    List<TarjetaDeCreditoEntity> tarjetas;
    
    /**
     * Lista de cargas
     */
    @PodamExclude
    @OneToMany(
            mappedBy = "usuario", 
            fetch = FetchType.LAZY
    )
    private List<CargaEntity> cargas;
    /**
     * Constructor por defecto de la entidad.
     */
    public UsuarioEntity() {
        
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
     * @param lTarjetas la lista de tarjetas que se quiere cambiar.
     */
    public void setTarjetas(List<TarjetaDeCreditoEntity> lTarjetas)
    {
        this.tarjetas = lTarjetas;
    }
    
    /**
     * @return la lista de tarjetas de credito de un usuario. 
     */
    public List<TarjetaDeCreditoEntity> getTarjetas()
    {
        return tarjetas;
    }

    /**
     * @return the cargas
     */
    public List<CargaEntity> getCargas() {
        return cargas;
    }

    /**
     * @param cargas the cargas to set
     */
    public void setCargas(List<CargaEntity> cargas) {
        this.cargas = cargas;
    }
}
