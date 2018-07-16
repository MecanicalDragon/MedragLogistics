package net.medrag.model.service;

import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.WaypointDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface WaypointHandlerService {
    void compileRoute(WaypointDto waypoint, CityDto destination);
    void completeWaypoint(WaypointDto completedWP);
}
