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
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author guolei
 */

public class ProjectDaoTest {
    private static EJBContainer container;
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private BigDecimal goal = BigDecimal.valueOf(100);
    private String title = "testProjetDao";
    private String title1 = "test1";
    private static String mail = "testUserDao@gmail.com";
    private static String password = "test";
    private static User user = new User(mail, password);
    private static Project project;
    private String description = "test";
    private Date endDate = java.sql.Date.valueOf("2100-01-01");
    private String tags = "test";

    public ProjectDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            container = ContainerInstance.getContainer();
            instanceUserDao = (UserDao) container.getContext().lookup("java:global/classes/UserDao");
            instanceProjectDao = (ProjectDao) container.getContext().lookup("java:global/classes/ProjectDao");
            instanceUserDao.save(user);
            user = instanceUserDao.getByMail(mail);
            System.out.println("ProjectDao unit test start");
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
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class ProjectDao.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        project = new Project(user, goal, title, description, endDate, tags);
        instanceProjectDao.save(project);
        assertEquals(instanceProjectDao.getByTitleProject(title), project);
        instanceProjectDao.delete(project);
    }

    /**
     * Test of delete method, of class ProjectDao.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        project = new Project(user, goal, title, description, endDate, tags);
        instanceProjectDao.save(project);
        assertEquals(instanceProjectDao.getByTitleProject(title), project);
        instanceProjectDao.delete(project);
        Project project = instanceProjectDao.getByTitleProject(title);
        Assert.assertNull(project);
    }

    /**
     * Test of update method, of class ProjectDao.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        project = new Project(user, goal, title, description, endDate, tags);
        instanceProjectDao.save(project);
        project.setTitle(title1);
        instanceProjectDao.update(project);
        assertEquals((instanceProjectDao.getByTitleProject(title1)).getTitle(), title1);
        instanceProjectDao.delete(project);
    }
}