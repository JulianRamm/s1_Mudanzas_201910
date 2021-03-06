/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class VehiculoPersistence {

    private static final Logger LOGGER = Logger.getLogger(VehiculoPersistence.class.getName());

    @PersistenceContext(unitName = "mudanzasPU")
    protected EntityManager em;

    /**
     * metodo que persiste una entidad
     * @param vehiculoEntity, entidad que se va a persistir
     * @return La entidad persistida
     */
    public VehiculoEntity create(VehiculoEntity vehiculoEntity) {
        em.persist(vehiculoEntity);
        return vehiculoEntity;
    }

    /**
     * Busca la entidad de un vehiculo por medio de su id.
     * @param vehiculoId id por el cual se va a buscar el vehículo
     * @return el vehículo que tenga ese iD.
     */
    public VehiculoEntity find(Long vehiculoId) {
        return em.find(VehiculoEntity.class, vehiculoId);
    }

    
    /**
     * Busca la entidad de un vehiculo por medio de su placa.
     * @param placa por la cual se va a buscar la entidad.
     * @return La entidad con la placa que entra por parámetro.
     */
    public VehiculoEntity findByPlaca(String placa) {
        VehiculoEntity rta;
        TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.placa = :pPlaca", VehiculoEntity.class);
        query = query.setParameter("pPlaca", placa);

        List<VehiculoEntity> samePlaca = query.getResultList();

        if (samePlaca == null) {
            rta = null;
        } else if (samePlaca.isEmpty()) {
            rta = null;
        } else {
            rta = samePlaca.get(0);
        }
        return rta;
    }

    
    /**
     * Busca la entidad de un vehiculo por medio de su ubicacion actual.
     * @param pId por la cual se va a buscar la entidad.
     * @return La entidad con el id de la ubicaciín actualo.
     */
    public VehiculoEntity findByUbicacionActual(Long pId) {
        VehiculoEntity rta;
        TypedQuery<VehiculoEntity> query = em.createQuery("select o from VehiculoEntity o where o.ubicacionActual.idPar = :pUbicacionActual", VehiculoEntity.class);
        query = query.setParameter("pUbicacionActual", pId);

        List<VehiculoEntity> sameUAct = query.getResultList();

        if (sameUAct == null) {
            rta = null;
        } else if (sameUAct.isEmpty()) {
            rta = null;
        } else {
            rta = sameUAct.get(0);
        }
        return rta;
    }
 /**
     * Busca la entidad de un vehiculo por medio de su agenda.
     * @param pId por la cual se va a buscar la entidad.
     * @param pPlaca con la cual se va a encontrar una entidad que se comparará(solo agenda).
     * @return La entidad con el id de la ubicaciín actualo.
     */
    
    public VehiculoEntity findByDia (long pId, String pPlaca) {
        VehiculoEntity rta = null;
        TypedQuery<DiaEntity> query = em.createQuery("select i from DiaEntity i where i.id = :pAgenda", DiaEntity.class);
        query = query.setParameter("pAgenda", pId);

        List<DiaEntity> sameAgenda = query.getResultList();

        if (sameAgenda == null) {
            rta = null;
        } else if (sameAgenda.isEmpty()) {
            rta = null;
        } else if(sameAgenda.get(0).getVehiculo() == null){
            rta =null;
        }
        else
        {
           if(sameAgenda.get(0).getVehiculo().getPlaca().equals(pPlaca))
           {
               rta = sameAgenda.get(0).getVehiculo();
           }
                
            
        }
        return rta;
    }

    /**
     * Elimina un vehiculo con
     *
     * @param vehiculoId de la base de datos.
     */
    public void delete(Long vehiculoId) {
        em.remove(find(vehiculoId));
    }

    /**
     * Actualiza una vehiculo con la bd
     *
     * @param cambiada vehiculo por el cual se va a cambiar la informacion
     * @return la vehiculo actualizada
     */
    public VehiculoEntity update(VehiculoEntity cambiada) {
        return em.merge(cambiada);
    }

    /**
     * Busca una vehiculo por el login del titular de la cuenta.
     *
     * @param login del proveedor que queremos buscar
     * @return la vehiculo que pertenece al proveedor que entra por parametro.
     */
    public VehiculoEntity findVehiculoPorLoginProveedor(String login, String placa) {
        TypedQuery query = em.createQuery("Select a From ProveedorEntity a where a.login = :login", ProveedorEntity.class);
        query = query.setParameter("login", login);
        List<ProveedorEntity> duenio = query.getResultList();
        VehiculoEntity resultado = null;
        if (duenio == null) {
            resultado = null;
        } else if (duenio.isEmpty()) {
            resultado = null;
        } else if (duenio.get(0).getVehiculos() == null) {
            resultado = null;
        } else {
            for (VehiculoEntity t : duenio.get(0).getVehiculos()) {
                if (t.getPlaca() == placa) {
                    resultado = t;
                }
            }
        }
        return resultado;
    }

    public List<VehiculoEntity> findAll() {
        TypedQuery<VehiculoEntity> query = em.createQuery("select u from VehiculoEntity u", VehiculoEntity.class);
        return query.getResultList();
    }
}
