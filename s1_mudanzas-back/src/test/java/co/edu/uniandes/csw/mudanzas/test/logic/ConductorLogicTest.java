/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ConductorLogic;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ConductorPersistence;
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
public class ConductorLogicTest {

    @Inject
    private ConductorLogic conLogic;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<ConductorEntity> data = new ArrayList<>();
    
    private ProveedorEntity proveedorData;
    
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ConductorEntity.class.getPackage())
                .addPackage(ConductorLogic.class.getPackage())
                .addPackage(ConductorPersistence.class.getPackage())
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

    private void insertData() {
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        proveedor.setLogin("df.machado10");
        em.persist(proveedor);
        proveedorData = proveedor;

        for (int j = 0; j < 4; j++) {
            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);
            entity.setProveedor(proveedorData);
            em.persist(entity);
            data.add(entity);
        }
    }

    private void clearData() {
        
        em.createQuery("delete from ConductorEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Prueba para la creacion de un conductor
     *
     */
    @Test
    public void createConductorTest()  {
        ConductorEntity nuevaEntidad = factory.manufacturePojo(ConductorEntity.class);
        nuevaEntidad.setNombre("Daniel Machado");
        
        //caso de prueba en el que si debería agregar
        try
        {
            ConductorEntity creado = conLogic.crearConductor(nuevaEntidad, proveedorData.getLogin());
            Assert.assertNotNull(creado);

            ConductorEntity entidad = em.find(ConductorEntity.class, creado.getId());

            Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
            Assert.assertEquals(nuevaEntidad.getNombre(), entidad.getNombre());
            Assert.assertEquals(nuevaEntidad.getCalificacion()+"", entidad.getCalificacion()+"");
        }
        catch(BusinessLogicException e){
            
            fail("no debería fallar");
        }
        
        //Caso de prueba de un conductor con un nombre de otro conductor
        ConductorEntity nuevaEntidad2 = factory.manufacturePojo(ConductorEntity.class);
        nuevaEntidad2.setNombre("Daniel Machado");
        
        try {
            ConductorEntity creado = conLogic.crearConductor(nuevaEntidad2, proveedorData.getLogin());
            fail("No debería agregar porque ya hay un conductor con ese nombre");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba de un conductor con un telefono no valido
        nuevaEntidad2.setTelefono("1234");
        try {
            ConductorEntity creado = conLogic.crearConductor(nuevaEntidad2, proveedorData.getLogin());
            fail("No debería agregar porque el telefono no es valido");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba de un conductor con el login de un proveedor que no existe
        try {
            ConductorEntity creado = conLogic.crearConductor(nuevaEntidad2, "otraCosa");
            fail("No debería agregar porque no existe el proveedor con ese login");
        } catch (BusinessLogicException e) {
        }
        
        //Caso de prueba de un conductor con un nombre que no es valido
        ConductorEntity nuevaEntidad3 = factory.manufacturePojo(ConductorEntity.class);
        nuevaEntidad2.setNombre("Daniel2 Machado2");
        
        try {
            ConductorEntity creado = conLogic.crearConductor(nuevaEntidad2, proveedorData.getLogin());
            fail("No debería agregar porque el nombre no es valido");
        } catch (BusinessLogicException e) {
        }
    }
    
    /**
     * Prueba que valida la obtencion de todas los Conductores.
     */
    @Test
    public void getConductoresTest() {
        List<ConductorEntity> lista = conLogic.getConductores();
        Assert.assertEquals(data.size(), lista.size());
        for (ConductorEntity entidad : lista) {
            boolean encontrado = false;
            for (ConductorEntity almacenado : data) {
                if (entidad.getId().equals(almacenado.getId())) {
                    encontrado = true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    /**
     * Prueba que valida que se pueda obtener un Conductor por su id.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test
    public void getConductorIdTest() throws BusinessLogicException {
        ConductorEntity entidad = data.get(0);
        ConductorEntity resultado = conLogic.getConductor(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombre(), entidad.getNombre());
        Assert.assertEquals(resultado.getCalificacion()+"", entidad.getCalificacion()+"");
    }
    
    /**
     * Prueba que valida que se pueda obtener un conductor por el login de su proveedor.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test
    public void getConductorLoginTest() throws BusinessLogicException {
        ConductorEntity entidad = data.get(0);
        ConductorEntity resultado = conLogic.getConductor(proveedorData.getLogin(), entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getNombre(), entidad.getNombre());
        Assert.assertEquals(resultado.getCalificacion()+"", entidad.getCalificacion()+"");
    }
    
    /**
     * Prueba que valida que se pueda actualizar una tarjeta ya existente.
     */
    @Test
    public void updateConductorTest() {
        ConductorEntity entidad = data.get(0);
        ConductorEntity nuevaEntidad = factory.manufacturePojo(ConductorEntity.class);
        nuevaEntidad.setId(entidad.getId());
        conLogic.updateConductor(nuevaEntidad);
        ConductorEntity respuesta = em.find(ConductorEntity.class, entidad.getId());
        Assert.assertNotNull(respuesta);
        Assert.assertEquals(respuesta.getId(), nuevaEntidad.getId());
        Assert.assertEquals(respuesta.getNombre(), nuevaEntidad.getNombre());
        Assert.assertEquals(respuesta.getCalificacion()+"", nuevaEntidad.getCalificacion()+"");
    }
    
    /**
     * Prueba que valida que se pueda eliminar un conductor.
     */
    @Test
    public void deleteConductorTest(){
        ConductorEntity entidad = data.get(1);
        conLogic.deleteConductor(entidad.getId());
        ConductorEntity borrar = em.find(ConductorEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }




}
