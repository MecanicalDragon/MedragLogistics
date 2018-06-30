package net.medrag.model.service;

import net.medrag.dto.WaypointDto;
import net.medrag.model.domain.entity.Waypoint;

/**
 * Standard service implementation for employment DAO interface{@link net.medrag.model.dao.WaypointDao},
 * working with domain object, that represents a {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class WaypointServiceImpl<D extends WaypointDto, E extends Waypoint> extends DTOServiceImpl<D, E>
        implements WaypointService<D, E> {

    private final String implementation = "waypointDaoImpl";
}
