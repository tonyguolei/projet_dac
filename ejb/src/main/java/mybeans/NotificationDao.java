/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        return query.getSingleResult();
    }
}
