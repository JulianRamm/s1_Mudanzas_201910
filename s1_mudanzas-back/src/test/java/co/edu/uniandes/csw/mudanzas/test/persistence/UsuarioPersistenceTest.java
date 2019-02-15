/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
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
 * Clase de pruebas para la persistencia del usuario
 * @author Luis Miguel
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {

    /**
     * Atributo que instancia a un usuario.
     */
    @Inject
    private UsuarioPersistence ep;
    
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
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();
        
    /**
     * Crea todo lo necesario para el desarrollo de las pruebas. 
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    /**
     * Prueba unitaria para probar la creacion de un usuario
     */
    @Test
    public void createUsuarioTest() {
        //podam nos crea una instancia automatica
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
        //llamamos al manager de persistencia, en este caso de usuario
        UsuarioEntity usuarioe = ep.create(usr);
        //verificamos que no devuelva algo nulo de la creacion en la base de datos. 
        Assert.assertNotNull(usuarioe);
        //Buscamos ese usuario directamente en la BD
        UsuarioEntity entity = em.find(UsuarioEntity.class, usuarioe.getId());
        
        //verificamos que el mismo que cree en mi propio metodo sea el mismo que relamente se creo en la BD.
        Assert.assertEquals(usr.getNombre(), entity.getNombre());
        
    }
    
//    @Test
//    public void findUsuarioTest()
//    {
//        //podam nos crea una instancia automatica
//        PodamFactory factory = new PodamFactoryImpl();
//        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
//        //llamamos al manager de persistencia, en este caso de usuario
//        UsuarioEntity usuarioe = ep.create(usr);
//        //Buscamos ese usuario directamente en la BD
//        UsuarioEntity entity = em.find(UsuarioEntity.class, usuarioe.getId());
//        
//        //verificamos que el mismo que cree en mi propio metodo sea el mismo que relamente se creo en la BD.
//        Assert.assertEquals(usr.getNombre(), entity.getNombre());
//    }
//    
//    @Test
//    public void deleteUsuarioTest()
//    {
//        //podam nos crea una instancia automatica
//        PodamFactory factory = new PodamFactoryImpl();
//        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);
//        //llamamos al manager de persistencia para que lo cree en la bd, en este caso de usuario
//        UsuarioEntity usuarioe = ep.create(usr);
//        
//        ep.delete(usuarioe.getId());
//        
//        //Buscamos ese usuario directamente en la BD
//        UsuarioEntity entity = em.find(UsuarioEntity.class, usuarioe.getId());
//        
//        //verificamos que despues de borrarlo buscarlo sea nulo.
//        Assert.assertNull(entity);
//    }
    
}   
