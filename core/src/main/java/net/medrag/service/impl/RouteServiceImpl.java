package net.medrag.service.impl;

import net.medrag.domain.dto.*;
import net.medrag.domain.entity.City;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.entity.Waypoint;
import net.medrag.domain.enums.*;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.*;
import net.medrag.service.dto.api.CityService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import net.medrag.service.dto.api.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

/**
 * Routing service. Most bulky service in the app.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class RouteServiceImpl implements RouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverHandlerServiceImpl.class);

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private MailService mailService;

    private DriverService<DriverDto, Driver> driverService;

    private TruckService<TruckDto, Truck> truckService;

    private RabbitService rabbitService;

    private DirectionsService directionsService;

    private CityService<CityDto, City> cityService;

    private DriverHandlerService driverHandlerService;

    @Autowired
    public void setDriverHandlerService(DriverHandlerService driverHandlerService) {
        this.driverHandlerService = driverHandlerService;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setDirectionsService(DirectionsService directionsService) {
        this.directionsService = directionsService;
    }

    @Autowired
    public void setRabbitService(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    /**
     * Method of logistics, that assigns new route.
     *
     * @param departure     - departure city
     * @param destination   - destination city
     * @param truckLoad     - cargoes list
     * @param assignedTruck - try to guess
     * @param brigade       - drivers, assigned to this route
     * @throws MedragServiceException - yes, throws.
     */
    @Override
    @Transactional
    public void compileRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad,
                             TruckDto assignedTruck, List<DriverDto> brigade) throws MedragServiceException {

//        Setting new statuses to truck and drivers
        for (DriverDto driver : brigade) {
            driver.setState(DriverState.PORTER);
            driver.setCurrentTruck(assignedTruck);
            driver.setDestinationId(destination.getId());
            driver.setDestinationName(destination.getName());
            driverService.updateDtoStatus(driver, new Driver());
            new Thread(() -> {
                try {
                    mailService.sendCompiledRouteMesaage(driver, destination.getName());
                } catch (MessagingException e) {
                    LOGGER.error("Could not send email to driver with personal number {} about his assigning to the route. {}",
                            driver.getPersonalNumber(), e);
                }
            }).start();
        }
        assignedTruck.setStatus(TruckStatus.IN_USE);
        assignedTruck.setManageable(Manageable.TRUE);
        assignedTruck.setBrigade(brigade);
        assignedTruck.setDestinationId(destination.getId());
        assignedTruck.setDestinationName(destination.getName());
        truckService.updateDtoStatus(assignedTruck, new Truck());

//        Adding waypoints for every cargo in truckload
        for (CargoDto cargo : truckLoad) {
            innerCompileWaypoint(departure, destination, assignedTruck, cargo);
        }
    }

    /**
     * Method from the warehouse of the city, that sets waypoint completed.
     *
     * @param completedWP - waypoint, that becomes completed itself.
     */
    @Override
    @Transactional
    public Boolean completeWaypoint(WaypointDto completedWP) throws MedragServiceException {

        Boolean firstWpInCity = false;
        completedWP = waypointService.refreshDto(completedWP, new Waypoint());
        completedWP.setComplete("true");

//            Setting new current city to truck, if it's a first waypoint in this city:
        if (!completedWP.getCurrentTruck().getCityId().equals(completedWP.getCity().getId())) {
            completedWP.getCurrentTruck().setCityId(completedWP.getCity().getId());
            completedWP.getCurrentTruck().setCityName(completedWP.getCity().getName());

//            If city of waypoint is also destination city for the truck
            if (completedWP.getCurrentTruck().getDestinationId().equals(completedWP.getCurrentTruck().getCityId())) {
                completedWP.getCurrentTruck().setDestinationId(null);
                completedWP.getCurrentTruck().setDestinationName(null);
                completedWP.getCurrentTruck().setManageable(Manageable.FALSE);
            } else {

//                If truck was "UNCOMPLETED"
                if (completedWP.getCurrentTruck().getManageable().equals(Manageable.UNCOMPLETED)) {
                    completedWP.getCurrentTruck().setManageable(Manageable.NEED_TO_COMPLETE);
                } else {

//                    If it was a try to save current brigade for the next route
                    if (completedWP.getCurrentTruck().getManageable().equals(Manageable.SAVE_BRIGADE)) {
                        CityDto destination = cityService.getDtoById(new CityDto(), new City(), completedWP.getCurrentTruck().getDestinationId());
                        CityDto departure = completedWP.getCity();
                        Integer[] tripTime = directionsService.getTripTime(departure, destination);
                        List<DriverDto> brigade = driverHandlerService.getReadyDrivers(tripTime[1], completedWP.getCurrentTruck().getBrigade());

//                        If success
                        if (brigade.size() == Integer.valueOf(completedWP.getCurrentTruck().getBrigadeStr())) {
                            compileRouteForTruck(completedWP.getCurrentTruck(), brigade);
                        } else {

//                            If not
                            completedWP.getCurrentTruck().setManageable(Manageable.NEED_TO_COMPLETE);
                        }
                    }
                }
            }

//             Setting new current city for brigade:
            for (DriverDto driver : completedWP.getCurrentTruck().getBrigade()) {
                driver.setCityId(completedWP.getCity().getId());
                driver.setCityName(completedWP.getCity().getName());
                driver.setState(DriverState.PORTER);

//                Nulling destination, if driver is on it
                if (driver.getDestinationId() != null && driver.getDestinationId().equals(driver.getCityId())) {
                    driver.setDestinationId(null);
                    driver.setDestinationName(null);
                }
                driverService.updateDtoStatus(driver, new Driver());
            }

//            Set new current city for all transit cargoes
            List<WaypointDto> wps = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "WP_TYPE",
                    "'LOAD'", "COMPLETE", "true", "TRUCK_ID", completedWP.getCurrentTruck().getId().toString());
            for (WaypointDto wp : wps) {
                if (!wp.getCargo().getState().equals(CargoState.DESTINATION)) {
                    wp.getCargo().setCurrentCityId(completedWP.getCity().getId());
                    waypointService.updateDtoStatus(wp, new Waypoint());
                }
            }

            firstWpInCity = true;
        }

        completeWaypointCargoPart(completedWP);

