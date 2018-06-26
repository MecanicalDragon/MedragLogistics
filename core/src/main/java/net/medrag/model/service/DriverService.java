package net.medrag.model.service;

import net.medrag.model.domain.Driver;
import net.medrag.model.dto.DriverDto;

import java.util.List;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.DriverDao},
 * working with domain object, that represents a {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverService {
    void addDriver(DriverDto driverDto);
    void updateDriverStatus(DriverDto driverDto);
    void removeDriver(DriverDto driverDto);
    DriverDto getDriverById(Integer id);
    DriverDto getDriverByPersonalNumber(String id);
    List<DriverDto> getDriverList();

    void testDriver(DriverDto driverDto);
}
