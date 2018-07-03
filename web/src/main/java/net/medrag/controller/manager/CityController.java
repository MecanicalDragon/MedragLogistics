package net.medrag.controller.manager;

import net.medrag.dto.CityDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller, that handles city.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-city")
public class CityController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public String returnView(Model model){
        model.addAttribute("city", new CityDto());
        return "manager/city";
    }

    @GetMapping("printCity")
    public String printCity(Model model){
        System.out.println(cityService.getDtoById(new CityDto(), new City(), 5));
        model.addAttribute("city", new CityDto());
        return "manager/managerPage";
    }

    @PostMapping("addCity")
    public String addCity(@ModelAttribute("city") CityDto city){
        cityService.addDto(city, new City());
        return "manager/managerPage";
    }

}
