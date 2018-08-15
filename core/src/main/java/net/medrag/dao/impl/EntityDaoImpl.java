package net.medrag.dao.impl;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.dao.api.EntityDao;
import net.medrag.domain.entity.Entity;
import org.hibernate.HibernateException;
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
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.SAVE + entity.getClass().getSimpleName());
        }
    }

    @Override
    public void updateEntityStatus(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.MERGE + entity.getClass().getSimpleName());
        }
    }

    @Override
    public E refreshEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.refresh(entity);
            return entity;
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.REFRESH + entity.getClass().getSimpleName());
        }
    }

    @Override
    public void removeEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.remove(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.REMOVE + entity.getClass().getSimpleName());
        }
    }

    @Override
    public void saveOrUpdateEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.SAVE_OR_UPDATE + entity.getClass().getSimpleName());
        }
    }

    @Override
    public E getEntityById(E entity, Integer id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.find(entity.getClass(), id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.FIND + entity.getClass().getSimpleName());
        }
    }

    @Override
    public E getEntityByNaturalId(E entity, String id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.bySimpleNaturalId(entity.getClass()).load(id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.BY_SIMPLE_NAME + entity.getClass().getSimpleName());
        }
    }

    @Override
    public Integer getEntityCount(E entity, String... args) throws MedragRepositoryException {
        String fromTable = String.format("select count(*) from %s", entity.getClass().getSimpleName().toLowerCase());
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder(fromTable);
            sb.append(" where ");
            for (int i = 0; i < args.length; i += 2) {
                if (i + 1 < args.length) {
                    sb.append(args[i]).append("=").append(args[i + 1]);
                }
                if (i + 3 < args.length) {
                    sb.append(" or ");
                } else break;
            }
            fromTable = sb.toString();
        }
        try {
            Session session = sessionFactory.getCurrentSession();
            return ((Number) session.createNativeQuery(fromTable).getSingleResult()).intValue();
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST + entity.getClass().getSimpleName());
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
        } else if (args.length == 1 && args[0].trim().length() > 1) {
            String[] conditions = args[0].split("%");
            fromTable = fromTable + " " + conditions[0];
            if (conditions.length > 1) {
                try {
                    Session session = sessionFactory.getCurrentSession();
                    return (List<E>) session.createQuery(fromTable).setMaxResults(Integer.valueOf(conditions[1])).list();
                } catch (HibernateException e) {
                    throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST + entity.getClass().getSimpleName());
                }
            }
        }
        try {
            Session session = sessionFactory.getCurrentSession();
            return (List<E>) session.createQuery(fromTable).list();
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST + entity.getClass().getSimpleName());
        }
    }
}
