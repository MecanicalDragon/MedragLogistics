package net.medrag.controller.pub;

import net.medrag.model.dto.CityDto;
import net.medrag.model.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("city")
public class CityController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public String returnView(Model model){
        model.addAttribute("city", new CityDto());
        return "public/city";
    }

    @GetMapping("printCity")
    public String printCity(Model model){
        System.out.println(cityService.getCityById(1));
        model.addAttribute("city", new CityDto());
        return "public/city";
    }

    @PostMapping("dbin")
    public String addCity(@ModelAttribute("city") CityDto city){
        cityService.addCity(city);
        return "public/city";
    }

}
