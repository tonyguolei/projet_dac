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
public class FundDaoTest {
    private static FundDao instanceFundDao;
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;

    private static User user;
    private static Project project;

    private Fund fund;

    public FundDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("FundDao unit test start");
        try {
            instanceFundDao = BeanTestUtils.lookup(FundDao.class, "FundDao");
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
        System.out.println("FundDao unit test stop");
    }

    @Before
    public void setUp() {
        fund = new Fund(user, project, BigDecimal.ONE);
        instanceFundDao.save(fund);
    }

    @After
    public void tearDown() {
        instanceFundDao.delete(fund);
    }

    @Test
    public void testGetByIdFund() {
        System.out.println("getByIdFund");
        int id = fund.getIdFund();
        Fund result = instanceFundDao.getByIdFund(id);
        Assert.assertEquals(fund, result);
    }

    @Test
    public void testGetAll() {
        System.out.println("getAll");
        List<Fund> result = instanceFundDao.getAll();
        Assert.assertTrue(result.contains(fund));
    }

    @Test
    public void testGetFundLevel() {
        System.out.println("getFundLevel");
        BigDecimal result = instanceFundDao.getFundLevel(project);
        Assert.assertEquals(1, result.intValue());
    }

    @Test
    public void testGetFundByUser() {
        System.out.println("getFundByUser");
        Fund result = instanceFundDao.getFundByUser(user, project);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getValue().intValue());
    }
}
