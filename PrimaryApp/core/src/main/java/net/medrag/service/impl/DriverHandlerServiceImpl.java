package net.medrag.service.impl;

import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.User;
import net.medrag.domain.enums.DriverState;
import net.medrag.domain.enums.Manageable;
import net.medrag.domain.enums.TruckStatus;
import net.medrag.domain.enums.UserRole;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.api.MailService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import net.medrag.service.dto.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This service handles some requests, bounded with {@link Driver} entity.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverHandlerServiceImpl implements DriverHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverHandlerServiceImpl.class);

    private UserService<UserDto, User> userService;

    private DriverService<DriverDto, Driver> driverService;

    private MailService mailService;

    private BCryptPasswordEncoder encoder;

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setUserService(UserService<UserDto, User> userService) {
        this.userService = userService;
    }

    /**
     * Method for identifying new driver as a user. It creates and adds into the database new user with driver
     * authorities and the same name.
     *
     * @param driverDto - inputted driverDto
     * @throws MedragServiceException - maybe.
     */
    @Override
    @Transactional
    public void identifyNewDriver(DriverDto driverDto) throws MedragServiceException {

        User driverUser = new User();
        driverUser.setUsername(driverDto.getPersonalNumber());
        driverUser.setEmail(driverDto.getEmail());
        driverUser.setRole(UserRole.ROLE_DRIVER);

        StringBuilder passwordBuilder = new StringBuilder();
        do {
            passwordBuilder.append(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));
        } while (passwordBuilder.toString().length() < 16);
        String password = passwordBuilder.toString().substring(0, 8);

        driverUser.setPassword(encoder.encode(password));
        try {
            mailService.sendLoginPasswordEmail(driverUser.getEmail(), driverUser.getUsername(), password, "new");
            Integer id = driverService.addDto(driverDto, new Driver());
            Driver driver = new Driver();
            driver.setId(id);
            driverUser.setDriver(driver);
            userService.addNewUser(driverUser);
        } catch (MessagingException e) {
            throw new MedragServiceException(e);
        }

    }

    /**
     * This method updates appropriate user, if driver's email has been updated.
     *
     * @param driver - updated driver.
     * @throws MedragServiceException - as usual.
     */
    @Override
    @Transactional
    public void updateDriver(DriverDto driver) throws MedragServiceException {
        User user = userService.getUserByUsername(driver.getPersonalNumber());
        if (user != null) {
            user.setEmail(driver.getEmail());
            userService.updateUser(user);
        }
        driverService.updateDtoStatus(driver, new Driver());
    }

    /**
     * Method returns all available and matching with the route time drives.
     *
     * @param cityId - id of the departure city.
     * @param time   - time, needed for the route.
     * @return - filtered list of matching drivers.
     * @throws MedragServiceException - get ready.
     */
    @Override
    @Transactional
    public List<DriverDto> getDriverList(Integer cityId, Integer time) throws MedragServiceException {

//        Get all free drivers in current city
        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver(),
                "CURRENT_CITY_ID", cityId.toString(), "STATE", "'READY_TO_ROUTE'");
        return getReadyDrivers(time, drivers);


    }

    /**
     * Method filters list of drivers by matching the time requirements of the next route.
     *
     * @param time    -time, required for the route.
     * @param drivers - list of all available drivers.
     * @return - filtered list of drivers.
     */
    @Override
    public List<DriverDto> getReadyDrivers(Integer time, List<DriverDto> drivers) {

        int timeLimit = 10560;
        ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));
        ZonedDateTime rim = now.with(TemporalAdjusters.firstDayOfNextMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long difference = ChronoUnit.MINUTES.between(now, rim);

        return drivers.stream().filter(d -> d.getWorkedTime() + time <= timeLimit ||
                difference < timeLimit - d.getWorkedTime()).collect(Collectors.toList());

    }

    /**
     * Method frees truck, if it's last driver goes to rest and sets "ON_SHIFT" statuses to rest drivers of current
     * brigade, if one of them takes control of the truck.
     *
     * @param driver - that driver.
     * @throws MedragServiceException - maybe.
     */
    @Override
    @Transactional
    public boolean changeDriverState(DriverDto driver) throws MedragServiceException {

//        Free truck part
        if (driver.getState().equals(DriverState.REST) || driver.getState().equals(DriverState.READY_TO_ROUTE)) {
            TruckDto truck = driver.getCurrentTruck();

            if (truck.getBrigade().size() == 1) {
                if (truck.getDestinationId() != null) {
                    return false;
                }
                truck.getBrigade().clear();
                truck.setStatus(TruckStatus.STAY_IDLE);
                truck.setManageable(Manageable.FALSE);
                truck.setDestinationId(null);
                truck.setDestinationName(null);
                truckService.updateDtoStatus(truck, new Truck());
            }
            driver.setCurrentTruck(null);
            driver.setDestinationId(null);
            driver.setDestinationName(null);

//        Change comrades statuses part
        } else {
            if (driver.getState().equals(DriverState.DRIVING)) {
                for (DriverDto comrade : driver.getCurrentTruck().getBrigade()) {
                    if (!comrade.equals(driver)) {
                        if (comrade.getDestinationId() != null) {
                            comrade.setState(DriverState.ON_SHIFT);
                        } else {
                            comrade.setState(DriverState.REST);
                            comrade.setCurrentTruck(null);
                        }
                        driverService.updateDtoStatus(comrade, new Driver());
                    }
                }
            }
        }

        driverService.updateDtoStatus(driver, new Driver());

        return true;
    }

    /**
     * Methods returns list of drivers, available to the route for the truck, including current truck brigade.
     *
     * @param truck - routing truck.
     * @param time  - time, required for the route.
     * @return - list of available drivers.
     * @throws MedragServiceException - if "getReadyDrivers" fails.
     */
    @Override
    @Transactional
    public List<DriverDto> getDriverListWithTruckBrigade(TruckDto truck, Integer time) throws MedragServiceException {

//        Get all free drivers in current city
        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver(),
                "CURRENT_CITY_ID", truck.getCityId().toString(), "STATE", "'READY_TO_ROUTE'");
        List<DriverDto> brigade = truck.getBrigade();
        drivers.addAll(0, brigade);

        return getReadyDrivers(time, drivers);
    }

    /**
     * This method launches a separate thread, that sleeps all day long, wakes up at 00:00, checks if it's not a
     * first day of the month ahd fells into sleep again. If it's suddenly a fist day of the month, thread sets to each
     * driver value of paid hours for last month, nulls workedHours and paidHours anf fells into sleep again. Nice job.
     */
    @PostConstruct
    public void nullWorkedHours() {

        Thread thread = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {

//                If this is a first day of month, set workedTime and paidTime to zeroes, set lastMonthHours for each driver
                ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));
                if (now.getDayOfMonth() == 1) {
                    try {
                        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver());
                        for (DriverDto driver : drivers) {
                            driver.setHoursLastMonth(driver.getPaidTime());
                            driver.setWorkedTime(0);
                            driver.setPaidTime(0);
                            driverService.updateDtoStatus(driver, new Driver());
                        }

                    } catch (MedragServiceException e) {
                        LOGGER.error("Error while nulling DriverWorkedHours in DriverPaidHoursNullerThread: {}", e);
                    }
                }

//                Sleep until next day will come
                now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));

                int h = now.getHour() * 3600;
                int m = now.getMinute() * 60;
                int s = now.getSecond();
                try {
                    TimeUnit.SECONDS.sleep(86400 - h - m - s + 1);
                } catch (InterruptedException e) {
                    LOGGER.error("Error while sleeping in DriverPaidHoursNullerThread: {}", e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
