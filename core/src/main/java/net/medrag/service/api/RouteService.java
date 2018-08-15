package net.medrag.service.api;

import net.medrag.domain.dto.*;
import net.medrag.service.MedragServiceException;

import java.util.List;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface RouteService {

    void compileRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad,
                      TruckDto assignedTruck, List<DriverDto> brigade)throws MedragServiceException;

    Boolean completeWaypoint(WaypointDto completedWP) throws MedragServiceException;

    void compileUncompletedRoute(CityDto departureCity, CityDto destinationCity, List<CargoDto> truckLoad, TruckDto assignedTruck, Boolean currentBrigade) throws MedragServiceException;

    void compileRouteForTruck(TruckDto truck, List<DriverDto> brigade) throws MedragServiceException;
}
