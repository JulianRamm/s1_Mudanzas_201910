/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.VehiculoLogic;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.VehiculoPersistence;
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
public class VehiculoLogicTest 
{
  
    PodamFactory factory = new PodamFactoryImpl();
    
    
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
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Editorial, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(VehiculoEntity.class.getPackage())
                .addPackage(VehiculoLogic.class.getPackage())
                .addPackage(VehiculoPersistence.class.getPackage())
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
        for (int i = 0; i < 3; i++) {

            VehiculoEntity entity = factory.manufacturePojo(VehiculoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    private void clearData() 
    {
        em.createQuery("delete from VehiculoEntity").executeUpdate();
    }
    
    @Test
    public void createVehiculoTest() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        VehiculoEntity result = VLogic.crearVehiculo(newEntity);
        Assert.assertNotNull(result);

        VehiculoEntity entity = em.find(VehiculoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPlaca(), entity.getPlaca());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createVehiculoConMismaPlaca() throws BusinessLogicException
    {
        VehiculoEntity newEntity = factory.manufacturePojo(VehiculoEntity.class);
        newEntity.setPlaca(data.get(0).getPlaca());
        VLogic.crearVehiculo(newEntity);
    }
    
    
}
