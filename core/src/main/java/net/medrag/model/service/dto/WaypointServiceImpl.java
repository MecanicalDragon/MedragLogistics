package net.medrag.model.service.dto;

import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.domain.entity.Waypoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Standard service implementation for employment DAO interface{@link net.medrag.model.dao.WaypointDao},
 * working with domain object, that represents a {@link Waypoint}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class WaypointServiceImpl<D extends WaypointDto, E extends Waypoint> extends DTOServiceImpl<D, E>
        implements WaypointService<D, E> {

    private static final String implementation = "waypointDaoImpl";

    @Override
    @Transactional
    public void compileRoute(WaypointDto load, CityDto destination){

        Integer id = addDto((D)load, (E)new Waypoint());
        load.setId(id);
        updateDtoStatus((D)load, (E)new Waypoint());

        load.setCity(destination);
        load.setWayPointType("UNLOAD");
        load.setId(null);
        id = addDto((D)load, (E)new Waypoint());
        load.setId(id);
        updateDtoStatus((D)load, (E)new Waypoint());

    }
}
