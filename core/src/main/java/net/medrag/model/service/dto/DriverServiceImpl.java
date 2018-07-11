package net.medrag.model.service.dto;

import net.medrag.model.dao.DriverDao;
import net.medrag.model.domain.entity.Driver;
import net.medrag.dto.DriverDto;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link DriverDao},
 * working with domain object, that represents a {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverServiceImpl<D extends DriverDto, E extends Driver> extends DTOServiceImpl<D, E>
        implements DriverService<D, E> {

    private static final String implementation = "driverDaoImpl";

}
