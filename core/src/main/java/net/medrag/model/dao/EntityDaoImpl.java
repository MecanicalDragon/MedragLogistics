package net.medrag.model.dao;

import net.medrag.model.domain.entity.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Parent class for all DAO implementations
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public abstract class EntityDaoImpl<E extends Entity> implements EntityDao<E> {

//    @PersistenceUnit(unitName = "medrag_logistics_db")
//    private EntityManager entityManager;

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer addEntity(E entity) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer)session.save(entity);
    }

    @Override
    public void updateEntityStatus(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    @Override
    public void removeEntity(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    @Override
    public void saveOrUpdateEntity(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }

    @Override
    public E getEntityById(E entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (E)session.find(entity.getClass(), id);
    }

    @Override
    public E getEntityByNaturalId(E entity, String id){
        Session session = sessionFactory.getCurrentSession();
        return (E)session.bySimpleNaturalId(entity.getClass()).load(id);
    }

    @Override
    public List<E> getEntityList(E entity){
        String fromTable = "from " + entity.getClass().getSimpleName();
        Session session = sessionFactory.getCurrentSession();
        return (List<E>) session.createQuery(fromTable).list();
    }
}
