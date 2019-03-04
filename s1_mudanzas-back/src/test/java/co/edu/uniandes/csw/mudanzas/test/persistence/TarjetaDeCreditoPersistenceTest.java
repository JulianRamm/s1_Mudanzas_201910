/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.persistence.TarjetaDeCreditoPersistence;
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
 * @author Luis Miguel
 */
@RunWith(Arquillian.class)
public class TarjetaDeCreditoPersistenceTest {

    /**
     * Atributo que instancia a una tarjeta.
     */
    @Inject
    private TarjetaDeCreditoPersistence tp;

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
    private List<TarjetaDeCreditoEntity> data = new ArrayList<TarjetaDeCreditoEntity>();
    
    private UsuarioEntity usuarioData;

    private PodamFactory factory = new PodamFactoryImpl();

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
        em.createQuery("delete from TarjetaDeCreditoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        UsuarioEntity usr = factory.manufacturePojo(UsuarioEntity.class);

        em.persist(usr);

        usuarioData = usr;
        
        for (int i = 0; i < 3; i++) {

            TarjetaDeCreditoEntity entity = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
            
            entity.setUsuario(usr);
            
            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba unitaria para probar la creacion de una tarjeta.
     */
    @Test
    public void createTarjetaTest() {
        //podam nos crea una instancia automatica

        TarjetaDeCreditoEntity trjt = factory.manufacturePojo(TarjetaDeCreditoEntity.class);
        //llamamos al manager de persistencia, en este caso de usuario
        TarjetaDeCreditoEntity tarjetae = tp.create(trjt);
        //verificamos que no devuelva algo nulo de la creacion en la base de datos. 
        Assert.assertNotNull(tarjetae);
        //Buscamos ese usuario directamente en la BD
        TarjetaDeCreditoEntity entity = em.find(TarjetaDeCreditoEntity.class, tarjetae.getId());

        //verificamos que el mismo que cree en mi propio metodo sea el mismo que relamente se creo en la BD.
        Assert.assertEquals(trjt.getTitularCuenta(), entity.getTitularCuenta());

    }

    /**
     * Prueba, obtiene de la base de datos todas las tarjetas que han sido
     * creadas...
     */
   @Test
    public void getTarjetasTest() {
        List<TarjetaDeCreditoEntity> lista = tp.findAll();
        Assert.assertEquals(data.size(), lista.size());

        for (TarjetaDeCreditoEntity enLista : lista) {
            boolean loEncontre = false;
            for (TarjetaDeCreditoEntity enData : data) {
                if (enLista.getId().equals(enData.getId()))
                loEncontre = true;
            }
            Assert.assertTrue(loEncontre);
        }

    }

    /**
     * Prueba para obtener solo una tarjeta.
     */
    @Test
    public void getTarjetaTest() {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity nuevo = tp.find(entidad.getId());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(entidad.getCodigoSeguridad(), nuevo.getCodigoSeguridad());
        Assert.assertEquals(entidad.getId(), nuevo.getId());
        Assert.assertEquals(entidad.getNombreTarjeta(), nuevo.getNombreTarjeta());
        Assert.assertEquals(entidad.getNumeroSerial(), nuevo.getNumeroSerial());
        Assert.assertEquals(entidad.getTitularCuenta(), nuevo.getTitularCuenta());
    }

    /**
     * Prueba para borrar una tarjeta de la bd
     */
    @Test
    public void deleteTarjetaTest() {
        TarjetaDeCreditoEntity entidad = data.get(0);
        tp.delete(entidad.getId());
        TarjetaDeCreditoEntity borrado = em.find(TarjetaDeCreditoEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }

    /**
     * Prueba para actualizar una tarjeta en la base de datos.
     */
    @Test
    public void updateTarjetaTest() {
        TarjetaDeCreditoEntity entidad = data.get(0);

        TarjetaDeCreditoEntity cambiada = factory.manufacturePojo(TarjetaDeCreditoEntity.class);

        cambiada.setId(entidad.getId());

        tp.update(cambiada);

        TarjetaDeCreditoEntity encontrada = em.find(TarjetaDeCreditoEntity.class, entidad.getId());

        Assert.assertEquals(cambiada.getCodigoSeguridad(), encontrada.getCodigoSeguridad());
        Assert.assertEquals(cambiada.getId(), encontrada.getId());
        Assert.assertEquals(cambiada.getNombreTarjeta(), encontrada.getNombreTarjeta());
        Assert.assertEquals(cambiada.getNumeroSerial(), encontrada.getNumeroSerial());
        Assert.assertEquals(cambiada.getTitularCuenta(), encontrada.getTitularCuenta());
    }

    /**
     * Prueba para buscar una tarjeta por el nombre de su propietario.
     */
    @Test
    public void buscarTarjetaPorLogin() {
        TarjetaDeCreditoEntity entidad = data.get(0);
        TarjetaDeCreditoEntity nuevo = tp.findTarjetaPorLoginUsuario(entidad.getUsuario().getLogin(), entidad.getId());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(entidad.getUsuario().getLogin(), nuevo.getUsuario().getLogin());

        nuevo = tp.findTarjetaPorLoginUsuario(entidad.getUsuario().getLogin(), null);
        Assert.assertNull(nuevo);
    }

}
