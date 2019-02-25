/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ViajesPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author je.osorio
 */
@RunWith(Arquillian.class)
public class ViajesLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    

    @Inject
    private ViajesLogic viajesLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ViajesEntity> data = new ArrayList<ViajesEntity>();
    private List<CargaEntity> cargaData = new ArrayList<CargaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ViajesEntity.class.getPackage())
                .addPackage(ViajesLogic.class.getPackage())
                .addPackage(ViajesPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ViajesEntity").executeUpdate();
        em.createQuery("delete from CargaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ViajesEntity viajes = factory.manufacturePojo(ViajesEntity.class);
            em.persist(viajes);
            data.add(viajes);
            if (i == 0) {
                cargaData.get(i).setViaje(viajes);
            }
        }
        for (int i = 0; i < 3; i++) {
            CargaEntity cargaEntity = factory.manufacturePojo(CargaEntity.class);
            em.persist(cargaEntity);
            cargaData.add(cargaEntity);         
        }
    }
    /**
     * test que prueba la igualdad al crear 2 viajes
     * @throws BusinessLogicException 
     */
    @Test
    public void createViajesTest() throws BusinessLogicException {
        ViajesEntity newEntity = factory.manufacturePojo(ViajesEntity.class);
        ViajesEntity result = viajesLogic.createViajes(newEntity);
        Assert.assertNotNull(result);
        ViajesEntity entity = em.find(ViajesEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCargas(), entity.getCargas());
        Assert.assertEquals(newEntity.getClima(), entity.getClima());
        Assert.assertEquals(newEntity.getConductor(), entity.getConductor());
        Assert.assertEquals(newEntity.getGastoGasolina(), entity.getGastoGasolina());
        Assert.assertEquals(newEntity.getHoraLlegada(), entity.getHoraLlegada());
        Assert.assertEquals(newEntity.getHoraPartida(), entity.getHoraPartida());
        Assert.assertEquals(newEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(newEntity.getLugarSalida(), entity.getLugarSalida());
        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
        Assert.assertEquals(newEntity.getVehiculoDelViaje(), entity.getVehiculoDelViaje());
    }
    /**
     * test que falla al crear una entidad con gasto de gasolina igual a cero
     * @throws BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createVIajesConMismoNombreTest() throws BusinessLogicException {
        ViajesEntity newEntity = factory.manufacturePojo(ViajesEntity.class);
        newEntity.setGastoGasolina(0);
        viajesLogic.createViajes(newEntity);
    }
    /**
     * prueba para encontrar los viajes
     */
    @Test
    public void getViajesesTest() {
        List<ViajesEntity> list = viajesLogic.getViajes();
        Assert.assertEquals(data.size(), list.size());
        for (ViajesEntity entity : list) {
            boolean found = false;
            for (ViajesEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     /**
     * Prueba para consultar un Viajes.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @Test
    public void getViajesTest() throws BusinessLogicException {
        ViajesEntity newEntity = data.get(0);
        ViajesEntity entity = viajesLogic.getViaje(newEntity.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCargas(), entity.getCargas());
        Assert.assertEquals(newEntity.getClima(), entity.getClima());
        Assert.assertEquals(newEntity.getConductor(), entity.getConductor());
        Assert.assertEquals(newEntity.getGastoGasolina(), entity.getGastoGasolina());
        Assert.assertEquals(newEntity.getHoraLlegada(), entity.getHoraLlegada());
        Assert.assertEquals(newEntity.getHoraPartida(), entity.getHoraPartida());
        Assert.assertEquals(newEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(newEntity.getLugarSalida(), entity.getLugarSalida());
        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
        Assert.assertEquals(newEntity.getVehiculoDelViaje(), entity.getVehiculoDelViaje());
    }
    /**
     * Prueba para actualizar un viaje.
     */
    @Test
    public void updateViajeTest() {
        ViajesEntity entity = data.get(0);
        ViajesEntity pojoEntity = factory.manufacturePojo(ViajesEntity.class);
        pojoEntity.setId(entity.getId());
        viajesLogic.updateViaje(pojoEntity);
        ViajesEntity resp = em.find(ViajesEntity.class, entity.getId());
         Assert.assertNotNull(entity);
        Assert.assertEquals(resp.getId(), pojoEntity.getId());
        Assert.assertEquals(resp.getCargas(), pojoEntity.getCargas());
        Assert.assertEquals(resp.getClima(), pojoEntity.getClima());
        Assert.assertEquals(resp.getConductor(), pojoEntity.getConductor());
        Assert.assertEquals(resp.getGastoGasolina(), pojoEntity.getGastoGasolina());
        Assert.assertEquals(resp.getHoraLlegada(), pojoEntity.getHoraLlegada());
        Assert.assertEquals(resp.getHoraPartida(), pojoEntity.getHoraPartida());
        Assert.assertEquals(resp.getLugarLlegada(), pojoEntity.getLugarLlegada());
        Assert.assertEquals(resp.getLugarSalida(), pojoEntity.getLugarSalida());
        Assert.assertEquals(resp.getTiempo(), pojoEntity.getTiempo());
        Assert.assertEquals(resp.getVehiculoDelViaje(), pojoEntity.getVehiculoDelViaje());
    }
    /**
     * prueba para eliminar un viaje
     * @throws BusinessLogicException 
     */
    @Test
    public void deleteViajesTest() throws BusinessLogicException {
        ViajesEntity entity = data.get(1);
        viajesLogic.deleteViaje(entity.getId());
        ViajesEntity deleted = em.find(ViajesEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
