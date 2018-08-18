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
public class EntityDaoImpl<E extends Entity> implements EntityDao<E> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Adding new entity to database method.
     *
     * @param entity - added entity
     * @return - newly added entity id.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public Integer addEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Integer) session.save(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.SAVE + entity.getClass().getSimpleName());
        }
    }

    /**
     * Updating entity in database method.
     *
     * @param entity - updated entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public void updateEntityStatus(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.merge(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.MERGE + entity.getClass().getSimpleName());
        }
    }

    /**
     * Refresing entity method.
     *
     * @param entity - entity, that must be refreshed.
     * @return - refreshed entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
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

    /**
     * Removing entty from database method.
     *
     * @param entity - removed entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public void removeEntity(E entity) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.remove(entity);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.REMOVE + entity.getClass().getSimpleName());
        }
    }

    /**
     * Getting from database entity with denoted id parameter.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param id     - id of required entity.
     * @return - entity object with denoted id parameter.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public E getEntityById(E entity, Integer id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.find(entity.getClass(), id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.FIND + entity.getClass().getSimpleName());
        }
    }

    /**
     * Getting from database entity with specified id parameter.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param id     - natural id of required entity.
     * @return - entity with specified id feom database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public E getEntityByNaturalId(E entity, String id) throws MedragRepositoryException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (E) session.bySimpleNaturalId(entity.getClass()).load(id);
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.BY_SIMPLE_NAME + entity.getClass().getSimpleName());
        }
    }

    /**
     * Getting number of entities of specified type, saved in the database.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args   - parameters for filtering entities.
     * @return number of entities with specified parameters in the database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
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

    /**
     * Getting from database list of entities with specified parameters.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args   - parameters for filtering.
     * @return - list of entities with specified parameters from the database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
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
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST + entity.getClass().getSimpleName());
        }
    }

    /**
     * Getting from the database list of last persisted entities.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param count  - number of entities, that you wish to get.
     * @return - last {@param count} persisted to the database entities.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    @Override
    public List<E> getLastEntities(E entity, Integer count) throws MedragRepositoryException {
        String fromTable = String.format("from %s ORDER BY id DESC", entity.getClass().getSimpleName());
        try {
            Session session = sessionFactory.getCurrentSession();
            return (List<E>) session.createQuery(fromTable).setMaxResults(count).list();
        } catch (HibernateException e) {
            throw new MedragRepositoryException("" + MedragRepositoryException.OperationType.LIST + entity.getClass().getSimpleName());
        }

    }
}
