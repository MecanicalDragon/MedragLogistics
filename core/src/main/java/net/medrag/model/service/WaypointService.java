package net.medrag.model.service;

import net.medrag.dto.WaypointDto;
import net.medrag.model.domain.entity.Waypoint;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.WaypointDao},
 * working with domain object {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface WaypointService<D extends WaypointDto, E extends Waypoint> extends DTOService<D, E> {
}
