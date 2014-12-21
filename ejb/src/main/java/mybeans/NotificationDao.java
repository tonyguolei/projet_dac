/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
