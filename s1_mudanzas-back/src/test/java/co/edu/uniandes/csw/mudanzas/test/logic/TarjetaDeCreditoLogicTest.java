/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
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
 * @author Luis Miguel
 */
@RunWith(Arquillian.class)
public class TarjetaDeCreditoLogicTest {

    /**
     * Inyeccion de la dependencia a la clase TarjetaDeCreditoLogic cuyos
     * metodos se van a probar.
     */
    @Inject
    private TarjetaDeCreditoLogic tarjetaLogic;

    /**
     * Atributo que instancia a un usuario.
     */
    @Inject
    private TarjetaDeCreditoPersistence ep;

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
    private List<TarjetaDeCreditoEntity> data = new ArrayList<TarjetaDeCreditoEntity>();

    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaDeCreditoEntity.class.getPackage())
                .addPackage(TarjetaDeCreditoLogic.class.getPackage())
                .addPackage(TarjetaDeCreditoPersistence.class.getPackage())
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
        em.createQuery("delete from TarjetaDeCreditoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void createTarjetaDeCreditoTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.crearTarjeta(nuevaEntidad);
        Assert.assertNotNull(resultado);
        TarjetaDeCreditoEntity entidad = em.find(TarjetaDeCreditoEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(nuevaEntidad.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(nuevaEntidad.getTitularCuenta(), entidad.getTitularCuenta());
        Assert.assertEquals(nuevaEntidad.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    @Test(expected = BusinessLogicException.class)
    public void createTarjetaDeCreditoMismoIdTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        nuevaEntidad.setId(data.get(0).getId());
        tarjetaLogic.crearTarjeta(nuevaEntidad);
    }

    @Test
    public void getTarjetasDeCreditoTest() {
        List<TarjetaDeCreditoEntity> lista = tarjetaLogic.getTarjetas();
        Assert.assertEquals(data.size(), lista.size());
        for (TarjetaDeCreditoEntity entidad : lista) {
            boolean encontrado = false;
            for (TarjetaDeCreditoEntity almacenado : data) {
                if (entidad.getId().equals(almacenado.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    @Test
    public void getTarjetaDeCreditoPorIdTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.getTarjeta(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(resultado.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(resultado.getTitularCuenta(), entidad.getTitularCuenta());
        Assert.assertEquals(resultado.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    @Test
    public void getTarjetaDeCreditoPorLoginTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.getTarjeta(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(resultado.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(resultado.getTitularCuenta(), entidad.getTitularCuenta());
        Assert.assertEquals(resultado.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    @Test
    public void updateTarjetaDeCreditoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        nuevaEntidad.setId(entidad.getId());
        tarjetaLogic.updateTarjeta(nuevaEntidad);
        TarjetaDeCreditoEntity respuesta = em.find(TarjetaDeCreditoEntity.class, entidad.getId());
        Assert.assertEquals(respuesta.getId(), nuevaEntidad.getId());
        Assert.assertEquals(respuesta.getNombreTarjeta(), nuevaEntidad.getNombreTarjeta());
        Assert.assertEquals(respuesta.getNumeroSerial(), nuevaEntidad.getNumeroSerial());
        Assert.assertEquals(respuesta.getTitularCuenta(), nuevaEntidad.getTitularCuenta());
        Assert.assertEquals(respuesta.getCodigoSeguridad(), nuevaEntidad.getCodigoSeguridad());
    }

    @Test
    public void deleteTarjetaDeCreditoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(1);
        tarjetaLogic.deleteTarjeta(entidad.getId());
        TarjetaDeCreditoEntity borrar = em.find(TarjetaDeCreditoEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }

}
