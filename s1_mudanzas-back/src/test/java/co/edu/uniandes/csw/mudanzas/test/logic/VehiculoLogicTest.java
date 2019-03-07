/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
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
 * @author Samuel Bernal Neira
 */
@RunWith(Arquillian.class)
public class VehiculoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private VehiculoLogic VLogic;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
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
    private List<VehiculoEntity> data = new ArrayList<VehiculoEntity>();

    /**
     * Atributo que almacena un usuario duenio de muchas tarjetas.
     */
    private ProveedorEntity proveedorData;

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
                .addPackage(VehiculoEntity.class.getPackage())
                .addPackage(VehiculoLogic.class.getPackage())
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
        proveedor.setLogin("samuel");
        em.persist(proveedor);
        proveedorData = proveedor;

        for (int i = 0; i < 3; i++) {

            VehiculoEntity entity = factory.manufacturePojo(VehiculoEntity.class);
            entity.setProveedor(proveedorData);
            em.persist(entity);
            data.add(entity);
        }
    }

    private void clearData() {
        em.createQuery("delete from VehiculoEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }
    
    
   


    @Test
    public void createVehiculoTest() throws BusinessLogicException {
        VehiculoEntity nuevaEntidad = factory.manufacturePojo(VehiculoEntity.class);
        nuevaEntidad.setNumeroConductores(4);
        VehiculoEntity resultado = VLogic.crearVehiculo(nuevaEntidad, proveedorData.getLogin());
        Assert.assertNotNull(resultado);
        VehiculoEntity entidad = em.find(VehiculoEntity.class, resultado.getId());
      //  Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getNumeroConductores(), entidad.getNumeroConductores());
        
    }
    
   // @Test
    //(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity veh = factory.manufacturePojo(VehiculoEntity.class);
        veh.setColor(null);
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(veh, proveedorData.getLogin());
    }

   // @Test
    //(expected = BusinessLogicException.class)
    public void createVehiculoConMismaPlaca() throws BusinessLogicException {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        newEntity.setPlaca(data.get(0).getPlaca());
        VLogic.crearVehiculo(newEntity, proveedorData.getLogin());
    }
    
   // @Test
    //(expected = BusinessLogicException.class)
    public void createVehiculoConMismaAgenda() throws BusinessLogicException {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        newEntity.setAgenda(data.get(0).getAgenda());
        VLogic.crearVehiculo(newEntity, proveedorData.getLogin());
    }
    
   // @Test
    //(expected = BusinessLogicException.class)
    public void createVehiculoConMismaUbicacionActual() throws BusinessLogicException {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        newEntity.setUbicacionActual(data.get(0).getUbicacionActual());
        VLogic.crearVehiculo(newEntity, proveedorData.getLogin());
    }
    
    /**
     * Prueba la regla de negocio para la placa del vehiculo
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    //@Test(expected = BusinessLogicException.class)
    public void PlacaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity vec = factory.manufacturePojo(VehiculoEntity.class);
        vec.setPlaca("B0GA234");
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(vec, proveedorData.getLogin());
    }
    
//    (expected = BusinessLogicException.class)
   // @Test
    public void MarcaTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity vec = factory.manufacturePojo(VehiculoEntity.class);
        vec.setMarca("Bugati");
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(vec, proveedorData.getLogin());
        try
        {
            VehiculoEntity vehiculo = new VehiculoEntity();
            vehiculo.setMarca(vec.getMarca());
            VLogic.crearVehiculo(vehiculo, proveedorData.getLogin());
            Assert.fail("a");
        }
        catch(Exception e)
        {
            
        }
    }
    
    /**
     * Prueba la regla de negocio para el nombre del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void colorTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity usr = factory.manufacturePojo(VehiculoEntity.class);
        usr.setColor("M0V345Y");
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(usr, proveedorData.getLogin());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void numeroConductoresTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity vec = factory.manufacturePojo(VehiculoEntity.class);
        vec.setNumeroConductores(44);
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(vec, proveedorData.getLogin());
    }
    
    //@Test(expected = BusinessLogicException.class)
    public void RendimientoTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        VehiculoEntity vec = factory.manufacturePojo(VehiculoEntity.class);
        vec.setRendimiento(-34);
        //llamamos al manager de persistencia, en este caso no se creara
        VLogic.crearVehiculo(vec, proveedorData.getLogin());
    }

}
