/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
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
public class ConductorPersistenceTest 
{
    @Inject
    private ConductorPersistence ConPersistence;
    
    
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
    private List<ConductorEntity> data = new ArrayList<ConductorEntity>();
    
    
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
                .addPackage(ConductorPersistence.class.getPackage())
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
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    private void clearData() 
    {
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }
    
    /**
     * Prueba unitaria para probar la creacion de una tarjeta.
     */
    @Test
    public void createConductorTest() {
        //podam nos crea una instancia automatica
        
        ConductorEntity conductor = factory.manufacturePojo(ConductorEntity.class);
        //llamamos al manager de persistencia, en este caso de usuario
        ConductorEntity creado = ConPersistence.create(conductor);
        //verificamos que no devuelva algo nulo de la creacion en la base de datos. 
        Assert.assertNotNull(creado);
        //Buscamos ese usuario directamente en la BD
        ConductorEntity entity = em.find(ConductorEntity.class, creado.getId());

        //verificamos que el mismo que cree en mi propio metodo sea el mismo que relamente se creo en la BD.
        Assert.assertEquals(conductor.getNombre(), entity.getNombre());

    }
    
    /**
     * Prueba, obtiene de la base de datos todos los conductores que han sido creadas...
     */
    @Test
    public void getConductoresTest() {
        List<ConductorEntity> lista = ConPersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());

        for (ConductorEntity enLista : lista) {
            boolean loEncontre = false;
            for (ConductorEntity enData : data) {
                if (enLista.getId().equals(enData.getId()));
                    loEncontre = true;
            }
            Assert.assertTrue(loEncontre);
        }

    }
    
    /**
     * Prueba para obtener solo un conductor.
     */
    @Test
    public void getConcuctorTest() {
        ConductorEntity entidad = data.get(0);
        ConductorEntity nuevo = ConPersistence.find(entidad.getId());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(entidad.getNombre(), nuevo.getNombre());
        Assert.assertEquals(entidad.getId(), nuevo.getId());
        Assert.assertEquals(entidad.getTelefono(), nuevo.getTelefono());
    }
    
    /**
     * Prueba para borrar un conductor de la bd
     */
    @Test
    public void deleteConductorTest()
    {
        ConductorEntity entidad = data.get(0);
        ConPersistence.delete(entidad.getId());
        ConductorEntity borrado = em.find(ConductorEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }
    
    /**
     * Prueba para actualizar un conductor en la base de datos.
     */
    @Test
    public void updateTarjetaTest()
    {
        ConductorEntity entidad = data.get(0);
         
        ConductorEntity cambiada = factory.manufacturePojo(ConductorEntity.class);
        
        cambiada.setId(entidad.getId());
        
        ConPersistence.update(cambiada);
        
        ConductorEntity encontrada = em.find(ConductorEntity.class, entidad.getId());
        
        Assert.assertEquals(encontrada.getNombre(), cambiada.getNombre());
        Assert.assertEquals(encontrada.getId(), cambiada.getId());
        Assert.assertEquals(encontrada.getTelefono(), cambiada.getTelefono());
    }
}
