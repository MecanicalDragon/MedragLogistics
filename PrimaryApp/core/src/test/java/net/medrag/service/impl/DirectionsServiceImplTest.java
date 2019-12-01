package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.entity.City;
import net.medrag.service.dto.api.CityService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DirectionsServiceImplTest {

    @Mock
    private CityService<CityDto, City> cityService;

    @Spy
    private DirectionsServiceImpl directionsService;

    private CityDto current;
    private CityDto departure;
    private CityDto destination;

    @Before
    public void setUp() throws Exception {

        directionsService = new DirectionsServiceImpl();
        directionsService.setCityService(cityService);
        Field useGoogle = directionsService.getClass().getDeclaredField("useGoogle");
        useGoogle.setAccessible(true);
        useGoogle.set(directionsService, false);

        departure = new CityDto();
        departure.setCoordinatesX("58.5562984");
        departure.setCoordinatesY("31.1723534");
        departure.setId(1);
        departure.setName("Novgorod");

        destination = new CityDto();
        destination.setCoordinatesX("47.2611562");
        destination.setCoordinatesY("39.4879173");
        destination.setId(2);
        destination.setName("Rostov");

        current = new CityDto();
        current.setCoordinatesX("55.826938");
        current.setCoordinatesY("49.025906");
        current.setId(3);
        current.setName("Kazan");

        when(cityService.getDtoById(any(CityDto.class), any(City.class), eq(1))).thenReturn(departure);
        when(cityService.getDtoById(any(CityDto.class), any(City.class), eq(2))).thenReturn(destination);
        when(cityService.getDtoById(any(CityDto.class), any(City.class), eq(3))).thenReturn(current);

    }

    @After
    public void tearDown() throws Exception {

        directionsService = null;
        current = null;
        departure = null;
        destination = null;
    }

    @Test
    public void getTripTime() throws Exception {

        Integer[] trip = directionsService.getTripTime(departure, destination);

        assertNotNull(trip[0]);
        assertNotNull(trip[1]);
        assertTrue(trip[0] > 0);
        assertTrue(trip[1] > 0);

    }

    @Test
    public void getComletePersent() throws Exception {

        CargoDto cargo = new CargoDto();
        cargo.setDepartureId(1);
        cargo.setDestinationId(2);
        cargo.setCurrentCityId(3);

        Integer i = directionsService.getComletePersent(cargo);

        assertNotNull(i);
        assertTrue(i > 0);
        assertTrue(i < 100);
    }

    @Test
    public void getTripTimeByTruck() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setDestinationId(2);
        truck.setCityId(3);

        Integer[] trip = directionsService.getTripTimeByTruck(truck);

        assertNotNull(trip[0]);
        assertNotNull(trip[1]);
        assertTrue(trip[0] > 0);
        assertTrue(trip[1] > 0);

    }

}