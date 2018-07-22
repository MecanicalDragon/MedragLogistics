package net.medrag.model.service;

import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.*;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;

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

    @Override
    @Transactional
    public void compileRoute(CityDto departure, CityDto destination, List<CargoDto> truckLoad,
                             TruckDto assignedTruck, Set<DriverDto> brigade) throws MedragServiceException {

//        Setting new statuses to truck and drivers
        for (DriverDto driverDto : brigade) {
            driverDto.setState("PORTER");
            driverDto.setCurrentTruck(assignedTruck);
        }
        assignedTruck.setStatus("IN_USE");
        assignedTruck.setBrigade(brigade);

        for (DriverDto driver : brigade) {
            driverService.updateDtoStatus(driver, new Driver());
            try {
                mailService.sendCompiledRouteMesaage(driver, destination);
            } catch (MessagingException e) {
            }
        }

//        Adding waypoints for every cargo in truckload
        for (CargoDto cargo : truckLoad) {
            cargo.setState("PREPARED");
            WaypointDto load = new WaypointDto();
            load.setCity(departure);
            load.setWayPointType("LOAD");
            load.setComplete("false");
            load.setCargo(cargo);
            load.setCurrentTruck(assignedTruck);
            load.setBrigade(brigade);

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
        }

    }


    /**
     * Method from the warehouse of the city, that sets waypoint complete.
     *
     * @param completedWP - waypoint, that becomes completed itself.
     */
    @Override
    @Transactional
    public void completeWaypoint(WaypointDto completedWP) throws MedragServiceException {

        completedWP.setComplete("true");

//        If waypoint is LOAD type - setting cargo state 'ON_BOARD', and that's all
        if (completedWP.getWayPointType().equals("LOAD")) {
            completedWP.getCargo().setState("ON_BOARD");


        } else {
//            If not - set new current city to cargo
            completedWP.getCargo().setCurrentCityId(completedWP.getCity().getId());
            completedWP.getCargo().setCurrentCityName(completedWP.getCity().getName());

//            Setting new current city to truck and brigade, if it's a first waypoint in a stack
            if (!completedWP.getCurrentTruck().getCityId().equals(completedWP.getCity().getId())) {
                completedWP.getCurrentTruck().setCityId(completedWP.getCity().getId());
                completedWP.getCurrentTruck().setCityName(completedWP.getCity().getName());
//                 for brigade:
                for (DriverDto driver : completedWP.getBrigade()) {
                    driver.setCityId(completedWP.getCity().getId());
                    driver.setCityName(completedWP.getCity().getName());
                    driver.setState("PORTER");
                    driverService.updateDtoStatus(driver, new Driver());
                }
            }

//            If this city is final destination for cargo, set state 'DESTINATION'
            if (completedWP.getCargo().getDestinationId().equals(completedWP.getCity().getId())) {
                completedWP.getCargo().setState("DESTINATION");

//                Sending email to owner, if it's not null
                if (completedWP.getCargo().getOwner().getEmail() != null) {
                    try {
                        mailService.sendDeliveredCargoEmail(completedWP.getCargo());
                    } catch (MessagingException e) {
                    }
                }

            } else {
//                If this city isn't a target city for cargo, set state 'TRANSIENT'
                completedWP.getCargo().setState("TRANSIENT");
            }

        }

//        update waypoint status
        waypointService.updateDtoStatus(completedWP, new Waypoint());

    }
}
