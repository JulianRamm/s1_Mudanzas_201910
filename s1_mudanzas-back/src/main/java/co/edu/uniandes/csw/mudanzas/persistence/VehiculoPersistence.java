/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
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

    public VehiculoEntity create(VehiculoEntity vehiculoEntity) {
        em.persist(vehiculoEntity);
        return vehiculoEntity;
    }

    public VehiculoEntity find(Long vehiculoId) {
        return em.find(VehiculoEntity.class, vehiculoId);
    }

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

    public VehiculoEntity findByUbicacionActual(DireccionEntity uAct) {
        VehiculoEntity rta;
        TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.ubicacionActual.idPar = :pUbicacionActual", VehiculoEntity.class);
        query = query.setParameter("pUbicacionActual", uAct.getIdPar());

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

    public VehiculoEntity findByDia(DiaEntity Agenda) {
        VehiculoEntity rta;
        TypedQuery<VehiculoEntity> query = em.createQuery("select e from VehiculoEntity e where e.agenda.id = :pAgenda", VehiculoEntity.class);
        query = query.setParameter("pAgenda", Agenda.getId());

        List<VehiculoEntity> sameAgenda = query.getResultList();

        if (sameAgenda == null) {
            rta = null;
        } else if (sameAgenda.isEmpty()) {
            rta = null;
        } else {
            rta = sameAgenda.get(0);
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
     * @param cambiada
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
        TypedQuery query = em.createQuery("Select e From ProveedorEntity e where e.login = :login", ProveedorEntity.class);
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
