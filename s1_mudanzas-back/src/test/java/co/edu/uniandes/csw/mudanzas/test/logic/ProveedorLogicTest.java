/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
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
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Daniel Machado
 */
@RunWith(Arquillian.class)
public class ProveedorLogicTest {
    
    
    @Inject
    private ProveedorLogic pL;
    
    
    @Inject
    private ProveedorPersistence pP;
    
     /**
     * Llamamos al encargado de la BD
     */
    @PersistenceContext
    private EntityManager eM;
    
        /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction uTX;
    
     /**
     * Lista que tiene los datos de prueba.
     */
    private List<ProveedorEntity> data = new ArrayList<>();

    
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
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorLogic.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            uTX.begin();
            eM.joinTransaction();
            clearData();
            insertData();
            uTX.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                uTX.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        eM.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {

            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            eM.persist(entity);

            data.add(entity);
        }
    }
    
    /**
     * Prueba para la creacion de un proveedor
     *
     * @throws BusinessLogicException en caso que una de las reglas de negocio
     * no se cumpla.
     */
    @Test
    public void crearProveedorTest()throws BusinessLogicException{
        
        ProveedorEntity entidad = factory.manufacturePojo(ProveedorEntity.class);
        entidad.setNombre("Pajarito Acarreos");
        entidad.setLogin("pajarito24");
        entidad.setPassword("P4jarito!");
        entidad.setCiudadOrigen("Bogotá DC");
        entidad.setTelefono("6131226");
        entidad.setNumeroVehiculos(4);
        entidad.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad);
            Assert.assertNotNull(nuevoP);
            ProveedorEntity entidadNueva = eM.find(ProveedorEntity.class, nuevoP.getId());
            Assert.assertEquals(entidad.getId(), entidad.getId());
            Assert.assertEquals(entidad.getNombre(), entidad.getNombre());
            Assert.assertEquals(entidad.getCorreoElectronico(), entidad.getCorreoElectronico());
            Assert.assertEquals(entidad.getPassword(), entidad.getPassword());
            Assert.assertEquals(entidad.getLogin(), entidad.getLogin());
        } catch (BusinessLogicException e) {
            fail("no debería fallar: " + e.getMessage());
        }
        
        //Caso de prueba para un proveeodr con un nombre que ya tiene otro proveedor
        ProveedorEntity entidad2 = factory.manufacturePojo(ProveedorEntity.class);
        entidad.setNombre("Pajarito Acarreos");
        entidad.setLogin("pajaro56");
        entidad.setPassword("P4jarito!");
        entidad.setCiudadOrigen("Bogotá DC");
        entidad.setTelefono("6131226");
        entidad.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        entidad.setNumeroVehiculos(4);
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de Prueba para un proveedor con un login ya existente
        ProveedorEntity entidad3 = factory.manufacturePojo(ProveedorEntity.class);
        entidad3.setNombre("Gatico Acarreos");
        entidad3.setLogin("pajarito24");
        entidad3.setPassword("P4jarito!");
        entidad3.setCiudadOrigen("Bogotá DC");
        entidad3.setTelefono("6131226");
        entidad3.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        entidad3.setNumeroVehiculos(4);
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad3);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba para un proveedor con un login invalido
        ProveedorEntity entidad4 = factory.manufacturePojo(ProveedorEntity.class);
        entidad4.setNombre("Lunita Acarreos");
        entidad4.setLogin("lunita!");
        entidad4.setPassword("P4jarito!");
        entidad4.setCiudadOrigen("Bogotá DC");
        entidad4.setTelefono("6131226");
        entidad4.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        entidad4.setNumeroVehiculos(4);
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad4);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba para un conductor con una contraseña invalido
        ProveedorEntity entidad5 = factory.manufacturePojo(ProveedorEntity.class);
        entidad5.setNombre("Caballito Acarreos");
        entidad5.setLogin("caballo10");
        entidad5.setPassword("caa");
        entidad5.setCiudadOrigen("Bogotá DC");
        entidad5.setTelefono("6131226");
        entidad5.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        entidad5.setNumeroVehiculos(4);
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad5);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba para un conductor con un telefono invalido
        ProveedorEntity entidad6 = factory.manufacturePojo(ProveedorEntity.class);
        entidad6.setNombre("Rojo Acarreos");
        entidad6.setLogin("rojo0");
        entidad6.setPassword("caaaaaa23!");
        entidad6.setCiudadOrigen("Bogotá DC");
        entidad6.setTelefono("6126");
        entidad6.setCorreoElectronico("pajaritoAcarreos@gmail.com");
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad6);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba para un conductor con un correo invalido
        ProveedorEntity entidad7 = factory.manufacturePojo(ProveedorEntity.class);
        entidad7.setNombre("Azul Acarreos");
        entidad7.setLogin("Azul10");
        entidad7.setPassword("caaaaaa23!");
        entidad7.setCiudadOrigen("Bogotá DC");
        entidad7.setTelefono("6126");
        entidad7.setCorreoElectronico("pajaritoAcarreos");
        try {
            ProveedorEntity nuevoP = pL.createProveedor(entidad7);
            fail("No deberia crear el proveedor");
        } catch (BusinessLogicException e) {
        }
    }
    
    /**
     * Prueba la obtencion de todos los proveedores
     *
     */
    @Test
    public void getProveedoresTest() {
        List<ProveedorEntity> lista = pL.getProveedores();
        Assert.assertEquals(data.size(), lista.size());
        for (ProveedorEntity entidad : lista) {
            boolean encontrado = false;
            for (ProveedorEntity almacenado : data) {
                if (entidad.getId().equals(almacenado.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    /**
     * Prueba la obtencion de un proveedor por su id
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getProveedorPorIdTest() throws BusinessLogicException {
        ProveedorEntity entidad = data.get(0);
        ProveedorEntity resultado = pL.getProveedor(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entidad.getId(), resultado.getId());
        Assert.assertEquals(entidad.getNombre(), resultado.getNombre());
        Assert.assertEquals(entidad.getCorreoElectronico(), resultado.getCorreoElectronico());
        Assert.assertEquals(entidad.getPassword(), resultado.getPassword());
        Assert.assertEquals(entidad.getLogin(), resultado.getLogin());
    }
    
    /**
     * Prueba la obtencion de un proveedor por su login
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getUsuarioPorLoginTest() throws BusinessLogicException {
        ProveedorEntity entidad = data.get(0);
        ProveedorEntity resultado = pL.getProveedor(entidad.getLogin());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entidad.getId(), resultado.getId());
        Assert.assertEquals(entidad.getNombre(), resultado.getNombre());
        Assert.assertEquals(entidad.getCorreoElectronico(), resultado.getCorreoElectronico());
        Assert.assertEquals(entidad.getPassword(), resultado.getPassword());
        Assert.assertEquals(entidad.getLogin(), resultado.getLogin());
    }
    
    /**
     * Prueba la eliminacion de un usuario.
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void deleteUsuarioTest() throws BusinessLogicException {
        ProveedorEntity entidad = data.get(1);
        pL.deleteUsuario(entidad.getId());
        ProveedorEntity borrar = eM.find(ProveedorEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }
}
