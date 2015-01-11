/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Vincent
 */
@Stateless
public class NotificationDao extends Dao<Notification> {
    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the Notification entity bean having the given primary key.
     *
     * @param id Primary key of the notification.
     * @return The notification having id as a primary key.
     */
    public Notification getByIdNotification(int id) {
        TypedQuery<Notification> query = em.createNamedQuery("Notification.findByIdNotification", Notification.class);
        query = query.setParameter("idNotification", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the number of notification not read by a user
     * @param idUser
     * @return 
     */
    public long getNonReadNumber(User idUser) {
        TypedQuery<Long> query = em.createNamedQuery("Notification.findNotReadByUser", Long.class);
        query = query.setParameter("idUser", idUser);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return 0;
        }
    }
    
    /**
     * Get all not read notifications of one user
     * @param idUser
     * @return 
     */
    public List<Notification> getAllByUserId(User idUser) {
        TypedQuery<Notification> query = em.createNamedQuery("Notification.findAllByUserid", Notification.class);
        query = query.setParameter("idUser", idUser);
        return query.getResultList();
    }
    
    /**
     * Get the list of all notifications.
     * 
     * @return The list of all notifications.
     */
    public List<Notification> getAll() {
        TypedQuery<Notification> query = em.createNamedQuery("Notification.findAll", Notification.class);
        return query.getResultList();
    }
}
