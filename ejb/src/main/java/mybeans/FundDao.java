/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.math.BigDecimal;
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
public class FundDao extends Dao<Fund> {

    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the Fund entity bean having the given primary key.
     *
     * @param id Primary key of the fund.
     * @return The fund having id as a primary key.
     */
    public Fund getByIdFund(int id) {
        TypedQuery<Fund> query = em.createNamedQuery("Fund.findByIdFund", Fund.class);
        query = query.setParameter("idFund", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the list of all funds.
     * 
     * @return The list of all funds.
     */
    public List<Fund> getAll() {
        TypedQuery<Fund> query = em.createNamedQuery("Fund.findAll", Fund.class);
        return query.getResultList();
    }
    
    /**
     * Return the sum of the funds for a project
     * @param project
     * @return 
     */
    public BigDecimal getFundLevel(Project project) {
        TypedQuery<BigDecimal> query = em.createNamedQuery("Fund.getFundLevel", BigDecimal.class);
        query = query.setParameter("idProject", project);
        return query.getSingleResult();
    }
    
    /**
     * Return the fund of a user for a project
     * @param user
     * @param project
     * @return 
     */
    public Fund getFundByUser(User user, Project project) {
        TypedQuery<Fund> query = em.createNamedQuery("Fund.findAFund", Fund.class);
        query = query.setParameter("idUser", user);
        query = query.setParameter("idProject", project);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
