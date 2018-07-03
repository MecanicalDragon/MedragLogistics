package net.medrag.controller.manager;

import net.medrag.dto.DriverDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller, that handles driver.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-driver")
public class DriverController {

    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("printD")
    public String printTruck(Model model){
        System.out.println(driverService.getDtoById(new DriverDto(), new Driver(), 1));
        model.addAttribute("driver", new DriverDto());
        return "manager/managerPage";
    }

    @GetMapping()
    public String returnView(Model model){
        model.addAttribute("driver", new DriverDto());
        return "manager/driver";
    }

    @PostMapping("addDriver")
    public String addDriver(@ModelAttribute("driver") DriverDto driver){
        driverService.addDto(driver, new Driver());
        return "public/managerPage";
    }

}