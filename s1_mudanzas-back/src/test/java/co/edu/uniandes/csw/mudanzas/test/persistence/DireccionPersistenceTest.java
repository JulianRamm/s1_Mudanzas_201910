/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import co.edu.uniandes.csw.mudanzas.persistence.DireccionPersistence;
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
 * @author je.osorio
 */
@RunWith(Arquillian.class)
public class DireccionPersistenceTest {
     @Inject
    UserTransaction utx;
    
    @PersistenceContext
    private EntityManager em;

    private List<DireccionEntity> data = new ArrayList <DireccionEntity>();

    @Inject
    private DireccionPersistence persistence;
    
    /**
     * método que retorna un archivo jar dada la información del archivo persistence.xml y beans.xml
     * @return 
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DireccionEntity.class.getPackage())
                .addPackage(DireccionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * método que configura las pruebas para que puedan ser ejecutadas de forma correcta
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
        em.createQuery("delete from DireccionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            DireccionEntity entity = factory.manufacturePojo(DireccionEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    /**
     * Crea una entidad de Direccions aleatoria y una con el método que fue implmentado en la clase de persistencia
     * Verifica que no sea null el objeto creado, lo busca con el método find de la clase de persistencia y compara
     * los id's de la entidad creada aleatoriamente y de la creada con el método create
     */
    @Test
    public void createDireccionTest() {
        PodamFactory factory = new PodamFactoryImpl();
        DireccionEntity newEntity = factory.manufacturePojo(DireccionEntity.class);
        DireccionEntity result = persistence.create(newEntity);

        Assert.assertNotNull(result);
        DireccionEntity entity = em.find(DireccionEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    /**
     * Busca todos los Direccions que hay en la base de datos ingresados de manera aleatoria
     * y compara los id's de estos con la lista generada por el método findAll de la clase de persisitencia
     */
    @Test
    public void getDireccionsTest() {
        List  <DireccionEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (DireccionEntity ent : list) {
            boolean found = false;
            for  (DireccionEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    /**
     * Busca un Direccion dado un id, en este caso, se prueba con el id de la primera entidad generada en el atributo
     * data, compara este objeto retornado al invocar al método find() de la clase de perisistencia y lo compara con null,
     * además de comparar ambas id's
     */
    @Test
    public void getDireccionTest() {
        DireccionEntity entity = data.get(0);
        DireccionEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    @Test
    /**
     * Con la entidad generada aleatoriamente que se encuentra en la posición 0 
     * del atributo data, se llama al método delete de la clase de persistencia
     * y luego se compara este con null lo cual debe ser true ya que el objeto fue eliminado
     */
    public void deleteDireccionTest() {
        DireccionEntity entity = data.get(0);
        persistence.delete(entity.getId());
        DireccionEntity deleted = em.find(DireccionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    /**
     * Con la enridad generada aleatoriamente que se encuentra en la posición 0
     * del atributo data, crea otra nueva entidad con podam y le asigna el id de la 
     * id de la entidad de data y se invoca al método update de la clase de perisistencia, luego,
     * se busca el mismo objeto en la base de datos para ver si se encuentra con el id actualizado
     */
    @Test
    public void updateDireccionTest() {
        DireccionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DireccionEntity newEntity = factory.manufacturePojo(DireccionEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        DireccionEntity resp = em.find(DireccionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
}
