/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
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
public class SubastaPersistenceTest {

    @Inject
    private SubastaPersistence SubPer;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<SubastaEntity> data;

    private PodamFactory factory = new PodamFactoryImpl();

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
        em.createQuery("delete from SubastaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);

        em.persist(usr);

        ProveedorEntity prv = factory.manufacturePojo(ProveedorEntity.class);

        em.persist(prv);

        for (int i = 0; i < 3; i++) {

            SubastaEntity subEntity = factory.manufacturePojo(SubastaEntity.class);

            subEntity.setUsuario(usr);

            subEntity.setProveedor(prv);

            em.persist(subEntity);

            data.add(subEntity);
        }
    }

    @Test
    public void createSubastaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SubastaEntity newEntity = factory.manufacturePojo(SubastaEntity.class);

        SubastaEntity subEntity = SubPer.create(newEntity);

        Assert.assertNotNull(subEntity);

        SubastaEntity subFound = em.find(SubastaEntity.class, subEntity.getId());

        Assert.assertEquals(subEntity, subFound);

    }

    @Test
    public void getSubastasTest() {

        List<SubastaEntity> listaEn = SubPer.findAll();
        Assert.assertEquals(listaEn.size(), data.size());
        for (SubastaEntity actual : listaEn) {
            boolean loEncontre = false;
            for (SubastaEntity enData : data) {
                if (actual.getId().equals(enData.getId()));
                loEncontre = true;
            }
            Assert.assertTrue(loEncontre);
        }
    }

    @Test
    public void getTarjetaTest() {
        SubastaEntity entidad = data.get(0);
        SubastaEntity subEncontrada = SubPer.find(entidad.getId());
        Assert.assertNotNull(subEncontrada);
        Assert.assertTrue(entidad.getValorInicial() == subEncontrada.getValorInicial());
        Assert.assertEquals(entidad.getId(), subEncontrada.getId());
        Assert.assertEquals(entidad.getUsuario(), subEncontrada.getUsuario());
        Assert.assertTrue(entidad.getValorFinal() == subEncontrada.getValorFinal());
        Assert.assertEquals(entidad.getProveedor(), subEncontrada.getProveedor());
        Assert.assertEquals(entidad.getOfertas(), subEncontrada.getOfertas());

    }

    @Test
    public void deleteTarjetaTest() {
        SubastaEntity entidad = data.get(0);
        SubPer.delete(entidad.getId());
        SubastaEntity borrado = em.find(SubastaEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }

    @Test
    public void updateTarjetaTest() {
        SubastaEntity entidad = data.get(0);

        SubastaEntity cambiada = factory.manufacturePojo(SubastaEntity.class);

        cambiada.setId(entidad.getId());

        SubPer.update(cambiada);

        SubastaEntity subEncontrada = em.find(SubastaEntity.class, entidad.getId());

        Assert.assertNotNull(subEncontrada);
        Assert.assertTrue(entidad.getValorInicial() == subEncontrada.getValorInicial());
        Assert.assertEquals(entidad.getId(), subEncontrada.getId());
        Assert.assertEquals(entidad.getUsuario(), subEncontrada.getUsuario());
        Assert.assertTrue(entidad.getValorFinal() == subEncontrada.getValorFinal());
        Assert.assertEquals(entidad.getProveedor(), subEncontrada.getProveedor());
        Assert.assertEquals(entidad.getOfertas(), subEncontrada.getOfertas());
    }

    /**
     * Prueba para buscar una tarjeta por el nombre de su propietario.
     */
    @Test
    public void buscarTarjetaPorLogin() {
        SubastaEntity prueba = data.get(0);
        SubastaEntity nuevo = SubPer.findOneByUser(prueba.getUsuario().getLogin(), prueba.getId());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(prueba.getUsuario().getLogin(), nuevo.getUsuario().getLogin());

        nuevo = SubPer.findOneByUser(prueba.getUsuario().getLogin(), null);
        Assert.assertNull(nuevo);

        nuevo = SubPer.findOneByProveedor(prueba.getProveedor().getLogin(), prueba.getId());

        Assert.assertNotNull(nuevo);
        Assert.assertEquals(prueba.getProveedor().getLogin(), nuevo.getProveedor().getLogin());

        nuevo = SubPer.findOneByUser(prueba.getProveedor().getLogin(), null);
        Assert.assertNull(nuevo);

    }

}
