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
public interface EntityDao<E extends Entity>  {
    Integer addEntity(E entity)throws MedragRepositoryException;
    void updateEntityStatus(E entity)throws MedragRepositoryException;
    void removeEntity(E entity)throws MedragRepositoryException;
    void saveOrUpdateEntity (E entity)throws MedragRepositoryException;
    E refreshEntity(E entity) throws MedragRepositoryException;
    E getEntityById(E entity, Integer id)throws MedragRepositoryException;
    E getEntityByNaturalId(E entity, String id)throws MedragRepositoryException;
    Integer getEntityCount(E entity, String... args) throws MedragRepositoryException;
    List<E> getEntityList(E entity, String... args) throws  MedragRepositoryException;
}
