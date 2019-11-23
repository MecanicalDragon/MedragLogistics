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

    /**
     * Method for identifying new driver as a user. It creates and adds into the database new user with driver
     * authorities and the same name.
     *
     * @param driverDto - inputted driverDto
     * @throws MedragServiceException - maybe.
     */
    void identifyNewDriver(DriverDto driverDto) throws MedragServiceException;

    /**
     * This method updates appropriate user, if driver's email has been updated.
     *
     * @param driver - updated driver.
     * @throws MedragServiceException - as usual.
     */
    void updateDriver(DriverDto driver) throws MedragServiceException;

    /**
     * Method returns all available and matching for the route drives.
     *
     * @param cityId - id of the departure city.
     * @param time   - time, needed for the route.
     * @return - filtered list of matching drivers.
     * @throws MedragServiceException - get ready.
     */
    List<DriverDto> getDriverList(Integer cityId, Integer time) throws MedragServiceException;

    /**
     * Method frees truck, if it's last driver goes to rest and sets "ON_SHIFT" statuses to rest drivers,
     * if one of them takes control of the truck.
     *
     * @param driver - that driver.
     * @throws MedragServiceException - maybe.
     */
    boolean changeDriverState(DriverDto driver) throws MedragServiceException;

    /**
     * Methods returns list of drivers, available to the route for the truck, including current truck brigade.
     *
     * @param truck - routing truck.
     * @param time  - time, required for the route.
     * @return - list of available drivers.
     * @throws MedragServiceException - if "getReadyDrivers" fails.
     */
    List<DriverDto> getDriverListWithTruckBrigade(TruckDto truck, Integer time) throws MedragServiceException;

    /**
     * Method filters list of drivers by matching the time requirements of the next route.
     *
     * @param time    -time, required for the route.
     * @param drivers - list of all available drivers.
     * @return - filtered list of drivers.
     */
    List<DriverDto> getReadyDrivers(Integer time, List<DriverDto> drivers);
}
