package net.medrag.service.dto.impl;

import net.medrag.dao.api.CityDao;
import net.medrag.domain.entity.City;
import net.medrag.domain.dto.CityDto;
import net.medrag.service.dto.api.CityService;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link CityDao},
 * working with domain object, that represents a {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CityServiceImpl<D extends CityDto, E extends City> extends DTOServiceImpl<D, E>
        implements CityService<D, E> {

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static final String implementation = "cityDaoImpl";

}
