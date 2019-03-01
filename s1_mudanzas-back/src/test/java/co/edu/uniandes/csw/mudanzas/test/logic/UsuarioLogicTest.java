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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setENombre("Luis Miguel");
        nuevaEntidad.setEApellido("Gomez");
        nuevaEntidad.setCiudadDeOrigen("Manizales");
        nuevaEntidad.setELogin("lm.gomezl");
        nuevaEntidad.setEPassword("M0v345Y!");
        nuevaEntidad.setECorreoElectronico("moveasy_desarrollo@uniandes.com");
        UsuarioEntity resultado = usuarioLogic.crearUsuario(nuevaEntidad);
        Assert.assertNotNull(resultado);
        UsuarioEntity entidad = em.find(UsuarioEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getENombre(), entidad.getENombre());
        Assert.assertEquals(nuevaEntidad.getEApellido(), entidad.getEApellido());
        Assert.assertEquals(nuevaEntidad.getECiudadDeOrigen(), entidad.getECiudadDeOrigen());
        Assert.assertEquals(nuevaEntidad.getECorreoElectronico(), entidad.getECorreoElectronico());
        Assert.assertEquals(nuevaEntidad.getEPassword(), entidad.getEPassword());
        Assert.assertEquals(nuevaEntidad.getELogin(), entidad.getELogin());
    }

    @Test(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setELogin(null);
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void loginTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setELogin("moveasy!");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void passwordTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setEPassword("moveasy1234");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void nameTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setENombre("M0V345Y");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void apellidoTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setEApellido("D354RR0LL0");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void ciudadTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setCiudadDeOrigen("B0G0T4");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void namesTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        usr.setECorreoElectronico("m0v345y.d3s4rr0ll0@@andes.co");
        //llamamos al manager de persistencia, en este caso no se creara
        usuarioLogic.crearUsuario(usr);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioMismoLoginTest() throws BusinessLogicException {
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setELogin(data.get(0).getELogin());
        usuarioLogic.crearUsuario(nuevaEntidad);
    }

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

    @Test
    public void getUsuarioPorIdTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity resultado = usuarioLogic.getUsuario(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getENombre(), entidad.getENombre());
        Assert.assertEquals(resultado.getEApellido(), entidad.getEApellido());
        Assert.assertEquals(resultado.getECiudadDeOrigen(), entidad.getECiudadDeOrigen());
        Assert.assertEquals(resultado.getECorreoElectronico(), entidad.getECorreoElectronico());
        Assert.assertEquals(resultado.getEPassword(), entidad.getEPassword());
        Assert.assertEquals(resultado.getELogin(), entidad.getELogin());
    }

    @Test
    public void getUsuarioPorLoginTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity resultado = usuarioLogic.getUsuario(entidad.getELogin());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getENombre(), entidad.getENombre());
        Assert.assertEquals(resultado.getEApellido(), entidad.getEApellido());
        Assert.assertEquals(resultado.getECiudadDeOrigen(), entidad.getECiudadDeOrigen());
        Assert.assertEquals(resultado.getECorreoElectronico(), entidad.getECorreoElectronico());
        Assert.assertEquals(resultado.getEPassword(), entidad.getEPassword());
        Assert.assertEquals(resultado.getELogin(), entidad.getELogin());
    }

    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entidad = data.get(0);
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setId(entidad.getId());
        usuarioLogic.updateUsuario(nuevaEntidad);
        UsuarioEntity respuesta = em.find(UsuarioEntity.class, entidad.getId());
        Assert.assertEquals(nuevaEntidad.getId(), respuesta.getId());
        Assert.assertEquals(nuevaEntidad.getENombre(), respuesta.getENombre());
        Assert.assertEquals(nuevaEntidad.getEApellido(), respuesta.getEApellido());
        Assert.assertEquals(nuevaEntidad.getECiudadDeOrigen(), respuesta.getECiudadDeOrigen());
        Assert.assertEquals(nuevaEntidad.getECorreoElectronico(), respuesta.getECorreoElectronico());
        Assert.assertEquals(nuevaEntidad.getEPassword(), respuesta.getEPassword());
        Assert.assertEquals(nuevaEntidad.getELogin(), respuesta.getELogin());
    }
    
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        UsuarioEntity entidad = data.get(1);
        usuarioLogic.deleteUsuario(entidad.getId());
        UsuarioEntity borrar = em.find(UsuarioEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }
    
}