//        update waypoint status
        waypointService.updateDtoStatus(completedWP, new Waypoint());

//        send update to watcher
        rabbitService.sendCargo(completedWP.getCargo());

        return firstWpInCity;
    }

    /**
     * Method from logistics. Compiles route for truck, that's already in use.
     *
     * @param departure - current destination city.
     * @param destination - new destination city.
     * @param truckLoad - cargoes in truck.
     * @param assignedTruck - managed truck.
     * @param currentBrigade - current brigade.
     * @throws MedragServiceException - looks like it cans.
     */
    @Override
    @Transactional
    public void compileUncompletedRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad, TruckDto assignedTruck, Boolean currentBrigade) throws MedragServiceException {

        assignedTruck.setManageable(currentBrigade ? Manageable.SAVE_BRIGADE : Manageable.UNCOMPLETED);
        assignedTruck.setDestinationId(destination.getId());
        assignedTruck.setDestinationName(destination.getName());
        truckService.updateDtoStatus(assignedTruck, new Truck());

//        Adding waypoints for every cargo in truckload
        for (CargoDto cargo : truckLoad) {
            if (cargo.getCurrentCityId().equals(departure.getId())) {
                innerCompileWaypoint(departure, destination, assignedTruck, cargo);
            }
        }

        List<WaypointDto> wps = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "TRUCK_ID",
                assignedTruck.getId().toString(), "WP_TYPE", "'UNLOAD'", "CITY_ID", departure.getId().toString());
        for (WaypointDto wp : wps) {
            if (truckLoad.contains(wp.getCargo())) {
                wp.setCity(destination);
//                wp.setBrigade(null);
                waypointService.updateDtoStatus(wp, new Waypoint());
            }
        }
    }

    /**
     * Method assigns truck brigade for a new route: changes statuses of current drivers and sets to new.
     *
     * @param assignedTruck - managed truck.
     * @param brigade - new brigade.
     * @throws MedragServiceException - don't know.
     */
    @Override
    @Transactional
    public void compileRouteForTruck(TruckDto assignedTruck, List<DriverDto> brigade) throws MedragServiceException {

//        Setting new statuses to old brigade
        for (DriverDto driver : assignedTruck.getBrigade()) {
            if (!brigade.contains(driver)) {
                driver.setDestinationId(null);
                driver.setDestinationName(null);
                driver.setCurrentTruck(assignedTruck);
                driver.setState(DriverState.PORTER);
                driverService.updateDtoStatus(driver, new Driver());
            }
        }

//        Setting new statuses to new brigade
        for (DriverDto driver : brigade) {
            driver.setDestinationId(assignedTruck.getDestinationId());
            driver.setDestinationName(assignedTruck.getDestinationName());
            driver.setCurrentTruck(assignedTruck);
            driver.setState(DriverState.PORTER);
            driverService.updateDtoStatus(driver, new Driver());
            new Thread(() -> {
                try {
                    mailService.sendCompiledRouteMesaage(driver, assignedTruck.getDestinationName());
                } catch (MessagingException e) {
                    LOGGER.error("Exception happened in rerouting brigade of truck {}. {}",
                            assignedTruck.getRegNumber(), e);
                }
            }).start();
        }

//        Updating brigade in tuck
        assignedTruck.setManageable(Manageable.TRUE);
        assignedTruck.setBrigade(brigade);
        truckService.updateDtoStatus(assignedTruck, new Truck());

    }

    /**
     * Separated method, that compiles pair of waypoints.
     *
     * @param departure - city of LOAD waypoint.
     * @param destination - city of UNLOAD waypoint.
     * @param assignedTruck - assigned truck.
     * @param cargo - waypoint's cargo.
     * @throws MedragServiceException - i'm afraid to print "be sure"
     */
    private void innerCompileWaypoint(CityDto departure, CityDto destination, TruckDto assignedTruck, CargoDto cargo) throws MedragServiceException {
        cargo.setState(CargoState.PREPARED);
        cargo.setCurrentTruck(assignedTruck);
        WaypointDto load = new WaypointDto();
        load.setCity(departure);
        load.setWayPointType(WaypointType.LOAD);
        load.setComplete("false");
        load.setCargo(cargo);
        load.setCurrentTruck(assignedTruck);

//            Create LOAD waypoint
        Integer id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());

