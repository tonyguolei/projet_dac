/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bemug
 */
public class PrivateMessageDaoTest {

    private static ProjectDao projectDao;
    private static UserDao userDao;
    private static PrivateMessageDao privateMessageDao;
    private User user1, user2;
    private Project project;
    private PrivateMessage message;

    private Set<PrivateMessage> otherMessages = new HashSet();

    private static final String DEFAULT_MESSAGE = "All your base are belong to us.";

    @BeforeClass
    public static void setUpClass() {
        System.out.println("CommentDao unit test start");
        try {
            userDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            projectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            privateMessageDao = BeanTestUtils.lookup(PrivateMessageDao.class, "PrivateMessageDao");
        } catch (NamingException ex) {
            Logger.getLogger(UserDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("CommentDao unit test stop");
    }

    @Before
    public void setUp() {
        user1 = new User("test" + Math.random() + "@test.com", "test");
        userDao.save(user1);
        user2 = new User("test" + Math.random() + "@test.com", "test");
        userDao.save(user2);
        project = new Project(user1, BigDecimal.TEN, "Projet test" + Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
        projectDao.save(project);

        message = new PrivateMessage(user2, user1, DEFAULT_MESSAGE);
        message.setIsRead(true);
        privateMessageDao.save(message);

        otherMessages.clear();
        for (int i = 0; i < 4; i++) {
            PrivateMessage m = new PrivateMessage(user1, user2, "Message " + i);
            privateMessageDao.save(m);
            otherMessages.add(m);
        }
    }

    @After
    public void tearDown() {
        for (PrivateMessage m : otherMessages) {
            privateMessageDao.delete(m);
        }
        privateMessageDao.delete(message);

        projectDao.delete(project);
        userDao.delete(user1);
        userDao.delete(user2);
    }

    @Test
    public void testGetById() {
        System.out.println("getById");
        PrivateMessage result = privateMessageDao.getByIdPrivateMessage(message.getIdPrivateMessage());
        assertEquals(message, result);

        assertNull(privateMessageDao.getByIdPrivateMessage(-1));
    }

    @Test
    public void testNonReadByUser() {
        System.out.println("getNonReadByUser");

        Long resultCount = privateMessageDao.getNonReadNumber(user2);
        assertEquals(new Long(otherMessages.size()), resultCount);

        List<PrivateMessage> result = privateMessageDao.getAllNotReadByUserId(user2);
        assertEquals(otherMessages.size(), result.size());
        for (PrivateMessage m : result) {
            assertTrue(otherMessages.contains(m));
        }

        resultCount = privateMessageDao.getNonReadNumber(user1);
        assertEquals(new Long(0), resultCount);

        result = privateMessageDao.getAllNotReadByUserId(user1);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetConversations() {
        List<PrivateMessage> result = privateMessageDao.getConversations(user1);
        assertEquals(1, result.size());
        for (PrivateMessage m : result) {
            assertTrue(otherMessages.contains(m) || message.equals(m));
        }

        result = privateMessageDao.getConversations(user2);
        assertEquals(1, result.size());
        for (PrivateMessage m : result) {
            assertTrue(otherMessages.contains(m) || message.equals(m));
        }
    }

    @Test
    public void testGetConversation() {
        List<PrivateMessage> result = privateMessageDao.getConversation(user1, user2);
        assertEquals(otherMessages.size() + 1, result.size());
        for (PrivateMessage m : result) {
            assertTrue(otherMessages.contains(m) || message.equals(m));
        }

        result = privateMessageDao.getConversation(user2, user2);
        assertEquals(0, result.size());
    }

    @Test
    public void testSameConversationList() {
        PrivateMessage loopback = new PrivateMessage(user1, user1, "Nice haircut.");
        List<PrivateMessage> others = new ArrayList(otherMessages);
        assertFalse(privateMessageDao.sameConversationList(others, loopback));
        assertTrue(privateMessageDao.sameConversationList(others, message));
    }

    @Test
    public void testGettersSetters() {
        PrivateMessage m = new PrivateMessage();
        
        final String MESSAGE = "Fus ro dah !";
        m.setIdPrivateMessage(Integer.MIN_VALUE);
        m.setDest(user1);
        m.setExp(user2);
        m.setIsRead(true);
        m.setMessage(MESSAGE);
        
        assertEquals(MESSAGE, m.getMessage());
        assertEquals(user1, m.getDest());
        assertEquals(user2, m.getExp());
        assertEquals(true, m.getIsRead());
        assertEquals(new Integer(Integer.MIN_VALUE), m.getIdPrivateMessage());
    }

    @Test
    public void testEquals() {
        assertFalse(message.equals(project));
        PrivateMessage notSaved = new PrivateMessage(user2, user1, DEFAULT_MESSAGE);
        assertFalse(message.equals(notSaved));
        assertFalse(notSaved.equals(message));
    }

    @Test
    public void testToString() {
        assertEquals(message.toString(), "mybeans.PrivateMessage[ idPrivateMessage=" + message.getIdPrivateMessage() + " ]");
    }

    @Test
    public void constructors() {
        PrivateMessage m = new PrivateMessage(-1);
        assertEquals(m.getIdPrivateMessage(), new Integer(-1));

        final String MESSAGE = "The cake is a lie.";
        m = new PrivateMessage(-1, MESSAGE, true);
        assertEquals(m.getMessage(), MESSAGE);
        assertEquals(m.getIdPrivateMessage(), new Integer(-1));
        assertEquals(m.getIsRead(), true);

        m = new PrivateMessage(message);
        assertEquals(message.getMessage(), m.getMessage());
        assertEquals(message.getIdPrivateMessage(), m.getIdPrivateMessage());
        assertEquals(message.getIsRead(), m.getIsRead());
        assertEquals(message.getDest(), m.getDest());
        assertEquals(message.getExp(), message.getExp());
    }
}
