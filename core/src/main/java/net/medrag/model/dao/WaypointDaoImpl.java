package net.medrag.model.dao;

import net.medrag.model.domain.entity.Waypoint;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class WaypointDaoImpl<E extends Waypoint> extends EntityDaoImpl<E> implements WaypointDao<E> {
}