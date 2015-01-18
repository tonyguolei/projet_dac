/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.math.BigDecimal;
import java.sql.Date;
import junit.framework.Assert;
import org.junit.*;

import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author guolei
 */
public class UserDaoTest {
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private static FundDao instanceFundDao;
    
    public UserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        try {
            instanceUserDao = (UserDao) BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceFundDao = (FundDao) BeanTestUtils.lookup(FundDao.class, "FundDao");
            instanceProjectDao = (ProjectDao) BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            System.out.println("UsertDao unit test start");
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        User entity = instanceUserDao.getByMail("test@gmail.com");
        if(entity != null){
            instanceUserDao.delete(entity);
        }
        System.out.println("UserDao unit test stop");
    }
    
    /**
     * Test of getByMail method, of class UserDao.
     */
    @Test
    public void testGetByMail() throws Exception {
        System.out.println("getByMail");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        assertEquals(u, instanceUserDao.getByMail("test@gmail.com"));
        instanceUserDao.delete(u);
    }
    
    /**
     * Test of delete method, of class UserDao.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        instanceUserDao.delete(u);
        User v = instanceUserDao.getByMail("test@gmail.com");
        Assert.assertNull(v);
    }
    
    /**
     * Test of save method, of class UserDao.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        assertEquals(instanceUserDao.getByMail("test@gmail.com"),u);
        instanceUserDao.delete(u);
    }
    
    /**
     * Test of update method, of class UserDao.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        u.setPassword("test1");
        instanceUserDao.update(u);
        assertEquals((instanceUserDao.getByMail("test@gmail.com")).getPassword(), "test1");
        instanceUserDao.delete(u);
    }
    
    /**
     * Test of getByIdUser method, of class UserDao.
     */
    @Test
    public void testGetByIdUser() throws Exception {
        System.out.println("getByIdUser");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        int id = u.getIdUser();
        assertEquals(u, instanceUserDao.getByIdUser(id));
        instanceUserDao.delete(u);
        Assert.assertNull(instanceUserDao.getByIdUser(id));
    }
    
    /**
     * Test of getAll method, of class UserDao.
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        Assert.assertNotNull(instanceUserDao.getAll());
        Assert.assertTrue(instanceUserDao.getAll().size()>=1);
        instanceUserDao.delete(u);
    }
    
    /**
     * Test of getProjects method, of class UserDao.
     */
    @Test
    public void testGetProjects() throws Exception {
        System.out.println("getProjects");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        Project p = new Project(u, BigDecimal.TEN, "Title test", "Project description", Date.valueOf("2015-10-01"), "testtag1,testtag2");
        instanceProjectDao.save(p);
        assertEquals(p, instanceUserDao.getProjects(u).get(0));
        instanceProjectDao.delete(p);
        instanceUserDao.delete(u);
    }
    
    /**
     * Test of getFunds method, of class UserDao.
     */
    @Test
    public void testGetFunds() throws Exception {
        System.out.println("getFunds");
        User u = new User("test@gmail.com", "test");
        instanceUserDao.save(u);
        Project p = new Project(u, BigDecimal.TEN, "Title test", "Project description", Date.valueOf("2015-10-01"), "testtag1,testtag2");
        instanceProjectDao.save(p);
        Fund f = new Fund(u, p, BigDecimal.ONE);
        instanceFundDao.save(f);
        assertEquals(f, instanceUserDao.getFunds(u).get(0));
        instanceFundDao.delete(f);
        instanceProjectDao.delete(p);
        instanceUserDao.delete(u);
    }
}
