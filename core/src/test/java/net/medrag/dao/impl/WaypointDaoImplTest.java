package net.medrag.dao.impl;

import net.medrag.dao.api.WaypointDao;
import net.medrag.domain.entity.Waypoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WaypointDaoImplTest {

    private WaypointDao<Waypoint>waypointDao;

    @Test
    public void nothingToTest() throws Exception {
        waypointDao = new WaypointDaoImpl<>();
    }

}