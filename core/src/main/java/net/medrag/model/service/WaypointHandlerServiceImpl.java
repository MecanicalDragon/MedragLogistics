package net.medrag.model.service;

import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.dto.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.Set;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class WaypointHandlerServiceImpl implements WaypointHandlerService {

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private MailService mailService;

    private static final Logger logger = LoggerFactory.getLogger(WaypointHandlerService.class);

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
    public void compileRoute(WaypointDto load, CityDto destination){

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

    @Override
    @Transactional
    public void completeWaypoint(WaypointDto completedWP) {
        completedWP.setComplete("true");
        if(completedWP.getWayPointType().equals("LOAD")){
            completedWP.getCargo().setState("ON_BOARD");
        } else {

//            Set new current city to cargo, truck and brigade
            completedWP.getCargo().setCurrentCityId(completedWP.getCity().getId());
            completedWP.getCargo().setCurrentCityName(completedWP.getCity().getName());
            completedWP.getCurrentTruck().setCityId(completedWP.getCity().getId());
            completedWP.getCurrentTruck().setCityName(completedWP.getCity().getName());
            for (DriverDto driver : completedWP.getBrigade()){
                driver.setCityId(completedWP.getCity().getId());
                driver.setCityName(completedWP.getCity().getName());
            }

            if(completedWP.getCargo().getDestinationId().equals(completedWP.getCity().getId())){
                completedWP.getCargo().setState("DESTINATION");
                if (completedWP.getCargo().getOwner().getEmail() != null){
                    try {
                        mailService.sendDeliveredCargoEmail(completedWP.getCargo());
                    } catch (MessagingException e) {
                        logger.error("Could not send email to customer with delivery info about {}",completedWP.getCargo() );
                    }
                }

            } else {
                completedWP.getCargo().setState("TRANSIENT");
            }
        }

        waypointService.updateDtoStatus(completedWP, new Waypoint());

//        int completedWaypoints = 0;
//        for(WaypointDto wp : completedWP.getCurrentTruck().getRoute()){
//            if (wp.getComplete().equals("true")){
//                completedWaypoints++;
//            }
//        }
//        if (completedWaypoints == completedWP.getCurrentTruck().getRoute().size()){
//            completedWP.getCurrentTruck().setStatus("STAY_IDLE");
//            for (DriverDto driver : completedWP.getBrigade()){
//                driver.setState("READY_TO_ROUTE");
//            }
//        }
    }
}
