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
public class MemoriseProjectDao extends Dao<MemoriseProject> {
    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the MemoriseProject entity bean having the given primary key.
     *
     * @param id Primary key of the "memorise project".
     * @return The "memorise project" having id as a primary key.
     */
    public MemoriseProject getByIdMemoriseProject(int id) {
        TypedQuery<MemoriseProject> query = em.createNamedQuery("MemoriseProject.findByIdMemoriseProject", MemoriseProject.class);
        query = query.setParameter("idMemoriseProject", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the list of all MemoriseProject entities.
     * 
     * @return The list of all MemoriseProject entities.
     */
    public List<MemoriseProject> getAll() {
        TypedQuery<MemoriseProject> query = em.createNamedQuery("MemoriseProject.findAll", MemoriseProject.class);
        return query.getResultList();
    }

    public List<Project> getByUser(User user) {
        TypedQuery<Project> query = em.createNamedQuery("MemoriseProject.findProjectByUser", Project.class);
        query.setParameter("idUser", user);
        return query.getResultList();
    }
}
