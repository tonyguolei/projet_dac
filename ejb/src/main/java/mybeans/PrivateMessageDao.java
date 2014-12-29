/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vincent
 */
@Stateless
public class PrivateMessageDao extends Dao<PrivateMessage> {
    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the PrivateMessage entity bean having the given primary key.
     *
     * @param id Primary key of the private message.
     * @return The private message having id as a primary key.
     */
    public PrivateMessage getByIdPrivateMessage(int id) {
        TypedQuery<PrivateMessage> query = em.createNamedQuery("PrivateMessage.findByIdPrivateMessage", PrivateMessage.class);
        query = query.setParameter("idPrivateMessage", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the list of all private messages.
     * 
     * @return The list of all private messages.
     */
    public List<PrivateMessage> getAll() {
        TypedQuery<PrivateMessage> query = em.createNamedQuery("PrivateMessage.findAll", PrivateMessage.class);
        return query.getResultList();
    }
}
