package net.medrag.service.dto.impl;

import net.medrag.service.MedragServiceException;
import net.medrag.domain.dto.Dto;
import net.medrag.dao.api.EntityDao;
import net.medrag.dao.MedragRepositoryException;
import net.medrag.domain.entity.Entity;
import net.medrag.service.dto.api.DTOService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Superclass for all DTO services.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class DTOServiceImpl<D extends Dto, E extends Entity> implements DTOService<D, E> {

    protected EntityDao<E> entityDao;

    /**
     * String, that denotes correct implementation for subclasses. Needed here for being overridden in subclasses.
     */
    private static final String implementation = "userDaoImpl";

    @Autowired
    public void setEntityDao(@Qualifier(implementation) EntityDao<E> entityDao) {
        this.entityDao = entityDao;
    }

    /**
     * Persist new dto do database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @return - id parameter of persisted object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public Integer addDto(D dto, E entity) throws MedragServiceException {
        E result = (E) new ModelMapper().map(dto, entity.getClass());
        try {
            return entityDao.addEntity(result);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Get object from database by id.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param id     - id of requested object.
     * @return - requested object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public D getDtoById(D dto, E entity, Integer id) throws MedragServiceException {
        E entityById = null;
        try {
            entityById = entityDao.getEntityById(entity, id);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        return (D) new ModelMapper().map(entityById, dto.getClass());
    }

    /**
     * Updating object in database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public void updateDtoStatus(D dto, E entity) throws MedragServiceException {
        try {
            E e = (E) new ModelMapper().map(dto, entity.getClass());
            entityDao.updateEntityStatus(e);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Remove object from database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public void removeDto(D dto, E entity) throws MedragServiceException {
        try {
            entityDao.removeEntity((E) new ModelMapper().map(dto, entity.getClass()));
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * reload object from database
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @return - refreshed object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public D refreshDto(D dto, E entity) throws MedragServiceException {
        try {
            E refreshed = entityDao.refreshEntity((E) new ModelMapper().map(dto, entity.getClass()));
            return (D) new ModelMapper().map(refreshed, dto.getClass());
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Getting from database object by it's natural id.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param id - object natural id.
     * @return - requested object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public D getDtoByNaturalId(D dto, E entity, String id) throws MedragServiceException {
        E result = null;
        try {
            result = entityDao.getEntityByNaturalId(entity, id);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        if (result == null) {
            return null;
        } else {
            return (D) new ModelMapper().map(result, dto.getClass());
        }
    }

    /**
     * Getting number of objects in database with specified parameters.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args - parameters of filtering.
     * @return - filtered list of objects.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public Integer getDtoCount(E entity, String... args) throws MedragServiceException {
        try {
            return entityDao.getEntityCount(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    /**
     * Getting from database list of objects, filtered by specified parameters.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param args - filter parameters.
     * @return - filtered list of objects from database.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public List<D> getDtoList(D dto, E entity, String... args) throws MedragServiceException {
        List<E> entityList;
        try {
            entityList = entityDao.getEntityList(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        List<D> dtoList = new ArrayList<>();
        entityList.forEach(e -> dtoList.add((D) new ModelMapper().map(e, dto.getClass())));
        return dtoList;
    }

    /**
     * Getting from database list of last added objects.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param count - number of last added objects.
     * @return - list of last added objects.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    @Override
    @Transactional
    public List<D> getLastObjects(D dto, E entity, Integer count) throws MedragServiceException {
        List<E> entityList;
        try {
            entityList = entityDao.getLastEntities(entity, count);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        List<D> dtoList = new ArrayList<>();
        entityList.forEach(e -> dtoList.add((D) new ModelMapper().map(e, dto.getClass())));
        return dtoList;
    }
}
