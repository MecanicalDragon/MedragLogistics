package net.medrag.service.dto.impl;

import net.medrag.dao.MedragRepositoryException;
import net.medrag.dao.api.EntityDao;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.enums.DriverState;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.MailService;
import net.medrag.service.api.RabbitService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceImplTest extends DTOServiceImpl {

    @Mock
    private RabbitService rabbitService;

    @Mock
    private MailService mailService;

    @Mock
    private EntityDao<Driver> entityDao;

    @Spy
    private DriverServiceImpl<DriverDto, Driver> driverService;

    private DriverDto driver;

    @Before
    public void setUp() throws Exception {

        driverService = new DriverServiceImpl<>();
        driverService.setRabbitService(rabbitService);
        driverService.setMailService(mailService);
        driverService.setEntityDao(entityDao);

        doNothing().when(entityDao).updateEntityStatus(any(Driver.class));
        doNothing().when(mailService).sendWorkedTimeLimitMail(any(DriverDto.class));
        doNothing().when(rabbitService).sendMessage(anyString());

        driver = new DriverDto();
        driver.setWorkedTime(10);
        driver.setPaidTime(15);
        driver.setLastChange(1534364133L);

    }

    @After
    public void tearDown() throws Exception {

        driverService = null;
        driver = null;
    }

    @Test   //Testing changes in driver state and correctness of calculating working time and paid time with these state conditions
    public void updateDtoStatus_ON_SHIFT_REST() throws Exception {

        driver.setPreviousState(DriverState.ON_SHIFT);
        driver.setState(DriverState.REST);

        int dwt = driver.getWorkedTime();
        int dpt = driver.getPaidTime();

        driverService.updateDtoStatus(driver, new Driver());

        assertEquals(driver.getState(), driver.getPreviousState());
        assertTrue(dwt < driver.getWorkedTime());
        assertTrue(dpt < driver.getPaidTime());
        assertEquals((int)((driver.getWorkedTime()-10)*1.4), (int)(driver.getPaidTime()-15));
    }

    @Test   //Testing changes in driver state and correctness of calculating working time and paid time with these state conditions
    public void updateDtoStatus_REST_ON_SHIFT() throws Exception {

        driver.setPreviousState(DriverState.REST);
        driver.setState(DriverState.ON_SHIFT);

        int dwt = driver.getWorkedTime();
        int dpt = driver.getPaidTime();

        driverService.updateDtoStatus(driver, new Driver());

        assertEquals(driver.getState(), driver.getPreviousState());
        assertTrue(dwt == driver.getWorkedTime());
        assertTrue(dpt == driver.getPaidTime());
    }

    @Test   //Testing changes in driver state and correctness of calculating working time and paid time with these state conditions
    public void updateDtoStatus_DRIVING_DRIVING() throws Exception {

        driver.setPreviousState(DriverState.DRIVING);
        driver.setState(DriverState.DRIVING);

        int dwt = driver.getWorkedTime();
        int dpt = driver.getPaidTime();

        driverService.updateDtoStatus(driver, new Driver());

        assertEquals(driver.getState(), driver.getPreviousState());
        assertTrue(dwt == driver.getWorkedTime());
        assertTrue(dpt == driver.getPaidTime());

    }

    @Test   //Testing changes in driver state and correctness of calculating working time and paid time with these state conditions
    public void updateDtoStatus_DRIVING_PORTER() throws Exception {

        driver.setPreviousState(DriverState.DRIVING);
        driver.setState(DriverState.PORTER);

        int dwt = driver.getWorkedTime();
        int dpt = driver.getPaidTime();

        driverService.updateDtoStatus(driver, new Driver());

        assertEquals(driver.getState(), driver.getPreviousState());
        assertTrue(dwt < driver.getWorkedTime());
        assertTrue(dpt < driver.getPaidTime());
        assertEquals((int)((driver.getWorkedTime()-10)*1.6), (int)(driver.getPaidTime()-15));

    }

    @Test   //Testing changes in driver state and correctness of calculating working time and paid time with these state conditions
    public void updateDtoStatus_PORTER_READY_TO_ROUTE() throws Exception {

        driver.setPreviousState(DriverState.PORTER);
        driver.setState(DriverState.READY_TO_ROUTE);

        int dwt = driver.getWorkedTime();
        int dpt = driver.getPaidTime();

        driverService.updateDtoStatus(driver, new Driver());

        assertEquals(driver.getState(), driver.getPreviousState());
        assertTrue(dwt < driver.getWorkedTime());
        assertTrue(dpt < driver.getPaidTime());
        assertEquals((int)((driver.getWorkedTime()-10)*1.2), (int)(driver.getPaidTime()-15));

    }

    @Test   //Testing filtering and return drivers dto list.
    public void getDtoList() throws Exception {

        Driver dbDriver = new Driver();
        dbDriver.setLastChange(1534364133L);
        dbDriver.setWorkedTime(10560);
        dbDriver.setPaidTime(11560);
        dbDriver.setState(DriverState.READY_TO_ROUTE);
        dbDriver.setPreviousState(DriverState.READY_TO_ROUTE);
        dbDriver.setId(1);

        Driver dbDriver2 = new Driver();
        dbDriver2.setLastChange(Instant.now().getEpochSecond());
        dbDriver2.setWorkedTime(10);
        dbDriver2.setPaidTime(15);
        dbDriver2.setState(DriverState.READY_TO_ROUTE);
        dbDriver2.setPreviousState(DriverState.READY_TO_ROUTE);
        dbDriver2.setId(2);

        List<Driver>dbDrivers = new ArrayList<>();
        dbDrivers.add(dbDriver);
        dbDrivers.add(dbDriver2);

        when(entityDao.getEntityList(any(Driver.class))).thenReturn(dbDrivers);
        List<DriverDto> extractedDrivers = driverService.getDtoList(new DriverDto(), new Driver());
        verify(entityDao, times(1)).updateEntityStatus(any(Driver.class));

        assertTrue( extractedDrivers.size() == 1);
        assertTrue( dbDrivers.contains(dbDriver2));

    }

    @Test   (expected = MedragServiceException.class)  //Testing throwing exception.
    public void getDtoListException() throws Exception {

        when(entityDao.getEntityList(any(Driver.class))).thenThrow(MedragRepositoryException.class);
        List<DriverDto> extractedDrivers = driverService.getDtoList(new DriverDto(), new Driver());

    }

    @Test   //Testing getting driver by unique personal number
    public void getDtoByNaturalId() throws Exception {

        Driver dbDriver = new Driver();
        dbDriver.setLastChange(1534364133L);
        dbDriver.setWorkedTime(10560);
        dbDriver.setPaidTime(11560);
        dbDriver.setState(DriverState.READY_TO_ROUTE);
        dbDriver.setPreviousState(DriverState.READY_TO_ROUTE);
        dbDriver.setId(1);

        when(entityDao.getEntityByNaturalId(any(Driver.class), anyString())).thenReturn(dbDriver);
        driver = driverService.getDtoByNaturalId(new DriverDto(), new Driver(), "faggot");

        assertEquals((int)driver.getId(), 1);
        assertEquals(driver.getState(), DriverState.READY_TO_ROUTE);
        assertTrue(driver.getWorkedTime() > dbDriver.getWorkedTime());
        assertTrue(driver.getPaidTime() > dbDriver.getPaidTime());

    }

}