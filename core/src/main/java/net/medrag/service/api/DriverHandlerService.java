package net.medrag.service.api;

import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;

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

    List<DriverDto> getDriverList(Integer cityId, Integer time) throws MedragServiceException;

    boolean changeDriverState(DriverDto driver) throws MedragServiceException;

    List<DriverDto> getDriverListWithTruckBrigade(TruckDto chosenTruck, Integer integer) throws MedragServiceException;

    List<DriverDto> getReadyDrivers(Integer time, List<DriverDto> drivers);
}
