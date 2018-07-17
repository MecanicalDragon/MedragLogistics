package net.medrag.controller.driver;

import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.SecurityService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.TruckService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    public String returnView(Model model, HttpServletRequest request) {

        DriverDto driver = driverService.getDtoByNaturalId(new DriverDto(), new Driver(), securityService.getUsernameOfSignedInUser());
        List<WaypointDto> waypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "COMPLETE", "false");

        request.getSession().setAttribute("sessionDriver", driver);
        model.addAttribute("driver", driver);
        model.addAttribute("wps", waypoints);

        int workedHours = driver.getWorkedTime() / 60;
        int workedMinutes = driver.getWorkedTime() % 60;
        String workedTime = String.format("%d:%d", workedHours, workedMinutes);

        int paidHours = driver.getPaidTime() / 60;
        int paidMinutes = driver.getPaidTime() % 60;
        String paidTime = String.format("%d:%d", paidHours, paidMinutes);

        model.addAttribute("workedTime", workedTime);
        model.addAttribute("paidTime", paidTime);

        return "driver/driverPage";
    }

    @GetMapping("changeState/{option}")
    public String changeState(@PathVariable String option, HttpServletRequest request) {

        DriverDto driver = (DriverDto) request.getSession().getAttribute("sessionDriver");
        driver.setState(option);
        if (option.equals("REST") || option.equals("READY_TO_ROUTE")){
            driver.setCurrentTruck(null);
        }
        driverService.updateDtoStatus(driver, new Driver());

        return "redirect: ../../drv-main";
    }

    @GetMapping("freeTruck")
    public String freeTruck(HttpServletRequest request){

        DriverDto driver = (DriverDto)request.getSession().getAttribute("sessionDriver");
        TruckDto truck = driver.getCurrentTruck();
        truck.setStatus("STAY_IDLE");
        truckService.updateDtoStatus(truck, new Truck());

        return "redirect: ../drv-main";
    }
}
