/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
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
public class UsuarioLogicTest {

    /**
     * Inyeccion de la dependencia a la clase UsuarioLogic cuyos metodos se van
     * a probar.
     */
    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Atributo que instancia a un usuario.
     */
    @Inject
    private UsuarioPersistence up;

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
    private UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();
    /**
     * Lista que contiene los datos de las tarjetas de credito que pertenecen a
     * los usuarios
     */
    private List<TarjetaDeCreditoEntity> tarjetaData = new ArrayList<TarjetaDeCreditoEntity>();
    /**
     * Podam
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        for (int i = 0; i < 3; i++) {
            TarjetaDeCreditoEntity tarjetas = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
            em.persist(tarjetas);
            tarjetaData.add(tarjetas);
        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                tarjetaData.get(i).setUsuario(entity);
            }
        }
    }

    /**
     * Prueba para la creacion de un usuario
     *
     * @throws BusinessLogicException en caso que una de las reglas de negocio
     * no se cumpla.
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setNombre("Luis Miguel");
        nuevaEntidad.setApellido("Gomez");
        nuevaEntidad.setCiudadDeOrigen("Manizales");
        nuevaEntidad.setLogin("lm.gomezl");
        nuevaEntidad.setPassword("M0v345Y!");
        nuevaEntidad.setCorreoElectronico("moveasy_desarrollo@uniandes.com");
        UsuarioEntity resultado = usuarioLogic.crearUsuario(nuevaEntidad);
        Assert.assertNotNull(resultado);
        UsuarioEntity entidad = em.find(UsuarioEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getNombre(), entidad.getNombre());
        Assert.assertEquals(nuevaEntidad.getApellido(), entidad.getApellido());
        Assert.assertEquals(nuevaEntidad.getCiudadDeOrigen(), entidad.getCiudadDeOrigen());
        Assert.assertEquals(nuevaEntidad.getCorreoElectronico(), entidad.getCorreoElectronico());
        Assert.assertEquals(nuevaEntidad.getPassword(), entidad.getPassword());
        Assert.assertEquals(nuevaEntidad.getLogin(), entidad.getLogin());
    }

    /**
     * Prueba que valida que no se puede crear una usuario si este es nulo.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setLogin(null);
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para el login del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void loginTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setLogin("moveasy!");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para la contrasenia del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void passwordTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setPassword("moveasy1234");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para el nombre del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void nameTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setNombre("M0V345Y");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para el apellido del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void apellidoTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setApellido("D354RR0LL0");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para la ciudad del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void ciudadTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setCiudadDeOrigen("B0G0T4");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para el correo electronico del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void emailTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setCorreoElectronico("m0v345y.d3s4rr0ll0@@andes.co");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }

    /**
     * Prueba la regla de negocio para la creacion de un usuario con un mismo
     * login
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioMismoLoginTest() throws BusinessLogicException {
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setLogin(data.get(0).getLogin());
        usuarioLogic.crearUsuario(nuevaEntidad);
    }

    /**
     * Prueba la obtencion de todos los usuarios
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> lista = usuarioLogic.getUsuarios();
        Assert.assertEquals(data.size(), lista.size());
        for (UsuarioEntity entidad : lista) {
            boolean encontrado = false;
            for (UsuarioEntity almacenado : data) {
                if (entidad.getId().equals(almacenado.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba la obtencion de un usuario por su id
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getUsuarioPorIdTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity resultado = usuarioLogic.getUsuario(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombre(), entidad.getNombre());
        Assert.assertEquals(resultado.getApellido(), entidad.getApellido());
        Assert.assertEquals(resultado.getCiudadDeOrigen(), entidad.getCiudadDeOrigen());
        Assert.assertEquals(resultado.getCorreoElectronico(), entidad.getCorreoElectronico());
        Assert.assertEquals(resultado.getPassword(), entidad.getPassword());
        Assert.assertEquals(resultado.getLogin(), entidad.getLogin());
    }

    /**
     * Prueba la obtencion de un usuario por su login
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getUsuarioPorLoginTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity resultado = usuarioLogic.getUsuario(entidad.getLogin());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombre(), entidad.getNombre());
        Assert.assertEquals(resultado.getApellido(), entidad.getApellido());
        Assert.assertEquals(resultado.getCiudadDeOrigen(), entidad.getCiudadDeOrigen());
        Assert.assertEquals(resultado.getCorreoElectronico(), entidad.getCorreoElectronico());
        Assert.assertEquals(resultado.getPassword(), entidad.getPassword());
        Assert.assertEquals(resultado.getLogin(), entidad.getLogin());
    }

    /**
     * Prueba la actualizacion de un usuario.
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setId(entidad.getId());
        usuarioLogic.updateUsuario(nuevaEntidad);
        UsuarioEntity respuesta = em.find(UsuarioEntity.class, entidad.getId());
        Assert.assertEquals(nuevaEntidad.getId(), respuesta.getId());
        Assert.assertEquals(nuevaEntidad.getNombre(), respuesta.getNombre());
        Assert.assertEquals(nuevaEntidad.getApellido(), respuesta.getApellido());
        Assert.assertEquals(nuevaEntidad.getCiudadDeOrigen(), respuesta.getCiudadDeOrigen());
        Assert.assertEquals(nuevaEntidad.getCorreoElectronico(), respuesta.getCorreoElectronico());
        Assert.assertEquals(nuevaEntidad.getPassword(), respuesta.getPassword());
        Assert.assertEquals(nuevaEntidad.getLogin(), respuesta.getLogin());
    }

    /**
     * Prueba la eliminacion de un usuario.
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(1);
        usuarioLogic.deleteUsuario(entidad.getId());
        UsuarioEntity borrar = em.find(UsuarioEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }

}
