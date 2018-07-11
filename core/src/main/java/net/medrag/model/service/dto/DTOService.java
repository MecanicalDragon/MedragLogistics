package net.medrag.model.service.dto;

import net.medrag.dto.Dto;
import net.medrag.model.domain.entity.Entity;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DTOService<D extends Dto, E extends Entity> {
    Integer addDto(D dto, E entity);
    void updateDtoStatus(D dto, E entity);
    void removeDto(D dto, E entity);
    void saveOrUpdateDto(D dto, E entity);
    D getDtoById(D dto, E entity, Integer id);
    D getDtoByNaturalId(D dto, E entity, String id);
    List<D> getDtoList(D dto, E entity);
}
