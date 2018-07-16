package net.medrag.model.dao;

import net.medrag.model.domain.entity.Entity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for all DAO implementations
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public abstract class EntityDaoImpl<E extends Entity> implements EntityDao<E> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer addEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Integer) session.save(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.SAVE);
        }
    }

    @Override
    public void updateEntityStatus(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.MERGE);
        }
    }

    @Override
    public void removeEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.remove(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.REMOVE);
        }
    }

    @Override
    public void saveOrUpdateEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.SAVE_OR_UPDATE);
        }
    }

    @Override
    public E getEntityById(E entity, Integer id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.find(entity.getClass(), id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.FIND);
        }
    }

    @Override
    public E getEntityByNaturalId(E entity, String id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.bySimpleNaturalId(entity.getClass()).load(id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.BY_SIMPLE_NAME);
        }
    }

    @Override
    public List<E> getEntityList(E entity, String... args) throws MedragRepositoryException {
        String fromTable = String.format("from %s", entity.getClass().getSimpleName());
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder(fromTable);
            sb.append(" where ");
            for (int i = 0; i < args.length; i += 2) {
                if (i + 1 < args.length) {
                    sb.append(args[i]).append("=").append(args[i + 1]);
                }
                if (i + 3 < args.length) {
                    sb.append(" and ");
                } else break;
            }
            fromTable = sb.toString();
        }
        try {
            Session session = sessionFactory.getCurrentSession();
            return (List<E>) session.createQuery(fromTable).list();
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST);
        }
    }
}
