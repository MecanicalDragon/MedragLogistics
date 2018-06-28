package net.medrag.controller.manager;

import net.medrag.dto.DriverDto;
import net.medrag.model.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("driver")
public class DriverController {

    private DriverService driverService;

    @Autowired
    public void setDriverService(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("printD")
    public String printTruck(Model model){
        System.out.println(driverService.getDriverById(1));
        model.addAttribute("driver", new DriverDto());
        return "manager/driver";
    }

    @GetMapping()
    public String returnView(Model model){
        model.addAttribute("driver", new DriverDto());
        return "public/driver";
    }

    @PostMapping("dbind")
    public String addDriver(@ModelAttribute("driver") DriverDto driver){
        driverService.addDriver(driver);
        return "public/driver";
    }

}