package net.medrag.service.impl;

import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.entity.City;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.entity.Waypoint;
import net.medrag.service.api.DirectionsService;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.api.MailService;
import net.medrag.service.api.RabbitService;
import net.medrag.service.dto.api.CityService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import net.medrag.service.dto.api.WaypointService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceImplTest {

    @Mock
    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Mock
    private MailService mailService;

    @Mock
    private DriverService<DriverDto, Driver> driverService;

    @Mock
    private TruckService<TruckDto, Truck> truckService;

    @Mock
    private RabbitService rabbitService;

    @Mock
    private DirectionsService directionsService;

    @Mock
    private CityService<CityDto, City> cityService;

    @Mock
    private DriverHandlerService driverHandlerService;

    @Spy
    private RouteServiceImpl routeService;

    @Before
    public void setUp() throws Exception {

        routeService = new RouteServiceImpl();
        routeService.setCityService(cityService);
        routeService.setDirectionsService(directionsService);
        routeService.setWaypointService(waypointService);
        routeService.setMailService(mailService);
        routeService.setDriverService(driverService);
        routeService.setDriverHandlerService(driverHandlerService);
        routeService.setTruckService(truckService);
        routeService.setRabbitService(rabbitService);
    }

    @After
    public void tearDown() throws Exception {

        routeService = null;
    }

    @Test
    public void compileRoute() throws Exception {
    }

    @Test
    public void completeWaypoint() throws Exception {
    }

    @Test
    public void compileUncompletedRoute() throws Exception {
    }

    @Test
    public void compileRouteForTruck() throws Exception {
    }

}