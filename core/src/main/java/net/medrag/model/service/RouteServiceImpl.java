package net.medrag.model.service;

import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

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

    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);

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
    public void compileRoute(WaypointDto load, CityDto destination)throws MedragServiceException {

        for (DriverDto driver : load.getBrigade()) {
            driverService.updateDtoStatus(driver, new Driver());
        }

        Integer id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());

        load.setCity(destination);
        load.setWayPointType("UNLOAD");
        load.setId(null);
        id = waypointService.addDto(load, new Waypoint());
        load.setId(id);
        waypointService.updateDtoStatus(load, new Waypoint());

    }


    /**
     * Method from the warehouse of the city, that sets waypoint complete.
     * @param completedWP - waypoint, that becomes completed itself.
     */
    @Override
    @Transactional
    public void completeWaypoint(WaypointDto completedWP) throws MedragServiceException{

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
                        logger.error("Could not send email to customer with delivery info about {}", completedWP.getCargo());
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
