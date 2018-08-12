package net.medrag.model.service;

import net.medrag.model.domain.dto.DriverDto;
import net.medrag.model.domain.dto.TruckDto;

import java.util.List;

/**
 * Interface, that is responsible for some request, bounded with drivers.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DriverHandlerService {

    void identifyNewDriver(DriverDto driverDto) throws MedragServiceException;

    void updateDriver(DriverDto newDriver) throws MedragServiceException;

    void removeDriver(DriverDto removableDriver) throws MedragServiceException;

    List<DriverDto> getDriverList(Integer cityId, Integer time) throws MedragServiceException;

    boolean changeDriverState(DriverDto driver) throws MedragServiceException;

    List<DriverDto> getDriverListWithTruckBrigade(TruckDto chosenTruck, Integer integer) throws MedragServiceException;

    List<DriverDto> getReadyDrivers(Integer time, List<DriverDto> drivers);
}
