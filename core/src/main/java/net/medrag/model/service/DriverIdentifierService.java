package net.medrag.model.service;

import net.medrag.model.dto.DriverDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverIdentifierService {

    void identifyNewDriver(DriverDto driverDto) throws MedragServiceException;

    void updateDriver(DriverDto newDriver) throws MedragServiceException;

    void removeDriver(DriverDto removableDriver) throws MedragServiceException;
}
