/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

@Stateless
public class UserDao extends Dao<User> {

    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Get the User entity bean having the given primary key.
     *
     * @param id Primary key of the user.
     * @return The user having id as a primary key.
     */
    public User getByIdUser(int id) {
        TypedQuery<User> query = em.createNamedQuery("User.findByIdUser", User.class);
        query = query.setParameter("idUser", id);
        return query.getSingleResult();
    }

    /**
     * Get the User with the entity bean having the given mail field.
     *
     * Mail field has unique constraint in the DB, so there shouldn't be
     * {@link NonUniqueResultException NonUniqueResultException} exception
     * thrown.
     *
     * @param mail Mail of the user.
     * @return The user having mail as email address.
     */
    public User getByMail(String mail) {
        TypedQuery<User> query = em.createNamedQuery("User.findByMail", User.class);
        query = query.setParameter("mail", mail);
        return query.getSingleResult();
    }

    /**
     * Get the list of all users.
     * 
     * @return The list of all users.
     */
    public List<User> getAll() {
        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
        return query.getResultList();
    }
    
    /**
     * Insert a new user after checking that the mail is unique.
     * @param user
     * @return true on success, false when an other user already have this mail.
     */
    public boolean insert(User user) {
        //TODO do a transaction
        if (!this.getByMail(user.getMail()).isEmpty()) {
            return false;
        }
        this.save(user);
        return true;
    }
}
