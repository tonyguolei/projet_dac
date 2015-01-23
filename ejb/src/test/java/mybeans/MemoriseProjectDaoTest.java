package mybeans;

import junit.framework.Assert;
import org.junit.*;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Simon Jacquin
 */
public class MemoriseProjectDaoTest {
    private static MemoriseProjectDao instanceMemoriseProjectDao;
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;

    private static User user;
    private static Project project;

    private MemoriseProject memoriseProject;

    public MemoriseProjectDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("MemoriseProjectDao unit test start");
        try {
            instanceMemoriseProjectDao = BeanTestUtils.lookup(MemoriseProjectDao.class, "MemoriseProjectDao");
            instanceUserDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceProjectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");

            user = new User("test"+Math.random()+"@test.com", "test");
            instanceUserDao.save(user);
            project = new Project(user, BigDecimal.TEN, "Projet test"+Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
            instanceProjectDao.save(project);
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        if (project != null) {
            instanceProjectDao.delete(project);
        }
        if (user != null) {
            instanceUserDao.delete(user);
        }
        System.out.println("MemoriseProjectDao unit test stop");
    }

    @Before
    public void setUp() {
        memoriseProject = new MemoriseProject(user, project);
        instanceMemoriseProjectDao.save(memoriseProject);
    }

    @After
    public void tearDown() {
        if (memoriseProject != null 
                && instanceMemoriseProjectDao.getByIdMemoriseProject(memoriseProject.getIdMemoriseProject()) != null) {
            instanceMemoriseProjectDao.remove(memoriseProject);
        }
    }

    @Test
    public void testGetById() {
        System.out.println("getById");
        MemoriseProject result = instanceMemoriseProjectDao.getByIdMemoriseProject(memoriseProject.getIdMemoriseProject());
        Assert.assertEquals(memoriseProject, result);
    }

    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List<MemoriseProject> result = instanceMemoriseProjectDao.getAll();
        Assert.assertTrue(result.contains(memoriseProject));
    }

    @Test
    public void testGetByUser() {
        System.out.println("getByUser");
        List<Project> result = instanceMemoriseProjectDao.getByUser(user);
        Assert.assertTrue(result.contains(memoriseProject.getIdProject()));
    }

    @Test
    public void testRemove() {
        System.out.println("remove");
        instanceMemoriseProjectDao.remove(memoriseProject);
        MemoriseProject result = instanceMemoriseProjectDao.getByIdMemoriseProject(memoriseProject.getIdMemoriseProject());
        Assert.assertNull(result);
    }

    @Test
    public void testGettersSetters() {
        // getters
        Assert.assertEquals(user, memoriseProject.getIdUser());
        Assert.assertEquals(project, memoriseProject.getIdProject());

        // equals
        User user2 = new User("test"+Math.random()+"@test.com", "test");
        MemoriseProject mem2 = new MemoriseProject(user2, project);
        Assert.assertFalse(memoriseProject.equals(mem2));
        Assert.assertFalse(mem2.equals(memoriseProject));
        Assert.assertTrue(memoriseProject.equals(memoriseProject));
        Assert.assertFalse(memoriseProject.equals(1));

        // toString
        Assert.assertNotNull(memoriseProject.toString());

        // hashcode
        try {
            int hash = memoriseProject.hashCode();
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
