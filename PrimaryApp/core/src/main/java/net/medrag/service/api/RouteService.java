package net.medrag.service.api;

import net.medrag.domain.dto.*;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Routing API.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface RouteService {

    /**
     * Method of logistics, that assigns new route.
     *
     * @param departure     - departure city
     * @param destination   - destination city
     * @param truckLoad     - cargoes list
     * @param assignedTruck - try to guess
     * @param brigade       - drivers, assigned to this route
     * @throws MedragServiceException - yes, throws.
     */
    void compileRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad,
                      TruckDto assignedTruck, List<DriverDto> brigade)throws MedragServiceException;

    /**
     * Method from the warehouse of the city, that sets waypoint completed.
     *
     * @param completedWP - waypoint, that becomes completed itself.
     */
    Boolean completeWaypoint(WaypointDto completedWP) throws MedragServiceException;

    /**
     * Method from logistics. Compiles route for truck, that's already in use.
     *
     * @param departure - current destination city.
     * @param destination - new destination city.
     * @param truckLoad - cargoes in truck.
     * @param assignedTruck - managed truck.
     * @param currentBrigade - current brigade.
     * @throws MedragServiceException - looks like it cans.
     */
    void compileUncompletedRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad, TruckDto assignedTruck, Boolean currentBrigade) throws MedragServiceException;

    /**
     * Method assigns truck brigade for a new route: changes statuses of current drivers and sets to new.
     *
     * @param assignedTruck - managed truck.
     * @param brigade - new brigade.
     * @throws MedragServiceException - don't know.
     */
    void compileRouteForTruck(TruckDto assignedTruck, List<DriverDto> brigade) throws MedragServiceException;
}
