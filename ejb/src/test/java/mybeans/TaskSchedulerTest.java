/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Token;
import org.junit.*;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;


/**
 *
 * @author guillaumeperrin
 */

public class TaskSchedulerTest {
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private static FundDao instanceFundDao;
    private static MemoriseProjectDao instanceMemoriseProject;
    private static NotificationDao instanceNotification;
    private static TaskScheduler instanceTaskScheduler;

    private User owner = null;
    private User funder = null;
    private User memoriser = null;
    private Project project = null;
    private Project projectNotFund = null;
    private Project projectFuture = null;
    private Fund fund = null;
    private MemoriseProject mp = null;
    private static String FUND_VALUE = "15.00";

    public TaskSchedulerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            instanceUserDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceProjectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            instanceFundDao = BeanTestUtils.lookup(FundDao.class, "FundDao");
            instanceMemoriseProject = BeanTestUtils.lookup(MemoriseProjectDao.class, "MemoriseProjectDao");
            instanceTaskScheduler = BeanTestUtils.lookup(TaskScheduler.class, "TaskScheduler");
            instanceNotification = BeanTestUtils.lookup(NotificationDao.class, "NotificationDao");
            System.out.println("TaskScheduler unit test start");
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("TaskScheduler unit test stop");
    }

    @Before
    public void setUp() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
        //Create users
        owner = new User("owner@test.com", "test");
        funder = new User("funder@test.com", "test");
        memoriser = new User("memoriser@test.com", "test");

        //Create project
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = java.sql.Date.valueOf(sdf.format(new java.util.Date()));
        project = new Project(owner, new BigDecimal(10), "Test taskscheduler", "desc", endDate, "test");
        projectNotFund = new Project(owner, new BigDecimal(10), "Test taskscheduler not fund", "desc", endDate, "test");
        endDate = java.sql.Date.valueOf("2100-01-01");
        projectFuture = new Project(owner, new BigDecimal(10), "Test taskscheduler future", "desc", endDate, "test");

        //Link
        Stripe.apiKey = "sk_test_GIZv9WnqWKyYNYzsBpDhx0GI";
        HashMap<String, Object> tokenParams = new HashMap<String, Object>();
        HashMap<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 1);
        cardParams.put("exp_year", 2017);
        cardParams.put("cvc", "314");
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);
        fund = new Fund(funder, project, new BigDecimal(FUND_VALUE), token.getId());
        mp = new MemoriseProject(memoriser, project);

        //Inserting
        instanceUserDao.save(owner);
        instanceUserDao.save(funder);
        instanceUserDao.save(memoriser);
        instanceProjectDao.save(project);
        instanceProjectDao.save(projectNotFund);
        instanceProjectDao.save(projectFuture);
        instanceFundDao.save(fund);
        instanceMemoriseProject.save(mp);
    }

    @After
    public void tearDown() {
        //Deleting
        instanceMemoriseProject.delete(mp);
        instanceFundDao.delete(fund);
        instanceProjectDao.delete(project);
        instanceProjectDao.delete(projectNotFund);
        instanceProjectDao.delete(projectFuture);
        instanceUserDao.delete(owner);
        instanceUserDao.delete(funder);
        instanceUserDao.delete(memoriser);
    }

    /**
     * Test of checkDeadline
     */
    @Test
    public void testCheckDeadline() throws Exception {
        System.out.println("checkDeadline");
        instanceTaskScheduler.checkDeadLine();
        //Check status project and owner
        project = instanceProjectDao.getByIdProject(project.getIdProject());
        assertTrue(project.alreadyTransferred());
        owner = instanceUserDao.getByMail(owner.getMail());
        assertEquals(new BigDecimal(FUND_VALUE), owner.getBalance());
        //Check notifications
        List<Notification> notifOwner = instanceNotification.getAllByUserId(owner);
        List<Notification> notifFunder = instanceNotification.getAllByUserId(funder);
        List<Notification> notifMemoriser = instanceNotification.getAllByUserId(memoriser);
        assertEquals(2, notifOwner.size());
        assertEquals(1, notifFunder.size());
        assertEquals(1, notifMemoriser.size());

        for (Notification n : notifOwner) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifFunder) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifMemoriser) {
            instanceNotification.delete(n);
        }
    }

    /**
     * Test of two consecutive call of checkDeadline
     */
    @Test
    public void testDoubleCallCheckDeadline() throws Exception {
        System.out.println("doubleCallCheckDeadline");
        instanceTaskScheduler.checkDeadLine();
        instanceTaskScheduler.checkDeadLine();
        //Check status project and owner
        project = instanceProjectDao.getByIdProject(project.getIdProject());
        assertTrue(project.alreadyTransferred());
        owner = instanceUserDao.getByMail(owner.getMail());
        assertEquals(new BigDecimal(FUND_VALUE), owner.getBalance());
        //Check notifications
        List<Notification> notifOwner = instanceNotification.getAllByUserId(owner);
        List<Notification> notifFunder = instanceNotification.getAllByUserId(funder);
        List<Notification> notifMemoriser = instanceNotification.getAllByUserId(memoriser);
        assertEquals(2, notifOwner.size());
        assertEquals(1, notifFunder.size());
        assertEquals(1, notifMemoriser.size());

        for (Notification n : notifOwner) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifFunder) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifMemoriser) {
            instanceNotification.delete(n);
        }
    }


    /**
     * Test of checkDeadline, with a user who funds and memorize the same
     * project
     */
    @Test
    public void testCheckDeadlineSameUser() throws Exception {
        System.out.println("checkDeadlineWithSameUser");

        instanceMemoriseProject.save(new MemoriseProject(funder, project));

        instanceTaskScheduler.checkDeadLine();
        //Check status project and owner
        project = instanceProjectDao.getByIdProject(project.getIdProject());
        assertTrue(project.alreadyTransferred());
        owner = instanceUserDao.getByMail(owner.getMail());
        assertEquals(new BigDecimal(FUND_VALUE), owner.getBalance());
        //Check notifications
        List<Notification> notifOwner = instanceNotification.getAllByUserId(owner);
        List<Notification> notifFunder = instanceNotification.getAllByUserId(funder);
        List<Notification> notifMemoriser = instanceNotification.getAllByUserId(memoriser);
        assertEquals(2, notifOwner.size());
        assertEquals(1, notifFunder.size());
        assertEquals(1, notifMemoriser.size());

        for (Notification n : notifOwner) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifFunder) {
            instanceNotification.delete(n);
        }
        for (Notification n : notifMemoriser) {
            instanceNotification.delete(n);
        }
    }
}
