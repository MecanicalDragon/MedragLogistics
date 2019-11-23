package net.medrag.service.dto.api;

import net.medrag.dao.api.DriverDao;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.dto.DriverDto;

/**
 * Standard service interface for DAO interface{@link DriverDao},
 * working with domain object {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverService<D extends DriverDto, E extends Driver> extends DTOService<D, E> {
}
