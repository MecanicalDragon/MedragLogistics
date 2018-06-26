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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String returnView(){

        return "public/driver";
    }

    // bind to DTO in JSP

    @PostMapping("dbind")
    public String addDriver(
            @RequestParam String name,
            @RequestParam String number,
            @RequestParam String surname,
            @RequestParam String time,
            @RequestParam String state,
            @RequestParam String city,
            @RequestParam String truck){

        driverService.addDriver(name, number, surname, time, state, city, truck);

        return "public/driver";

    }

}