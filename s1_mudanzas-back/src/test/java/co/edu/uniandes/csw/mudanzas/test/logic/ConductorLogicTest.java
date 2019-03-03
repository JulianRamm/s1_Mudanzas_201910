/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ConductorLogic;
import co.edu.uniandes.csw.mudanzas.entities.ConductorEntity;
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

    @Inject
    private ConductorPersistence conPersistence;
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ConductorEntity entity = factory.manufacturePojo(ConductorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    private void clearData() {
        em.createQuery("delete from ConductorEntity").executeUpdate();
    }

    @Test
    public void createConductorTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
        ConductorEntity result = conLogic.crearConductor(newEntity);
        Assert.assertNotNull(result);

        ConductorEntity entity = em.find(ConductorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test(expected = BusinessLogicException.class)
    public void createConductorConMismoNombre() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        ConductorEntity newEntity = factory.manufacturePojo(ConductorEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        conLogic.crearConductor(newEntity);
    }

}
