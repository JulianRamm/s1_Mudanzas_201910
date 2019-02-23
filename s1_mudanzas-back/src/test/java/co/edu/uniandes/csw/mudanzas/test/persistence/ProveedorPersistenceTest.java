/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
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
public class ProveedorPersistenceTest {
    
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
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
    private List<ProveedorEntity> data = new ArrayList<ProveedorEntity>();

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
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }
    
     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createProveedorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = proveedorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ProveedorEntity entity = em.find(ProveedorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
    }

    @Test
    public void getProveedoresTest() {
        List<ProveedorEntity> lista = proveedorPersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());

        for (ProveedorEntity enLista : lista) {
            boolean encontrado = false;
            for (ProveedorEntity enData : data) {
                if (enLista.getId().equals(enData.getId()));
                encontrado = true;
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    @Test
    public void getProveedorTest() {
        ProveedorEntity entidad = data.get(0);
        ProveedorEntity proveedor = proveedorPersistence.find(entidad.getId());
        Assert.assertNotNull(proveedor);
        Assert.assertEquals(entidad.getId(), proveedor.getId());
        Assert.assertEquals(entidad.getNombre(), proveedor.getNombre());
        Assert.assertEquals(entidad.getCorreoElectronico(), proveedor.getCorreoElectronico());
        Assert.assertEquals(entidad.getLogin(), proveedor.getLogin());
        Assert.assertEquals(entidad.getPassword(), proveedor.getPassword());
    }

    @Test
    public void deleteUsuarioTest() {
        ProveedorEntity entidad = data.get(0);
        proveedorPersistence.delete(entidad.getId());
        ProveedorEntity borrado = em.find(ProveedorEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }
    
    @Test
    public void updateUsuarioTest() {
        ProveedorEntity entidad = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity cambiada = factory.manufacturePojo(ProveedorEntity.class);

        cambiada.setId(entidad.getId());

        proveedorPersistence.update(cambiada);

        ProveedorEntity encontrada = em.find(ProveedorEntity.class, entidad.getId());

        Assert.assertEquals(cambiada.getNombre(), encontrada.getNombre());
        Assert.assertEquals(cambiada.getId(), encontrada.getId());
        Assert.assertEquals(cambiada.getCorreoElectronico(), encontrada.getCorreoElectronico());
        Assert.assertEquals(cambiada.getLogin(), encontrada.getLogin());
        Assert.assertEquals(cambiada.getPassword(), encontrada.getPassword());
    }
    
    @Test
    public void buscarProveedorPorLogin() {
        ProveedorEntity entidad = data.get(0);
        ProveedorEntity nuevo = proveedorPersistence.findUsuarioPorLogin(entidad.getLogin());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(entidad.getLogin(), nuevo.getLogin());

        nuevo = proveedorPersistence.findUsuarioPorLogin(null);
        Assert.assertNull(nuevo);
    }
    
}
