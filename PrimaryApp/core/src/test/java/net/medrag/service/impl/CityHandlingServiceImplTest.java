package net.medrag.service.impl;

import net.medrag.dao.api.DriverDao;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.entity.Cargo;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.service.dto.api.CargoService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityHandlingServiceImplTest {

    @Mock
    private CargoService<CargoDto, Cargo> cargoService;

    @Mock
    private DriverService<DriverDto, Driver> driverService;

    @Mock
    private TruckService<TruckDto, Truck> truckService;

    @Spy
    private CityHandlingServiceImpl cityHandler;

    private DriverDto driver;
    private TruckDto truck;
    private CargoDto cargo;
    private CityDto city;

    private List<CargoDto> cargoes;
    private List<DriverDto> drivers;
    private List<TruckDto> trucks;

    @Before
    public void setUp() throws Exception {

        cityHandler = new CityHandlingServiceImpl();
        cityHandler.setCargoService(cargoService);
        cityHandler.setDriverService(driverService);
        cityHandler.setTruckService(truckService);

        driver = new DriverDto();
        truck = new TruckDto();
        cargo = new CargoDto();

        city = new CityDto();
        city.setId(1);

        cargoes = new ArrayList<>();
        trucks = new ArrayList<>();
        drivers = new ArrayList<>();

        when(driverService.getDtoList(any(DriverDto.class), any(Driver.class), anyString(), anyString())).thenReturn(drivers);
        when(cargoService.getDtoList(any(CargoDto.class), any(Cargo.class), anyString(), anyString())).thenReturn(cargoes);
        when(truckService.getDtoList(any(TruckDto.class), any(Truck.class), anyString(), anyString())).thenReturn(trucks);

    }

    @After
    public void tearDown() throws Exception {

        cityHandler = null;
        driver = null;
        truck = null;
        cargo = null;
        cargoes = null;
        drivers = null;
        trucks = null;
        city = null;

    }

    @Test
    public void removeCityWithCargo() throws Exception {

        cargoes.add(cargo);
        boolean verdict = cityHandler.removeCity(city);
        assertFalse(verdict);

    }

    @Test
    public void removeCityWithTruck() throws Exception {

        trucks.add(truck);
        boolean verdict = cityHandler.removeCity(city);
        assertFalse(verdict);

    }

    @Test
    public void removeCityWithDriver() throws Exception {

        drivers.add(driver);
        boolean verdict = cityHandler.removeCity(city);
        assertFalse(verdict);

    }

    @Test
    public void removeVacantCity() throws Exception {

        boolean verdict = cityHandler.removeCity(city);
        assertTrue(verdict);

    }

}