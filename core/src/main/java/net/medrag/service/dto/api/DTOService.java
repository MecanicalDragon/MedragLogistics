package net.medrag.service.dto.api;

import net.medrag.domain.dto.Dto;
import net.medrag.domain.entity.Entity;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Superclass for all dto service interfaces.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DTOService<D extends Dto, E extends Entity> {

    /**
     * Persist new dto do database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @return - id parameter of persisted object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    Integer addDto(D dto, E entity) throws MedragServiceException;

    /**
     * Updating object in database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    void updateDtoStatus(D dto, E entity) throws MedragServiceException;

    /**
     * Remove object from database.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    void removeDto(D dto, E entity) throws MedragServiceException;

    /**
     * reload object from database
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @return - refreshed object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    D refreshDto(D dto, E entity) throws MedragServiceException;

    /**
     * Get object from database by id.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param id     - id of requested object.
     * @return - requested object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    D getDtoById(D dto, E entity, Integer id) throws MedragServiceException;

    /**
     * Getting from database object by it's natural id.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param id - object natural id.
     * @return - requested object.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    D getDtoByNaturalId(D dto, E entity, String id) throws MedragServiceException;

    /**
     * Getting number of objects in database with specified parameters.
     *
     * @param entity - blank entity object for ModelMapper.
     * @param args - parameters of filtering.
     * @return - filtered list of objects.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    Integer getDtoCount(E entity, String... args) throws MedragServiceException;

    /**
     * Getting from database list of objects, filtered by specified parameters.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param args - filter parameters.
     * @return - filtered list of objects from database.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    List<D> getDtoList(D dto, E entity, String... args) throws MedragServiceException;

    /**
     * Getting from database list of last added objects.
     *
     * @param dto    - blank dto object for ModelMapper.
     * @param entity - blank entity object for ModelMapper.
     * @param count - number of last added objects.
     * @return - list of last added objects.
     * @throws MedragServiceException - throws MedragServiceException.
     */
    List<D> getLastObjects(D dto, E entity, Integer count) throws MedragServiceException;
}
