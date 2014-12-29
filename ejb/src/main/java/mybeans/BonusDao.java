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
public class BonusDao extends Dao<Bonus> {

    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the Bonus entity bean having the given primary key.
     *
     * @param id Primary key of the bonus.
     * @return The bonus having id as a primary key.
     */
    public Bonus getByIdBonus(int id) {
        TypedQuery<Bonus> query = em.createNamedQuery("Bonus.findByIdBonus", Bonus.class);
        query = query.setParameter("idBonus", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the list of all bonus.
     * 
     * @return The list of all bonus.
     */
    public List<Bonus> getAll() {
        TypedQuery<Bonus> query = em.createNamedQuery("Bonus.findAll", Bonus.class);
        return query.getResultList();
    }
}
