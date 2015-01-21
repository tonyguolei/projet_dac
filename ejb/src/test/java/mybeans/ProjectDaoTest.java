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
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author guolei
 */

public class ProjectDaoTest {
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private static User user;
    private static Project project;

    public ProjectDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("ProjectDao unit test start");
        try {
            instanceUserDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceProjectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if(project != null){
            instanceProjectDao.delete(project);
        }
        if (user != null) {
            instanceUserDao.delete(user);
        }
        System.out.println("ProjectDao unit test stop");
    }

    @Before
    public void setUp() {
        user = new User("test"+Math.random()+"@test.com", "test");
        instanceUserDao.save(user);
        project = new Project(user, BigDecimal.TEN, "Projet test"+Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
        instanceProjectDao.save(project);
    }

    @After
    public void tearDown() {
        instanceProjectDao.delete(project);
        instanceUserDao.delete(user);
    }

    @Test
    public void testGetIdProject() {
        System.out.println("getIdProject");
        Project p1 = instanceProjectDao.getByIdProject(project.getIdProject());
        Assert.assertEquals(project, p1);
        Project p2 = instanceProjectDao.getByIdProject(-1);
        Assert.assertNull(p2);
    }
    
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List l = instanceProjectDao.getAll();
        Assert.assertTrue(l.size() >= 1);
    }
    
    @Test
    public void testGetAllMatching() {
        System.out.println("getAllMatching");
        List l = instanceProjectDao.getAllMatching("test1");
        Assert.assertTrue(l.size() >= 1);
    }
    
    @Test
    public void testGettersSetters() {
        System.out.println("getters and setters");
        project.setGoal(BigDecimal.ONE);
        String t = "Title"+Math.random();
        project.setTitle(t);
        assertEquals(project.getTitle(), t);
        String d = "Description"+Math.random();
        project.setDescription(d);
        assertEquals(project.getDescription(), d);
        System.out.println(project.getCreationDate());
        project.setEndDate(Date.valueOf("2101-01-01"));
        project.setTags("coucou");
        assertEquals(project.getTags(), "coucou");
        project.setFlagged(true);
        Assert.assertTrue(project.getFlagged());
        Assert.assertFalse(project.equals(1));
        Project p = new Project(user, BigDecimal.TEN, "Projet test"+Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
        Assert.assertFalse(p.equals(project));
        Assert.assertFalse(project.equals(p));
        Assert.assertNotNull(project.toString());
        project.getBonusCollection();
    }
}
