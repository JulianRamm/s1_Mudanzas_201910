/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author je.osorio
 */
@Entity
public class CargaEntity extends BaseEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    /**
     * Objeto viaje que contiene 1 o muchos objetos derivados de la clase CargaEntity, este objeto existe debido a la relación 
     * definida desde el modelo conceptual
     */
    @PodamExclude
    @ManyToOne
    ViajesEntity viaje;
    /**
     * Representa los datos de env�o de la carga que se lleva de un lugar a otro
     */
    @PodamExclude
    @OneToMany(
            mappedBy="carga",
            fetch=FetchType.LAZY
    )
    List<DireccionEntity> direcciones;
    /**
     * usuario de la carga
     */
    @PodamExclude
    @ManyToOne
    UsuarioEntity usuario;
    
    
    private String datosEnvio;

    /**
     * volumen de la carga en metros al cubo
     */
    private int volumen;

    /**
     * lista encadenada de im�genes de la carga del env�o
     */
    private LinkedList<String> imagenes;

    /**
     * direcci�n del lugar de salida de la carga
     */
    private String lugarSalida;

    /**
     * direcci�n del lugar de llegada de la carga
     */
    private String lugarLlegada;

    /**
     * fecha estimada de llegada definida por el proveedor
     */
    @Temporal(TemporalType.DATE)
    private Date fechaEstimadaLlegada;

    /**
     * fecha en la que la carga va a ser trasladada
     */
    @Temporal(TemporalType.DATE)
    private Date fechaEnvio;

    /**
     * observaciones sadicionales definidas por el cliente
     */
    private String observaciones;

    /**
     *
     */
    private int valorInicialS;
    
    /**
     * @return the datosEnvio
     */
    public String getDatosEnvio() {
        return datosEnvio;
    }

    /**
     * @param datosEnvio the datosEnvio to set
     */
    public void setDatosEnvio(String datosEnvio) {
        this.datosEnvio = datosEnvio;
    }

    /**
     * @return the volumen
     */
    public int getVolumen() {
        return volumen;
    }

    /**
     * @param volumen the volumen to set
     */
    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    /**
     * @return the imagenes
     */
    public LinkedList<String> getImagenes() {
        return imagenes;
    }

    /**
     * @param imagenes the imagenes to set
     */
    public void setImagenes(LinkedList<String> imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * @return the lugarSalida
     */
    public String getLugarSalida() {
        return lugarSalida;
    }

    /**
     * @param lugarSalida the lugarSalida to set
     */
    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    /**
     * @return the lugarLlegada
     */
    public String getLugarLlegada() {
        return lugarLlegada;
    }

    /**
     * @param lugarLlegada the lugarLlegada to set
     */
    public void setLugarLlegada(String lugarLlegada) {
        this.lugarLlegada = lugarLlegada;
    }

    /**
     * @return the fechaEstimadaLlegada
     */
    public Date getFechaEstimadaLlegada() {
        return fechaEstimadaLlegada;
    }

    /**
     * @param fechaEstimadaLlegada the fechaEstimadaLlegada to set
     */
    public void setFechaEstimadaLlegada(Date fechaEstimadaLlegada) {
        this.fechaEstimadaLlegada = fechaEstimadaLlegada;
    }

    /**
     * @return the fechaEnvio
     */
    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * @param fechaEnvio the fechaEnvio to set
     */
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the valorInicialS
     */
    public int getValorInicialS() {
        return valorInicialS;
    }

    /**
     * @param valorInicialS the valorInicialS to set
     */
    public void setValorInicialS(int valorInicialS) {
        this.valorInicialS = valorInicialS;
    }
    /**
     * Constructor por defecto
     */
    public CargaEntity() {

    }

    /**
     * @return the viaje
     */
    public ViajesEntity getViaje() {
        return viaje;
    }

    /**
     * @param viaje the viaje to set
     */
    public void setViaje(ViajesEntity viaje) {
        this.viaje = viaje;
    }

    /**
     * @return the direcciones
     */
    public List<DireccionEntity> getDirecciones() {
        return direcciones;
    }

    /**
     * @param direcciones the direcciones to set
     */
    public void setDirecciones(List<DireccionEntity> direcciones) {
        this.direcciones = direcciones;
    }
    /**
     * método que devualeve un par de latitudes longitudes dao el id del par
     * guarda en la posicion 0 la direccion inicial y en la posicion 1 la direccion final
     * @param id
     * @return 
     */
    public LinkedList<DireccionEntity> encontrarParDirecciones(long id){
        LinkedList<DireccionEntity> res=new LinkedList<>();       
        for(DireccionEntity dir : direcciones){
            if(dir.getIdPar()==id&&dir.getIsDeSalida()==true){
                res.add(0, dir);
            }
            else if(dir.getIdPar()==id&&dir.getIsDeSalida()==false){
                res.add(1, dir);
            }
        }
        return res;
    }
    /**
     * calcula la distancia entre dos diferenciales de latitud y longitud usando la formula harvesiana
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @return 
     */
    public double calcularDistancia(double lat1,double lat2,double lon1,double lon2){
        // distance between latitudes and longitudes 
        double dLat = Math.toRadians(lat2 - lat1); 
        double dLon = Math.toRadians(lon2 - lon1); 
  
        // convert to radians 
        lat1 = Math.toRadians(lat1); 
        lat2 = Math.toRadians(lat2); 
  
        // apply formulae 
        double a = Math.pow(Math.sin(dLat / 2), 2) +  
                   Math.pow(Math.sin(dLon / 2), 2) *  
                   Math.cos(lat1) *  
                   Math.cos(lat2); 
        double rad = 6371; 
        double c = 2 * Math.asin(Math.sqrt(a)); 
        return rad * c; 
    }

    /**
     * @return the usuario
     */
    public UsuarioEntity getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}
