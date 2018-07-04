package net.medrag.model.service;

import net.medrag.model.dao.CityDao;
import net.medrag.model.dao.EntityDao;
import net.medrag.model.domain.entity.City;
import net.medrag.dto.CityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private static final String implementation = "cityDaoImpl";

}
