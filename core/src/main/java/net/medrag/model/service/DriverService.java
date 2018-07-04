package net.medrag.model.service;

import net.medrag.model.domain.entity.Driver;
import net.medrag.dto.DriverDto;

import java.util.List;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.DriverDao},
 * working with domain object, {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverService<D extends DriverDto, E extends Driver> extends DTOService<D, E> {
}
