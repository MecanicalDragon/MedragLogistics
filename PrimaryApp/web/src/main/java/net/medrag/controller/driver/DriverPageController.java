package net.medrag.controller.driver;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Waypoint;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.enums.DriverState;
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
 * Controller for handling requests to driverPage.jsp
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

    /**
     * First request to driverPage.
     *
     * @param model   - model
     * @param request - request
     * @return - driverPage.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
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

    /**
     * Changing driver's state post request
     *
     * @param option   - new state
     * @param request  - request
     * @param redirect - 'returnView' method
     * @return - driverPage.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping()
    public String changeState(@RequestParam String option, HttpServletRequest request, RedirectAttributes redirect) throws MedragControllerException {

        DriverDto driver = (DriverDto) request.getSession().getAttribute("sessionDriver");
        try {
            DriverState temp = driver.getState();
            int dest = 0;
            if (driver.getDestinationId() != null){
                dest = driver.getDestinationId();
            }
            driver = driverService.refreshDto(driver, new Driver());
            int newdest = 0;
            if (driver.getDestinationId() != null){
                newdest = driver.getDestinationId();
            }
            if (!temp.equals(driver.getState()) || dest != newdest) {
                redirect.addFlashAttribute("newState", true);
                return "redirect:/drv-main";
            }
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        driver.setState(DriverState.valueOf(option));

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