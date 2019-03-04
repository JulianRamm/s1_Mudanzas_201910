/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.DiaLogic;
import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
public class DiaLogicTest 
{ 
    PodamFactory factory = new PodamFactoryImpl();

    
     @Inject
    private DiaLogic DLogic;
    
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
     * Lista que tiene los datos de prueba.
     */
    
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
                .addPackage(DiaLogic.class.getPackage())
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
    
    @Test
    public void createAgendaTest() throws BusinessLogicException
    {
        
      DiaEntity nuevaEntidad = factory.manufacturePojo(DiaEntity.class);
     nuevaEntidad.setHoraInicio(toDateTime("08:02:11"));
      nuevaEntidad.setHoraFin(toDateTime("10:02:11"));
     nuevaEntidad.setDiaActual(toDate("02/03/2019"));
      nuevaEntidad.setIsDisponibilidad(true);
      DiaEntity resultado = DLogic.crearDia(nuevaEntidad);
      Assert.assertNotNull(resultado);
      DiaEntity entidad = em.find(DiaEntity.class, resultado.getId());
      Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
      Assert.assertEquals(nuevaEntidad.getHoraInicio(), entidad.getHoraInicio());
        Assert.assertEquals(nuevaEntidad.getHoraFin(), entidad.getHoraFin());
        Assert.assertEquals(nuevaEntidad.getDiaActual(), entidad.getDiaActual());
       Assert.assertEquals(nuevaEntidad.getIsDisponibilidad(), entidad.getIsDisponibilidad());
   
    }
    private LocalDateTime toDateTime(String pHora)
    {
        LocalDateTime rta = null;
        try
        {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalDateTime date = LocalDateTime.parse(pHora, form);
        rta = date;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return rta;
    }
    private LocalDate toDate(String pDia)
    {
        LocalDate rta = null;
        try
        {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(pDia, form);
        rta = date;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return rta;
    }
    
    /**
     * Prueba que valida que no se puede crear una usuario si este es nulo.
     *
     * @throws BusinessLogicException si no se cumple esta regla de negocio.
     */
    @Test(expected = BusinessLogicException.class)
    public void nullTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setDiaActual(null);
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr);
    }
    
    /**
     * Prueba la regla de negocio para el correo electronico del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void horaInicioTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setHoraInicio(toDateTime("123456"));
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr);
    }
    
     /**
     * Prueba la regla de negocio para el correo electronico del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void horaFinTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setHoraFin(toDateTime("456789"));
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr);
    }
    
     /**
     * Prueba la regla de negocio para el correo electronico del usuario
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void diaActualTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setDiaActual(toDate("123456"));
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr);
    }
    
    
    
    
}