//            Create UNLOAD waypoint
        load.setCity(destination);
        load.setWayPointType(WaypointType.UNLOAD);
        load.setId(null);
        id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());
        rabbitService.sendCargo(cargo);
    }

    /**
     * Separated method for updating cargo of completed waypoint.
     *
     * @param completedWP - completed waypoint.
     */
    private void completeWaypointCargoPart(WaypointDto completedWP) {

//        If waypoint is LOAD type - setting cargo state 'ON_BOARD', and that's all
        if (completedWP.getWayPointType().equals(WaypointType.LOAD)) {
            completedWP.getCargo().setState(CargoState.ON_BOARD);

//        If not - set new current city to cargo
        } else {
            completedWP.getCargo().setCurrentCityId(completedWP.getCity().getId());
            completedWP.getCargo().setCurrentCityName(completedWP.getCity().getName());
            completedWP.getCargo().setCurrentTruck(null);

//            If this city is final destination for cargo, set state 'DESTINATION'
            if (completedWP.getCargo().getDestinationId().equals(completedWP.getCity().getId())) {
                completedWP.getCargo().setState(CargoState.DESTINATION);

//                Sending email to owner, if it's not null
                if (completedWP.getCargo().getOwner().getEmail() != null) {
                    new Thread(() -> {
                        try {
                            mailService.sendDeliveredCargoEmail(completedWP.getCargo());
                        } catch (MessagingException e) {
                            LOGGER.error("Could not send email with cargo 'DELIVERED' state to customer with document {}. {}"
                            , completedWP.getCargo().getOwner().getPassport(), e);
                        }
                    }).start();
                }
            } else {

//                If this city isn't a target city for cargo, set state 'TRANSIENT'
                completedWP.getCargo().setState(CargoState.TRANSIENT);
            }
        }
    }
}
