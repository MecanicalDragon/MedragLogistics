package net.medrag.model.service.dto;

import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.WaypointCompilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class WaypointCompilingServiceImpl implements WaypointCompilingService {

    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }
}
