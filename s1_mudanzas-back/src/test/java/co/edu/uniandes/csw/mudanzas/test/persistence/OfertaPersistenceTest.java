/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.persistence.OfertaPersistence;
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
 * @author Andres Gonzalez
 */

@RunWith(Arquillian.class)
public class OfertaPersistenceTest {
    
    
    @Inject
    private OfertaPersistence ofertPrst;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    PodamFactory factory =new PodamFactoryImpl();
    
    private List<OfertaEntity> data = new ArrayList<OfertaEntity>();

      /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public  static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(OfertaEntity.class.getPackage())
                .addPackage(OfertaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
        em.createQuery("delete from OfertaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        
        ProveedorEntity prv = factory.manufacturePojo(ProveedorEntity.class);
        em.persist(prv);
        
        SubastaEntity sub = factory.manufacturePojo(SubastaEntity.class);
        em.persist(sub);
        for (int i = 0; i < 3; i++) {

            OfertaEntity entity = factory.manufacturePojo(OfertaEntity.class);

            entity.setProveedor(prv);
            
            entity.setSubasta(sub);
            em.persist(entity);

            data.add(entity);
        }
    }

     @Test
    public void createOfertaTest(){
        
        OfertaEntity newEntity = factory.manufacturePojo(OfertaEntity.class);
        
        OfertaEntity oferEntity = ofertPrst.create(newEntity);
        
        Assert.assertNotNull(oferEntity);
        OfertaEntity oferFound = em.find(OfertaEntity.class, oferEntity.getId());
        
        Assert.assertEquals(oferEntity, oferFound);
    }
    
    @Test
    public void getOfertasTest()
    {
        List<OfertaEntity> prueba = ofertPrst.findAll();
        Assert.assertEquals(prueba.size(), data.size());
        
        for (OfertaEntity pruebaActual : prueba) {
            boolean encontro = false;
            for (OfertaEntity dataACtual : data) {
                if(dataACtual.equals(pruebaActual))
                    encontro = true;
            }
            Assert.assertTrue(encontro);
        }
    }
    
    @Test
    public void getOfertaTest()
    {
        OfertaEntity real = data.get(0);
       OfertaEntity prueba =  ofertPrst.find(data.get(0).getId());
       
       Assert.assertNotNull(prueba);
       Assert.assertTrue(real.getValor()==prueba.getValor());
       Assert.assertEquals(prueba.getComentario(), real.getComentario());
       Assert.assertEquals(prueba.getProveedor(), real.getProveedor());
              Assert.assertEquals(prueba.getId(), real.getId());
          Assert.assertEquals(prueba.getSubasta(), real.getSubasta());

    }
    
    
    @Test
    public void buscarTarjetaLoginTest()
    {
             OfertaEntity real = data.get(0);
    OfertaEntity prueba = ofertPrst.findOneByProveedor(real.getProveedor().getLogin(), real.getId());
    Assert.assertNotNull(prueba);
    
    Assert.assertEquals(prueba.getId(), real.getId());
    
    prueba = ofertPrst.findOneByProveedor(real.getProveedor().getLogin(), null);
        Assert.assertNull(prueba);
    }
    
    @Test
    public void deleteOfertaTest()
    {
        OfertaEntity real = data.get(0);
        ofertPrst.delete(real.getId());
        
        OfertaEntity prueba = em.find(OfertaEntity.class,real.getId());
        Assert.assertNull(prueba);
        
    }
    
    
    @Test
    public void updateTarjetaTest() {
        OfertaEntity original = data.get(0);

        OfertaEntity real = factory.manufacturePojo(OfertaEntity.class);

        real.setId(original.getId());

        ofertPrst.update(real);
        

        OfertaEntity prueba = em.find(OfertaEntity.class, original.getId());

        Assert.assertNotNull(prueba);
        Assert.assertTrue(real.getValor() == prueba.getValor());
        Assert.assertEquals(real.getId(), prueba.getId());
        Assert.assertEquals(real.getSubasta(), prueba.getSubasta());
        Assert.assertEquals(real.getProveedor(), prueba.getProveedor());
        Assert.assertEquals(real.getComentario(), prueba.getComentario());
    }

}
