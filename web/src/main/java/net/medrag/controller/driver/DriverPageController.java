package net.medrag.controller.driver;

import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.SecurityService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
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
    public String returnView(HttpServletRequest request) {

        DriverDto driver = driverService.getDtoByNaturalId(new DriverDto(), new Driver(), securityService.getUsernameOfSignedInUser());
        List<WaypointDto> waypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "COMPLETE", "false");
        request.getSession().setAttribute("driver", driver);
        request.getSession().setAttribute("wps", waypoints);

        return "driver/driverPage";
    }

    @GetMapping("changeState/{option}")
    public String changeState(@PathVariable String option, HttpServletRequest request) {

        DriverDto driver = (DriverDto) request.getSession().getAttribute("driver");
        driver.setState(option);
        driverService.updateDtoStatus(driver, new Driver());

        return "driver/driverPage";
    }
}
