package net.medrag.controller.pub;

import net.medrag.model.domain.enums.DriverState;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.CityService;
import net.medrag.model.service.DriverService;
import net.medrag.model.service.TruckService;
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
    public String printTruck(){
        System.out.println(driverService.getDriverById(1));
        return "public/driver";
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