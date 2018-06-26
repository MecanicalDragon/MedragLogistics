package net.medrag.controller.pub;

import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("truck")
public class TruckController {

    private TruckService truckService;

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping()
    public String returnView(Model model){
        model.addAttribute("truck", new TruckDto());
        return "public/truck";
    }

    @GetMapping("printTruck")
    public String printTruck(Model model){
        System.out.println(truckService.getTruckById(1));
        model.addAttribute("truck", new TruckDto());
        return "public/truck";
    }

    @PostMapping("dbint")
    public String post(@ModelAttribute("truck") TruckDto truck){
        truckService.addTruck(truck);
        return "public/truck";

    }

}