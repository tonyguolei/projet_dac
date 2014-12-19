/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package mybeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Stateless
public class UserDao {
    @PersistenceContext(unitName = "unitDac")
    private EntityManager em;
    public void save(User entity)
    {
        this.em.persist(entity);
    }
    public void delete(User entity)
    {
        em.remove(em.merge(entity));
    }
    public void update(User entity)
    {
        em.merge(entity);
    }
    public List<User> getAll()
    {
        Query query =em.createNamedQuery("User.findAll");
        return query.getResultList();
    }
    public User getById(int id)
    {
        return (User)em.createNamedQuery("User.findByUserid").getSingleResult();
    }
    public User getByUserCode(String usercode)
    {
        Query query = em.createNamedQuery("User.findByUsercode");
        query = query.setParameter("usercode",usercode);
        return (User)query.getSingleResult();
    }
    public User getByUserName(String username)
    {
        Query query = em.createNamedQuery("User.findByUsername");
        return (User)query.getSingleResult();
    }
}
