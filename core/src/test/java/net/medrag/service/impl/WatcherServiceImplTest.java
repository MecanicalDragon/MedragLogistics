package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CargoForm;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.entity.Cargo;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.service.api.RabbitService;
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


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WatcherServiceImplTest {

    @Mock
    private CargoService<CargoDto, Cargo> cargoService;

    @Mock
    private RabbitService rabbit;

    @Mock
    private DriverService<DriverDto, Driver> driverService;

    @Mock
    private TruckService<TruckDto, Truck> truckService;

    @Spy
    private WatcherServiceImpl watcher;

    @Before
    public void setUp() throws Exception {
        watcher = new WatcherServiceImpl();
        watcher.setRabbit(rabbit);
        doNothing().when(rabbit).run();

    }

    @After
    public void tearDown() throws Exception {
        watcher = null;
    }

    @Test
    public void getCargoesList() throws Exception {

        watcher.setCargoService(cargoService);
        doNothing().when(rabbit).run();
        when(cargoService.getLastObjects(any(CargoDto.class), any(Cargo.class), anyInt())).thenReturn
                (Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList()));
        List<CargoForm>result = watcher.getCargoesList();
        verify(rabbit).run();
        assertTrue(result.size() == 3);

    }

    @Test
    public void getStats() throws Exception {

        watcher.setDriverService(driverService);
        watcher.setTruckService(truckService);
        when(driverService.getDtoCount(any(Driver.class), anyString(), anyString())).thenReturn(2);
        when(driverService.getDtoCount(any(Driver.class), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString())).thenReturn(4);
        when(truckService.getDtoCount(any(Truck.class), anyString(), anyString())).thenReturn(3);

        Integer[] stats = watcher.getStats();

        assertEquals(4, (int)stats[0]);
        assertEquals(2, (int)stats[1]);
        assertEquals(2, (int)stats[2]);
        assertEquals(3, (int)stats[3]);
        assertEquals(3, (int)stats[4]);
        assertEquals(3, (int)stats[5]);
    }

}