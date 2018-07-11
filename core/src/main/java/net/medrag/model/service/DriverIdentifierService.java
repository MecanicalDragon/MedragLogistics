package net.medrag.model.service;

import net.medrag.dto.DriverDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverIdentifierService {
    void identifyNewDriver(DriverDto driverDto);
    void updateDriver(DriverDto newDriver);
    void removeDriver(DriverDto removableDriver);
}
