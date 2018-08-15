package net.medrag.controller.driver;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Waypoint;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.SecurityService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("/drv-main")
public class DriverPageController {

    private SecurityService securityService;

    private DriverService<DriverDto, Driver> driverService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private DriverHandlerService driverHandlerService;

    @Autowired
    public void setDriverHandlerService(DriverHandlerService driverHandlerService) {
        this.driverHandlerService = driverHandlerService;
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

        DriverDto driver;
        try {
            driver = driverService.getDtoByNaturalId(new DriverDto(), new Driver(), securityService.getUsernameOfSignedInUser());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (driver.getCurrentTruck() != null) {
            try {
                List<WaypointDto> waypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "COMPLETE", "false",
                        "TRUCK_ID", driver.getCurrentTruck().getId().toString());
                model.addAttribute("wps", waypoints);
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        }

        request.getSession().setAttribute("sessionDriver", driver);
        model.addAttribute("driver", driver);

        return "driver/driverPage";
    }

    @PostMapping()
    public String changeState(@RequestParam String option, HttpServletRequest request, RedirectAttributes redirect) throws MedragControllerException {

        DriverDto driver = (DriverDto) request.getSession().getAttribute("sessionDriver");
        driver.setState(option);

        if (driver.getCurrentTruck() != null &&
                (option.equals("REST") || option.equals("READY_TO_ROUTE") || option.equals("DRIVING"))) {
            try {
                if (!driverHandlerService.changeDriverState(driver)) {
                    redirect.addFlashAttribute("standalone", true);
                    return "redirect:/drv-main";
                }
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        }

        try {
            driverService.updateDtoStatus(driver, new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        redirect.addFlashAttribute("changed", true);
        return "redirect:/drv-main";
    }

}