/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.persistence;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
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
 * @author Samuel Bernal Neira
 */
@RunWith(Arquillian.class)
public class DiaPersistenceTest 
{
    PodamFactory factory = new PodamFactoryImpl();
     
    /**
     * Inyeccion de la persistencia del dia
     */
    @Inject
    private DiaPersistence APersistence;
    
    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
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
    private List<DiaEntity> data = new ArrayList<DiaEntity>();

   
    
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
                .addPackage(DiaEntity.class.getPackage())
                .addPackage(DiaPersistence.class.getPackage())
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
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {

            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    private void clearData() 
    {
        em.createQuery("delete from DiaEntity").executeUpdate();
    }
    /**
     * Verificación de todas las reglas de negocio de un dia
     */
    @Test
    public void createAgendaTest()
    {
        DiaEntity newEntity = factory.manufacturePojo(DiaEntity.class);
        DiaEntity result = APersistence.create(newEntity);
        Assert.assertNotNull(result);

        DiaEntity entity = em.find(DiaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    /**
     * Verificación de que si se pueda eliminar un dia
     */
    @Test
    public void deleteDiaTest() {
        DiaEntity entidad = data.get(0);
        APersistence.delete(entidad.getId());
        DiaEntity borrado = em.find(DiaEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }
    /**
     * Verificación de que si se pueda obtener un dia
     */
    @Test
    public void getAgendaTest() {
        DiaEntity entidad = data.get(0);
        DiaEntity nuevo = APersistence.find(entidad.getId());
        Assert.assertNotNull(nuevo);
        Assert.assertEquals(entidad.getHoraInicio(), nuevo.getHoraInicio());
        Assert.assertEquals(entidad.getId(), nuevo.getId());
        Assert.assertEquals(entidad.getHoraFin(), nuevo.getHoraFin());
        Assert.assertEquals(entidad.getDiaActual().getYear(), nuevo.getDiaActual().getYear());
        Assert.assertEquals(entidad.getIsDisponibilidad(), nuevo.getIsDisponibilidad());
    }
    
    /**
     * Verificación de que si se pueda obtener todos los dias
     */
    @Test
    public void getDiasTest() {
        List<DiaEntity> lista = APersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());

        for (DiaEntity enLista : lista) {
            boolean loEncontre = false;
            for (DiaEntity enData : data) {
                if (enLista.getId().equals(enData.getId()));
                loEncontre = true;
            }
            Assert.assertTrue(loEncontre);
        }

    }

    
    /**
     * Verificación de que si se pueda actualizar un dia
     */
    @Test
    public void updateAgendaTest() {
        DiaEntity entidad = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DiaEntity cambiada = factory.manufacturePojo(DiaEntity.class);

        cambiada.setId(entidad.getId());

        APersistence.update(cambiada);

        DiaEntity encontrada = em.find(DiaEntity.class, entidad.getId());
        Assert.assertEquals(cambiada.getHoraInicio(), encontrada.getHoraInicio());
        Assert.assertEquals(cambiada.getHoraFin(), encontrada.getHoraFin());
        Assert.assertEquals(cambiada.getDiaActual().getYear(), encontrada.getDiaActual().getYear());
        Assert.assertEquals(cambiada.getIsDisponibilidad(), encontrada.getIsDisponibilidad());
    }
}
