package net.medrag.service.dto.api;

import net.medrag.dao.api.CityDao;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.entity.City;

/**
 * Standard service interface for DAO interface{@link CityDao},
 * working with domain object {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CityService<D extends CityDto, E extends City> extends DTOService<D, E> {
}
