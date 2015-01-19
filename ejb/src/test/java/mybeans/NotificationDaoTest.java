/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;

import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author guillaumeperrin
 */
public class NotificationDaoTest {
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private static NotificationDao instanceNotificationDao;
    private static User user;
    private static Project project;

    public NotificationDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            instanceUserDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceProjectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            instanceNotificationDao = BeanTestUtils.lookup(NotificationDao.class, "NotificationDao");
            user = new User("test_notification@test.com", "test");
            instanceUserDao.save(user);
            Date endDate = java.sql.Date.valueOf("2100-01-01");
            project = new Project(user, new BigDecimal(10), "Test taskscheduler future", "desc", endDate, "test");
            instanceProjectDao.save(project);
            System.out.println("NotificationDao unit test start");
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
        System.out.println("NotificationDao unit test stop");
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        List<Notification> list = instanceNotificationDao.getAllByUserId(user);
        for (Notification n : list) {
            instanceNotificationDao.delete(n);
        }
    }
    
    /**
     * Test of getById method, of NotificationDao
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        Notification expected = new Notification(user, project, "Test notif id");
        instanceNotificationDao.save(expected);
        Notification notif =  instanceNotificationDao.getByIdNotification(expected.getIdNotification());
        assertEquals(expected, notif);
        assertNull(instanceNotificationDao.getByIdNotification(-1));
    }
    
    /**
     * Test of getNonReadNumber method, of NotificationDao
     */
    @Test
    public void testNonReadNumber() {
        System.out.println("nonReadNumber");
        //Create 2 notification, read one
        Notification read = new Notification(user, project, "Test notif non read : false");
        read.setIsRead(true);
        instanceNotificationDao.save(read);
        Notification nonRead = new Notification(user, project, "Test notif non read : true");
        instanceNotificationDao.save(nonRead);
        //User with no notification
        User u2 = new User("test_notification_u2@test.com", "test");
        instanceUserDao.save(u2);
        
        assertEquals(1, instanceNotificationDao.getNonReadNumber(user));
        assertEquals(0, instanceNotificationDao.getNonReadNumber(u2));
        
        //Clean
        instanceNotificationDao.delete(read);
        instanceUserDao.delete(u2);
    }
    
    /**
     * Test of getAllByUserId method, of NotificationDao
     */
    @Test
    public void testGetAllByUserId() {
        System.out.println("getAllByUserId");
        //Create second user and notification
        User u2 = new User("test_notification_u2@test.com", "test");
        instanceUserDao.save(u2);
        Notification notifUser = new Notification(user, project, "Notif user 1");
        instanceNotificationDao.save(notifUser);
        Notification notifU2 = new Notification(u2, project, "Notif user 2");
        instanceNotificationDao.save(notifU2);
        
        assertEquals(1, instanceNotificationDao.getAllByUserId(user).size());
        
        //Clean
        instanceNotificationDao.delete(notifU2);
        instanceUserDao.delete(u2);
    }
    
    /**
     * Test of getters and setters method, of class Notification.
     */
    @Test
    public void testGettersSetters() {
        System.out.println("getters and setters");
        //Creation
        Notification notif = new Notification(user, project, "Test get/set");
        instanceNotificationDao.save(notif);
        Notification notif2 = new Notification(user, project, "Test 2 get/set");
        notif = instanceNotificationDao.getByIdNotification(notif.getIdNotification());
        
        assertEquals("Test get/set", notif.getDescription());
        notif.setDescription("Test2");
        assertEquals("Test2", notif.getDescription());
        assertEquals(user, notif.getIdUser());
        assertEquals(project, notif.getIdProject());
        assertFalse(notif.getIsRead());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(sdf.format(new Date()), sdf.format(notif.getDate()));
        assertTrue(notif.equals(notif));
        assertFalse(notif.equals(notif2));
        assertFalse(notif.equals(null));
        Notification notif3 = new Notification();
        assertFalse(notif3.equals(notif));
        assertEquals("mybeans.Notification[ idNotification=" + notif.getIdNotification() + " ]", notif.toString());
    }
}
