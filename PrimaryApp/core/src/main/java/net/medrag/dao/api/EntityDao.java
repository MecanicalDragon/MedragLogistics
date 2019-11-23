package net.medrag.dao.api;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.domain.entity.Entity;

import java.util.List;

/**
 * Unified DAO interface for all domain objects
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface EntityDao<E extends Entity> {

    /**
     * Adding new entity to database method.
     *
     * @param entity - added entity
     * @return - newly added entity id.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    Integer addEntity(E entity) throws MedragRepositoryException;

    /**
     * Updating entity in database method.
     *
     * @param entity - updated entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    void updateEntityStatus(E entity) throws MedragRepositoryException;

    /**
     * Removing entty from database method.
     *
     * @param entity - removed entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    void removeEntity(E entity) throws MedragRepositoryException;

    /**
     * Refresing entity method.
     *
     * @param entity - entity, that must be refreshed.
     * @return - refreshed entity.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    E refreshEntity(E entity) throws MedragRepositoryException;

    /**
     * Getting from database entity with denoted id parameter.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param id     - id of required entity.
     * @return - entity object with denoted id parameter.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    E getEntityById(E entity, Integer id) throws MedragRepositoryException;

    /**
     * Getting from database entity with specified id parameter.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param id     - natural id of required entity.
     * @return - entity with specified id feom database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    E getEntityByNaturalId(E entity, String id) throws MedragRepositoryException;

    /**
     * Getting number of entities of specified type, saved in the database.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args   - parameters for filtering entities.
     * @return number of entities with specified parameters in the database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    Integer getEntityCount(E entity, String... args) throws MedragRepositoryException;

    /**
     * Getting from database list of entities with specified parameters.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args   - parameters for filtering.
     * @return - list of entities with specified parameters from the database.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    List<E> getEntityList(E entity, String... args) throws MedragRepositoryException;

    /**
     * Getting from the database list of last persisted entities.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param count  - number of entities, that you wish to get.
     * @return - last {@param count} persisted to the database entities.
     * @throws MedragRepositoryException - throws MedragRepositoryException.
     */
    List<E> getLastEntities(E entity, Integer count) throws MedragRepositoryException;
}
