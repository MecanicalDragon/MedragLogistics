package net.medrag.controller.pub;

import net.medrag.model.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("truck")
public class TruckController {

    private TruckService truckService;

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping()
    public String returnView(){

        return "public/truck";
    }

    @GetMapping("printTruck")
    public String printTruck(){

        System.out.println(truckService.getTruckById(1));

        return "public/truck";
    }

    @PostMapping("dbint")
    public String post(@RequestParam String number,
                       @RequestParam String brigade,
                       @RequestParam String capacity,
                       @RequestParam String state,
                       @RequestParam String currentCity){

        truckService.addTruck(number, brigade, capacity, state, currentCity);

        return "public/truck";

    }

}