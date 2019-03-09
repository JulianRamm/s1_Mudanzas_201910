/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.SubastaLogic;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.SubastaPersistence;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.Assert.fail;
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
public class SubastaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SubastaLogic subastaLogic;

    @Inject
    private SubastaPersistence sp;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SubastaEntity> subastasData = new LinkedList<>();

    private UsuarioEntity usuario;
    
    private ProveedorEntity proveedor;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SubastaEntity.class.getPackage())
                .addPackage(SubastaLogic.class.getPackage())
                .addPackage(SubastaPersistence.class.getPackage())
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
        em.createQuery("delete from SubastaEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        UsuarioEntity user = factory.manufacturePojo(UsuarioEntity.class);
        user.setLogin("luismigolondo");
        em.persist(user);
        this.usuario = user;

        ProveedorEntity prv = factory.manufacturePojo(ProveedorEntity.class);
        prv.setLogin("Andres");
        em.persist(prv);
        proveedor = prv;

        for (int i = 0; i < 3; i++) {
            SubastaEntity subastas = factory.manufacturePojo(SubastaEntity.class);
            subastas.setUsuario(user);
            subastas.setProveedor(prv);
            em.persist(subastas);
            subastasData.add(subastas);
        }

    }

    @Test
    public void createSubastaTest() throws BusinessLogicException {
        SubastaEntity ManufacturedEntity = factory.manufacturePojo(SubastaEntity.class);
        ManufacturedEntity.setValorFinal(ManufacturedEntity.getValorInicial());
        SubastaEntity subcreada = subastaLogic.createSubastaUsuario(ManufacturedEntity, usuario.getLogin());
        Assert.assertNotNull(subcreada);
        SubastaEntity entidadFound = subastaLogic.getSubasta(subcreada.getId());
        Assert.assertEquals(ManufacturedEntity.getId(), entidadFound.getId());

    }

    @Test
    public void createSubastasIguales() {
        SubastaEntity ManufacturedEntity = factory.manufacturePojo(SubastaEntity.class);
        ManufacturedEntity.setValorFinal(ManufacturedEntity.getValorInicial());
        try {
            subastaLogic.createSubastaUsuario(ManufacturedEntity, usuario.getLogin());
            subastaLogic.createSubastaUsuario(ManufacturedEntity, usuario.getLogin());

            fail("No deberia crear dos subs iguales");
        } catch (BusinessLogicException ex) {
            //no deberia llegar aca
        }

    }

    @Test(expected = BusinessLogicException.class)
    public void createSubastasDiferenteValor() throws BusinessLogicException {
        SubastaEntity ManufacturedEntity = factory.manufacturePojo(SubastaEntity.class);
        ManufacturedEntity.setValorFinal(10);
        ManufacturedEntity.setValorInicial(2);
            subastaLogic.createSubastaUsuario(ManufacturedEntity, usuario.getLogin());
        
    }

   

    @Test
    public void getSubastas() {
        List<SubastaEntity> prueba = subastaLogic.getSubastas();
        Assert.assertEquals(prueba.size(), subastasData.size());
        boolean encontro;
        for (SubastaEntity pruebaActual : prueba) {
            encontro = false;
            for (SubastaEntity realActual : subastasData) {
                if (pruebaActual.equals(realActual)) {
                    encontro = true;
                }
            }
            Assert.assertTrue(encontro);

        }

    }
    
    
    @Test
    public void getSubastasUserProv() throws BusinessLogicException
    {
        try {
            List<SubastaEntity> real = new LinkedList<>();
            utx.begin();
            
            
            UsuarioEntity nUser = factory.manufacturePojo(UsuarioEntity.class);
            ProveedorEntity nProv = factory.manufacturePojo(ProveedorEntity.class);
            nProv.setLogin("Papaoutai");
            nUser.setLogin("StolenDance");
            em.persist(nUser);
            em.persist(nProv);
            
            utx.commit();
            
            for (int i = 0; i < 5; i++) {
                SubastaEntity nuevaActual = factory.manufacturePojo(SubastaEntity.class);
                nuevaActual.setValorFinal(nuevaActual.getValorInicial());
                nuevaActual.setUsuario(nUser);
                nuevaActual.setId(Long.MIN_VALUE+ i);
                nuevaActual.setProveedor(nProv);
                
                subastaLogic.createSubastaUsuario(nuevaActual, nUser.getLogin());
                subastaLogic.createSubastaProveedor(nuevaActual, nProv.getLogin());

                real.add(nuevaActual);
            }
            
            Assert.assertEquals(subastaLogic.getSubastasUsuario("StolenDance"), real);
            Assert.assertEquals(subastaLogic.getSubastasProveedor("Papaoutai"), real);
        } catch (Exception ex) {
            ex.printStackTrace(); }
    }
    
    
    @Test
    public void getSubastaProvUser() throws BusinessLogicException
    {
        SubastaEntity real = subastasData.get(0);
        SubastaEntity pruebaPv = subastaLogic.getSubastaProveedor( real.getProveedor().getLogin(),real.getId());

        SubastaEntity pruebaUs = subastaLogic.getSubasta( real.getId());
        Assert.assertNotNull(pruebaUs);
        Assert.assertNotNull(pruebaPv);
        Assert.assertEquals(pruebaUs, real);
        Assert.assertEquals(pruebaPv, real);
    }
    @Test
    public void updateSubastaTest() {
        SubastaEntity entidad = subastasData.get(0);

        SubastaEntity cambiada = factory.manufacturePojo(SubastaEntity.class);

        cambiada.setId(entidad.getId());

        subastaLogic.update(cambiada);
        

        SubastaEntity subEncontrada = em.find(SubastaEntity.class, entidad.getId());

        Assert.assertNotNull(subEncontrada);
        Assert.assertTrue(cambiada.getValorInicial() == subEncontrada.getValorInicial());
        Assert.assertEquals(cambiada.getId(), subEncontrada.getId());
        Assert.assertEquals(cambiada.getUsuario(), subEncontrada.getUsuario());
        Assert.assertTrue(cambiada.getValorFinal() == subEncontrada.getValorFinal());
        Assert.assertEquals(cambiada.getProveedor(), subEncontrada.getProveedor());
        Assert.assertEquals(cambiada.getOfertas(), subEncontrada.getOfertas());
    }

    
    
    
     @Test
    public void deleteSubasta() {
        SubastaEntity entidad = subastasData.get(0);
        subastaLogic.delete(entidad.getId());
        SubastaEntity borrar = em.find(SubastaEntity.class, entidad.getId());
        Assert.assertNull(borrar);
    }
}
