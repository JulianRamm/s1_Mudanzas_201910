/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.test.logic;

import co.edu.uniandes.csw.mudanzas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
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
 * @author Luis Miguel
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {

    /**
     * Inyeccion de la dependencia a la clase UsuarioLogic cuyos metodos se van
     * a probar.
     */
    @Inject
    private UsuarioLogic usuarioLogic;

    /**
     * Atributo que instancia a un usuario.
     */
    @Inject
    private UsuarioPersistence ep;

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity resultado = usuarioLogic.crearUsuario(nuevaEntidad);
        Assert.assertNotNull(resultado);
        UsuarioEntity entidad = em.find(UsuarioEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getId(), entidad.getId());
        Assert.assertEquals(nuevaEntidad.getNombre(), entidad.getNombre());
        Assert.assertEquals(nuevaEntidad.getApellido(), entidad.getApellido());
        Assert.assertEquals(nuevaEntidad.getCiudadDeOrigen(), entidad.getCiudadDeOrigen());
        Assert.assertEquals(nuevaEntidad.getCorreoElectronico(), entidad.getCorreoElectronico());
        Assert.assertEquals(nuevaEntidad.getPassword(), entidad.getPassword());
        Assert.assertEquals(nuevaEntidad.getLogin(), entidad.getLogin());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createUsuarioMismoLoginTest() throws BusinessLogicException
    {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity nuevaEntidad = factory.manufacturePojo(UsuarioEntity.class);
        nuevaEntidad.setLogin(data.get(0).getLogin());
        usuarioLogic.crearUsuario(nuevaEntidad);
    }

}
