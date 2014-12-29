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
public class ProjectDao extends Dao<Project> {
    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Get the Project entity bean having the given primary key.
     *
     * @param id Primary key of the project.
     * @return The project having id as a primary key.
     */
    public Project getByIdProject(int id) {
        TypedQuery<Project> query = em.createNamedQuery("Project.findByIdProject", Project.class);
        query = query.setParameter("idProject", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    
    /**
     * Get the list of all projects.
     * 
     * @return The list of all projects.
     */
    public List<Project> getAll() {
        TypedQuery<Project> query = em.createNamedQuery("Project.findAll", Project.class);
        return query.getResultList();
    }
}
