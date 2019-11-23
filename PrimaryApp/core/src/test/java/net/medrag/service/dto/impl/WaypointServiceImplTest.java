package net.medrag.service.dto.impl;

import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.entity.Orderr;
import net.medrag.domain.entity.Waypoint;
import net.medrag.service.dto.api.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WaypointServiceImplTest {

    private WaypointServiceImpl<WaypointDto, Waypoint> waypointService;

    @Test
    public void nothingToTest(){
        waypointService = new WaypointServiceImpl<>();
    }

}