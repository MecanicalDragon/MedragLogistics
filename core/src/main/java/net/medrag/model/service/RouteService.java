package net.medrag.model.service;

import net.medrag.model.dto.*;

import java.util.List;
import java.util.Set;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface RouteService {

    void compileRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad,
                      TruckDto assignedTruck, Set<DriverDto> brigade)throws MedragServiceException;

    void completeWaypoint(WaypointDto completedWP) throws MedragServiceException;
}
