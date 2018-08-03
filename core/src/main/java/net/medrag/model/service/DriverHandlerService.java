package net.medrag.model.service;

import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverHandlerService {

    void identifyNewDriver(DriverDto driverDto) throws MedragServiceException;

    void updateDriver(DriverDto newDriver) throws MedragServiceException;

    void removeDriver(DriverDto removableDriver) throws MedragServiceException;

    List<DriverDto> getDriverList(Integer cityId, Integer time) throws MedragServiceException;

    void changeDriverState(DriverDto driver) throws MedragServiceException;
}
