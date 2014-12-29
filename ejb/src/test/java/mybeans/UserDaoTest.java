/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import junit.framework.Assert;
import org.junit.*;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author guolei
 */
public class UserDaoTest {
    private static EJBContainer container;
    private static UserDao instance;
    
    public UserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        try {
            container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
            instance = (UserDao) container.getContext().lookup("java:global/classes/UserDao");
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        User entity = instance.getByMail("test@gmail.com");
        if(entity != null){
            instance.delete(entity); 
        }
        container.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getByMail method, of class UserDao.
     */
    @Test
    public void testGetByMail() throws Exception {
        System.out.println("getByMail");
        User entity = new User("test@gmail.com", "test");
        instance.save(entity);
        assertEquals(entity, instance.getByMail("test@gmail.com"));
        instance.delete(entity);
    }
    
    /**
     * Test of delete method, of class UserDao.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        User entity = new User("test@gmail.com", "test");
        instance.save(entity);
        instance.delete(entity);
        User entity1 = instance.getByMail("test@gmail.com");
        Assert.assertNull(entity1);
    }
    
    /**
     * Test of save method, of class UserDao.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        User entity = new User("test@gmail.com", "test");
        instance.save(entity);
        assertEquals(instance.getByMail("test@gmail.com"),entity);
        instance.delete(entity);
    }
    
    /**
     * Test of update method, of class UserDao.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User entity = new User("test@gmail.com", "test");
        instance.save(entity);
        entity.setPassword("test1");
        instance.update(entity);
        assertEquals((instance.getByMail("test@gmail.com")).getPassword(), "test1");
        instance.delete(entity);
    } 
}
