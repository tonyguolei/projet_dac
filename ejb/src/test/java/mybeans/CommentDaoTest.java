/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
public class CommentDaoTest {
    
    private static UserDao instanceUserDao;
    private static ProjectDao instanceProjectDao;
    private static CommentDao instanceCommentDao;
    private static User user;
    private static Project project;
    private static List<Comment> comments;
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("CommentDao unit test start");
        try {
            instanceUserDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            instanceProjectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            instanceCommentDao = BeanTestUtils.lookup(CommentDao.class, "CommentDao");
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
        user = new User("test"+Math.random()+"@test.com", "test");
        instanceUserDao.save(user);
        project = new Project(user, BigDecimal.TEN, "Projet test"+Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
        instanceProjectDao.save(project);
        comments = new ArrayList<Comment>();
        for (int i=0; i<10; i++) {
            Comment c = new Comment(user, project, "This is a test comment "+Math.random());
            comments.add(c);
            instanceCommentDao.save(c);
        }
    }
    
    @After
    public void tearDown() {
        for (Comment c : comments) {
            instanceCommentDao.delete(c);
        }
        instanceProjectDao.delete(project);
        instanceUserDao.delete(user);
    }

    @Test
    public void testGetById() {
        System.out.println("getById");
        Comment c = comments.get(0);
        Comment c2 = instanceCommentDao.getByIdComment(c.getIdComment());
        assertNotNull(c2);
    }
    
    public void testGetComments() {
        System.out.println("getComments");
        List<Comment> list = instanceCommentDao.getComments(project);
        for (Comment c : list) {
            assertTrue(comments.contains(c));
        }
    }
    
    @Test
    public void testGettersSetters() {
        System.out.println("getters and setters");
        //Getters
        Comment c = comments.get(0);
        Comment c2 = comments.get(1);
        Comment c3 = new Comment();
        assertNotNull(c.getComment());
        assertNotNull(c.getDate());
        assertNotNull(c.getIdComment());
        assertEquals(project, c.getIdProject());
        assertEquals(user, c.getIdUser());
        c.toString();
        assertFalse(c.equals(new Integer(0)));
        assertEquals(c, c2);
        assertEquals(c, null);
        assertFalse(c3.equals(c));
        assertEquals(c, c);
    }
    
}
