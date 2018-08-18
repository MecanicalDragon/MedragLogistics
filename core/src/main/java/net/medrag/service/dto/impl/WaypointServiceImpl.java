package net.medrag.service.dto.impl;

import net.medrag.dao.api.WaypointDao;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.entity.Waypoint;
import net.medrag.service.dto.api.WaypointService;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link WaypointDao},
 * working with domain object, that represents a {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class WaypointServiceImpl<D extends WaypointDto, E extends Waypoint> extends DTOServiceImpl<D, E>
        implements WaypointService<D, E> {

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static final String implementation = "waypointDaoImpl";

}
