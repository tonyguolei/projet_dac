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
public class BonusDaoTest {

    private static BonusDao bonusDao;
    private static ProjectDao projectDao;
    private static UserDao userDao;
    private User user;
    private Project project;
    private Set<Bonus> otherBonus = new HashSet();
    private Bonus bonus;

    private static final String DEFAULT_DESCR = "Bien au dessus du prix d'une patate ordinaire, mais révolutionnaire et compatible Mac.";
    private static final String DEFAULT_TITLE = "Une iPatate";
    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(17.5);

    @BeforeClass
    public static void setUpClass() {
        System.out.println("CommentDao unit test start");
        try {
            userDao = BeanTestUtils.lookup(UserDao.class, "UserDao");
            projectDao = BeanTestUtils.lookup(ProjectDao.class, "ProjectDao");
            bonusDao = BeanTestUtils.lookup(BonusDao.class, "BonusDao");
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
        user = new User("test" + Math.random() + "@test.com", "test");
        userDao.save(user);
        project = new Project(user, BigDecimal.TEN, "Projet test" + Math.random(), "Description test", Date.valueOf("2100-01-01"), "test,test1,test2");
        projectDao.save(project);
        bonus = new Bonus(DEFAULT_PRICE, DEFAULT_TITLE, DEFAULT_DESCR, project);
        bonusDao.save(bonus);

        otherBonus.clear();
        for (int i = 0; i < 4; i++) {
            Bonus b = new Bonus(new BigDecimal(i), "Bonus" + i, "Useless", project);
            bonusDao.save(b);
            otherBonus.add(b);
        }
    }

    @After
    public void tearDown() {
        for (Bonus b : otherBonus) {
            bonusDao.delete(b);
        }
        bonusDao.delete(bonus);
        projectDao.delete(project);
        userDao.delete(user);
    }

    @Test
    public void testGetById() {
        System.out.println("getById");
        Bonus result = bonusDao.getByIdBonus(bonus.getIdBonus());
        assertEquals(bonus, result);

        assertNull(bonusDao.getByIdBonus(-1));
    }

    @Test
    public void testGetBonus() {
        System.out.println("getBonus");

        // checking result
        List<Bonus> result = bonusDao.getBonus(project);
        assertEquals(otherBonus.size() + 1, result.size());
        for (Bonus b : result) {
            assertTrue(otherBonus.contains(b) || b.equals(bonus));
        }
    }

    @Test
    public void testGettersSetters() {
        assertEquals(bonus.getDescription(), DEFAULT_DESCR);
        assertEquals(bonus.getTitle(), DEFAULT_TITLE);
        assertEquals(bonus.getIdProject(), project);
        assertEquals(bonus.getValue(), DEFAULT_PRICE);

        Bonus b = new Bonus();
        final String DESCR = "Descr";
        final String TITLE = "Title";
        final BigDecimal PRICE = new BigDecimal(4.25);
        final int id = -1;
        b.setDescription(DESCR);
        b.setTitle(TITLE);
        b.setIdProject(project);
        b.setValue(PRICE);
        b.setIdBonus(id);

        assertEquals(b.getDescription(), DESCR);
        assertEquals(b.getTitle(), TITLE);
        assertEquals(b.getDescription(), DESCR);
        assertEquals(b.getIdProject(), project);
        assertEquals(b.getValue(), PRICE);
    }

    @Test
    public void testEquals() {
        assertFalse(bonus.equals(project));
        Bonus notSaved = new Bonus(DEFAULT_PRICE, DEFAULT_TITLE, DEFAULT_DESCR, project);
        assertFalse(bonus.equals(notSaved));
        assertFalse(notSaved.equals(bonus));
    }
    
    @Test
    public void testToString() {
        assertEquals(bonus.toString(), "mybeans.Bonus[ idBonus=" + bonus.getIdBonus() + " ]");
    }
    
    @Test
    public void constructors() {
        Bonus b = new Bonus(-1);
        assertEquals(b.getIdBonus(), new Integer(-1));
        
        b = new Bonus(-1, DEFAULT_PRICE, DEFAULT_TITLE, DEFAULT_DESCR);
        assertEquals(b.getDescription(), DEFAULT_DESCR);
        assertEquals(b.getIdBonus(), new Integer(-1));
        assertEquals(b.getValue(), DEFAULT_PRICE);
        assertEquals(b.getTitle(), DEFAULT_TITLE);
    }
}
