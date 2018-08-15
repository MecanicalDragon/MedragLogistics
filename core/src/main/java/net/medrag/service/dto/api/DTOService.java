package net.medrag.service.dto.api;

import net.medrag.domain.dto.Dto;
import net.medrag.domain.entity.Entity;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DTOService<D extends Dto, E extends Entity> {
    Integer addDto(D dto, E entity) throws MedragServiceException;
    void updateDtoStatus(D dto, E entity) throws MedragServiceException;
    void removeDto(D dto, E entity) throws MedragServiceException;
    void saveOrUpdateDto(D dto, E entity) throws MedragServiceException;
    D refreshDto(D dto, E entity) throws MedragServiceException;
    D getDtoById(D dto, E entity, Integer id) throws MedragServiceException;
    D getDtoByNaturalId(D dto, E entity, String id) throws MedragServiceException;
    Integer getDtoCount(E entity, String... args) throws MedragServiceException;
    List<D> getDtoList(D dto, E entity, String... args) throws MedragServiceException;
}
