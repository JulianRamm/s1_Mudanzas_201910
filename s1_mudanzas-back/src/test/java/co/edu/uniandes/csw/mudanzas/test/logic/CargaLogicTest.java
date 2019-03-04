/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.CargaLogic;
import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.entities.ViajesEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.CargaPersistence;
import static com.sun.enterprise.security.perms.SMGlobalPolicyUtil.CommponentType.car;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
public class CargaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();
    /**
     * Inyeccion de la dependencia a la clase CargaLogic cuyos metodos se van a
     * probar.
     */
    @Inject
    private CargaLogic cargaLogic;

    /**
     * Atributo que instancia a un Carga.
     */
    @Inject
    private CargaPersistence ep;

    /**
     * Llamamos al encargado de la BD
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<CargaEntity> data = new ArrayList<CargaEntity>();

    /**
     * Atributo que almacena un usuario duenio de muchas tarjetas.
     */
    private UsuarioEntity usuarioData;
    private ViajesEntity viajeData;
    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CargaEntity.class.getPackage())
                .addPackage(CargaLogic.class.getPackage())
                .addPackage(CargaPersistence.class.getPackage())
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
        em.createQuery("delete from CargaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        ViajesEntity viaje=factory.manufacturePojo(ViajesEntity.class);
        em.persist(viaje);
        viajeData = viaje;
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(usuario);
        usuarioData = usuario;
        for (int i = 0; i < 3; i++) {
            CargaEntity entity = factory.manufacturePojo(CargaEntity.class);
            entity.setUsuario(usuarioData);
            entity.setViaje(viajeData);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Editorial.
     *
     *
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @Test
    public void createCargaTest() throws BusinessLogicException {
        CargaEntity newEntity = data.get(0);
        newEntity.setVolumen(344);
        Date a = new Date(2019, 4, 25, 10, 0);
        Date b = new Date(2019,4,25,20,0);
        Date c= new Date(2019,4,25,2,0);
        newEntity.getViaje().setTiempo(b.getHours());
        newEntity.setFechaEstimadaLlegada(a);
        newEntity.setFechaEnvio(c);
        CargaEntity result = cargaLogic.createCarga(newEntity, usuarioData.getLogin());
        Assert.assertNotNull(result);
        CargaEntity entity = em.find(CargaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDatosEnvio(), entity.getDatosEnvio());
        Assert.assertEquals(newEntity.getDirecciones(), entity.getDirecciones());
        Assert.assertEquals(newEntity.getImagenes(), entity.getImagenes());
        Assert.assertEquals(newEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(newEntity.getLugarSalida(), entity.getLugarSalida());
        Assert.assertEquals(newEntity.getObservaciones(), entity.getObservaciones());
        Assert.assertEquals(newEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(newEntity.getViaje(), entity.getViaje());
        Assert.assertEquals(newEntity.getVolumen(), entity.getVolumen());
    }

    /**
     * Prueba para crear una carga que tiene volumen =0
     *
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createCargaVolumen0() throws BusinessLogicException {
        CargaEntity newEntity = factory.manufacturePojo(CargaEntity.class);
        int haber = 1;
        newEntity.setVolumen(haber);
        cargaLogic.createCarga(newEntity, usuarioData.getLogin());
    }

    /**
     * Prueba para consultar la lista de cargas.
     */
    @Test
    public void getEditorialsTest() {
        List<CargaEntity> list = cargaLogic.getCargas();
        Assert.assertEquals(data.size(), list.size());
        for (CargaEntity entity : list) {
            boolean found = false;
            for (CargaEntity storedEntity : data) {
                if (Objects.equals(entity.getId(), storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una carga.
     *
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    @Test
    public void getCargaTest() throws BusinessLogicException {
        CargaEntity entity = data.get(0);
        CargaEntity resultEntity = cargaLogic.getCarga(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDatosEnvio(), entity.getDatosEnvio());
        Assert.assertEquals(resultEntity.getDirecciones(), entity.getDirecciones());
        Assert.assertEquals(resultEntity.getImagenes(), entity.getImagenes());
        Assert.assertEquals(resultEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(resultEntity.getLugarSalida(), entity.getLugarSalida());
        Assert.assertEquals(resultEntity.getObservaciones(), entity.getObservaciones());
        Assert.assertEquals(resultEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(resultEntity.getViaje(), entity.getViaje());
        Assert.assertEquals(resultEntity.getVolumen(), entity.getVolumen());
    }

    /**
     * Prueba para actualizar una carga.
     */
    @Test
    public void updateCargaTest() {
        CargaEntity entity = data.get(0);
        CargaEntity nuevaEntitdad = factory.manufacturePojo(CargaEntity.class);
        nuevaEntitdad.setId(entity.getId());
        cargaLogic.updateCarga(nuevaEntitdad);
        CargaEntity resp = em.find(CargaEntity.class, entity.getId());
        Assert.assertEquals(nuevaEntitdad.getId(), resp.getId());
        Assert.assertEquals(nuevaEntitdad.getDatosEnvio(), resp.getDatosEnvio());
        Assert.assertEquals(nuevaEntitdad.getDirecciones(), resp.getDirecciones());
        Assert.assertEquals(nuevaEntitdad.getImagenes(), resp.getImagenes());
        Assert.assertEquals(nuevaEntitdad.getLugarLlegada(), resp.getLugarLlegada());
        Assert.assertEquals(nuevaEntitdad.getLugarSalida(), resp.getLugarSalida());
        Assert.assertEquals(nuevaEntitdad.getObservaciones(), resp.getObservaciones());
        Assert.assertEquals(nuevaEntitdad.getUsuario(), resp.getUsuario());
        Assert.assertEquals(nuevaEntitdad.getViaje(), resp.getViaje());
        Assert.assertEquals(nuevaEntitdad.getVolumen(), resp.getVolumen());
    }

    /**
     * prueba para eliminar una carga
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteCargaTest() throws BusinessLogicException {
        CargaEntity entity = data.get(1);
        cargaLogic.deleteCarga(entity.getId());
        CargaEntity deleted = em.find(CargaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * test de buscar una carga con usuario
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getCargaUsuarioTest() throws BusinessLogicException {
        CargaEntity entity = data.get(0);
        CargaEntity resultEntity = cargaLogic.getCargaUsuario(entity.getUsuario().getLogin(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getDatosEnvio(), entity.getDatosEnvio());
        Assert.assertEquals(resultEntity.getDirecciones(), entity.getDirecciones());
        Assert.assertEquals(resultEntity.getImagenes(), entity.getImagenes());
        Assert.assertEquals(resultEntity.getLugarLlegada(), entity.getLugarLlegada());
        Assert.assertEquals(resultEntity.getLugarSalida(), entity.getLugarSalida());
        Assert.assertEquals(resultEntity.getObservaciones(), entity.getObservaciones());
        Assert.assertEquals(resultEntity.getUsuario(), entity.getUsuario());
        Assert.assertEquals(resultEntity.getViaje(), entity.getViaje());
        Assert.assertEquals(resultEntity.getVolumen(), entity.getVolumen());
    }

    /**
     * test de obtener una lista de cargas dado un login de usuario
     *
     * @throws BusinessLogicException
     */
    @Test
    public void getCargasTest() throws BusinessLogicException {
        CargaEntity entity = data.get(0);
        List<CargaEntity> resultEntity = cargaLogic.getCargas(entity.getUsuario().getLogin());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(3, resultEntity.size());
        Assert.assertTrue(listEqualsIgnoreOrder(resultEntity, data));
    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}
