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
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public abstract class DTOServiceImpl<D extends Dto, E extends Entity> implements DTOService<D, E> {

    protected EntityDao<E> entityDao;

    private static final String implementation = "userDaoImpl";

    @Autowired
    public void setEntityDao(@Qualifier(implementation) EntityDao<E> entityDao) {
        this.entityDao = entityDao;
    }

    @Override
    @Transactional
    public Integer addDto(D dto, E entity) throws MedragServiceException{
        E result = (E) new ModelMapper().map(dto, entity.getClass());
        try {
            return entityDao.addEntity(result);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

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

    @Override
    @Transactional
    public void removeDto(D dto, E entity) throws MedragServiceException {
        try {
            entityDao.removeEntity((E) new ModelMapper().map(dto, entity.getClass()));
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public void saveOrUpdateDto(D dto, E entity) throws MedragServiceException {
        try {
            entityDao.saveOrUpdateEntity((E) new ModelMapper().map(dto, entity.getClass()));
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public D refreshDto(D dto, E entity) throws MedragServiceException{
        try {
            E refreshed = entityDao.refreshEntity((E) new ModelMapper().map(dto, entity.getClass()));
            return (D) new ModelMapper().map(refreshed, dto.getClass());
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

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

    @Override
    @Transactional
    public Integer getDtoCount(E entity, String... args) throws MedragServiceException {
        try {
            return entityDao.getEntityCount(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<D>getDtoList(D dto, E entity, String... args) throws MedragServiceException{
        List<E> entityList = null;
        try {
            entityList = entityDao.getEntityList(entity, args);
        } catch (MedragRepositoryException e) {
            throw new MedragServiceException(e);
        }
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            D d = (D) new ModelMapper().map(e, dto.getClass());
            dtoList.add(d);
        }
        return dtoList;
    }
}
