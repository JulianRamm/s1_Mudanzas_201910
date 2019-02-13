/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
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
 * @author Luis Miguel
 */
@RunWith(Arquillian.class)
public class TarjetaDeCreditoPersistenceTest {

    /**
     * Atributo que instancia a una tarjeta.
     */
    @Inject
    private TarjetaDeCreditoPersistence ep;

    /**
     * Llamamos al encargado de la BD
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaDeCreditoEntity.class.getPackage())
                .addPackage(TarjetaDeCreditoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Prueba unitaria para probar la creacion de una tarjeta.
     */
    @Test
    public void createTarjetaTest() {
        //podam nos crea una instancia automatica
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        //llamamos al manager de persistencia, en este caso de usuario
        TarjetaDeCreditoEntity tarjetae = ep.create(trjt);
        //verificamos que no devuelva algo nulo de la creacion en la base de datos. 
        Assert.assertNotNull(tarjetae);
        //Buscamos ese usuario directamente en la BD
        TarjetaDeCreditoEntity entity = em.find(TarjetaDeCreditoEntity.class, tarjetae.getId());

        //verificamos que el mismo que cree en mi propio metodo sea el mismo que relamente se creo en la BD.
        Assert.assertEquals(trjt.getTitularCuenta(), entity.getTitularCuenta());

    }

}
