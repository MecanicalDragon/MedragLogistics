package net.medrag.service.impl;

import net.medrag.domain.dto.*;
import net.medrag.domain.entity.City;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.entity.Waypoint;
import net.medrag.domain.enums.Manageable;
import net.medrag.domain.enums.WaypointType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    private CustomerDto customer;

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

        customer = new CustomerDto();
        customer.setPassport("doc-1");

//        For innerCompileWaypoint
        doNothing().when(rabbitService).sendCargo(any(CargoDto.class));
        doNothing().when(waypointService).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        when(waypointService.addDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(1);
    }

    @After
    public void tearDown() throws Exception {

        routeService = null;
        customer = null;
    }

    @Test   //Testing route compiling
    public void compileRoute() throws Exception {

        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        doNothing().when(truckService).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        lenient().doNothing().when(mailService).sendCompiledRouteMesaage(any(DriverDto.class), anyString());

        CityDto departure = new CityDto();
        CityDto destination = new CityDto();
        destination.setId(17);
        destination.setName("City-17");
        List<CargoDto> truckLoad = Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList());
        TruckDto truck = new TruckDto();
        List<DriverDto>brigade = Stream.of(new DriverDto(), new DriverDto()).collect(Collectors.toList());

        routeService.compileRoute(departure, destination, truckLoad, truck, brigade);

        verify(driverService, times(2)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
//        verify(mailService).sendCompiledRouteMesaage(any(DriverDto.class), anyString()); //hereCollision
        verify(truckService, times(1)).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(waypointService, times(6)).addDto(any(WaypointDto.class), any(Waypoint.class));
        verify(waypointService, times(6)).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService, times(3)).sendCargo(any(CargoDto.class));


    }

    @Test   //Testing completing LOAD waypoint, not first in city.
    public void completeLoadNotFirstWaypoint() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setCityId(17);

        CityDto city = new CityDto();
        city.setId(17);

        CargoDto cargo = new CargoDto();
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.LOAD);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService).sendCargo(any(CargoDto.class));
        assertFalse(verdict);
    }

    @Test   //Testing completing UNLOAD waypoint, not first in city, destination for cargo, with not null owner email.
    public void completeUnloadNotFirstDestinationNotNullEmailWaypoint() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setCityId(17);

        CityDto city = new CityDto();
        city.setId(17);
        city.setName("City-17");

        customer = new CustomerDto();
        customer.setEmail("example@example.com");

        CargoDto cargo = new CargoDto();
        cargo.setDestinationId(17);
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.UNLOAD);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);
        lenient().doNothing().when(mailService).sendDeliveredCargoEmail(any(CargoDto.class));

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService).sendCargo(any(CargoDto.class));
        verify(mailService, times(0)).sendDeliveredCargoEmail(any(CargoDto.class)); //hereCollision
        assertFalse(verdict);
    }

    @Test   //Testing completing UNLOAD waypoint, not first in city.
    public void completeUnloadNotFirstWaypoint() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setCityId(17);

        CityDto city = new CityDto();
        city.setId(17);
        city.setName("City-17");

        CargoDto cargo = new CargoDto();
        cargo.setDestinationId(15);
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.UNLOAD);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService).sendCargo(any(CargoDto.class));
        assertFalse(verdict);
    }

    @Test   //Testing completing UNLOAD waypoint, not first in city, destination for cargo, with null owner email.
    public void completeUnloadNotFirstDestinationNullEmailWaypoint() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setCityId(17);

        CityDto city = new CityDto();
        city.setId(17);
        city.setName("City-17");

        customer = new CustomerDto();
        customer.setPassport("doc-1");

        CargoDto cargo = new CargoDto();
        cargo.setDestinationId(17);
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.UNLOAD);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService).sendCargo(any(CargoDto.class));
        verify(mailService, never()).sendDeliveredCargoEmail(any(CargoDto.class));
        assertFalse(verdict);
    }

    @Test   //Testing completing LOAD waypoint, first in city.
    public void completeLoadFirstWaypoint() throws Exception {

        List<DriverDto>brigade = Stream.of(new DriverDto(), new DriverDto()).collect(Collectors.toList());

        TruckDto truck = new TruckDto();
        truck.setId(1);
        truck.setCityId(15);
        truck.setDestinationId(17);
        truck.setBrigade(brigade);

        CityDto city = new CityDto();
        city.setId(17);

        CargoDto cargo = new CargoDto();
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.LOAD);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);
        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        when(waypointService.getDtoList(any(WaypointDto.class), any(Waypoint.class), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService, times(1)).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService).sendCargo(any(CargoDto.class));
        verify(driverService, times(2)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);
    }

    @Test   //Testing completing LOAD waypoint, first in city, not null drivers' destination and cargo's current truck.
    public void completeLoadFirstWaypointMotNullDriversDestinationAndCargoTruck() throws Exception {

        List<DriverDto>brigade = Stream.of(new DriverDto(), new DriverDto()).collect(Collectors.toList());
        brigade.forEach(d -> {d.setDestinationId(17); d.setCityId(17);});

        TruckDto truck = new TruckDto();
        truck.setId(1);
        truck.setCityId(15);
        truck.setDestinationId(18);
        truck.setBrigade(brigade);
        truck.setManageable(Manageable.UNCOMPLETED);

        CityDto city = new CityDto();
        city.setId(17);

        CargoDto cargo = new CargoDto();
        cargo.setCurrentTruck(truck);
        cargo.setOwner(customer);

        WaypointDto waypoint = new WaypointDto();
        waypoint.setCity(city);
        waypoint.setCargo(cargo);
        waypoint.setCurrentTruck(truck);
        waypoint.setWayPointType(WaypointType.LOAD);

        List<WaypointDto>wps = new ArrayList<>();
        wps.add(waypoint);

        when(waypointService.refreshDto(any(WaypointDto.class), any(Waypoint.class))).thenReturn(waypoint);
        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        when(waypointService.getDtoList(any(WaypointDto.class), any(Waypoint.class), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString())).thenReturn(wps);

        boolean verdict = routeService.completeWaypoint(waypoint);

        verify(waypointService, times(2)).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
        verify(rabbitService, times(1)).sendCargo(any(CargoDto.class));
        verify(driverService, times(2)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);
    }

    @Test   //The only test for this method
    public void compileUncompletedRoute() throws Exception {

        doNothing().when(truckService).updateDtoStatus(any(TruckDto.class), any(Truck.class));

        CityDto departure = new CityDto();
        departure.setId(15);

        CityDto destination = new CityDto();
        destination.setId(17);
        destination.setName("City-17");

        List<CargoDto>truckLoad = Stream.of(new CargoDto(), new CargoDto()).collect(Collectors.toList());
        truckLoad.get(0).setCurrentCityId(15);
        truckLoad.get(1).setCurrentCityId(16);

        TruckDto truck = new TruckDto();
        truck.setId(1);
        WaypointDto wp = new WaypointDto();
        wp.setCargo(truckLoad.get(0));
        List<WaypointDto> wps = Stream.of(wp).collect(Collectors.toList());

        when(waypointService.getDtoList(any(WaypointDto.class), any(Waypoint.class), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString())).thenReturn(wps);

        routeService.compileUncompletedRoute(departure, destination, truckLoad, truck, true);

        verify(truckService).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(waypointService, times(2)).addDto(any(WaypointDto.class), any(Waypoint.class));
        verify(waypointService, times(3)).updateDtoStatus(any(WaypointDto.class), any(Waypoint.class));
    }

    @Test   //The only test for this method
    public void compileRouteForTruck() throws Exception {

        doNothing().when(truckService).updateDtoStatus(any(TruckDto.class), any(Truck.class));

        DriverDto driver1 = new DriverDto();
        driver1.setId(1);
        driver1.setPersonalNumber("AB11111");

        DriverDto driver2 = new DriverDto();
        driver2.setId(2);
        driver2.setPersonalNumber("AB22222");

        DriverDto driver3 = new DriverDto();
        driver3.setId(3);
        driver3.setPersonalNumber("AB33333");

        List<DriverDto>oldBrigade = Stream.of(driver1, driver2).collect(Collectors.toList());
        List<DriverDto>newBrigade = Stream.of(driver3, driver2).collect(Collectors.toList());

        TruckDto managedTruck = new TruckDto();
        managedTruck.setBrigade(oldBrigade);
        managedTruck.setDestinationId(17);
        managedTruck.setDestinationName("City-17");

        routeService.compileRouteForTruck(managedTruck, newBrigade);

        verify(truckService, times(1)).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(3)).updateDtoStatus(any(DriverDto.class), any(Driver.class));

    }

}