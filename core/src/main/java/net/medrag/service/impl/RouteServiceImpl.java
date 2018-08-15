package net.medrag.service.impl;

import net.medrag.domain.dto.*;
import net.medrag.domain.entity.City;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.entity.Waypoint;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.*;
import net.medrag.service.dto.api.CityService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import net.medrag.service.dto.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class RouteServiceImpl implements RouteService {

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
            driver.setState("PORTER");
            driver.setCurrentTruck(assignedTruck);
            driver.setDestinationId(destination.getId());
            driver.setDestinationName(destination.getName());
            driverService.updateDtoStatus(driver, new Driver());
            new Thread(() -> {
                try {
                    mailService.sendCompiledRouteMesaage(driver, destination.getName());
                } catch (MessagingException e) {
                }
            }).start();
        }
        assignedTruck.setStatus("IN_USE");
        assignedTruck.setBrigade(brigade);
        assignedTruck.setManageable("TRUE");
        assignedTruck.setDestinationId(destination.getId());
        assignedTruck.setDestinationName(destination.getName());
        truckService.updateDtoStatus(assignedTruck, new Truck());

//        Adding waypoints for every cargo in truckload
        for (CargoDto cargo : truckLoad) {
            innerCompileWaypoint(departure, destination, assignedTruck, cargo);
        }
    }

    /**
     * Method from the warehouse of the city, that sets waypoint complete.
     *
     * @param completedWP - waypoint, that becomes completed itself.
     */
    @Override
    @Transactional
    public Boolean completeWaypoint(WaypointDto completedWP) throws MedragServiceException {

        Boolean firstWpInCity = false;
        completedWP = waypointService.refreshDto(completedWP, new Waypoint());
        completedWP.setComplete("true");

//            Setting new current city to truck and brigade, if it's a first waypoint in a stack
        if (!completedWP.getCurrentTruck().getCityId().equals(completedWP.getCity().getId())) {
            completedWP.getCurrentTruck().setCityId(completedWP.getCity().getId());
            completedWP.getCurrentTruck().setCityName(completedWP.getCity().getName());
            if (completedWP.getCurrentTruck().getDestinationId().equals(completedWP.getCurrentTruck().getCityId())) {
                completedWP.getCurrentTruck().setDestinationId(null);
                completedWP.getCurrentTruck().setDestinationName(null);
                completedWP.getCurrentTruck().setManageable("FALSE");
            } else {
                if (completedWP.getCurrentTruck().getManageable().equals("UNCOMPLETED")) {
                    completedWP.getCurrentTruck().setManageable("NEED_TO_COMPLETE");
                } else {
                    if (completedWP.getCurrentTruck().getManageable().equals("SAVE_BRIGADE")) {
                        CityDto destination = cityService.getDtoById(new CityDto(), new City(), completedWP.getCurrentTruck().getDestinationId());
                        CityDto departure = completedWP.getCity();
                        Integer[] tripTime = directionsService.getTripTime(departure, destination);
                        List<DriverDto> brigade = driverHandlerService.getReadyDrivers(tripTime[1], completedWP.getCurrentTruck().getBrigade());
                        if (brigade.size() == Integer.valueOf(completedWP.getCurrentTruck().getBrigadeStr())) {
                            compileRouteForTruck(completedWP.getCurrentTruck(), brigade);
                        } else {
                            completedWP.getCurrentTruck().setManageable("NEED_TO_COMPLETE");
                        }
                    }
                }
            }

//             for brigade:
            for (DriverDto driver : completedWP.getCurrentTruck().getBrigade()) {
                driver.setCityId(completedWP.getCity().getId());
                driver.setCityName(completedWP.getCity().getName());
                driver.setState("PORTER");
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
                if (!wp.getCargo().getState().equals("DESTINATION")) {
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

    private void completeWaypointCargoPart(WaypointDto completedWP) {

//        If waypoint is LOAD type - setting cargo state 'ON_BOARD', and that's all
        if (completedWP.getWayPointType().equals("LOAD")) {
            completedWP.getCargo().setState("ON_BOARD");

//        If not - set new current city to cargo
        } else {
            completedWP.getCargo().setCurrentCityId(completedWP.getCity().getId());
            completedWP.getCargo().setCurrentCityName(completedWP.getCity().getName());
            completedWP.getCargo().setCurrentTruck(null);

//            If this city is final destination for cargo, set state 'DESTINATION'
            if (completedWP.getCargo().getDestinationId().equals(completedWP.getCity().getId())) {
                completedWP.getCargo().setState("DESTINATION");

//                Sending email to owner, if it's not null
                if (completedWP.getCargo().getOwner().getEmail() != null) {
                    new Thread(() -> {
                        try {
                            mailService.sendDeliveredCargoEmail(completedWP.getCargo());
                        } catch (MessagingException e) {
                        }
                    }).start();
                }

            } else {
//                If this city isn't a target city for cargo, set state 'TRANSIENT'
                completedWP.getCargo().setState("TRANSIENT");
            }
        }
    }

    @Override
    @Transactional
    public void compileUncompletedRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad, TruckDto assignedTruck, Boolean currentBrigade) throws MedragServiceException {

        assignedTruck.setManageable(currentBrigade ? "SAVE_BRIGADE" : "UNCOMPLETED");
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

    @Override
    @Transactional
    public void compileRouteForTruck(TruckDto assignedTruck, List<DriverDto> brigade) throws MedragServiceException {

//        Setting new statuses to old brigade
        for (DriverDto driver : assignedTruck.getBrigade()) {
            if (!brigade.contains(driver)) {
                driver.setDestinationId(null);
                driver.setDestinationName(null);
                driver.setCurrentTruck(assignedTruck);
                driver.setState("PORTER");
                driverService.updateDtoStatus(driver, new Driver());
            }
        }


//        Setting new statuses to new brigade
        for (DriverDto driver : brigade) {
            driver.setDestinationId(assignedTruck.getDestinationId());
            driver.setDestinationName(assignedTruck.getDestinationName());
            driver.setCurrentTruck(assignedTruck);
            driver.setState("PORTER");
            driverService.updateDtoStatus(driver, new Driver());
            new Thread(() -> {
                try {
                    mailService.sendCompiledRouteMesaage(driver, assignedTruck.getDestinationName());
                } catch (MessagingException e) {
                }
            }).start();
        }

//        Updating brigade in tuck
        assignedTruck.setManageable("TRUE");
        assignedTruck.setBrigade(brigade);
        truckService.updateDtoStatus(assignedTruck, new Truck());

////        Assign new brigade for destination waypoints
//        List<WaypointDto> wpsDestination = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "TRUCK_ID",
//                assignedTruck.getId().toString(), "CITY_ID", assignedTruck.getDestinationId().toString());
//        for (WaypointDto wp : wpsDestination) {
//            wp.setBrigade(brigade);
//            waypointService.updateDtoStatus(wp, new Waypoint());
//        }
//
////        Assign new brigade for departure waypoints
//        List<WaypointDto> wpsDeparture = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "TRUCK_ID",
//                assignedTruck.getId().toString(), "CITY_ID", assignedTruck.getCityId().toString(), "WP_TYPE", "'LOAD'");
//        for (WaypointDto wp : wpsDeparture) {
//            wp.setBrigade(brigade);
//            waypointService.updateDtoStatus(wp, new Waypoint());
//        }
    }

    private void innerCompileWaypoint(CityDto departure, CityDto destination, TruckDto assignedTruck, CargoDto cargo) throws MedragServiceException {
        cargo.setState("PREPARED");
        cargo.setCurrentTruck(assignedTruck);
        WaypointDto load = new WaypointDto();
        load.setCity(departure);
        load.setWayPointType("LOAD");
        load.setComplete("false");
        load.setCargo(cargo);
        load.setCurrentTruck(assignedTruck);
//        load.setBrigade(brigadeSet);

//            Create LOAD waypoint
        Integer id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());

//            Create UNLOAD waypoint
        load.setCity(destination);
        load.setWayPointType("UNLOAD");
        load.setId(null);
        id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());
        rabbitService.sendCargo(cargo);
    }
}
