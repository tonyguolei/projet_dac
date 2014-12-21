/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 * Abstract class for the DAOs of the server.
 * 
 * Group some trivial functions (insert / update / delete) for the other DAOs.
 * 
 * @author Vincent
 * @param <T> Type of the entities handled by this Dao.
 */
public abstract class Dao<T extends Object> {
    /**
     * Get the entity manager of this Dao.
     * 
     * @return The entity manager of this Dao.
     */
    protected abstract EntityManager getEntityManager();
    
    /**
     * Save the parameter as a new entity.
     *
     * @param entity The new entity to save.
     */
    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Remove the entity from the database.
     *
     * @param entity Entity to remove.
     */
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Update an already existing entity to the database.
     *
     * @param entity Entity to update.
     */
    public void update(T entity) {
        getEntityManager().merge(entity);
    }
}
