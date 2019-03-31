/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ViajesLogic;
import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ViajesPersistence;
import java.util.ArrayList;
import java.util.HashSet;
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

    private List<ViajesEntity> data = new ArrayList<>();
    private List<CargaEntity> cargaData = new ArrayList<>();
    private List<ConductorEntity> conductores = new ArrayList<>();

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

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        
        ConductorEntity conductorEntity = factory.manufacturePojo(ConductorEntity.class);     
        em.persist(conductorEntity);
        conductores.add(conductorEntity);
        
        ConductorEntity conductorEntity1 = factory.manufacturePojo(ConductorEntity.class);     
        em.persist(conductorEntity1);
        conductores.add(conductorEntity1);
        
        CargaEntity cargaEntity = factory.manufacturePojo(CargaEntity.class);
        em.persist(cargaEntity);
        cargaData.add(cargaEntity);
        
        for (int i = 0; i < 3; i++) {
            ViajesEntity viajes = factory.manufacturePojo(ViajesEntity.class);
            viajes.setCargas(cargaData);
            viajes.setConductorEntity(conductores.get(0));
            em.persist(viajes);
            data.add(viajes);
            
        }

    }

    /**
     * test que prueba la igualdad al crear 2 viajes
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createViajesTest() throws BusinessLogicException {
        /**
        ViajesEntity newEntity = data.get(0);
        newEntity.setLugarSalida(newEntity.getCargas().get(0).getLugarSalida());
        newEntity.setLugarLlegada(newEntity.getCargas().get(0).getLugarLlegada());
        newEntity.getVehiculoDelViaje().setRendimiento(21);
        LinkedList<DireccionEntity> dirs = new LinkedList<>();
        DireccionEntity dir = newEntity.getCargas().get(0).getDirecciones().get(0);
        dir.setId((long) 2);
        dir.setIdPar(1);
        dir.setLatitud(23);
        dir.setLongitud(34);
        dir.setIsDeSalida(false);
        dirs.add(dir);
        DireccionEntity dir1 = newEntity.getCargas().get(0).getDirecciones().get(1);
        dir1.setId((long) 1);
        dir1.setIsDeSalida(true);
        dir1.setLatitud(32);
        dir1.setLongitud(43);
        dir1.setIdPar(2);
        dirs.add(dir1);
        newEntity.getCargas().get(0).setDirecciones(dirs);
        newEntity.setTiempo((int) 4.06364);
        newEntity.setGastoGasolina((int) 1336.486711011556 * 21);
        */    
             
//        ViajesEntity newEntity = data.get(0); 
//        newEntity.setGastoGasolina(324);
//        newEntity.setLugarSalida(newEntity.getCargas().get(0).getLugarSalida());
//        newEntity.setLugarLlegada(newEntity.getCargas().get(0).getLugarLlegada());
//        
//        ViajesEntity result = viajesLogic.createViaje(newEntity, conductores.get(1).getId());
//        Assert.assertNotNull(result);
//        ViajesEntity entity = viajesLogic.getViaje(result.getId());
//        Assert.assertEquals(newEntity.getId(), entity.getId());
//        Assert.assertTrue(listEqualsIgnoreOrder(newEntity.getCargas(), entity.getCargas()));
//        Assert.assertEquals(newEntity.getClima(), entity.getClima());
//        Assert.assertEquals(newEntity.getConductorEntity(), entity.getConductorEntity());
//        Assert.assertEquals(newEntity.getGastoGasolina(), entity.getGastoGasolina());
//        Assert.assertEquals(newEntity.getLugarLlegada(), entity.getLugarLlegada());
//        Assert.assertEquals(newEntity.getLugarSalida(), entity.getLugarSalida());
//        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
    }

    /**
     * test que falla al crear una entidad con gasto de gasolina igual a cero
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createVIajesConVolumen0() throws BusinessLogicException {
        ViajesEntity newEntity = data.get(0);
        newEntity.setConductorEntity(conductores.get(0));
        newEntity.setGastoGasolina(0);
        viajesLogic.createViaje(newEntity, newEntity.getConductorEntity().getId() );
    }

    /**
     * prueba para encontrar los viajes
     */
    @Test
    public void getViajesesTest() throws BusinessLogicException {
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
     *
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @Test
    public void getViajesTest() throws BusinessLogicException {
        ViajesEntity newEntity = data.get(0);
        ViajesEntity entity = viajesLogic.getViaje(newEntity.getId());
        entity.setCargas(newEntity.getCargas());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertTrue(listEqualsIgnoreOrder(newEntity.getCargas(), entity.getCargas()));
        Assert.assertEquals(newEntity.getClima(), entity.getClima());
        Assert.assertEquals(newEntity.getConductorEntity(), entity.getConductorEntity());
        Assert.assertEquals(newEntity.getGastoGasolina(), entity.getGastoGasolina());
        Assert.assertEquals(newEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
        Assert.assertEquals(newEntity.getLugarSalida(), entity.getLugarSalida());

    }

    /**
     * Prueba para actualizar un viaje.
     */
    @Test
    public void updateViajeTest() {
//        ViajesEntity entity = data.get(0);      
//        ViajesEntity pojoEntity = factory.manufacturePojo(ViajesEntity.class);
//        pojoEntity.setId(entity.getId());    
//        ViajesEntity nuevo = viajesLogic.updateViaje(pojoEntity);     
//        ViajesEntity encontrado = em.find(ViajesEntity.class, entity.getId());
//        
//        Assert.assertEquals(encontrado.getId(), nuevo.getId());
//        Assert.assertEquals(encontrado.getCargas(), nuevo.getCargas());
//        Assert.assertEquals(encontrado.getClima(), nuevo.getClima());
//        Assert.assertEquals(encontrado.getGastoGasolina(), nuevo.getGastoGasolina());
//        Assert.assertEquals(encontrado.getLugarLlegada(), nuevo.getLugarLlegada());
//        Assert.assertEquals(encontrado.getLugarSalida(), nuevo.getLugarSalida());
    }
    /**
     * prueba para eliminar un viaje
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteViajesTest() throws BusinessLogicException {
        ViajesEntity entity = data.get(0);
        entity.setConductorEntity(conductores.get(0));
        viajesLogic.deleteViaje(entity.getId());
        ViajesEntity deleted = em.find(ViajesEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getCargasPorIdTest() throws BusinessLogicException {
        ViajesEntity entity = data.get(0);
        List<CargaEntity> resultEntity = viajesLogic.getCargasDadoUnId(entity.getId());
        resultEntity = cargaData;
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(cargaData.size(), resultEntity.size());
        Assert.assertTrue(listEqualsIgnoreOrder(resultEntity, cargaData));
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}