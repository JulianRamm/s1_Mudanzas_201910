/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.OfertaLogic;
import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.OfertaPersistence;
import java.util.LinkedList;
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
 * @author Andres Gonzalez
 */
@RunWith(Arquillian.class)

public class OfertaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private OfertaLogic ofertaLogic;

    @Inject
    private OfertaPersistence op;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<OfertaEntity> ofertasData = new LinkedList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OfertaEntity.class.getPackage())
                .addPackage(OfertaLogic.class.getPackage())
                .addPackage(OfertaPersistence.class.getPackage())
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
        em.createQuery("delete from OfertaEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
 OfertaEntity ofer = factory.manufacturePojo(OfertaEntity.class);

        em.persist(ofer);

        ProveedorEntity prv = factory.manufacturePojo(ProveedorEntity.class);

        em.persist(prv);

        for (int i = 0; i < 3; i++) {
            OfertaEntity oferActual = factory.manufacturePojo(OfertaEntity.class);
            oferActual.setProveedor(prv);
            em.persist(oferActual);
            
            ofertasData.add(oferActual);
        }

    }

        
    @Test
    public void createOfertaTest() throws BusinessLogicException, Exception {
        OfertaEntity ManufacturedEntity = factory.manufacturePojo(OfertaEntity.class);
        OfertaEntity oferCreada = ofertaLogic.createOferta(ManufacturedEntity);
        Assert.assertNotNull(oferCreada);
        OfertaEntity entidadFound = em.find(OfertaEntity.class, oferCreada.getId());
        Assert.assertEquals(ManufacturedEntity.getId(), entidadFound.getId());

    }

    

   

    
    
    @Test
    public void getOfertaProv() throws BusinessLogicException
    {
        try {
            List<OfertaEntity> real = new LinkedList<>();
            utx.begin();
            
            
            ProveedorEntity nProv = factory.manufacturePojo(ProveedorEntity.class);
            nProv.setLogin("StressedOut");
            em.persist(nProv);
            
            utx.commit();
            
            for (int i = 0; i < 5; i++) {
                OfertaEntity nuevaActual = factory.manufacturePojo(OfertaEntity.class);
                nuevaActual.setId(Long.MIN_VALUE+ i);
                nuevaActual.setProveedor(nProv);
                
                ofertaLogic.createOferta(nuevaActual);
                real.add(nuevaActual);
            }
            
            Assert.assertEquals(ofertaLogic.getOfertasProveedor("StressedOut"), real);
        } catch (Exception ex) {
            ex.printStackTrace(); }
    }
    
    
    @Test
    public void getSubastaProvUser() throws BusinessLogicException
    {
        OfertaEntity real = ofertasData.get(0);
        OfertaEntity pruebaPv = ofertaLogic.getOfertaProveedor(real.getId(), real.getProveedor().getLogin());

        Assert.assertNotNull(pruebaPv);
        Assert.assertEquals(pruebaPv, real);
        Assert.assertEquals(pruebaPv, real);
    }
    @Test
    public void updateSubastaTest() {
        OfertaEntity entidad = ofertasData.get(0);

        OfertaEntity cambiada = factory.manufacturePojo(OfertaEntity.class);

        cambiada.setId(entidad.getId());

        ofertaLogic.updateOferta(cambiada);
        

        OfertaEntity oferEncontrada = em.find(OfertaEntity.class, entidad.getId());

        Assert.assertNotNull(oferEncontrada);
        Assert.assertEquals(cambiada.getId(), oferEncontrada.getId());
        Assert.assertTrue(cambiada.getValor() == oferEncontrada.getValor());
        Assert.assertEquals(cambiada.getProveedor(), oferEncontrada.getProveedor());
        Assert.assertEquals(cambiada.getSubasta(), oferEncontrada.getSubasta());
    }
}
