package net.medrag.model.dao;

import net.medrag.model.domain.entity.Entity;

import java.util.List;

/**
 * Unified DAO interface for all domain objects
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface EntityDao<E extends Entity>  {
    void addEntity(E entity);
    void updateEntityStatus(E entity);
    void removeEntity(E entity);
    E getEntityById(E entity, Integer id);
    E getEntityByNaturalId(E entity, String id);
    List<E> getEntityList(E entity);
}
