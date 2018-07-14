package net.medrag.model.service.dto;

import net.medrag.model.dao.CityDao;
import net.medrag.model.dto.CityDto;
import net.medrag.model.domain.entity.City;

/**
 * Standard service interface for DAO interface{@link CityDao},
 * working with domain object {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CityService<D extends CityDto, E extends City> extends DTOService<D, E> {
}
