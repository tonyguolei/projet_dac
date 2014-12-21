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
        return query.getSingleResult();
    }
}
