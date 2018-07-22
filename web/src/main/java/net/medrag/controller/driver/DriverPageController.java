package net.medrag.controller.driver;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.SecurityService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.TruckService;
import net.medrag.model.service.dto.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("drv-main")
public class DriverPageController {

    private SecurityService securityService;

    private DriverService<DriverDto, Driver> driverService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping()
    public String returnView(Model model, HttpServletRequest request) throws MedragControllerException {

        DriverDto driver = null;
        try {
            driver = driverService.getDtoByNaturalId(new DriverDto(), new Driver(), securityService.getUsernameOfSignedInUser());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        List<WaypointDto> waypoints = null;
        if (driver.getCurrentTruck() != null) {
            try {
                waypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "COMPLETE", "false", "TRUCK_ID", driver.getCurrentTruck().getId().toString());
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
            model.addAttribute("wps", waypoints);
        }

        request.getSession().setAttribute("sessionDriver", driver);
        model.addAttribute("driver", driver);

        return "driver/driverPage";
    }

    @GetMapping("changeState/{option}")
    public String changeState(@PathVariable String option, HttpServletRequest request) throws MedragControllerException {

        DriverDto driver = (DriverDto) request.getSession().getAttribute("sessionDriver");
        driver.setState(option);
        if (option.equals("REST") || option.equals("READY_TO_ROUTE")) {
            TruckDto truck = driver.getCurrentTruck();
            if (truck != null) {
                try {
                    truckService.refreshDto(truck, new Truck());
                } catch (MedragServiceException e) {
                    throw new MedragControllerException(e);
                }
                if (truck.getBrigade().size() == 1) {
                    truck.getBrigade().clear();
                    truck.setStatus("STAY_IDLE");
                    try {
                        truckService.updateDtoStatus(truck, new Truck());
                    } catch (MedragServiceException e) {
                        throw new MedragControllerException(e);
                    }
                }
            }
            driver.setCurrentTruck(null);
        }
        try {
            driverService.updateDtoStatus(driver, new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../../drv-main";
    }

}