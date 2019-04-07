/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.DiaLogic;
import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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

    /**
     * Inyección de la lógica del día
     */
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
     * Atrributo que modela la entidad de un vehiculo
    */

    private VehiculoEntity vehiculoData;
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
        VehiculoEntity vehiculo = factory.manufacturePojo(VehiculoEntity.class);
        vehiculo.setPlaca("BYC943");
        em.persist(vehiculo);
        vehiculoData = vehiculo;
        for (int i = 0; i < 3; i++) {
            DiaEntity entity = factory.manufacturePojo(DiaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    private void clearData() 
    {
        em.createQuery("delete from DiaEntity").executeUpdate();
        em.createQuery("delete from VehiculoEntity").executeUpdate();
    }
    
    Date horaFin = crearHoraFin();
    
    @Test
    public void createAgendaTest() throws BusinessLogicException
    {
        
      DiaEntity nuevaEntidad = factory.manufacturePojo(DiaEntity.class);
     nuevaEntidad.setHoraFin(horaFin);
      nuevaEntidad.setHoraInicio(crearHoraInicio(nuevaEntidad.getHoraFin()));
     nuevaEntidad.setDiaActual(Calendar.getInstance(TimeZone.getDefault()).getTime());
      nuevaEntidad.setIsDisponibilidad(true);
      DiaEntity resultado = DLogic.crearDia(nuevaEntidad, vehiculoData.getPlaca());
      Assert.assertNotNull(resultado);
      DiaEntity entidad = em.find(DiaEntity.class, resultado.getId());
      Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
      Assert.assertEquals(nuevaEntidad.getHoraInicio().getTime(), entidad.getHoraInicio().getTime());
        Assert.assertEquals(nuevaEntidad.getHoraFin().getTime(), entidad.getHoraFin().getTime());
        Assert.assertEquals(nuevaEntidad.getDiaActual().getYear(), entidad.getDiaActual().getYear());
       Assert.assertEquals(nuevaEntidad.getIsDisponibilidad(), entidad.getIsDisponibilidad());
   
    }
    /**
     * Crea un Date a partir de un String
     * @param pDia
     * @return 
     */
    private Date toDate(String pDia)
    {
        Date rta = null;
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       try
       {
       rta = sdf.parse(pDia);
       }
       catch(ParseException e)
       {
           e.printStackTrace();
       }
        return rta;
    }
    /**
* Metodo auxiliar para crear una horaFin que cumpla con sus reglas de negocio
* @return 
     */
    private Timestamp crearHoraFin()
    {
//        Random  rnd;
//        Date    dt;
//        long    ms;
//
//// Get a new random instance, seeded from the clock
//        rnd = new Random();
//
//// Get an Epoch value roughly between 1940 and 2010
//// -946771200000L = January 1, 1940
//// Add up to 80 years to it (using modulus on the next long)
//        ms = -946771200000L + (Math.abs(rnd.nextLong()) % (80L * 365 * 24 * 60 * 60 * 1000));
//
//// Construct a date
//        dt = new Date(ms);
//        return dt;
        
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand;
    }
    /**
     * Metodo auxiliar para crear una hora inicio que cumpla con sus reglas de negocio
     * @param pTime
     * @return 
     */
    private Date crearHoraInicio(Date pTime)
    {
        Timestamp rta=null;
        long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2019-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp[] ms = new Timestamp[100];

// Get a new random instance, seeded from the clock
        // Get an Epoch value roughly between 1940 and 2010
// -946771200000L = January 1, 1940
//// Add up to 80 years to it (using modulus on the next long)
         
         for(int i = 0; i<100;i++)
         {
            ms[i]= new Timestamp(offset + (long)(Math.random() * diff));;
            if(ms[i].before(pTime))
            {
                rta = ms[i];
            }
             
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
        DLogic.crearDia(usr, vehiculoData.getPlaca());
    }
    
    /**
     * Prueba la regla de negocio para la hora de inicio del vehiculo
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test 
    public void horaInicioTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setHoraInicio(crearHoraInicio(usr.getHoraFin()));
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr, vehiculoData.getPlaca());
    }
    
     /**
     * Prueba la regla de negocio para la horaFin
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test (expected = BusinessLogicException.class)
    public void horaFinTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setHoraFin(crearHoraFin());
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr, vehiculoData.getPlaca());
    }
    
     /**
     * Prueba la regla de negocio para el dia actual
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test(expected = BusinessLogicException.class)
    public void diaActualTest() throws BusinessLogicException {
        //podam nos crea una instancia automatica
        DiaEntity usr = factory.manufacturePojo(DiaEntity.class);
        usr.setDiaActual(toDate("123456"));
        //llamamos al manager de persistencia, en este caso no se creara
        DLogic.crearDia(usr, vehiculoData.getPlaca());
    }
    
    /**
     * Prueba la eliminacion de un Dia.
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void deleteDiaTest() throws BusinessLogicException {
        DiaEntity entidad = data.get(1);
        DLogic.deleteDia(entidad.getId());
        DiaEntity borrar = em.find(DiaEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }
    
    /**
     * Prueba la obtencion de una agenda por su id
     *
     * @throws BusinessLogicException si no se cumple la regla de negocio
     */
    @Test
    public void getDiaPorIdTest() throws BusinessLogicException {
        DiaEntity entidad = data.get(0);
        DiaEntity resultado = DLogic.getDia(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(resultado.getId(), entidad.getId());
        Assert.assertEquals(resultado.getHoraInicio(), entidad.getHoraInicio());
        Assert.assertEquals(resultado.getHoraFin(), entidad.getHoraFin());
        Assert.assertEquals(resultado.getIsDisponibilidad(), entidad.getIsDisponibilidad());
        Assert.assertEquals(resultado.getDiaActual().getYear(), entidad.getDiaActual().getYear());
    }
    
    
    
    
}
