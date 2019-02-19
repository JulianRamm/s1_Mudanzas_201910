/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Andres Gonzalez
 */
@RunWith(Arquillian.class)
public class SubastaPersistenceTest {

    @Inject
    private SubastaPersistence SubPer;
    
    @PersistenceContext
    private EntityManager entMan;

    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SubastaEntity.class.getPackage())
                .addPackage(SubastaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    @Test
    public void createSubastaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SubastaEntity newEntity = factory.manufacturePojo(SubastaEntity.class);

        SubastaEntity subEntity = SubPer.create(newEntity);

        Assert.assertNotNull(subEntity);

        SubastaEntity subFound = entMan.find(SubastaEntity.class, subEntity.getId());

        Assert.assertEquals(subEntity, subFound);

    }

}
