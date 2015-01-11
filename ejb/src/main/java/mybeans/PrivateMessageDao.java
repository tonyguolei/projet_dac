/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.util.LinkedList;
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
     * Get the number of private message not read by a user
     * @param idUser
     * @return 
     */
    public long getNonReadNumber(User idUser) {
        TypedQuery<Long> query = em.createNamedQuery("PrivateMessage.findNotReadByDest", Long.class);
        query = query.setParameter("dest", idUser);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return 0;
        }
    }
    
    /**
     * Get private message not read by a user
     * @param idUser
     * @return 
     */
    public List<PrivateMessage> getAllNotReadByUserId(User idUser) {
        TypedQuery<PrivateMessage> query = em.createNamedQuery("PrivateMessage.findAllNotReadByUserid", PrivateMessage.class);
        query = query.setParameter("dest", idUser);
        return query.getResultList();
    }
    
    /**
     * Get all private message of a user
     * @param user
     * @return 
     */
    public List<PrivateMessage> getConversations(User user) {
        TypedQuery<PrivateMessage> query = em.createNamedQuery("PrivateMessage.conversations", PrivateMessage.class);
        query = query.setParameter("user", user);
        List<PrivateMessage> list = query.getResultList();
        List<PrivateMessage> result = new LinkedList<>();
        
        for (PrivateMessage pm : list) {
            if (!sameConversationList(result, pm)) {
                result.add(pm);
            }
        }
        
        return result;
    }
    
    /**
     * Get a conversation between two users
     * @param exp
     * @param dest
     * @return 
     */
    public List<PrivateMessage> getConversation(User exp, User dest) {
        TypedQuery<PrivateMessage> query = em.createNamedQuery("PrivateMessage.conversation", PrivateMessage.class);
        query = query.setParameter("exp", exp);
        query = query.setParameter("dest", dest);
        return query.getResultList();
    }
    
    /**
     * Return true if any object in the list and pm are in the same conversation
     * @param list
     * @param pm
     * @return 
     */
    public boolean sameConversationList(List<PrivateMessage> list, PrivateMessage pm) {
        for (PrivateMessage pm2 : list) {
            if (pm2.sameConversation(pm)) {
                return true;
            }
        }
        return false;
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
