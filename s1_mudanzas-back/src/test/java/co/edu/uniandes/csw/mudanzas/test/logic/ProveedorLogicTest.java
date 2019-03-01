/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.ProveedorEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.UsuarioPersistence;
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
public class ProveedorLogicTest {
    
    
    @Inject
    private ProveedorLogic pL;
    
    
    @Inject
    private ProveedorPersistence pP;
    
     /**
     * Llamamos al encargado de la BD
     */
    @PersistenceContext
    private EntityManager eM;
    
        /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction uTX;
    
     /**
     * Lista que tiene los datos de prueba.
     */
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    /**
     * Crea todo lo necesario para el desarrollo de las pruebas.
     *
     * @return .jar
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            uTX.begin();
            eM.joinTransaction();
            clearData();
            insertData();
            uTX.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                uTX.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        eM.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            eM.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void crearProveedorTest()throws BusinessLogicException{
        
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity entidad = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity nuevoP = pL.createProveedor(entidad);
        Assert.assertNotNull(nuevoP);
        ProveedorEntity entidadNueva = eM.find(ProveedorEntity.class, nuevoP.getId());
        Assert.assertEquals(entidad.getId(), entidad.getId());
        Assert.assertEquals(entidad.getNombre(), entidad.getNombre());
        Assert.assertEquals(entidad.getCorreoElectronico(), entidad.getCorreoElectronico());
        Assert.assertEquals(entidad.getPassword(), entidad.getPassword());
        Assert.assertEquals(entidad.getLogin(), entidad.getLogin());
    }
}
