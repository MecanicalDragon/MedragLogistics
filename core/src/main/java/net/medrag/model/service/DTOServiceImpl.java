package net.medrag.model.service;

import net.medrag.dto.Dto;
import net.medrag.model.dao.EntityDao;
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
public abstract class DTOServiceImpl<D extends Dto, E extends Entity> implements DTOService<D, E> {

    private EntityDao<E> entityDao;

    private final String implementation = "userDaoImpl";


    @Autowired
    public void setEntityDao(@Qualifier(implementation) EntityDao<E> entityDao) {
        this.entityDao = entityDao;
    }

    @Override
    @Transactional
    public void addDto(D dto, E entity){
        E result = (E)new ModelMapper().map(dto, entity.getClass());
        entityDao.addEntity(result);
    }

    @Override
    @Transactional
    public D getDtoById(D dto, E entity, Integer id){
        E entityById = entityDao.getEntityById(entity, id);
        return (D)new ModelMapper().map(entityById, dto.getClass());
    }

    @Override
    @Transactional
    public void updateDtoStatus(D dto, E entity){
        entityDao.updateEntityStatus((E)new ModelMapper().map(dto, entity.getClass()));
    }

    @Override
    @Transactional
    public void removeDto(Dto dto, E entity){
        entityDao.removeEntity((E)new ModelMapper().map(dto, entity.getClass()));
    }

    @Override
    @Transactional
    public D getDtoByNaturalId(D dto, E entity, String id){
        E result = entityDao.getEntityByNaturalId(entity, id);
        return (D)new ModelMapper().map(result, dto.getClass());
    }

    @Override
    @Transactional
    public List<D> getDtoList(D dto, E entity){
        List<E> entityList = entityDao.getEntityList(entity);
        List<D> dtoList = new ArrayList<>();
        for (E e : entityList) {
            D d = (D)new ModelMapper().map(e, dto.getClass());
            dtoList.add(d);
        }
        return dtoList;
    }
}
