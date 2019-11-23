package net.medrag.service.dto.api;

import net.medrag.dao.api.WaypointDao;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.entity.Waypoint;

/**
 * Standard service interface for DAO interface{@link WaypointDao},
 * working with domain object {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface WaypointService<D extends WaypointDto, E extends Waypoint> extends DTOService<D, E> {
}
