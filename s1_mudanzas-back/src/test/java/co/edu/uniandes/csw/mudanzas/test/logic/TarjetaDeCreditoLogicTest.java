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
    private UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<TarjetaDeCreditoEntity> data = new ArrayList<TarjetaDeCreditoEntity>();

    /**
     * Atributo que almacena un usuario duenio de muchas tarjetas.
     */
    private UsuarioEntity usuarioData;

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setLogin("luismigolondo");
        em.persist(usuario);
        usuarioData = usuario;

        for (int j = 0; j < 3; j++) {
            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
            entity.setUsuario(usuarioData);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para la creacion de una tarjeta de credito
     *
     * @throws BusinessLogicException en caso que una de las reglas de negocio
     * no se cumpla.
     */
    @Test
    public void createTarjetaDeCreditoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        Integer i = 10007;
        Long idi = i.longValue();
        nuevaEntidad.setId(idi);
        nuevaEntidad.setNumeroSerial("123456789123");
        nuevaEntidad.setNombreTarjeta("Luis Miguel");
        Date fechaV = new Date();
        fechaV.setMonth(02);
        fechaV.setYear(2020);
        nuevaEntidad.setCodigoSeguridad(951);
        nuevaEntidad.setFechaVencimiento(fechaV);
        String usuariologin = usuarioData.getLogin();
        TarjetaDeCreditoEntity resultado = tarjetaLogic.crearTarjeta(nuevaEntidad, usuariologin);

        Assert.assertNotNull(resultado);

        TarjetaDeCreditoEntity entidad = tarjetaLogic.getTarjeta(resultado.getId());

        Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(nuevaEntidad.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(nuevaEntidad.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    /**
     * Prueba que valida que no se pueda crear una tarjeta con un mismo id.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void createTarjetaDeCreditoMismoIdTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity nuevaEntidad = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        nuevaEntidad.setId(data.get(0).getId());
        tarjetaLogic.crearTarjeta(nuevaEntidad, usuarioData.getLogin());
    }

    /**
     * Prueba que valida que no se puede crear una tarjet sin un propietario.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setUsuario(null);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getLogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void expresionRegularNombreTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setNombreTarjeta("l1234");
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getLogin());
    }

    /**
     * Prueba que valida que no se pueda crear una tarjeta con un serial menor
     * que 12 o mayor a 19 digitos.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void serialTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);
        String menorQue12 = "1234567890";
        trjt.setNumeroSerial(menorQue12);
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getLogin());
    }

    /**
     * Prueba que valida que no se pueda crear una tarjeta con un codigo de
     * seguridad invalido.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void codigoSeguridadTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        trjt.setCodigoSeguridad(1672);
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getLogin());
    }

    /**
     * Prueba que valida que no se pueda crear una tarjeta si por su fecha de
     * expedicion es invalida.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void fechaVencimientoTarjetaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        UsuarioEntity dummy = factory.manufacturePojo(UsuarioEntity.class);;
        Date fechaAnioPasado = new Date();
        fechaAnioPasado.setMonth(03);
        fechaAnioPasado.setYear(2018);
        trjt.setFechaVencimiento(fechaAnioPasado);
        trjt.setUsuario(dummy);
        //llamamos al manager de persistencia, en este caso no se creara
        tarjetaLogic.crearTarjeta(trjt, dummy.getLogin());
    }

    /**
     * Prueba que valida la obtencion de todas las tarjetas.
     */
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

    /**
     * Prueba que valida que se pueda obtener una tarjeta por su id.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test
    public void getTarjetaDeCreditoPorIdTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.getTarjeta(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(resultado.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(resultado.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    /**
     * Prueba que valida que se pueda obtener una tarjeta por su login.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test
    public void getTarjetaDeCreditoPorLoginTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity resultado = tarjetaLogic.getTarjeta(usuarioData.getLogin(), entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombreTarjeta(), entidad.getNombreTarjeta());
        Assert.assertEquals(resultado.getNumeroSerial(), entidad.getNumeroSerial());
        Assert.assertEquals(resultado.getCodigoSeguridad(), entidad.getCodigoSeguridad());
    }

    /**
     * Prueba que valida que se pueda actualizar una tarjeta ya existente.
     */
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
        Assert.assertEquals(respuesta.getCodigoSeguridad(), nuevaEntidad.getCodigoSeguridad());
    }

    /**
     * Prueba que valida que se pueda eliminar una tarjeta.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test
    public void deleteTarjetaDeCreditoTest() throws BusinessLogicException {
        TarjetaDeCreditoEntity entidad = data.get(1);
        tarjetaLogic.deleteTarjeta(entidad.getId());
        TarjetaDeCreditoEntity borrar = em.find(TarjetaDeCreditoEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }

}
