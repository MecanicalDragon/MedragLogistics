package net.medrag.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.medrag.dto.Dto;
import net.medrag.model.dao.EntityDao;
import net.medrag.model.dao.MedragRepositoryException;
import net.medrag.model.domain.entity.Entity;
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

    private EntityDao<E> entityDao;

    private static final String implementation = "userDaoImpl";

    private static final Logger logger = LoggerFactory.getLogger(DTOServiceImpl.class);


    @Autowired
    public void setEntityDao(@Qualifier(implementation) EntityDao<E> entityDao) {
        this.entityDao = entityDao;
    }

    @Override
    @Transactional
    public Integer addDto(D dto, E entity){
        E result = (E) new ModelMapper().map(dto, entity.getClass());
        try {
            return entityDao.addEntity(result);
        } catch (MedragRepositoryException e) {
            logger.error("Could not add to database {}", dto);
        }
        return null;
    }

    @Override
    @Transactional
    public D getDtoById(D dto, E entity, Integer id) {
        E entityById = null;
        try {
            entityById = entityDao.getEntityById(entity, id);
        } catch (MedragRepositoryException e) {
            logger.error("Could not extract from database {}", dto);
        }
        return (D) new ModelMapper().map(entityById, dto.getClass());
    }

    @Override
    @Transactional
    public void updateDtoStatus(D dto, E entity) {
        try {
            E e = (E) new ModelMapper().map(dto, entity.getClass());
            entityDao.updateEntityStatus(e);
        } catch (MedragRepositoryException e) {
            logger.error("Could not update in database {}", dto);
        }
    }

    @Override
    @Transactional
    public void removeDto(D dto, E entity) {
        try {
            entityDao.removeEntity((E) new ModelMapper().map(dto, entity.getClass()));
        } catch (MedragRepositoryException e) {
            logger.error("Could not remove from database {}", dto);
        }
    }

    @Override
    @Transactional
    public void saveOrUpdateDto(D dto, E entity) {
        try {
            entityDao.saveOrUpdateEntity((E) new ModelMapper().map(dto, entity.getClass()));
        } catch (MedragRepositoryException e) {
            logger.error("Could not save or update in database {}", dto);
        }
    }

    @Override
    @Transactional
    public D getDtoByNaturalId(D dto, E entity, String id) {
        E result = null;
        try {
            result = entityDao.getEntityByNaturalId(entity, id);
        } catch (MedragRepositoryException e) {
            logger.error("Could not extract by natural id from database {}", dto);
        }
        if (result == null) {
            return null;
        } else {
            return (D) new ModelMapper().map(result, dto.getClass());
        }
    }

    @Override
    @Transactional
    public List<D> getDtoList(D dto, E entity) {
        List<E> entityList = null;
        try {
            entityList = entityDao.getEntityList(entity);
        } catch (MedragRepositoryException e) {
            logger.error("Could not get from database list of{}", dto);
        }
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            D d = (D) new ModelMapper().map(e, dto.getClass());
            dtoList.add(d);
        }
        return dtoList;
    }
}
