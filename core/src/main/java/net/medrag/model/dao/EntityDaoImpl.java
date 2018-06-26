package net.medrag.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Parent class for all DAO implementations
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public abstract class EntityDaoImpl<E> implements EntityDao<E> {

    protected SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addEntity(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
    }

    public void updateEntityStatus(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    public void removeEntity(E entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    public E getEntityById(E entity, Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return (E)session.find(entity.getClass(), id);
    }

    public E getEntityByNaturalId(E entity, String id){
        Session session = sessionFactory.getCurrentSession();
        return (E)session.bySimpleNaturalId(entity.getClass()).load(id);
    }

    public List<E> getEntityList(E entity){
        String fromTable = "from " + entity.getClass().getSimpleName();
        Session session = sessionFactory.getCurrentSession();
        return (List<E>) session.createQuery(fromTable).list();
    }
}
