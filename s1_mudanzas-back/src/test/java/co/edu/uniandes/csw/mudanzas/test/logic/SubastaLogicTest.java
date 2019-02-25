/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import java.util.ArrayList;
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
 * @author Andres Gonzalez
 */
@RunWith(Arquillian.class)
public class SubastaLogicTest {
    
    private PodamFactory factory= new PodamFactoryImpl();
    
     @Inject
    private SubastaLogic subastaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    
    private ArrayList<SubastaEntity> subastaData; 
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SubastaEntity.class.getPackage())
                .addPackage(SubastaLogic.class.getPackage())
                .addPackage(SubastaPersistence.class.getPackage())
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
     private void clearData() {
        em.createQuery("delete from SubastaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SubastaEntity subastas = factory.manufacturePojo(SubastaEntity.class);
            em.persist(subastas);
            subastaData.add(subastas);
        }
        
    }
    
    @Test
    public void createSubastaTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        SubastaEntity ManufacturedEntity = factory.manufacturePojo(SubastaEntity.class);
        SubastaEntity subcreada = subastaLogic.createSubasta(ManufacturedEntity);
        Assert.assertNotNull(subcreada);
        SubastaEntity entidadFound = em.find(SubastaEntity.class, subcreada.getId());
        Assert.assertEquals(ManufacturedEntity.getId(), entidadFound.getId());

    }

}
