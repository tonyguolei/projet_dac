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
public class CommentDao extends Dao<Comment> {

    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the Comment entity bean having the given primary key.
     *
     * @param id Primary key of the comment.
     * @return The comment having id as a primary key.
     */
    public Comment getByIdComment(int id) {
        TypedQuery<Comment> query = em.createNamedQuery("Comment.findByIdComment", Comment.class);
        query = query.setParameter("idComment", id);
        return query.getSingleResult();
    }
}
