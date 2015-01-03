/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

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
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
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
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;      
        } 
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
    
    public List<Project> getProjects(User user) {
        TypedQuery<Project> query = em.createNamedQuery("User.getProjects", Project.class);
        query.setParameter("idOwner", user);
        return query.getResultList();
    }
}
