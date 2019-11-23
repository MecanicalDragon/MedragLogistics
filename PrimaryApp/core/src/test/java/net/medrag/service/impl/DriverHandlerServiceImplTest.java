package net.medrag.service.impl;

import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.entity.User;
import net.medrag.domain.enums.DriverState;
import net.medrag.service.api.MailService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import net.medrag.service.dto.api.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DriverHandlerServiceImplTest {

    @Mock
    private UserService<UserDto, User> userService;

    @Mock
    private DriverService<DriverDto, Driver> driverService;

    @Mock
    private MailService mailService;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private TruckService<TruckDto, Truck> truckService;

    @Spy
    private DriverHandlerServiceImpl service;

    private DriverDto driver;

    @Before
    public void setUp() throws Exception {

        service = new DriverHandlerServiceImpl();
        service.setDriverService(driverService);
        service.setEncoder(encoder);
        service.setUserService(userService);
        service.setMailService(mailService);
        service.setTruckService(truckService);

        driver = new DriverDto();
        driver.setPersonalNumber("DRV-12345");
        driver.setEmail("example@example.com");

    }

    @After
    public void tearDown() throws Exception {

        service = null;
        driver = null;
    }

    @Test   //Test for correct identifying new driver.
    public void identifyNewDriver() throws Exception {

        doNothing().when(mailService).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        when(driverService.addDto(any(DriverDto.class), any(Driver.class))).thenReturn(1);
        doNothing().when(userService).addNewUser(any(User.class));

        service.identifyNewDriver(driver);

        verify(mailService).sendLoginPasswordEmail(anyString(), anyString(), anyString(), anyString());
        verify(driverService).addDto(any(DriverDto.class), any(Driver.class));

    }

    @Test   //Test for updating user when updating driver.
    public void updateDriverWithNotNullUser() throws Exception {

        User user = new User();
        user.setId(5);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doNothing().when(userService).updateUser(user);
        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        service.updateDriver(driver);

        verify(userService).updateUser(user);
        verify(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        verify(userService).getUserByUsername(anyString());
    }

    @Test   //Test for updating driver with null user (That's can not be).
    public void updateDriverWithNullUser() throws Exception {

        User user = null;
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        service.updateDriver(driver);

        verify(userService, never()).updateUser(user);
        verify(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        verify(userService).getUserByUsername(anyString());
    }

    @Test   //Test for correct filtering drivers (If driver time matches with requirements).
    public void getDriverListWithZeroTimeDriver() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(0);
        drivers.add(driver);
        when(driverService.getDtoList(any(DriverDto.class), any(Driver.class), anyString(), anyString(), anyString(),
                anyString())).thenReturn(drivers);
        List<DriverDto> list = service.getDriverList(1, 500);

        assertEquals(list, drivers);
        assertTrue(list.contains(driver));
        assertEquals(1, list.size());

    }

    @Test   //Test for correct filtering drivers (If driver time doesn't match with requirements).
    public void getDriverListWithOverWorkedDriver() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(11000);
        drivers.add(driver);
        when(driverService.getDtoList(any(DriverDto.class), any(Driver.class), anyString(), anyString(), anyString(),
                anyString())).thenReturn(drivers);
        List<DriverDto> list = service.getDriverList(1, 500);

        assertNotEquals(list, drivers);
        assertFalse(list.contains(driver));
        assertEquals(0, list.size());

    }

    @Test   //Test for correct filtering drivers (If driver time matches with requirements).
    public void getReadyDriversWithZeroTimeDriver() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(10000);
        drivers.add(driver);

        List<DriverDto> list = service.getReadyDrivers(500, drivers);

        assertEquals(list, drivers);
        assertTrue(list.contains(driver));
        assertEquals(1, list.size());

    }

    @Test   //Test for correct filtering drivers (If driver time doesn't match with requirements).
    public void getReadyDriversWithDriversOnTheLimit() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(10500);
        drivers.add(driver);

        List<DriverDto> list = service.getReadyDrivers(500, drivers);

        assertNotEquals(list, drivers);
        assertFalse(list.contains(driver));
        assertEquals(0, list.size());

    }

    @Test   //Test for correct status changing (Last driver, when truck is not on it's destination).
    public void changeDriverStateIfStandalone() throws Exception {

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        truck.setDestinationId(1);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.READY_TO_ROUTE);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, never()).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, never()).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertFalse(verdict);

    }

    @Test   //Test for correct status changing (Last driver, when truck is on it's destination).
    public void changeDriverStateOfLastDriver() throws Exception {

        doNothing().when(truckService).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.REST);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, times(1)).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(1)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);

    }

    @Test   //Test for correct status changing (Not last driver).
    public void changeDriverStateOfNotLastDriver() throws Exception {

        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        brigade.add(new DriverDto());
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.REST);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, never()).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(1)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);

    }

    @Test   //Test for correct status changing (Idle function call. Don't try this in real code).
    public void changeDriverStateIdle() throws Exception {

        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.ON_SHIFT);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, never()).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(1)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);

    }

    @Test   //Test for correct status changing (Saving comrade case).
    public void changeDriverStateDrivingWithComradeNullDestination() throws Exception {

        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        brigade.add(new DriverDto());
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.DRIVING);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, never()).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(2)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);

    }

    @Test   //Test for correct status changing (Firing comrade case).
    public void changeDriverStateDrivingWithComradeNotNullDestination() throws Exception {

        doNothing().when(driverService).updateDtoStatus(any(DriverDto.class), any(Driver.class));

        List<DriverDto> brigade = new ArrayList<>();
        brigade.add(driver);
        DriverDto comrade = new DriverDto();
        comrade.setDestinationId(5);
        brigade.add(comrade);
        TruckDto truck = new TruckDto();
        truck.setBrigade(brigade);
        driver.setCurrentTruck(truck);

        driver.setState(DriverState.DRIVING);
        boolean verdict = service.changeDriverState(driver);

        verify(truckService, never()).updateDtoStatus(any(TruckDto.class), any(Truck.class));
        verify(driverService, times(2)).updateDtoStatus(any(DriverDto.class), any(Driver.class));
        assertTrue(verdict);

    }

    @Test   //Test for correct filtering drivers (If driver time matches with requirements).
    public void getDriverListWithTruckBrigadeWithZeroTime() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(0);
        drivers.add(driver);

        TruckDto truck = new TruckDto();
        truck.setCityId(2);
        truck.setBrigade(drivers);

        when(driverService.getDtoList(any(DriverDto.class), any(Driver.class), anyString(), anyString(), anyString(),
                anyString())).thenReturn(drivers);
        List<DriverDto> list = service.getDriverListWithTruckBrigade(truck, 500);

        assertTrue(list.contains(driver));
        assertEquals(2, list.size());

    }

    @Test   //Test for correct filtering drivers (If driver time doesn't match with requirements).
    public void getDriverListWithTruckBrigadeWithOverlimitedTime() throws Exception {

        List<DriverDto> drivers = new ArrayList<>();
        driver.setWorkedTime(10100);
        drivers.add(driver);

        TruckDto truck = new TruckDto();
        truck.setCityId(2);
        truck.setBrigade(drivers);

        when(driverService.getDtoList(any(DriverDto.class), any(Driver.class), anyString(), anyString(), anyString(),
                anyString())).thenReturn(drivers);
        List<DriverDto> list = service.getDriverListWithTruckBrigade(truck, 500);

        assertFalse(list.contains(driver));
        assertEquals(0, list.size());

    }

    @Test   //Testing post-construct periodical method.
    public void nullWorkedHours() throws Exception {
        service.nullWorkedHours();
    }

}