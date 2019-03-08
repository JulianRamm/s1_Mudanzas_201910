/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import java.io.Serializable;
import java.util.Date;

/**
 * TarjetaDeCreditoDTO Objeto de transferencia de datos de Usuarios. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 {
      "id": number,
      "nombreTarjeta": string,
      "numeroSerial": string,
      "codigoSeguridad": number,
      "usuario": string,
      "fechaVencimiento": string
  }
 </pre> Por ejemplo una tarjeta se representa asi:<br>
 * <pre>
 {
      "id": 321,
      "nombreTarjeta": "LUIS MIGUEL GOMEZ L",
      "numeroSerial": "987654325123",
      "codigoSeguridad": 951,
      "usuario": Luis Miguel Gomez Londono,
      "fechaVencimiento": "24/07/2019"
  }
 </pre>
 *
 * @author Luis Miguel
 */
public class TarjetaDeCreditoDTO implements Serializable {

    /**
     * Atributo que representa el id de la tarjeta.
     */
    private Long id;

    /**
     * Atributo que representa el nombre personalizado que se le quiere dar a la
     * tarjeta.
     */
    private String nombreTarjeta;

    /**
     * Atributo que representa el numero serial de la tarjeta
     */
    private String numeroSerial;

    /**
     * Atributo que representa el codigo de seguridad en la parte posterior de
     * la tarjeta.
     */
    private int codigoSeguridad;
    
    /**
     * Atributo que representa la fecha de vencimiento de la tarjeta de credito.
     */
    private Date fechaVencimiento;

    /**
     * Atributo que representa el nombre del usuario titular de la cuenta.
     */
    private UsuarioDTO usuario;
    
    /**
     * Constructor por defecto.
     */
    public TarjetaDeCreditoDTO() {

    }

    /**
     * Constructor que recibe una entidad y la convierte a DTO.
     *
     * @param tarjeta entidad con la que se va a crear el dto
     */
    public TarjetaDeCreditoDTO(TarjetaDeCreditoEntity tarjeta) {
        if (tarjeta != null) {
            this.id = tarjeta.getId();
            this.nombreTarjeta = tarjeta.getNombreTarjeta();
            this.numeroSerial = tarjeta.getNumeroSerial();
            this.codigoSeguridad = tarjeta.getCodigoSeguridad();
            this.fechaVencimiento = tarjeta.getFechaVencimiento();
            if (tarjeta.getUsuario() != null) {
                this.usuario = new UsuarioDTO(tarjeta.getUsuario());
            } else {
                this.usuario = null;
            }
        }
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
     * @return the nombreTarjeta
     */
    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    /**
     * @param nombreTarjeta the nombreTarjeta to set
     */
    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    /**
     * @return the numeroSerial
     */
    public String getNumeroSerial() {
        return numeroSerial;
    }

    /**
     * @param numeroSerial the numeroSerial to set
     */
    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    /**
     * @return the codigoSeguridad
     */
    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    /**
     * @param codigoSeguridad the codigoSeguridad to set
     */
    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Metodo que convierte una Tarjeta DTO a Entity
     *
     * @return TarjetaDeCreditoEntity El DTO ya convertido.
     */
    public TarjetaDeCreditoEntity toEntity() {
        TarjetaDeCreditoEntity tarjeta = new TarjetaDeCreditoEntity();
        tarjeta.setId(this.id);
        tarjeta.setNumeroSerial(this.numeroSerial);
        tarjeta.setCodigoSeguridad(this.codigoSeguridad);
        tarjeta.setNombreTarjeta(this.nombreTarjeta);
        tarjeta.setFechaVencimiento(this.fechaVencimiento);
        if (this.usuario != null)
        {
            tarjeta.setUsuario(this.usuario.toEntity());
        }
        return tarjeta;
    }

}
