/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
import java.util.ArrayList;
import java.util.Date;
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

    private PodamFactory factory = new PodamFactoryImpl();

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
        for (int i = 0; i < 3; i++) {

            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void createTarjetaDeCreditoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        nuevaEntidad.setNombreTarjeta("prueba");
        nuevaEntidad.setTitularCuenta("luism");
        nuevaEntidad.setNumeroSerial(1234567891011L);
        nuevaEntidad.setCodigoSeguridad(654);
        nuevaEntidad.setUsuario(dummy);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.crearTarjeta(nuevaEntidad, dummy.getELogin());
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
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setId(data.get(0).getId());
        nuevaEntidad.setUsuario(dummy);
        tarjetaLogic.crearTarjeta(nuevaEntidad, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setUsuario(null);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void expresionRegularNombreTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setNombreTarjeta("l1234");
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void expresionRegularTitularCuentaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);
        trjt.setTitularCuenta("l1234");
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void serialTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);
        Integer menorQue12 = 1234567890;
        trjt.setNumeroSerial(menorQue12.longValue());
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void codigoSeguridadTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setCodigoSeguridad(1672);
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void fechaVencimientoTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        Date fechaProximoAnio = new Date();
        fechaProximoAnio.setMonth(03);
        fechaProximoAnio.setYear(2020);
        trjt.setFechaVencimiento(fechaProximoAnio);
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getELogin());
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
